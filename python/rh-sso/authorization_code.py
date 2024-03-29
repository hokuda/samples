#!/usr/bin/python3
# -*- coding: utf-8 -*-

import sys
import requests
from requests import Session
from html.parser import HTMLParser
from urllib.parse import quote

# https://github.com/keycloak/keycloak-documentation/blob/master/server_admin/topics/clients/client-oidc.adoc
"""
OAuth 2.0 Mutual TLS Certificate Bound Access Tokens Enabled

Mutual TLS binds an access token and a refresh token with a client certificate exchanged during TLS handshake. This prevents an attacker who finds a way to steal these tokens from exercising the tokens. This type of token is called a holder-of-key token. Unlike bearer tokens, the recipient of a holder-of-key token can verify whether the sender of the token is legitimate.

If the following conditions are satisfied on a token request, {project_name} will bind an access token and a refresh token with a client certificate and issue them as holder-of-key tokens. If all conditions are not met, {project_name} rejects the token request.

The feature is turned on

A token request is sent to the token endpoint in an authorization code flow or a hybrid flow

On TLS handshake, {project_name} requests a client certificate and a client send its client certificate

On TLS handshake, {project_name} successfully verifies the client certificate

To enable mutual TLS in {project_name}, see Enable mutual SSL in WildFly.

In the following cases, {project_name} will verify the client sending the access token or the refresh token; if verification fails, {project_name} rejects the token.

A token refresh request is sent to the token endpoint with a holder-of-key refresh token

A UserInfo request is sent to UserInfo endpoint with a holder-of-key access token

A logout request is sent to Logout endpoint with a holder-of-key refresh token

Please see Mutual TLS Client Certificate Bound Access Tokens in the OAuth 2.0 Mutual TLS Client Authentication and Certificate Bound Access Tokens for more details.

Warning
None of the keycloak client adapters currently support holder-of-key token verification. Instead, keycloak adapters currently treat access and refresh tokens as bearer tokens.
"""

PROTOCOL='http'
HOST='localhost'
PORT='8080'
REALM='master'
USER='user1'
PASS='password'
CLIENT='client1'
REDIRECT_URI='http://localhost:8080/client1'

# form のパラメータを取得するクラス
class ExtractFormParser(HTMLParser):
    
    def __init__(self):
        HTMLParser.__init__(self)
        self.hiddens = []
        self.action = ""
        self.method = ""
        self.text = ""
        return

    def handle_starttag(self, tag, attrs):
        if tag == "form":
            attrs = dict(attrs)
            if "action" in attrs:
                self.action = attrs["action"]
            if "method" in attrs:
                self.method = attrs["method"]
        if tag == "input":
#            print attrs
            attrs = dict(attrs)
            if "type" in attrs:
                if attrs["type"] == "hidden":
                    print(attrs)
                    #self.hiddens.append((attrs["name"], attrs["value"]))
                    return

    def handle_data(self, data):
        if self.action:
            self.text += data
            return


def debug(arg):
    sys.stderr.write(str(arg))
    sys.stderr.write('\n')
    sys.stderr.flush()
    return


### main ###


############
# ブラウザで RP にアクセスして返ってきた 302 に応答して IDP にリダイレクト -> login form ゲット
############
print(" >> 1. 認可コード発行・・・認可コードA")
redirect_url = "{}://{}:{}/auth/realms/{}/protocol/openid-connect/auth?response_type=code&client_id={}&redirect_uri={}&state=41b501fa-89ca-46d8-aa3b-961d9badd77a&login=true&scope=openid".format(
    PROTOCOL,
    HOST,
    PORT,
    REALM,
    CLIENT,
    quote(REDIRECT_URI)
    )

cacert = './cacert.pem'
session = Session()
session.cert = './concat.pem'
resp = session.get(redirect_url, verify=cacert)

print("body: ", resp.text)
formparser = ExtractFormParser()
formparser.feed(resp.text)
formparser.close()
cookies = resp.cookies
action = formparser.action
method = formparser.method
hiddens = formparser.hiddens
debug(resp.status_code)
debug(resp.text)
debug('URL to submit form: ' + action)


# submit username/password -> get authentication code
#data={'username':'test1', 'password':'test1'}
data={'username':USER, 'password':PASS}
r = session.post(action, data=data, cookies=cookies, allow_redirects=False)
#print("----------------", r.headers)
#print(r.text)
debug('Location: ' + r.headers['Location'])
from urllib.parse import urlparse
parsed_url = urlparse(r.headers['Location'])
debug('parsed_url = ' + str(parsed_url))
from urllib.parse import parse_qs

# クッキーを保存する
shared_cookies = r.cookies

# AUTHORIZATION CODE 
the_code_A = parse_qs(parsed_url.query)['code'][0]

debug('＃＃＃ 認可コード = ' + the_code_A)
#print('＃＃＃ 認可コード = ' + the_code_A)
debug('＃＃＃ shared_cookies = ')
print(r.cookies.get_dict())

############
# sleep
############

#SLEEP_IN_SEC = 59 # the code will be available, if Client login timeout==60
#SLEEP_IN_SEC = 61 # the code will be expired, if Client login timeout==60
SLEEP_IN_SEC = 1 # the code will be expired, if Client login timeout==60

import time
for i in range(SLEEP_IN_SEC):
    time.sleep(1)
    debug(i)
    continue

    
############
# get token A 
############
print(" >> 2. トークンの取得")

client_id = "mtls"
client_secret = "64bcedc6-727b-46f5-81b4-0d2119130793"

import base64

#b64cred = base64.b64encode("{0}:{1}".format(client_id, client_secret).encode('utf-8')).decode('utf-8')
b64cred = base64.b64encode("{0}:{1}".format(CLIENT, 'secret').encode('utf-8')).decode('utf-8')

url = '{}://{}:{}/auth/realms/{}/protocol/openid-connect/token'.format(
    PROTOCOL,
    HOST,
    PORT,
    REALM,
    )
data = {'grant_type':'authorization_code',
        'code':the_code_A,
        'redirect_uri':REDIRECT_URI,
        'client_session_state':'IpgukFrjdsR03VxYZJ5v_6RnE3KOLsgqT_PhZwXj',
        'client_session_host':'dhcp-193-78.nrt.redhat.com',
        'scope':'offline_access'
        }
#headers = {'Authorization':'Basic YXBwLWpzcDpiY2U1ODE2ZC05OGM0LTQwNGYtYTE4ZC1iY2M1Y2IwMDVjNzk='}
headers = {'Authorization':'Basic {0}'.format(b64cred)}
#headers = {}
r = session.post(url, data=data, headers=headers)
print("=============== response from token endpoint: ", r.text)
import json
js = json.loads(r.text)
refresh_token_A = js['refresh_token']
access_token_A = js['access_token']
print("############access token A ############= ", access_token_A)
print("############refresh token A ############= ", refresh_token_A)


sys.exit(0)




############
# introspection の実行
############
print("############access token A introspect before logout############")
url = 'http://localhost:8180/auth/realms/master/protocol/openid-connect/token/introspect'
data = {
        'token':access_token_A,
        }
headers = {'Authorization':'Basic {0}'.format(b64cred),
           'Content-Type':'application/x-www-form-urlencoded'
            }
r = requests.post(url, data=data, headers=headers)
print(r.text)

print("############refresh_token_A introspect before logout############")
url = 'http://localhost:8180/auth/realms/master/protocol/openid-connect/token/introspect'
data = {
        'token':refresh_token_A,
        }
headers = {'Authorization':'Basic {0}'.format(b64cred),
           'Content-Type':'application/x-www-form-urlencoded'
            }
r = requests.post(url, data=data, headers=headers)
print(r.text)


############
# logout の実行
############
print(" >> 3. KEYCLOAK_IDENTITY を使ったログアウト処理")
redirect_url = "http://localhost:8180/auth/realms/master/protocol/openid-connect/logout?redirect_uri=http://localhost:8080/app-jsp/protected.jsp"
headers = {'Authorization':'Basic {0}'.format(b64cred)}

# クッキーをつけてリクエストする
resp = requests.get(redirect_url, headers=headers, cookies=shared_cookies, allow_redirects=False)
#resp = requests.get(redirect_url, headers=headers, cookies=d)
#print(resp.text)
#import json
#js = json.loads(resp.text)
#refresh_token_A = js['refresh_token']
#access_token_A = js['access_token']
#print("############refresh token A ############= ", refresh_token_A)
#print("############access token A ############= ", access_token_A)


############
# introspection の実行
############
print("############access token A introspect after logout############")
url = 'http://localhost:8180/auth/realms/master/protocol/openid-connect/token/introspect'
data = {
        'token':access_token_A,
        }
headers = {'Authorization':'Basic {0}'.format(b64cred),
           'Content-Type':'application/x-www-form-urlencoded'
            }
r = requests.post(url, data=data, headers=headers)
print(r.text)

print("############refresh_token_A introspect after logout############")
url = 'http://localhost:8180/auth/realms/master/protocol/openid-connect/token/introspect'
data = {
        'token':refresh_token_A,
        }
headers = {'Authorization':'Basic {0}'.format(b64cred),
           'Content-Type':'application/x-www-form-urlencoded'
            }
r = requests.post(url, data=data, headers=headers)
print(r.text)

