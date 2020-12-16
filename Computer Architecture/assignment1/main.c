#include <stdio.h>
#include <stdlib.h>


struct LLnode {
    int value;
    struct LLnode* next;
};


struct LLnode* insert(struct LLnode* LL, int value){
    struct LLnode* temp = LL;

    struct LLnode* newNode = NULL;
    newNode = (struct LLnode*) malloc(sizeof(struct LLnode));
    newNode->value = value;

    if (temp->value == value){
        return LL;
    }

    newNode->next = LL;
    return newNode;
}



void delete(struct LLnode** LL, int value){
struct LLnode* temp = *LL;
struct LLnode* prev = *LL;

if(temp != NULL){
//if head is to be deleted
if(temp->value == value){
*LL = temp->next;
return;
}

//advance through the list to find the value
while(temp != NULL && temp->value != value){
prev = temp;
temp = temp->next;
}

//delete the node
prev->next = temp->next;
}
}


int main(int argc, char** argv) {

    if (argc < 2) {
        printf("Missing Arguements\n");
        return 0;
    };

    FILE *txtFile;
    txtFile = fopen(argv[1], "r");

    char oper;
    int data;
    int count = 0;
    struct LLnode *head = NULL;


    while (fscanf(txtFile, "%c\t%d\n", &oper, &data) > 0) {
        fscanf(txtFile, "%c\t%d", &oper, &data);
        if (oper == 'i') {
            head = insert(head, data);
        } else if (oper == 'd') {
            delete(&head, data);
        }
    }

    while (head != NULL) {
        count++;
        head = head->next;
    }

    //test
    printf("count %d\n", count);

    //if there are nodes print the count and values
    if (count > 0) {
        printf("%d\n", count);
        while (head != NULL) {
            printf("%d  ", head->value);
            head = head->next;
        }

        //else print 0
    } else {
        printf("0\n");
    }

    //free and close
    fclose(txtFile);
    return 0;
}
