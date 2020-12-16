#include <stdio.h>
#include <stdlib.h>
#include <string.h>



int main(int argc, char** argv){

  if (argc < 2) {
      printf("Missing Arguements\n");
      return 0;
  }

  int size = 0;
  //get size
  for (int i = 1; i < argc; i++){
    size += strlen(argv[i]);
  }

  char* outWord = (char*) malloc(size);

  for (int i = 1; i < argc; i++){
    char* inWord = argv[i];
    outWord[i-1] = inWord[strlen(inWord)-1];
  }

  printf("%s\n", outWord);

  return 0;
}
