#include <stdio.h>
#include <stdlib.h>

//---------------------------

struct LLnode {
  int value;
  struct LLnode* next;
};

//----------------------------

struct LLnode* insertSorted(struct LLnode* head, int value){

    struct LLnode* newNode = (struct LLnode*) malloc(sizeof(struct LLnode));
    newNode->value = value;
    newNode->next = NULL;

    //empty head, just add a Node
    if (head == NULL){
      head = newNode;
      return head;
    }

    //duplicate value with head
    if (head->value == value){
        return head;
    }

    //if its smaller add it to the front
    if(newNode->value < head->value){
        newNode->next = head;
        head = newNode;
        return head;
    }

    struct LLnode* prev = head;
    struct LLnode* temp = head;

    //advance through the list to find a greater value;
    while(temp != NULL && temp->value < newNode->value){
        prev = temp;
        temp = temp->next;

        //jump out if duplicate
        if(temp != NULL && temp->value == newNode->value){
            return head;
        }

        //nothing greater, add it at the end
        if(temp == NULL){
          prev->next = newNode;
          return head;
        }
    }
    //insert the node
    newNode->next = temp;
    prev->next = newNode;
    return head;
}

//----------------------------

struct LLnode* delete(struct LLnode* head, int value){
  //Empty head nothing to delete
  if(head == NULL){
    return head;
  }

  //if head is to be deleted
  if(head->value == value){
    head = head->next;
    return head;
  }

  struct LLnode* temp = head;
  struct LLnode* prev = head;

    //advance through the list to find the value
    while(temp != NULL ){
        prev = temp;
        temp = temp->next;
        //not found, return
        if(temp == NULL){
            return head;
        }
        //break out to deletion if found
        if(temp->value == value){
            break;
        }
    }
    //delete the node
    prev->next = temp->next;
    return head;
}

//----------------------------

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

  char oper;
  int data;
  int count = 0;
  struct LLnode* head = NULL;
  int ins = fscanf(txtFile, "%c\t%d\n", &oper, &data);

  while(ins > 0){
      if(oper == 'i' ){
        head = insertSorted(head, data);
      }else if(oper == 'd'){
        head = delete(head, data);
      }
      ins = fscanf(txtFile, "%c\t%d\n", &oper, &data);
  }

  //count nodes
  struct LLnode* temp = head;
  while(temp != NULL){
    count++;
    temp = temp->next;
  }

  //if there are nodes print the count and values
  if(count > 0){
    printf("%d\n", count);
    struct LLnode* temp2 = head;
    while(temp2 != NULL){
      printf("%d\t", temp2->value);
      temp2 = temp2->next;
    }
  printf("\n");

  //else print 0
  }else{
    printf("0\n");
  }

  //free and close
  fclose(txtFile);
  return 0;
}
