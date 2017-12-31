package com.blackjack.player;

import com.blackjack.card.Card;

/**
 * The dealer extends player and only sets the cards visibility to false when needed and adds a should hit method.
 * 
 * @author Derek King
 * @author Ethan Chase
 * @version 1.0.0
 */
public class Dealer extends Player
{
	/**
	 * The constructor for the dealer class.
	 * 
	 * @param handSize How many cards the player should be able to have. This is handled by the dealers super
	 * class player.
	 */
    public Dealer(int handSize)
    {
        super(handSize);
    }
    
    /**
     * Adds a card to the players hand if the players hand index is 1 then the card shouldn't be visible.
     * 
	 * @param card The card that should be added to the players hand.
     */
    @Override
    public void addCard(Card card)
    {
    	super.addCard(card);
    	
    	if (getHandIndex() == 1)
    	{
    		getCards()[0].setVisible(false);
    	}
    }
    
    /**
     * A boolean that represents whether or not the dealer should hit or stay.
     * 
     * @return A boolean of whether or not the dealer should hit.
     */
    public boolean shouldHit()
    {
    	return getTotalCardValue() < 16;
    }
}
