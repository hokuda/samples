#!/usr/bin/python
import tornado.ioloop
import tornado.web
import tornado.websocket
 
cl = []
 
class WebSocketHandler(tornado.websocket.WebSocketHandler):
    def check_origin(self, origin):
        return True

    def open(self):
        if self not in cl:
            cl.append(self)
        for client in cl:
            client.write_message("""mgs=open
asdfasdfasdflkjaslkfjas;lkdfja;lkdsjf;ladsjflkadsjflajsdflkasjdf;lkadsjfa;lkdsjfa;lkdsjf;alkdsjf;alkjfd;ad
;alksdjflkajdsf;lkajdsf;lkajds;lfkajds;lkfajdsf;lkajsd;lkfjas;dkjfaslkfdjalkdsfja;lksjfa;lkdsjf;alkds
;alkjdflkajd;lkajdf;lkajdf;alkjdsf;lkajfd;lakjdf;lakjdf;lkajdf;lkajfd;lajdf;lkajdf;lkajdf;lakdjfa;lkdf
hoge hoge hoge hoge hoge hoge hoge hoge hoge hoge hoge hoge hoge hoge hoge hoge hoge hoge hoge hoge hoge hoge
aaaaaaaaaaaaaaaaaa""")
 
    def on_message(self, message):
        for client in cl:
            client.write_message(message)
 
    def on_close(self):
        if self in cl:
            cl.remove(self)
 
class MainHandler(tornado.web.RequestHandler):
    def get(self):
        self.render('test_ws.html')
 
application = tornado.web.Application([
    (r"/", MainHandler),
    (r"/websocket", WebSocketHandler),
])
 
if __name__ == "__main__":
    application.listen(8080, address='127.0.0.1')
    tornado.ioloop.IOLoop.current().start()
