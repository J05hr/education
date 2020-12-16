public class boyleReview {

    public static void main(String[] args){

       int inputCount = IO.readInt();
       int Total = 0;
       while(inputCount != 0){
           int Number = IO.readInt();
           if((Number % 2) == 0){
               Total += Number;
           }
           inputCount --;
       }
       System.out.println(Total);
    }
}
