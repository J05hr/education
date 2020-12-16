#include <stdio.h>
#include <stdlib.h>
#include <string.h>



int main(int argc, char** argv){

  if (argc < 2) {
      printf("Missing Arguements\n");
      return 0;
  }

  //loop through each word
  for (int i = 1; i < argc; i++){
    char* inWord = argv[i];

    //add a space before each new word
    if (i > 1){
      printf(" ");
    }
    //vowels case
    if(inWord[0] == 'a' || inWord[0] == 'e' || inWord[0] == 'i'||
      inWord[0] == 'o'|| inWord[0] == 'u'|| inWord[0] == 'A' ||
      inWord[0] == 'E' || inWord[0] == 'I'||
      inWord[0] == 'O'|| inWord[0] == 'U'){
      printf("%syay", inWord);
    }
    //consonant case
    else{
      int vowelIdx = -1;
      //find vowel location
      for(int i = 1; i < strlen(inWord); i++){
        if(inWord[i] == 'a' || inWord[i] == 'e' || inWord[i] == 'i'||
          inWord[i] == 'o'|| inWord[i] == 'u'|| inWord[i] == 'A' ||
          inWord[i] == 'E' || inWord[i] == 'I'||
          inWord[i] == 'O'|| inWord[i] == 'U'){
          vowelIdx = i;
          break;
        }
      }
      if(vowelIdx != -1){
        //print reordered word
        for(int i = vowelIdx; i < strlen(inWord); i++){
          printf("%c",inWord[i]);
        }
        for(int i = 0; i < vowelIdx; i++){
          printf("%c",inWord[i]);
        }
        printf("ay");
      }else{
        printf("%s", inWord);
      }
    }
  }
  printf("\n");
  return 0;
}
