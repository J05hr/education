public class LuckySevens {
    public static void main(String []args){

        System.out.println("lower end");
        int lower = IO.readInt();
        System.out.println("upper end");
        int upper = IO.readInt();

        if (upper < lower){
            IO.reportBadInput();
            return;
        }
        IO.outputIntAnswer(countLuckySevens(lower,upper));

    }

    public static int countLuckySevens(int lowerEnd, int upperEnd){
        int sevens = 0;
        if(lowerEnd<0 && upperEnd<0){
            int temp = upperEnd;
            upperEnd = lowerEnd * -1;
            lowerEnd = temp *-1;
        }else if ( lowerEnd<0 && upperEnd>0){
            int neglower = 0;
            int negupper = lowerEnd * -1;
            for (int cnt = neglower; cnt <= negupper; cnt++) {
                int negNum = cnt;
                int neglength = (int) (Math.log10(negupper) + 1);
                for (int j = 0; j < neglength; j++) {
                    if (negNum % 10 == 7) {
                        sevens++;
                    }
                    negNum -= negNum % 10;
                    negNum /= 10;
                }
            }
            lowerEnd = 0;}

        System.out.println("lower" + lowerEnd);
        System.out.println("upper" + upperEnd);

        int length = (int) (Math.log10(upperEnd)+1);
        System.out.println("length" + length);


        for (int cnt = lowerEnd; cnt <= upperEnd; cnt++) {
            int currentNum = cnt;
            for (int j = 0; j < length; j++) {
                if (currentNum % 10 == 7) {
                    sevens++;
                }
                currentNum -= currentNum % 10;
                currentNum /= 10;
            }
        }
        return sevens;
    }
}
