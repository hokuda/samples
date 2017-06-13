preparation to run the reproducer
----
1. untar server_socket.tar.gz
2. cd server_socket
3. copy proxy.conf to /etc/httpd/conf.d/
4. restart httpd



senario 1.
----
1. start server1.py which sends RST against any request without sending response body.

```
[hokuda@dhcp-193-78 server_socket]$ ./server1.py
```

2. start client1.py which sends a request once

3. then you get 502

```
[hokuda@dhcp-193-78 server_socket]$ ./client1.py
HTTP/1.1 502 Proxy Error
Date: Tue, 13 Jun 2017 05:20:01 GMT
Server: Apache/2.4.25 (Fedora) OpenSSL/1.0.2k-fips
Content-Length: 379
Keep-Alive: timeout=5, max=100
Connection: Keep-Alive
Content-Type: text/html; charset=iso-8859-1

<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
<html><head>
<title>502 Proxy Error</title>
</head><body>
<h1>Proxy Error</h1>
<p>The proxy server received an invalid
response from an upstream server.<br />
The proxy server could not handle the request <em><a href="/">GET&nbsp;/</a></em>.<p>
Reason: <strong>Error reading from remote server</strong></p></p>
</body></html>

[hokuda@dhcp-193-78 server_socket]$ 
```


senario 2.
----
1. start server2.py which sends a response body against first request and RST against second request without sending response body.

```
[hokuda@dhcp-193-78 server_socket]$ ./server2.py
```

2. start client2.py which sends 2 requests.

3. then you get 200 for the first request and no response body for the second request. Capturing packets, you can see server2.py sending RST and httpd sending FIN.

```
[hokuda@dhcp-193-78 server_socket]$ ./client2.py 
---- first access in a single keep-alive connection ----
HTTP/1.1 200 OK
Date: Tue, 13 Jun 2017 05:20:17 GMT
Server: Apache/2.4.25 (Fedora) OpenSSL/1.0.2k-fips
hoge: fuga
Content-Type: text/html; charset=UTF-8
Content-Length: 6
Keep-Alive: timeout=5, max=100
Connection: Keep-Alive

hoge

---- second access in a single keep-alive connection ----

[hokuda@dhcp-193-78 server_socket]$ 
```