#include <stdio.h>
#include <stdlib.h>


int main(int argc, char** argv){

  if (argc < 3) {
      printf("Missing Arguements\n");
      return 0;
  }

  FILE* trainFile;
  trainFile = fopen(argv[1],"r");
  //check if file exists;
  if(!trainFile){
    printf("error");
    return 0;
  }

  FILE* testFile;
  testFile = fopen(argv[2],"r");
  //check if file exists;
  if(!testFile){
    printf("error");
    return 0;
  }

  int trainingFeats;
  int trainingEx;

  int trainRows;
  int trainCols;

  int testRows;
  int testCols;

  double** trainMat;
  double** testMat;

  double* weights;
  double* trainPrices;
  double* testPrices;

  //get training features and training examples numbers
  //malloc the training matrix
  fscanf(trainFile, "%d\n%d\n", &trainRows, &trainCols);
  trainMat = (double**) malloc(trainRows * sizeof(double *));
  for(int i = 0; i < trainRows; i++){
      trainMat[i] = (int *) malloc(trainCols * sizeof(int));
  }
  //input values from file
  for(int i = 0; i < trainRows; i++){
    for(int j = 0; j < trainCols; j++){
      fscanf(trainFile,"%d,", &trainMat[i][j]);
    }
  }
  //print trainMat
  printf("trainMat \n");
  for(int i = 0; i < trainRows; i++){
    for(int j = 0; j < trainCols; j++){
      printf("%d ",trainMat[i][j]);
    }
    printf("\n");
  }

  //get testing features and testing examples numbers
  //malloc the testing matrix
  fscanf(testFile, "%d\n%d\n", &testRows, &testCols);
  testMat = (double**) malloc(testRows * sizeof(double *));
  for(int i = 0; i < testRows; i++){
      testMat[i] = (int *) malloc(testCols * sizeof(int));
  }
  //input values from file
  for(int i = 0; i < testRows; i++){
    for(int j = 0; j < testCols; j++){
      fscanf(testFile,"%d,", &testMat[i][j]);
    }
  }
  //print testMat
  printf("testMat \n");
  for(int i = 0; i < testRows; i++){
    for(int j = 0; j < testCols; j++){
      printf("%d ",testMat[i][j]);
    }
    printf("\n");
  }

/*
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
*/

 //free
 for(int i = 0; i < trainRows; i++){
   free(trainMat[i]);
 }
 for(int i = 0; i < testRows; i++){
   free(testMat[i]);
 }
 //for(int i = 0; i < finalRows; i++){
   //free(finalMat[i]);
 //}
 free(trainMat);
 free(testMat);
 //free(finalMat);
 fclose(trainFile);
 fclose(testFile);
 return 0;
}







//transpose

double** transpose;
transpose = mat1;

for(int i = 0; i < rows; i++){
	for(int j = 0; j < cols; j++){
		transpose[j][i] = mat[i][j];
	}
}




//inverse

//intialize
double** identMat;
identMat = mat1;

for(int i = 0; i < rows; i++){
	for(int j = 0; j < cols; j++){
		if( i == j){
			identMat[i][j] = 1;
		}
		else{
			identMat[i][j] = 0;
		}
	}
}


//row operations to create inverse
for(int i = 0; i < rows; i++){
	for(int j = 0; j < cols; j++){

		//change to one
		if( i == j){
			if(mat1[i][j] != 1){
				//divide the row
				double divisor = mat[i][j];
				for (int k = 0; k < cols; k++){
					mat[i][k] = mat[i][k] / divisor;
					identMat[i][k] = identMat[i][k] / divisor;
				}
			}
		}

		//change to zero
		else{
			if (mat1[i][j] != 0){
				//do the row op
				//find a suitable row and factor
				for (int n = 0; n < rows; n++){
					if (i != n){
						int factor = mat[n][j] / mat[i][j];
					}
				}
				//apply subtraction with the factor on the row
				for (int k = 0; k < cols; k++){
					mat[i][k] = factor * mat[i][k] - mat[n][k];
					identMat[i][k] = factor * identMat[i][k] - mat[n][k];
				}
			}
		}
	}
}


//print matricies
printf("Mat : \n");
for(int i = 0; i < rows; i++){
	for(int j = 0; j < cols; j++){
		printf("%d ", mat[i][j]);
	}
	printf("\n");
}


printf("IdentMat : \n");
for(int i = 0; i < rows; i++){
	for(int j = 0; j < cols; j++){
			printf("%d ", identMat[i][j]);
	}
	printf("\n");
}
