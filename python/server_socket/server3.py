#!/usr/bin/python

import socket
from contextlib import closing

def do_server_socket():
    host = '127.0.0.1'
    port = 8080
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
                res = b'HTTP/1.1 200 OK\r\n' + b'hoge: fuga\r\n' + b'Content-Type: text/html; charset=UTF-8\r\n' + b'Content-Length: 6\r\n' + b'\r\n' + b'hoge\r\n' + b'\r\n'
                conn.send(res)
            else:
                conn.close() # send RST
            print(counter)
            counter = counter + 1
            
if __name__ == '__main__':
    do_server_socket()
    
