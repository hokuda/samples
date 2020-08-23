#include <malloc.h>
#include <string.h>

int main()
{
  char *p1 = (char *) malloc(24);
  char *p2 = (char *) malloc(24);

  //          123456789012345678901234567890
  strcpy(p1, "str > 23 bytes corrupts BANANA");

  free(p2);
}
