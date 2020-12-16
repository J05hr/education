public class Compress {

    public static void main(String[] args) {
        System.out.println("input String");
        String test = IO.readString();
        System.out.println(compress(test));
    }

    public static String compress(String original) {

        String shorty = "";
        int numChar = 1;
        int lettaPointa = 0;
        //catch lonely first char
        char letta = original.charAt(lettaPointa);

        for (int r = 1; r < original.length(); r++) {
            //catch lonely chars
            if (letta != original.charAt(r)) {
                if (numChar < 2) {
                    shorty += letta;
                } else {
                    shorty = shorty + numChar + letta;
                    numChar = 1;
                }
                lettaPointa++;
                letta = original.charAt(lettaPointa);
                //catch ending lonely char
                if (lettaPointa == original.length() - 1) {
                    shorty += letta;
                }
            } else {
                numChar++;
                lettaPointa++;
                letta = original.charAt(lettaPointa);
                //catch ending multi char
                if (lettaPointa == original.length() - 1) {
                    shorty = shorty + numChar + letta;
                }
            }
        }
        return shorty;
    }
}
