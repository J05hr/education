



public class scratch {

    public static void main(String[] args) {

        int[] a = new int[10];
        System.out.println(a.length);
        for(int j = 1; j < a.length+1 ; j++){
            a[j-1] = j*10;}
        System.out.println(a[9]);

    }
}
