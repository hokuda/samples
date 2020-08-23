#!/usr/bin/python

import socket
from contextlib import closing
import sys

def do_server_socket():
    #host = '127.0.0.1'
    host = '0.0.0.0'
    port = 81
    backlog = 30
    bufsize = 4096
    counter = 1
    
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_LINGER, b'\1\0\0\0\0\0\0\0')

    with closing(sock):
        sock.bind((host, port))
        sock.listen(backlog)
        conn = None
        while True:
            try:
                conn.recv(0) # just poll
            except:
                conn, address = sock.accept()
            req = conn.recv(bufsize)
            print(req)
            if counter == 1:
                res = b'HTTP/1.1 401 Unauthorized\r\n'
                #res += b'Date: Wed, 18 Sep 2019 09:34:39 GMT\r\n'
                #res += b'Date: Tue, 24 Sep 2019 10:34:39 JST\r\n'
                #res += b'Server: Apache\r\n'
                res += b'WWW-Authenticate: Negotiate\r\n'
                res += b'WWW-Authenticate: Negotiate\r\n'
                res += b'Content-Length: 6\r\n'
                res += b'Content-Type: text/html; charset=iso-8859-1\r\n'
                res += b'\r\n'
                res += b'hoge\r\n' + b'\r\n'
                conn.send(res)
            else:
                conn.close() # send RST
                sys.exit()
            print(counter)
            counter = counter + 1
            
if __name__ == '__main__':
    do_server_socket()
    
