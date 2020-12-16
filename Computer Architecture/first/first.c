#include <stdio.h>
#include <stdlib.h>



int main(int argc, char** argv){

  if (argc < 2) {
      printf("Missing Arguements\n");
      return 0;
  };

  FILE* txtFile;
  txtFile = fopen(argv[1],"r");
  //check if file exists;
  if(!txtFile){
    printf("error");
    return 0;
  }

  int size;
  int* numbers;

  //get the size from the first line
    fscanf(txtFile, "%d\n", &size);

//malloc numbers
  numbers = (int*) malloc(size * sizeof(int));

//get the values and insert them into the array
  for(int i = 0; i < size; i++){
    fscanf(txtFile, "%d", &numbers[i]);
  }

  int ptr = 0;
  int* temp;
  temp = (int*) malloc(size * sizeof(int));

  //insertion sort
  for (int i = 1; i < size; i++){
    int curr = numbers[i];
    int sortedIndex = i - 1;

    while (sortedIndex >= 0){
      if (numbers[sortedIndex] > curr){
        numbers[sortedIndex+1] = numbers[sortedIndex];
        sortedIndex--;
      }else{
        break;
      }
      numbers[sortedIndex+1] = curr;
    }
  }

  //add all the evens to the temp array
  for(int i = 0; i < size; i++){
    if(numbers[i] % 2 == 0){
      temp[ptr] = numbers[i];
      ptr++;
    }
  }

  //add all the odds to the temp array
  for(int i = 0; i < size; i++){
    if(numbers[i] % 2 != 0){
      temp[ptr] = numbers[i];
      ptr++;
    }
  }


  //assign the final temp array back to A
  numbers = temp;


  for(int i = 0; i < size; i++){
    printf ("%d\t", numbers[i]);
  }

  //free and close
  free(numbers);
  fclose(txtFile);
  return 0;
}
