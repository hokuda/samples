#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
#include <math.h>


#define GB 1024 * 1024 * 1024
#define SIZE (1024 * 1024 * 1024)

int main()
{
  char *ptr1;

  ptr1 = (char *) malloc(4 * sizeof(char));
  ptr1[0] = ':';
  ptr1[1] = '-';
  ptr1[2] = ')';

  free(ptr1);
  
  char *ptr2;
  ptr2 = (char *) malloc(33 * sizeof(char));
  ptr2[0] = 'h';
  ptr2[1] = 'o';
  ptr2[2] = 'g';
  ptr2[3] = 'e';
  free(ptr2);

  char *p0[16];
  char *p1[16];
  for(int i=0; i<16; i++) {
    int sz = powl(2, i) + 1;
    char *ptr;
    ptr = (char *) malloc(sz * sizeof(char));
    p0[i] = ptr;
    ptr = (char *) malloc(sz * sizeof(char));
    p1[i] = ptr;
  }
  
  for(int i=0; i<16; i++) {
    free(p0[i]);
  }

  exit(0);
}
