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
	    this.Hand.addCard(c);
	}


	//Returns an array of Cards that the Player wishes to discard.
	//The game engine will call deal() on this player for each card
	//that exists in the return value.
	public Card[] discard() {
        Card[] discarded = new Card[5];
        this.Hand.sortByValue();

        //6 discard options
        //save the two highest cards
        if (this.scoreHand() == 0) {
            for (int r = 0; r < 3; r++) {
                discarded[r] = this.Hand.getCard(r);
                this.Hand.removeCard(r);
            }

        }
        //discard lowest card not in the pair to try for 2 pairs
        else if (this.Hand.numPairs()==1) {
            if (this.Hand.getCard(0).getValue() != this.Hand.getCard(1).getValue() && this.Hand.getCard(0).getValue() < 6) {
                discarded[0] = this.Hand.getCard(0);
                this.Hand.removeCard(0);
            }else if (this.Hand.getCard(1).getValue() != this.Hand.getCard(2).getValue() && this.Hand.getCard(1).getValue() < 6) {
                discarded[0] = this.Hand.getCard(1);
                this.Hand.removeCard(1);
            }else if (this.Hand.getCard(2).getValue() != this.Hand.getCard(3).getValue() && this.Hand.getCard(2).getValue() < 6) {
                discarded[0] = this.Hand.getCard(2);
                this.Hand.removeCard(2);
            }else if (this.Hand.getCard(3).getValue() != this.Hand.getCard(4).getValue() && this.Hand.getCard(3).getValue() < 6) {
                discarded[0] = this.Hand.getCard(3);
                this.Hand.removeCard(3);
            }else if (this.Hand.getCard(4).getValue() < 6) {
                discarded[0] = this.Hand.getCard(4);
                this.Hand.removeCard(4);
            }
        }
        //discard lowest card if not > 6, to try for a high card
        else if (this.Hand.numPairs()==2) {
            if (this.Hand.getCard(0).getValue() != this.Hand.getCard(1).getValue()) {
                if (this.Hand.getCard(0).getValue() < 6) {
                    discarded[0] = this.Hand.getCard(0);
                    this.Hand.removeCard(0);
                }
            } else if (this.Hand.getCard(3).getValue() != this.Hand.getCard(4).getValue()) {
                if (this.Hand.getCard(4).getValue() < 6) {
                    discarded[4] = this.Hand.getCard(4);
                    this.Hand.removeCard(4);
                }
            } else {
                if (this.Hand.getCard(2).getValue() < 6) {
                    discarded[2] = this.Hand.getCard(2);
                    this.Hand.removeCard(2);
                }
            }
            //else don't discard any

        }
        //discard lowest card not in the 3 pair to try for high card or full house
        else if (this.Hand.hasTriplet()) {
            if (this.Hand.getCard(0).getValue() != this.Hand.getCard(1).getValue()) {
                if (this.Hand.getCard(0).getValue() < 6) {
                    discarded[0] = this.Hand.getCard(0);
                    this.Hand.removeCard(0);
                }
            } else {
                if (this.Hand.getCard(3).getValue() < 6) {
                    discarded[3] = this.Hand.getCard(3);
                    this.Hand.removeCard(3);
                } else if (this.Hand.getCard(4).getValue() < 6) {
                    discarded[4] = this.Hand.getCard(4);
                    this.Hand.removeCard(4);
                }
            }

        }
        //discard lowest card not in the 4 pair if not > 6, to try for a high card
        else if (this.Hand.hasFourOfAKind()) {

            if (this.Hand.getCard(0).getValue() != this.Hand.getCard(1).getValue()) {
                if (this.Hand.getCard(0).getValue() < 6) {
                    discarded[0] = this.Hand.getCard(0);
                    this.Hand.removeCard(0);
                }
            } else {
                if (this.Hand.getCard(4).getValue() < 6) {
                    discarded[4] = this.Hand.getCard(4);
                    this.Hand.removeCard(4);
                }
            }
            //else don't discard any
        }
        return discarded;
    }


    //Returns an int score for a hand
	public int scoreHand(){
		int handScore = 0;
		if(this.Hand.hasStraight() && this.Hand.hasFlush()){
			handScore = 8;
		}else if (this.Hand.hasFourOfAKind()){
			handScore = 7;
		}else if (this.Hand.hasFullHouse()){
			handScore = 6;
		}else if (this.Hand.hasFlush()){
			handScore = 5;
		}else if (this.Hand.hasStraight()){
			handScore = 4;
		}else if (this.Hand.hasTriplet()){
			handScore = 3;
		}else if (this.Hand.numPairs()==2){
			handScore = 2;
		}else if (this.Hand.numPairs()==1){
			handScore = 1;
		}
		return handScore;
	}


	//Returns the amount that this player would like to wager, returns
	//-1.0 to fold this.Hand. Any non zero wager should immediately be deducted
	//from this player's balance. This player's balance can never be below
	// 0 at any time. This player's wager must be >= to the parameter min
	
	public double wager(double min) {
        System.out.println("The minimum bet is " + min + ".");
        double bet = -1;

        //4 hand betting levels, check balance/min ratio before betting.
        if (this.scoreHand() == 0) {
            System.out.println("This Hand is trash");
            bet = -1;
        } else if (this.scoreHand() > 0 && this.scoreHand() < 4) {
            System.out.println("This Hand is decent");
            if (min != 0){
                if (bal / min < 4) {
                    bet = min * 2;
                } else {
                    bet = min + min / 2;
                }
            }else{
                bet = (bal/75)+1;
            }
        } else if ((this.scoreHand() >= 4 && this.scoreHand() < 7)) {
            System.out.println("This Hand is good");
            if (min != 0){
                if (bal / min < 5) {
                    bet = min * 4;
                } else {
                    bet = min * 2;
                }
            }else{
                bet = (bal/10)+1;
            }
        } else {
            System.out.println("This Hand is excellent");
            if (min != 0) {
                if (min < bal) {
                    bet = bal;
                } else {
                    bet = -1;
                }
            } else {
                bet = (bal / 2) + 1;
            }
        }

        //even trash deserves a chance
        if(bet == -1 && min == 0){
            System.out.println("even trash deserves a chance");
            bet = (bal/1000)+1;
        }


        //check balance for valid bet.
        if (bet > bal) {
            if (min < bal) {
                bet = bal;
            } else {
                bet = -1;
            }
        }

        //if not folding subtract bet from balance and add to wagersThisRound
        if (bet != -1) {
            bal -= bet;
        }

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
	public void winnings(double amount) {
        bal += amount;
        this.Hand.clear();
    }

}