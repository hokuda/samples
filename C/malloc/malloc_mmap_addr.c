#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
#include <math.h>
#include <sys/mman.h>
#include <fcntl.h>


#define GB 1024 * 1024 * 1024
#define SIZE (1024 * 1024 * 1024)

char * gptr1 = (char *)100;
char * gptr2;
static char * gptr3 = "gege";
int i,j = 0x33;

int main()
{
  char *ptr10 = (char *) malloc(4 * sizeof(char));
  ptr10[0] = ':';
  ptr10[1] = '-';
  ptr10[2] = ')';
  char *ptr11 = (char *) malloc(23 * sizeof(char));
  char *ptr12 = (char *) malloc(24 * sizeof(char));
  char *ptr13 = (char *) malloc(25 * sizeof(char));
  char *ptr14 = (char *) malloc(26 * sizeof(char));

  for (int k=0; k < 132 * 1024 / 32 + 10000; k++) {
    char *ptr = (char *) malloc(24 * sizeof(char));
  }
    
  
  int fd = open("/dev/zero", O_RDONLY);
  char *ptr2 = mmap(NULL, 4, PROT_READ | PROT_WRITE, MAP_PRIVATE, fd, 0);

  printf("malloc=%x\n", ptr10);
  printf("mmap  =%x\n", ptr2);
}
