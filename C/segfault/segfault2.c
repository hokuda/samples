#include <signal.h>
#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>

volatile sig_atomic_t eflag = 0;

void segfault() {

  //asm("movq $0x12345678, %rbx");
  //asm("movq $0x77777777, %rcx");
  //asm("movq (%rbx), %rcx");

  char *p = (char*)0xdeadbeef;
  char c = *p;
}

void handle_segfault(int sig, siginfo_t *info, void *ctx) {
  // printf should not be used in a signal handler, because it's unsafe.
  printf("si_signo:%d\nsi_code:%d\n", info->si_signo, info->si_code);
  printf("si_pid:%d\nsi_uid:%d\n", (int)info->si_pid, (int)info->si_uid);
  eflag = 1;
  
  abort();
}

int main(int argc, char *args[])
{
  struct sigaction sa_sigsegv;
  memset(&sa_sigsegv, 0, sizeof(sa_sigsegv));
  sa_sigsegv.sa_sigaction = handle_segfault;
  sa_sigsegv.sa_flags = SA_SIGINFO;
  if ( sigaction(SIGSEGV, &sa_sigsegv, NULL) < 0 ) {
    exit(1);
  }

  segfault();
}

