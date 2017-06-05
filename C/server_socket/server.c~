#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/tcp.h>

int
main()
{
  int sock0;
  struct sockaddr_in addr;
  struct sockaddr_in client;
  int len;
  int sock;
  
  printf("creating socket\n");
  sock0 = socket(AF_INET, SOCK_STREAM, 0);
  
  addr.sin_family = AF_INET;
  addr.sin_port = htons(12345);
  addr.sin_addr.s_addr = INADDR_ANY;
  
  printf("binding socket\n");
  bind(sock0, (struct sockaddr *)&addr, sizeof(addr));
  
  int timeout=10000;
  setsockopt(sock0, IPPROTO_TCP, TCP_DEFER_ACCEPT, &timeout, sizeof(int));

  listen(sock0, 5);
  
  while (1) {
    len = sizeof(client);
    printf("accepting socket\n");

    //while(1){}
    sleep(10);

    sock = accept(sock0, (struct sockaddr *)&client, &len);
    

    printf(
           "accepted connection port=%d\n"
           ,ntohs(client.sin_port)
           //"accepted connection from %s, port=%d\n"
           //,inet_ntoa(client.sin_addr)
           //,ntohs(client.sin_port)
           );
    
    send(sock, "HELLO", 5, 0);
    
    close(sock);
  }
  
  close(sock0);
  
  return 0;
}
