public class PayFee {

    public static void main(String args[]){
        System.out.print("Input the payment amount to compute the fee:");
        double paymentAmount = IO.readDouble();
        if (paymentAmount <= 500 ){
            IO.outputDoubleAnswer(10.0);
        }
        else if (paymentAmount > 500 && paymentAmount < 5000 ){
           double onePercentFee = (paymentAmount / 100);
            IO.outputDoubleAnswer(Math.max(onePercentFee,20));
        }
        else if (paymentAmount >= 5000 && paymentAmount <= 10000 ){
            double twoPercentFee = 2*(paymentAmount / 100);
            IO.outputDoubleAnswer(Math.max(twoPercentFee,120));
        }
        else if (paymentAmount > 10000 ){
            double overTenK = (paymentAmount - 10000);
            double overFifteenK = (paymentAmount - 15000);
            double fee= 200;
            if (overTenK <= 5000){
                fee = (200+(3*(overTenK/100)));
            }
            else if (overTenK > 5000) {
                fee = (200 + 150 + (4 * (overFifteenK / 100)));
            }
            IO.outputDoubleAnswer(fee);

        }

    }
}
