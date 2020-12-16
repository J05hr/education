public class Party {

    public static void main(String args[]){
        System.out.println("Enter info to compute the proper order.");
        System.out.println("Enter the number of people attending the party: ");
        int numPeople = IO.readInt();
        System.out.println("Enter the number of slices of pizza each person should be able to eat: ");
        int slicesPerPerson = IO.readInt();
        System.out.println("Enter the number of cans of soda each person should be able to drink: ");
        int cansPerPerson = IO.readInt();
        System.out.println("Enter the cost of a pizza pie: ");
        double pizzaCost = IO.readDouble();
        System.out.println("Enter the number of slices in a pizza pie: ");
        int slicesPerPie = IO.readInt();
        System.out.println("Enter the cost of a case of soda: ");
        double sodaCost = IO.readDouble();
        System.out.println("Enter the number of cans in a case of soda: ");
        int cansPerCase = IO.readInt();
        
        double cansNeeded = cansPerPerson *  numPeople;
        double slicesNeeded = slicesPerPerson *  numPeople;
        double piesNeeded = Math.ceil(slicesNeeded / slicesPerPie);
        double casesNeeded = Math.ceil(cansNeeded / cansPerCase);
        double TotalCost = (piesNeeded * pizzaCost) + (casesNeeded * sodaCost);

        IO.outputDoubleAnswer((TotalCost));

    }
}

