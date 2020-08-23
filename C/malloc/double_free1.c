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
  char *ptr3 = ptr2;
  ptr2[0] = 'h';
  ptr2[1] = 'o';
  ptr2[2] = 'g';
  ptr2[3] = 'e';
  free(ptr1);
  free(ptr2);
  free(ptr3);

}
