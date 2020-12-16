public class SmallestLargest {

    public static void main(String []args){

        //System.out.println("terminator");
        double terminator = IO.readDouble();
        //System.out.println("values");
        double input = IO.readDouble();
        if (input == terminator){
            IO.reportBadInput();
            return;
        }
        double max = input;
        double min = input;
        while(input != terminator){
            min = Math.min(input,min);
            max = Math.max(input,max);
            input = IO.readDouble();
        }
        IO.outputDoubleAnswer(min);
        IO.outputDoubleAnswer(max);
    }
}
