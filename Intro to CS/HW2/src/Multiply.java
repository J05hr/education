public class Multiply {

    public static void main(String args[]){
        System.out.println("Enter two integers to be multiplied.");
        System.out.println("Enter number 1: ");
        int num1 = IO.readInt();
        System.out.println("Enter number 2: ");
        int num2 = IO.readInt();
        IO.outputIntAnswer((num1*num2));
    }
}
