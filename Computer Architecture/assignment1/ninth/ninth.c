#include <stdio.h>
#include <stdlib.h>

struct BSTnode {
  int data;
  int height;
  struct BSTnode* left;
  struct BSTnode* right;
};

//--------------------------------------------

struct BSTnode* createNode(int data){
  //make a new BSTnode
  struct BSTnode* newNode = (struct BSTnode*) malloc(sizeof(struct BSTnode));
  newNode->data = data;
  newNode->height = 1;
  newNode->left = NULL;
  newNode->right = NULL;
  return newNode;
}

//--------------------------------------------
//get max node
struct BSTnode* maxNode(struct BSTnode* root){
  struct BSTnode* temp = root;
  struct BSTnode* prev = root;
  while (temp != NULL){
    prev = temp;
    temp = temp->right;
  }
  return prev;
}

//--------------------------------------------
//get min node
struct BSTnode* minNode(struct BSTnode* root){
  struct BSTnode* temp = root;
  struct BSTnode* prev = root;
  while (temp != NULL){
    prev = temp;
    temp = temp->left;
  }
  return prev;
}

//--------------------------------------------

struct BSTnode* insert(struct BSTnode* root, int data,  int* depth){

  //base case
  if(root == NULL){
    root = createNode(data);
    root->height++;
  }
  //descend into the left subtree
  else if(data < root->data){
    (*depth)++;
    root->left = insert(root->left, data, depth);
  }
  //descend into the right subtree
  else{
    (*depth)++;
    root->right = insert(root->right, data, depth);
  }
  return root;
}

//--------------------------------------------

struct BSTnode* search(struct BSTnode* root, int data, int* depth){

  (*depth)++;
  //target is NULL
  if(root == NULL){
    return NULL;
  }
  //target is root
  else if(data == root->data){
    //printf("searching %d\n", root->data);
    return root;
  }
  //descend into the left subtree
  else if(data < root->data){
    //printf("searching %d\n", root->data);
    return search(root->left, data, depth);
  }
  //descend into the right subtree
  else{
    //printf("searching %d\n", root->data);
    return search(root->right, data, depth);
  }
}

//--------------------------------------------

struct BSTnode* delete(struct BSTnode* root, int data){

  //target is NULL
  if(root == NULL){
    return NULL;
  }
  //descend into the left subtree
  else if(data < root->data){
    root-> left = delete(root->left, data);
  }
  //descend into the right subtree
  else if(data > root->data){
    root->right = delete(root->right, data);
  }
  //target is root
  else if(data == root->data){
    //no children, leaf node case
    if(root->left == NULL && root->right == NULL){
      root = NULL;
    }
    //one right child
    else if(root->left == NULL){
      root = root->right;
    }
    //one left cild
    else if(root->right == NULL){
      root = root->left;
    }
    //two children, rotation
    else{
      //get max of left
      struct BSTnode* temp = minNode(root->right);
      root->data = temp->data;
      root->right = delete(root->right, temp->data);
    }
  }
  return root;
}

//--------------------------------------------

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
  //intialize root node;
  struct BSTnode* root = NULL;
  struct BSTnode* searchResult = NULL;

  int ins = fscanf(txtFile, "%c\t%d\n", &oper, &data);

  while(ins > 0){
    int depth = 0;

    if(oper == 'i' ){
      searchResult = search(root, data, &depth);
      depth = 0;
      if(searchResult == NULL){
        root = insert(root, data, &depth);
        printf("inserted %d\n", depth+1);
      }else{
        printf("duplicate\n");
      }

    }else if(oper == 's'){
      searchResult = search(root, data, &depth);
      if (searchResult == NULL){
          printf("absent\n");
      }else{
          printf("present %d\n", depth);
      }
    }

    else if(oper == 'd'){
      searchResult = search(root, data, &depth);
      if(searchResult == NULL){
          printf("fail\n");
      }else{
        //printf("deleting %d\n", data);
        root = delete(root, data);
        //printf("root = %d\n", root->data);
        printf("success\n");
      }
    }
    ins = fscanf(txtFile, "%c\t%d\n", &oper, &data);
  }
  return 0;
}
