#include <malloc.h>
#include <string.h>

int main()
{
  // fill main arena
  for(int i=0; i<135168/32-2; i++) {
    malloc(24);
  }
  
  char *p = (char *) malloc(16);
  //                  10        20        30        40        50
  //         12345678901234567890123456789012345678901234567890
  strcpy(p, "This message run over the main arena and causes BANANA");
}
