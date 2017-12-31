package com.blackjack.card;

/**
 * enums for all valid card types.
 * 
 * @author Derek King
 * @author Ethan Chase
 * @version 1.0.0
 */
public enum CardType
{
	ACE(1, 11),
	TWO(2),
	THREE(3),
	FOUR(4),
	FIVE(5),
	SIX(6),
	SEVEN(7),
	EIGHT(8),
	NINE(9),
	TEN(10),
	JACK(10),
	QUEEN(10),
	KING(10);
	
	
	/**
	 * A list of values this card could be worth.
	 */
	private Integer[] values;
	
	/**
	 * The constructor for the enum allowing values to be attached to the enum.
	 * 
	 * @param values The values that the card type could be worth depending on what is best for the player.
	 */
	CardType(Integer... values)
	{
		this.values = values;
	}
	
	/**
	 * A getter for the values of the enum.
	 * 
	 * @return The values that the card type could be worth.
	 */
	public Integer[] getValues()
	{
		return values;
	}
}
