public class WordCount {

    public static void main(String[] args) {
        System.out.println("input string");
        String test = IO.readString();
        System.out.println("input max length");
        int mLen = IO.readInt();
        System.out.println(countWords(test,mLen));
    }

    public static int letterCount(String word) {
        int letterCnt = 0;
        for (int r = 0; r < word.length(); r++) {
            if (Character.isLetter(word.charAt(r))) {
                letterCnt++;
            }
        }
        return letterCnt;
    }


    public static int countWords(String original, int maxLength) {
        original = original.toLowerCase();
        int words = 0;
        int prevIndx = 0;
        int ps = original.indexOf(" ");
        String line = "";

        //if no space take single word
        if (ps == -1) {
            if (letterCount(original) <= maxLength && letterCount(original)!=0) {
                words++;
            }
        //if multi word inputs iterate until no more spaces and take final word
        } else {
            while (ps != -1) {
                line = original.substring(prevIndx, ps);
                if (letterCount(line) <= maxLength) {
                    words++;
                }
                prevIndx = ps;
                ps = original.indexOf(" ", ps + 1);
                //catch last word
                if (ps == -1) {
                    line = original.substring(prevIndx, original.length());
                    if (letterCount(line) <= maxLength) {
                        words++;
                    }
                }
            }
        }
        return words;
    }
}
