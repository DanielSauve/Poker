import java.util.*;


/**
 * A poker hand is a list of cards, which can be of some "kind" (pair, straight, etc.)
 * 
 */
public class Hand implements Comparable<Hand> {

    public enum Kind {HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, 
        FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH}

    private final List<Card> cards;

    /**
     * Create a hand from a string containing all cards (e,g, "5C TD AH QS 2D")
     */
    public Hand(String c) {
        cards = new ArrayList<Card>();
        String[] cardsList = c.split("\\s+");
        for (String s: cardsList)
        	cards.add(new Card(s));
    }
    
    /**
     * @returns true if the hand has n cards of the same rank
	 * e.g., "TD TC TH 7C 7D" returns True for n=2 and n=3, and False for n=1 and n=4
     */
    protected boolean hasNKind(int n) {
    	HashMap<String, Integer> ranks = new HashMap<String, Integer>();
    	String name = new String();
    	for(Card card:cards){
    		name = card.getRank().name();
    		if (ranks.containsKey(name))
    		{
    			ranks.put(name, ranks.get(name)+1);
    		}
    		else
    		{
    			ranks.put(name, 1);
    		}
    	}
    	return ranks.containsValue(n);
    }
    
    /**
	 * Optional: you may skip this one. If so, just make it return False
     * @returns true if the hand has two pairs
     */
    public boolean isTwoPair() {
    	HashMap<String, Integer> ranks = new HashMap<String, Integer>();
    	String name = new String();
    	for(Card card:cards){
    		name = card.getRank().name();
    		if (ranks.containsKey(name))
    		{
    			ranks.put(name, ranks.get(name)+1);
    		}
    		else
    		{
    			ranks.put(name, 1);
    		}
    	}
    	Object[] tpCheck = ranks.values().toArray();
    	int twoCount = 0;
    	for(Object o:tpCheck)
    	{
    		if (o.equals(2))
    		{
    			twoCount++;
    		}
    	}
    	return twoCount==2;
    }   
    
    /**
     * @returns true if the hand is a straight 
     */
    public boolean isStraight() {
    	ArrayList<Integer> numbers = new ArrayList<Integer>();
    	for (Card card:cards)
    	{
    		numbers.add(card.getRank().ordinal());
    	}
    	Collections.sort(numbers);
    	for(int i = 1; i<5; i++)
    	{
    		if(numbers.get(i)-numbers.get(0)!=i)
    		{
    			return false;
    		}
    	}
        return true;
    }
    
    /**
     * @returns true if the hand is a flush
     */
    public boolean isFlush() {
    	String flush = cards.get(0).getSuit().name();
    	for(Card card:cards)
    	{
    		if(!flush.equals(card.getSuit().name()))
    			return false;
    	}
        return true;
    }
    
    @Override
    public int compareTo(Hand h) {
        //hint: delegate!
		//and don't worry about breaking ties
    	return 1;
    }
    
    /**
	 * This method is already implemented and could be useful! 
     * @returns the "kind" of the hand: flush, full house, etc.
     */
    public Kind kind() {
        if (isStraight() && isFlush()) return Kind.STRAIGHT_FLUSH;
        else if (hasNKind(4)) return Kind.FOUR_OF_A_KIND; 
        else if (hasNKind(3) && hasNKind(2)) return Kind.FULL_HOUSE;
        else if (isFlush()) return Kind.FLUSH;
        else if (isStraight()) return Kind.STRAIGHT;
        else if (hasNKind(3)) return Kind.THREE_OF_A_KIND;
        else if (isTwoPair()) return Kind.TWO_PAIR;
        else if (hasNKind(2)) return Kind.PAIR; 
        else return Kind.HIGH_CARD;
    }

}
