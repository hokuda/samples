#include <stdio.h>
#include <unistd.h>

static int i;

int main(int argc, char *args[])
{
  printf("i=%d\n", i++);
  execl("./sighup",NULL);
  //sleep(100000000);
}
