#include <malloc.h>
#include <string.h>

int main()
{
  char *p1 = (char *) malloc(16);
  char *p2 = (char *) malloc(16);
  char *p3 = (char *) malloc(16);
  free(p1);
  free(p2);

  strcpy(p2, "BANANA");

  char *p4 = (char *) malloc(16);
  char *p5 = (char *) malloc(16);
}
