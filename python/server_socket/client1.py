#!/usr/bin/python

from __future__ import print_function
import socket
from contextlib import closing

def do_request():
    host = '127.0.0.1'
    port = 80
    bufsize = 4096
    
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.connect((host, port))

    with closing(sock):
        sock.send(b'GET / HTTP/1.1\r\n'
                  + b'host: localhost:80\r\n'
                  + b'first: access\r\n'
                  + b'Connection: keep-alive\r\n'
                  + b'\r\n'
        )
        print(sock.recv(bufsize))
    
if __name__ == '__main__':
    do_request()
        
