#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <fcntl.h>


#define GB 1073741824
#define SIZE (1024 * 1024 * 1024)

int main()
{
  char *p;
  int i = 0;
  int fd = open("mmap.txt",O_RDWR);
  printf("fd: %d\n", fd);

  p = (char *)mmap(NULL, SIZE, PROT_READ|PROT_WRITE, MAP_SHARED, fd/*fd*/, 0 /*offset*/);
  printf("%d: %p\n", i++, p);
  for(int j=0; j<SIZE; j++) {
    p[j] = '*';
  }

  do {
    //p = (char *)malloc(GB);
    p = (char *)mmap(NULL, SIZE, PROT_READ|PROT_WRITE, MAP_PRIVATE, fd/*fd*/, 0 /*offset*/);
    printf("%d: %p\n", i, p);
  } while (p != MAP_FAILED && ++i);

  printf("%dGB\n", i);

  exit(0);
}
