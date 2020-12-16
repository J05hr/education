public class StringRec {

    public static void main(String[] args) {

        System.out.println(decompress("2h3e2lo"));
        System.out.println(multiChar('a', 10));

    }

    public static String multiChar(char c, int num) {
        if ( num == -1) {
            return "" + c;
        }else if (num == 1) {
            return "";
        } else {
            String s = multiChar(c, num - 1);
            s = s + c;
            return s;
        }
    }


    public static String decompress(String compressedText) {
       if (compressedText.length() <= 1 ){
           return compressedText;

        } else {
           String p;
           int x = -1;
           char c = compressedText.charAt(0);
           if ((c - '0' >= 1) && (c - '0' <= 9)) {
               x = c - '0';
               p = multiChar(compressedText.charAt(compressedText.indexOf(c)+1), x);
           }else{
               p = multiChar(compressedText.charAt(compressedText.indexOf(c)), x);
           }
           String s = p + decompress(compressedText.substring(1));
           return s;

        }
    }
}
