public class PigLatin {

    public static void main(String []args){

        String word = IO.readString();
        System.out.println(translate(word));

    }

    public static String translate(String original){
        original = original.toLowerCase();
        if(original.charAt(0) == 'a' ||
                original.charAt(0) == 'e' ||
                original.charAt(0) == 'i' ||
                original.charAt(0) == 'o' ||
                original.charAt(0) == 'u') {
            original = original + "vai";
        }else {
            original = original.substring(1, original.length()) + original.substring(0, 1) + "ai";
        }
        return original;
        }
    }
