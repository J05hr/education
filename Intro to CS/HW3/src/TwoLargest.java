public class TwoLargest {

    public static void main(String []args) {

        System.out.println("terminator");
        double terminator = IO.readDouble();
        System.out.println("values");
        
        double initial= IO.readDouble();
        if (initial == terminator) {
            while (initial== terminator) {
                IO.reportBadInput();
                System.out.println("re-enter");
                initial= IO.readDouble();
            }
        }
        double input2 = IO.readDouble();
        if (input2 == terminator) {
            while (input2 == terminator) {
                IO.reportBadInput();
                System.out.println("re-enter");
                input2 = IO.readDouble();
            }
        }

        double max = Math.max(initial,input2);
        double secMax = Math.min(initial,input2);

        double input3 = IO.readDouble();
        while (input3 != terminator) {

           if(input3 > max) {
                secMax = max;
                max = input3;
            }else if(input3 >= secMax){
                secMax = input3;
            }

            input3 = IO.readDouble();
        }

        IO.outputDoubleAnswer(max);
        IO.outputDoubleAnswer(secMax);
    }
}

