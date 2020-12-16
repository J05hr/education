#include <stdio.h>
#include <stdlib.h>



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

  int rows1;
  int cols1;
  int rows2;
  int cols2;
  int finalRows;
  int finalCols;
  int** mat1;
  int** mat2;
  int** finalMat;

  //get mat1 rows and columns
  fscanf(txtFile, "%d\t%d\n", &rows1, &cols1);
  finalRows = rows1;
  mat1 = (int**) malloc(rows1 * sizeof(int *));

  for(int i = 0; i < rows1; i++){
      mat1[i] = (int *) malloc(cols1 * sizeof(int));
    }
  //initialize
  for(int i = 0; i < rows1; i++){
    for(int j = 0; j < cols1; j++){
      mat1[i][j]= 0;
    }
  }
  //input values from file
  for(int i = 0; i < rows1; i++){
    for(int j = 0; j < cols1; j++){
      fscanf(txtFile,"%d ", &mat1[i][j]);
    }
  }
  //print matrix2
/*
  printf("matrix 1\n");
  for(int i = 0; i < rows1; i++){
    for(int j = 0; j < cols1; j++){
      printf("%d ",mat1[i][j]);
    }
    printf("\n");
  }
*/

  //get mat2 rows and columns
  fscanf(txtFile, "%d\t%d\n", &rows2, &cols2);
  finalCols = cols2;
  mat2 = (int**) malloc(rows2 * sizeof(int *));

  for(int i = 0; i < rows2; i++){
      mat2[i] = (int *) malloc(cols2 * sizeof(int));
    }
  //initialize
  for(int i = 0; i < rows2; i++){
    for(int j = 0; j < cols2; j++){
      mat2[i][j]= 0;
    }
  }
  //input values from file
  for(int i = 0; i < rows2; i++){
    for(int j = 0; j < cols2; j++){
      fscanf(txtFile,"%d ", &mat2[i][j]);
    }
  }
  //print matrix2
/*
  printf("matrix 2\n");
  for(int i = 0; i < rows2; i++){
    for(int j = 0; j < cols2; j++){
      printf("%d ",mat2[i][j]);
    }
    printf("\n");
  }
*/

  //matrix multiplication
  finalMat = (int**) malloc(finalRows * sizeof(int *));

  for(int i = 0; i < finalRows; i++){
      finalMat[i] = (int *) malloc(finalCols * sizeof(int));
    }
  //initialize
  for(int i = 0; i < finalRows; i++){
    for(int j = 0; j < finalCols; j++){
      finalMat[i][j]= 0;
    }
  }

  //check for mismatched matricies
  if(cols1 != rows2){
    printf("bad-matrices\n");

  }else{
    //multiply matricies
    for(int i = 0; i < rows1; i++){
      for(int j = 0; j < cols2; j++)
        for(int k = 0; k < cols1; k++){
        finalMat[i][j] += mat1[i][k] * mat2[k][j];
      }
    }
    //print final matrix
    //printf("final matrix 2\n");
    for(int i = 0; i < finalRows; i++){
      for(int j = 0; j < finalCols; j++){
        if (j != 0){printf("\t");}
        printf("%d",finalMat[i][j]);
      }
      printf("\n");
    }
  }

 //free
 for(int i = 0; i < rows1; i++){
   free(mat1[i]);
 }
 for(int i = 0; i < rows2; i++){
   free(mat2[i]);
 }
 for(int i = 0; i < finalRows; i++){
   free(finalMat[i]);
 }
 free(mat1);
 free(mat2);
 free(finalMat);
 fclose(txtFile);
 return 0;
}
