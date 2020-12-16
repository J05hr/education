/**
 * An object of type Hand represents a hand of cards.  The
 * cards belong to the class Card.  A hand is empty when it
 * is created, and any number of cards can be added to it.
 */

//import java.util.ArrayList;

public class Hand {
    private Card[] hand;   // The cards in the hand.
    private int count;


    public static void main(String[] args){
        Hand iHand = new Hand();
        iHand.addCard(new Card(2,Card.CLUBS));
        iHand.addCard(new Card(4,Card.SPADES));
        iHand.addCard(new Card(8,Card.DIAMONDS));
        iHand.addCard(new Card(8,Card.DIAMONDS));
        iHand.addCard(new Card(4,Card.SPADES));
        System.out.println("iHand: ");
        iHand.printHand();
        System.out.println("iHand Score: " + iHand.scoreHand());
        System.out.println("iHand numpairs: " + iHand.numPairs());
        System.out.println("iHand triplet: " + iHand.hasTriplet());
        System.out.println("iHand flush: " + iHand.hasFlush());
        System.out.println("iHand fullhouse: " + iHand.hasFullHouse());
        System.out.println("iHand straight: " + iHand.hasStraight());
        System.out.println("iHand fourofakind: " + iHand.hasFourOfAKind());
        System.out.println("iHand highestv: " + iHand.highestValue());
        System.out.println("iHand highestdup: " + iHand.highestDuplicate());
        System.out.println("-------------------------------------------- ");

        Hand pHand = new Hand();
        pHand.addCard(new Card(3,Card.CLUBS));
        pHand.addCard(new Card(4,Card.SPADES));
        pHand.addCard(new Card(8,Card.DIAMONDS));
        pHand.addCard(new Card(8,Card.DIAMONDS));
        pHand.addCard(new Card(4,Card.SPADES));
        System.out.println("pHand: ");
        pHand.printHand();
        System.out.println("pHand Score: " + pHand.scoreHand());
        System.out.println("pHand numpairs: " + pHand.numPairs());
        System.out.println("pHand triplet: " + pHand.hasTriplet());
        System.out.println("pHand flush: " + pHand.hasFlush());
        System.out.println("pHand fullhouse: " + pHand.hasFullHouse());
        System.out.println("pHand straight: " + pHand.hasStraight());
        System.out.println("pHand fourofakind: " + pHand.hasFourOfAKind());
        System.out.println("pHand highestv: " + pHand.highestValue());
        System.out.println("pHand highestdup: " + pHand.highestDuplicate());
        System.out.println("-------------------------------------------- ");

        System.out.println("compare to: "+iHand.compareTo(pHand));
    }


    //Create a hand that is initially empty
    public Hand() {
        hand = new Card[5];
        count = 0;
    }


    //Remove all cards from the hand, leaving it empty.
    public void clear() {
        for(int i=0 ; i<hand.length; i++){ hand[i] = null; }
        count = 0;
    }


    //Add a card to the hand.  It is added at the end of the current hand.
    //@param c the non-null card to be added.
    //@throws NullPointerException if the parameter c is null.
    public void addCard(Card c) {

        for(int i=0 ; i<hand.length; i++){
            if (hand[i] == null){
                hand[i] = c;
                count = count + 1;
                break;
            }
        }
    }


    //Remove a card from the hand, if present.
    // @param c the card to be removed.  If c is null or if the card is not in
    // the hand, then nothing is done.
    public void removeCard(Card c) {

        for(int i=0 ; i<hand.length; i++){
            if (hand[i]!=null && hand[i].equals(c)){
                hand[i] = null;
                count = count-1;
            }
        }

    }


    //Remove the card in a specified position from the hand.
    //@param position the position of the card that is to be removed, where
    //positions are starting from zero.
    //@throws IllegalArgumentException if the position does not exist in
    //the hand, that is if the position is less than 0 or greater than
    //or equal to the number of cards in the hand.
    public void removeCard(int position) {
        if (position < 0 || position >= hand.length)
            throw new IllegalArgumentException("Position does not exist in hand: "
                    + position);
        hand[position] = null;
        count --;
    }


    //Returns the number of cards in the hand.
    public int getCardCount() {
        return count;

    }


    //Gets the card in a specified position in the hand.  (Note that this card
    //is not removed from the hand!)
    //@param position the position of the card that is to be returned
    //@throws IllegalArgumentException if position does not exist in the hand
    public Card getCard(int position) {
        if (position < 0 || position >= hand.length)
            throw new IllegalArgumentException("Position does not exist in hand: "
                    + position);
        return hand[position];
    }


    //Sorts the cards in the hand so that cards of the same suit are
    //grouped together, and within a suit the cards are sorted by value.
    //Note that aces are considered to have the lowest value, 1.
    public void sortBySuit() {
        int size = count;
        int nonnull = 0;
        int index = 0;

        Card[] newHand = new Card[5];
        while (size > 0) {
            if (hand[nonnull] == null) { nonnull = nonnull+1; continue;}
            int pos = nonnull;  // Position of minimal card.
            Card c = hand[nonnull];  // Minimal card.

            for (int i = nonnull+1; i < hand.length; i++) {
                Card c1 = hand[i];
                if (c1 != null){
                    if ( c1.getSuit() < c.getSuit() ||
                            (c1.getSuit() == c.getSuit() && c1.getValue() < c.getValue()) ) {
                        pos = i;
                        c = c1;
                    }
                }
            }
            hand[pos] = null;
            size = size - 1;
            newHand[index++] = c;
            nonnull = 0;
        }
        hand = newHand;
    }


    //Sorts the cards in the hand so that cards of the same value are
    //grouped together.  Cards with the same value are sorted by suit.
    //Note that aces are considered to have the lowest value, 1.
    public void sortByValue() {
        int size = count;
        int nonnull = 0;
        int index = 0;

        Card[] newHand = new Card[5];
        while (size > 0) {
            if (hand[nonnull] == null) { nonnull = nonnull+1; continue;}
            int pos = nonnull;  // Position of minimal card.
            Card c = hand[nonnull];  // Minimal card.

            for (int i = nonnull+1; i < hand.length; i++) {

                Card c1 = hand[i];
                if (c1 != null){
                    if ( c1.getValue() < c.getValue() ||
                            (c1.getValue() == c.getValue() && c1.getSuit() < c.getSuit()) ) {
                        pos = i;
                        c = c1;
                    }
                }
            }
            hand[pos] = null;
            size = size - 1;
            newHand[index++] = c;
            nonnull = 0;
        }
        hand = newHand;
    }


    public void printHand(){
        for(int i=0; i<hand.length; i++){
            if (hand[i] != null){
                System.out.println(hand[i]);
            }
        }
        System.out.println();
    }


    //Returns the number of pairs this hand contains
    //sort, then look for a pair, if found skip next card and look for another pair, else see if next card has a pair
    public int numPairs() {
        sortByValue();
        int pairTally = 0;
        int crd = 0;
        while (crd< hand.length-1){
            if (hand[crd].getValue()==(hand[crd+1].getValue())) {
                pairTally++;
            }else{
                crd--;
            }
            crd+=2;
        }
        return pairTally;
    }

    //Returns a card of the pair, takes high or low argument
    public Card pairCard(char Highlow) {
        sortByValue();
        int pairsFound = 0;
        if (numPairs() == 2 && Highlow == 'L') {
            for (int crd = hand.length - 1; crd > 0; crd--) {
                if (hand[crd].getValue() == (hand[crd - 1].getValue())) {
                    pairsFound++;
                    if (pairsFound == 2){
                        return hand[crd];
                    }
                    crd++;
                }
            }
        } else {
            for (int crd = hand.length - 1; crd > 0; crd--) {
                if (hand[crd].getValue() == (hand[crd - 1].getValue())) {
                    return hand[crd];
                }
            }
        }
        return null;
    }




    //Returns true if this hand has 3 cards that are of the same value
    //Sets a cardTally in the outer loop to check that any card added to tripTally has 2 matches
    public boolean hasTriplet(){
        int tripTally = 0;
        for (int crd = 0; crd < hand.length; crd++) {
            int cardTally = 0;
            for (int rsthnd = crd + 1; rsthnd < hand.length; rsthnd++) {
                if (hand[crd].getValue()==(hand[rsthnd].getValue())) {
                    cardTally++;
                }
            }
            if (cardTally >= 2){
                tripTally++;
            }
        }
        if (tripTally == 1 ){
            return true;
        }else{
            return false;
        }
    }


    //Returns a card of the Triplet
    public Card tripletCard() {
        sortByValue();
        for(int crd =0; crd < hand.length-2; crd++){
            if (hand[crd].getValue()== hand[crd+1].getValue()&& hand[crd+1].getValue()==hand[crd+2].getValue()){
                return hand[crd];
            }
        }
        return null;
    }


    //Returns true if this hand has all cards that are of the same suit
    //Checks all cards for suit equality against index 0
    public boolean hasFlush(){
        int suitTally = 0;
        for (int crd = 0; crd < hand.length; crd++) {
            if (hand[0].getSuit()==(hand[crd].getSuit())) {
                suitTally++;

            }
        }
        if (suitTally == 5 ){
            return true;
        }else{
            return false;
        }
    }


    //Returns true if this hand has 5 consecutive cards of any suit
    //deal with the ace depending on high or low hand
    //sort by value and compare each card to incremented value of position 0
    public boolean hasStraight(){
        sortByValue();
        int orderTally = 0;
        int pnter = hand[0].getValue();
        //deal with ace
        if ((pnter==1) && (hand[1].getValue()>2)) {
            if (hand[4].getValue() == 13) {
                orderTally++;
            }
            pnter = hand[1].getValue();
            for (int crd = 2; crd < hand.length; crd++) {
                if (pnter + 1 == (hand[crd].getValue())) {
                    orderTally++;
                }
                pnter++;
            }
        }else{
            for (int crd = 1; crd < hand.length; crd++) {
                if (pnter + 1 == (hand[crd].getValue())) {
                    orderTally++;
                }
                pnter++;
            }
        }
        if (orderTally == 4){
            return true;
        }else{
            return false;
        }
    }


    //Returns true if this hand has a triplet and a pair of different //values
    public boolean hasFullHouse(){
        if (numPairs()==2 && hasTriplet()){
            return true;
        }else{
            return false;
        }
    }


    //Returns true if this hand has 4 cards that are of the same value
    public boolean hasFourOfAKind(){
        int quadTally = 0;
        for (int crd = 0; crd < hand.length; crd++) {
            int cardTally = 0;
            for (int rsthnd = crd + 1; rsthnd < hand.length; rsthnd++) {
                if (hand[crd].getValue()==(hand[rsthnd].getValue())) {
                    cardTally++;
                }
            }
            if (cardTally == 3){
                quadTally++;
            }
        }
        if (quadTally == 1 ){
            return true;
        }else{
            return false;
        }
    }


    //Returns a card of the four of a kind
    public Card fourOfAKindCard(){
        sortByValue();
        for(int crd =0; crd < hand.length-3; crd++){
            if (hand[crd].getValue()== hand[crd+1].getValue() && 
                    hand[crd+1].getValue()==hand[crd+2].getValue() &&
                    hand[crd+2].getValue()==hand[crd+3].getValue()){
                return hand[crd];
            }
        }
        return null;
    }


    //Returns the card with the highest value in the hand. When there is
    //more than one highest value card, you may return any one of them
    public Card highestValue(){
        sortByValue();
        if (hand[0].getValue() == 1 && (!hasStraight()||(hasStraight() && hand[1].getValue() != 2))){
            return hand[0];
        }else {
            return hand[4];
        }
    }


    //Returns the highest valued Card of any pair or triplet found, null if none.
    //When values are equal, you may return either
    public Card highestDuplicate(){
        sortByValue();

        for (int crd = hand.length-1; crd > 0; crd--) {
            if (hand[crd].getValue()==(hand[crd-1].getValue())) {
                return hand[crd];
            }
        }
        return null;
    }


    //returns high values relevant for tie breakers
    public int highestBreakerValue(int levelsDeep) {
        sortByValue();
        int HandHighestValue;

        if (levelsDeep > 1){
                return hand[5-levelsDeep].getValue();
        }else {
            //highval deal with aces
            if (this.highestValue().getValue() == 1 && (!hasStraight() || (hasStraight() && this.getCard(1).getValue() != 2))) {
                HandHighestValue = 14;
            } else {
                HandHighestValue = this.highestValue().getValue();
            }
            return HandHighestValue;
        }
    }


    //returns high values relevant for dup tie breakers
    public int highestDupBreakerValue(int levelsDeep) {
        int HandHighestDupValue;

        if (levelsDeep == 2) {
            if (this.hasFourOfAKind()) {
                HandHighestDupValue = highestBreakerValue(1);
            } else if (this.hasTriplet() || (numPairs() == 2)) {
                HandHighestDupValue = pairCard('L').getValue();
            }
        }

        //highdup deal with aces and nulls
        if (this.highestDuplicate() == null) {
            HandHighestDupValue = 0;
        } else if (this.highestDuplicate().getValue() == 1 && (!hasStraight() || (hasStraight() && this.getCard(1).getValue() != 2))) {
            HandHighestDupValue = 14;
        }else{
            HandHighestDupValue = this.highestDuplicate().getValue();
        }

        //highquads
        if (this.hasFourOfAKind()) {
            HandHighestDupValue = this.fourOfAKindCard().getValue();
        }

        //hightrips
        if (this.hasTriplet()) {
            HandHighestDupValue = this.tripletCard().getValue();
        }
        return HandHighestDupValue;
    }


    //Returns a int score for a hand
    public int scoreHand(){
        int handScore = 0;
        if(this.hasStraight() && this.hasFlush()){
            handScore = 8;
        }else if (this.hasFourOfAKind()){
            handScore = 7;
        }else if (this.hasFullHouse()){
            handScore = 6;
        }else if (this.hasFlush()){
            handScore = 5;
        }else if (this.hasStraight()){
            handScore = 4;
        }else if (this.hasTriplet()){
            handScore = 3;
        }else if (this.numPairs()==2){
            handScore = 2;
        }else if (this.numPairs()==1){
            handScore = 1;
        }
        return handScore;
    }
    

    //Returns -1 if the instance hand loses to the parameter hand, 0 if
    // the hands are equal, and +1 if the instance hand wins over the
    // parameter hand. Hint: you might want to use some of the methods above
    public int compareTo(Hand h) {

        int dupCheckLevel = 1;
        int checklevel = 1;

        if (h.scoreHand() > this.scoreHand()) {
            return -1;
        } else if (h.scoreHand() < this.scoreHand()) {
            return 1;

        } else {
            while(dupCheckLevel < 3) {
                if (h.highestDupBreakerValue(dupCheckLevel) > this.highestDupBreakerValue(dupCheckLevel)) {
                    return -1;
                } else if (h.highestDupBreakerValue(dupCheckLevel) < this.highestDupBreakerValue(dupCheckLevel)) {
                    return 1;
                } else {
                    dupCheckLevel++;
                }
            }

            while(checklevel < 6) {
                if (h.highestBreakerValue(checklevel) > this.highestBreakerValue(checklevel)) {
                    return -1;
                } else if (h.highestBreakerValue(checklevel) < this.highestBreakerValue(checklevel)) {
                    return 1;
                } else {
                    checklevel++;
                }
            }
        }
        return 0;
    }
    

}