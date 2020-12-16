public class ReverseWords {

    public static void main(String[] args){

        System.out.println("input string");
        String s = IO.readString();
        System.out.println(reversedemwordsbb(s));

    }

    public static String reversedemwordsbb(String sent){

        String newSentence = "";
        sent = sent.toLowerCase();
        int wordcount= 0;
        int spacePS =0;
        while(spacePS != -1){
            wordcount++;
            spacePS = sent.indexOf(" ");
            newSentence = sent.substring(0, spacePS+1) + newSentence;
            sent = sent.substring(spacePS+1);
            spacePS = sent.indexOf(" ");
            if(spacePS==-1 ){
                newSentence = sent.substring(1) + " " + newSentence;
                newSentence = sent.substring(0,1).toUpperCase() + newSentence;
            }

        }
        newSentence = newSentence.substring(0,(newSentence.lastIndexOf(" ")));
        newSentence += ".";
        return newSentence;
    }
}
