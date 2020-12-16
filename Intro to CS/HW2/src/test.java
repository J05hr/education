public class test {

    public static void main(String args[]){
        String s = "mortgags";


        String fullName = "lastname, firstname";
        String lastName;
        String firstName;
        if (fullName.indexOf(",") != -1){
            lastName = fullName.substring(0,fullName.indexOf(","));
            firstName = fullName.substring(fullName.indexOf(",")+2,fullName.length());
        }else{
            firstName = fullName.substring(0,fullName.indexOf(" "));
            lastName = fullName.substring(fullName.indexOf(" ")+1,fullName.length());
        }
        System.out.println(lastName);
        System.out.println(firstName);

        }
    }