#include <stdio.h>
#include <stdlib.h>
#include <math.h>



void checks(int** mat, int matSize){

  int magicConst = (matSize * (((matSize*matSize)+1) / 2));
  int rowSum = 0;
  int occs = 0;
  int* numbers = (int *) malloc((matSize*matSize) * sizeof(int));
  int numbIdx = 0;


  //save an array of numbers
  for(int i = 0; i < matSize; i++){
    for(int j = 0; j< matSize; j++){
      numbers[numbIdx] = mat[i][j];
      numbIdx++;
    }
  }
/*
  //print numbers
  printf("numbers\n");
  for (numbIdx = 0; numbIdx < (matSize*matSize); numbIdx++){
      printf("%d ",numbers[numbIdx]);
  }
    printf("\n");
*/
  //checks
  for(int i = 0; i < matSize; i++){
    for(int j = 0; j< matSize; j++){

      //check for duplicates
      for (numbIdx = 0; numbIdx < (matSize*matSize); numbIdx++){
        if (numbers[numbIdx] == mat[i][j]){
          occs++;
        }
      }
      //printf("%d occs %d\n", mat[i][j], occs);
      if(occs != 1){
        printf("not-magic\n");
        return;
      }
      occs = 0;

      //check for number <= n^2
      if (mat[i][j] > (matSize*matSize)){
        printf("not-magic\n");
        return;
      }

      rowSum += mat[i][j];
    }

    //check the rowsum against the magic constant
    if(rowSum != magicConst){
      printf("not-magic\n");
      return;
    }else{
      rowSum = 0;
    }
  }

  printf("magic\n");
  return;
}

//--------------------------------------

int main(int argc, char** argv){

  if (argc < 2) {
      printf("Missing Arguements\n");
      return 0;
  }

  FILE* txtFile;
  txtFile = fopen(argv[1],"r");
  //check if file exists;
  if(!txtFile){
    printf("error");
    return 0;
  }

  int matSize;

  //get the matSize
  fscanf(txtFile, "%d\n", &matSize);


  int ** mat;
  //malloc the matirx
  mat = (int**) malloc(matSize * sizeof(int *));

  for(int i = 0; i < matSize; i++){
      mat[i] = (int *) malloc(matSize * sizeof(int));
    }

  //initialize
  for(int i = 0; i < matSize; i++){
    for(int j = 0; j< matSize; j++){
      mat[i][j]= 0;
    }
  }

  //input values from file
  for(int i = 0; i < matSize; i++){
    for(int j = 0; j< matSize; j++){
      fscanf(txtFile,"%d ", &mat[i][j]);
    }
  }
/*
  //print matrix
  printf("matrix:\n");
  for(int i = 0; i < matSize; i++){
    for(int j = 0; j < matSize; j++){
      printf("%d ",mat[i][j]);
    }
    printf("\n");
  }
*/
  //do the checks
  checks(mat, matSize);

 //free
 for(int i = 0; i< matSize; i++){
   free(mat[i]);
  }
 free(mat);

 fclose(txtFile);
 return 0;
 }
