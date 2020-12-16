public class Player{

	//declare your fields here
	private Hand Hand;
	private double bal;

	//initialize your fields in the constructor
	public Player(double balance){
		 bal = balance;
		 Hand = new Hand();
	}

	public void deal(Card c){
	    Hand.addCard(c);
	}


	//Returns an array of Cards that the Player wishes to discard.
	//The game engine will call deal() on this player for each card
	//that exists in the return value. MS2 Instructions: Print the hand to
	//the terminal using System.out.println and ask the user which cards 
	//they would like to discard. The user will first enter the number of cards they
    //wish to discard, followed by the indices, one at a time, of
	//the card(s) they would like to discard, 
	//Return an array with the appropriate Card objects
	//that have been discarded, and remove the Card objects from this
	//player's hand. Use IO.readInt() for all inputs. In cases of error
	//re-ask the user for input.
	//
	// Example call to discard():
	//
	// This is your hand:
	// 0: Ace of Hearts
	// 1: 2 of Diamonds
	// 2: 5 of Hearts
	// 3: Jack of Spades
	// 4: Ace of Clubs
	// How many cards would you like to discard?
	// 2
	// 1
	// 2
	//
	// The resultant array will contain the 2 of Diamonds and the 5 of hearts in that order
	// This player's hand will now only have 3 cards
	public Card[] discard(){
		System.out.println("This is your hand:");
        for(int c = 0; c < 5; c++){
            System.out.println(c + " - " + Hand.getCard(c).toString());
        }
        System.out.println("Enter the number of cards you would like to discard");
		int numInputs = IO.readInt();
        while(numInputs < 0 || numInputs > 5){
            System.out.println("Invalid Input, Try again: ");
            numInputs = IO.readInt();
        }
		Card[] discarded = new Card[numInputs];
		for (int crd = 0; crd < numInputs; crd++) {
            System.out.println("Enter Index");
            int crdIndex = IO.readInt();
            while(crdIndex < 0 || crdIndex > 4){
                System.out.println("Invalid Input, Try again: ");
                crdIndex = IO.readInt();
            }
            if (Hand.getCard(crdIndex)!= null) {
                discarded[crd] = Hand.getCard(crdIndex);
                Hand.removeCard(crdIndex);
            }else{
                System.out.println("This position already has no card, skipping");
            }
        }
        return discarded;
	}

	//Returns the amount that this player would like to wager, returns
	//-1.0 to fold hand. Any non zero wager should immediately be deducted
	//from this player's balance. This player's balance can never be below
	// 0 at any time. This player's wager must be >= to the parameter min
	// MS2 Instructions: Show the user the minimum bet via the terminal 
	//(System.out.println), and ask the user for their wager. Use
	//IO.readDouble() for input. In cases of error re-ask the user for 
	//input.
	// 
	// Example call to wager()
	//
	// How much do you want to wager?
	// 200
	//
	// This will result in this player's balance reduced by 200
	
	public double wager(double min){

        System.out.println("The minimum bet is " + min + ".");
	    System.out.println("Enter a bet OR enter -1 to fold: ");
	    double bet = IO.readDouble();
	    if(bet == -1){
	        return -1;
        }
	    while(bet < min || bal - bet < 0){
	        System.out.println("Invalid bet, try again:");
            bet = IO.readDouble();
        }
        bal -= bet;
	    System.out.println("You bet $" + bet + ". New balance of $" + bal);
	    return bet;
	}

	//Returns this player's hand
	public Hand showHand(){
		return Hand;
	}

	//Returns this player's current balance
	public double getBalance(){
        System.out.println("Your balance is $" + bal);
		return bal;
	}

	//Increase player's balance by the amount specified in the parameter,
	//then reset player's hand in preparation for next round. Amount will
	//be 0 if player has lost hand
	public void winnings(double amount){
	    bal += amount;
	    Hand.clear();
	}

}