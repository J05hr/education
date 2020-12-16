public class Convert {

    public static void main(String[] args) {
        System.out.println("input string");
        String s = IO.readString();
        System.out.println("Sign bit?");
        Boolean signbit = IO.readBoolean();

        System.out.println(convert(s, signbit));


    }

    public static int convert(String binaryString, boolean signBit) {
        int sign = 1;
        int total = 0;
        int Bnumber = 0;
        if (signBit && binaryString.length()>1) {
            if (binaryString.substring(0, 1).equals("1")) {
                sign = -1;
            }
            binaryString = binaryString.substring(1);

        }
        int power = binaryString.length()-1;
        for (int j = 0 ; j< binaryString.length(); j++) {
            Bnumber = Character.getNumericValue(binaryString.charAt(j));
            total = total + (Bnumber * (int)Math.pow(2, power));
            power--;
        }
        total *= sign;
        return total;
    }
}

