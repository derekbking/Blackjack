package com.blackjack.card;

import javax.swing.*;

import java.net.URLClassLoader;

/**
 * A data class containing information about the card.
 * 
 * @author Derek King
 * @author Ethan Chase
 * @version 1.0.0
 */
public class Card
{
	/**
	 * The suit of the card.
	 */
    private Suit suit;
    /**
     * The type that the card is. i.e King, Queen, Ace or 10.
     */
    private CardType cardType;
    /**
     * The icon of the card, this will be set in the constructor based on the suit and card type.
     */
    private ImageIcon icon;
    /**
     * Useful for the dealer, but can be used for other features in the future. In this programs case the first two
     * cards for the dealer are not visible.
     */
    private boolean visible;

    /**
     * This calls the real constructor with a default value for whether or not the card is visible.
     * 
     * @param suit
     * @param cardType
     */
    public Card(Suit suit, CardType cardType)
    {
    	this(suit, cardType, true);
    }
    
    /**
     * The constructor for the card class which just stores most of the arguments to global variables and creates
     * a new image icon for the main class to use on JComponents.
     * 
     * @param suit
     * @param cardType
     * @param visable
     */
    public Card(Suit suit, CardType cardType, boolean visable)
    {
    	this.suit = suit;
        this.cardType = cardType;
        this.visible = visable;
        try {
            icon = new ImageIcon(URLClassLoader.getSystemResource(cardType.toString() + "_" + suit.toString() + ".png"));
        }
        catch (NullPointerException e)
        {
            System.out.println("Missing card: " + cardType.toString() + "_" + suit.toString() + ".png");
        }
    }

    /**
     * This method is a getter for the card type variable.
     * 
     * @return The type that this card is.
     */
    public CardType getType()
    {
        return cardType;
    }

    /**
     * A getter for the icon of this card.
     * 
     * @return The icon of this card which is generated in the constructor.
     */
    public ImageIcon getIcon()
    {
        return icon;
    }
    
    /**
     * A setter for the visible variable
     * 
     * @param visable Whether or not the user should be able to see the front face of this card. The main class will
     * check if a card is visible and set the icon to back instead of the image icon for this class.
     */
    public void setVisible(boolean visable)
    {
    	this.visible = visable;
    }
    
    /**
     * A getter for the global variable visible.
     * 
     * return Whether or not the user should be able to see the front face of this card. The main class will check
     * if a card is visible and set the icon to back instead of the image icon for this class.
     */
    public boolean isVisible()
    {
    	return visible;
    }
}
