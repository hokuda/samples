#!/usr/bin/python

#from __future__ import print_function
import socket
from contextlib import closing

def do_server_socket():
    host = '127.0.0.1'
    port = 8080
    backlog = 30
    bufsize = 4096
    
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
            conn.close() # send RST
            
if __name__ == '__main__':
    do_server_socket()
    
