#!/usr/bin/python3

import sys
from websocket import create_connection
ws = create_connection("ws://localhost:80/ws")
#ws = create_connection("ws://localhost:8080/websocket")
#ws = create_connection("ws://localhost:54321/websocket")
 
if len(sys.argv) > 1:
    message = sys.argv[1]
else:
    message = 'hello world!'
 
print(ws.send(message))
print(ws.recv())
print(ws.send(message + "again"))
 
ws.close()
