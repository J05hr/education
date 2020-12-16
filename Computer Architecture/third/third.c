#include <stdio.h>
#include <stdlib.h>

//------------------------

//node
struct Node {
  int data;
  struct Node* next;
};

//-------------------------

//hash function
int hashFunc(int data){
  data = abs(data);
  return data % 10000;
}

//--------------------------

//intialize a hashtable with 10000
struct Node* hashTable[10000];

//---------------------------

void search (int newData){

  struct Node* temp = (struct Node* ) malloc(sizeof(struct Node));
  temp->data = newData;
  temp->next = NULL;

  //get the hash
  int hashCode = hashFunc(temp->data);
  //printf("hashIndex %d", hashCode);

  //empty hashtable bucket, not found
  if(hashTable[hashCode] == NULL){
    printf("absent\n");
    return;
  }

  //find the newItem
  //if the newItem is at the head
  if(hashTable[hashCode]->data == temp->data){
    printf("present\n");
  //traverse to find the newItem
  }else{
    struct Node* tempTable = hashTable[hashCode];
    //advance through the list to find a greater value;
    while(tempTable != NULL){
        tempTable = tempTable->next;

        //jump out if found
        if(tempTable != NULL && tempTable->data == temp->data){
            printf("present\n");
            return;
        }
    }
    //hit the end of the list, not found
    printf("absent\n");
    return;
  }
}

//-------------------------

void insert(int newData){

  //get the hash
  int hashCode = hashFunc(newData);
  //printf("hashIndex %d", hashCode);

  //assign the data
  struct Node* newItem = (struct Node* ) malloc(sizeof(struct Node));
  newItem->data = newData;
  newItem->next = NULL;

  //test
  //printf("newItem %d \n",newItem->data);


  //empty bucket just add it
  if(hashTable[hashCode] == NULL){
    hashTable[hashCode] = newItem;
    printf("inserted\n");
    return;

  //insert at the end of the list if not found
  }else{
    //test
    //printf("bucket %d\n",hashTable[hashCode]->data);

    struct Node* tempBucket = hashTable[hashCode];
    struct Node* prev = hashTable[hashCode];

    //find the newItem
    //if the newItem is at the head
    if(tempBucket->data == newItem->data){
      printf("duplicate\n");
      return;

    }else{
      //traverse to find a duplicate
      while(tempBucket != NULL){
          prev = tempBucket;
          tempBucket = tempBucket->next;

          //jump out if found before the end
          if(tempBucket != NULL && tempBucket->data == newItem->data){
              printf("duplicate\n");
              return;
          }
      }
      //hit the end of the list, with no duplicates, insert
      //insert the node
      prev->next = newItem;
      printf("inserted\n");
      return;
    }
  }
}

//-------------------------

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

  char oper;
  int data;
  int ins = fscanf(txtFile, "%c\t%d\n", &oper, &data);

  //intialize
  for(int i = 0; i < 10000; i++){
    hashTable[i] = NULL;
  }

  //loop through the file
  while(ins > 0){
      //search or insert
      if(oper == 's' ){
        search(data);
      }else if(oper == 'i'){
        insert(data);
      }
    ins = fscanf(txtFile, "%c\t%d", &oper, &data);
  }

  fclose(txtFile);
  return 0;
}
