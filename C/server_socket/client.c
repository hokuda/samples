#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>

int main(void)
{
  int sockfd ;
  int len ;
  struct sockaddr_in dest ;
  int result ;
  char ch = 'A' ;
  static const unsigned char pkt4[173] = {
    0x16, 0x03, /* ........ */
    0x01, 0x00, 0xa8, 0x01, 0x00, 0x00, 0xa4, 0x03, /* ........ */
    0x01, 0x56, 0x0c, 0x3b, 0xc0, 0x76, 0xe2, 0xb6, /* .V.;.v.. */
    0xd3, 0xf4, 0xe4, 0xb7, 0x42, 0xe0, 0x80, 0xe1, /* ....B... */
    0xbc, 0xbc, 0xbf, 0x29, 0x13, 0x49, 0x21, 0x25, /* ...).I!% */
    0x10, 0x07, 0xf6, 0x3c, 0xca, 0x15, 0x22, 0x33, /* ...<.."3 */
    0x1b, 0x00, 0x00, 0x38, 0xc0, 0x09, 0xc0, 0x13, /* ...8.... */
    0x00, 0x2f, 0xc0, 0x04, 0xc0, 0x0e, 0x00, 0x33, /* ./.....3 */
    0x00, 0x32, 0xc0, 0x18, 0x00, 0x09, 0x00, 0x15, /* .2...... */
    0x00, 0x12, 0x00, 0x03, 0x00, 0x08, 0x00, 0x14, /* ........ */
    0x00, 0x11, 0xc0, 0x06, 0xc0, 0x10, 0x00, 0x02, /* ........ */
    0xc0, 0x01, 0xc0, 0x0b, 0xc0, 0x15, 0x00, 0x01, /* ........ */
    0x00, 0x1e, 0x00, 0x22, 0x00, 0x26, 0x00, 0x29, /* ...".&.) */
    0x00, 0x28, 0x00, 0x2b, 0x01, 0x00, 0x00, 0x43, /* .(.+...C */
    0x00, 0x0a, 0x00, 0x34, 0x00, 0x32, 0x00, 0x17, /* ...4.2.. */
    0x00, 0x01, 0x00, 0x03, 0x00, 0x13, 0x00, 0x15, /* ........ */
    0x00, 0x06, 0x00, 0x07, 0x00, 0x09, 0x00, 0x0a, /* ........ */
    0x00, 0x18, 0x00, 0x0b, 0x00, 0x0c, 0x00, 0x19, /* ........ */
    0x00, 0x0d, 0x00, 0x0e, 0x00, 0x0f, 0x00, 0x10, /* ........ */
    0x00, 0x11, 0x00, 0x02, 0x00, 0x12, 0x00, 0x04, /* ........ */
    0x00, 0x05, 0x00, 0x14, 0x00, 0x08, 0x00, 0x16, /* ........ */
    //0x00, 0x0b, 0x00, 0x02, 0x01, 0x00, 0xff, 0x01, /* ........ */
    0xff, 0xff, 0xff, 0x02, 0x01, 0x00, 0xff, 0x01, /* ........ */
    0x00, 0x01, 0x00                                /* ... */
  };

  sockfd = socket(AF_INET,SOCK_STREAM,0);

  memset(&dest, 0, sizeof(dest));                /* zero the struct */
  dest.sin_family = AF_INET ;
  dest.sin_addr.s_addr = inet_addr("127.0.0.1");
  //dest.sin_port = 8443;
  dest.sin_port = htons(12345);
  len = sizeof(dest);

  //result = connect(sockfd , (struct sockaddr *)&dest , len);
  result = connect(sockfd , (struct sockaddr *)&dest , sizeof(struct sockaddr));
  close(sockfd);

  if ( result == -1 ) {
    perror("oops: client2");
    exit(1);
  }

  //write(sockfd,&ch,1);
  write(sockfd,pkt4,173);
  
  read(sockfd,&ch,1);
  printf("char from server = %c \n",ch);
  close(sockfd);
  exit(0);
}
