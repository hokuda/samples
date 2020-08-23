#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
#include <math.h>

//#define N 8192 //(128*1024/16)
#define N 7

int main()
{
  char *p[N];
  //int nsize = 1008-16;
  int nsize = 0;
  for(int i=0; i<N; i++) {
    nsize += 16;
    char *dummy = (char *) malloc(nsize);
    p[i] = (char *) malloc(nsize);
    p[i][0] = 'J'; p[i][1] = 'B'; p[i][2] = 'o'; p[i][3] = 's'; p[i][4] = 's'; p[i][5] = '\0';
  }
  
  for(int i=0; i<N; i++) {
    free(p[i]);
  }

  //char *dummy = (char *) malloc(nsize);
  char *dummy1;
  char *dummy2;
  //dummy = (char *) malloc(8);
  //dummy = (char *) malloc(128);
  dummy1 = (char *) malloc(256);
  dummy2 = (char *) malloc(256);
  free(dummy1);
  //dummy = (char *) malloc(1024);

  while(1) {
    //char *dummy = (char *) malloc(131056-8);
    char *dummy = (char *) malloc(131040-8);
    free(dummy);
  }
    
  exit(0);
}
