package com.blackjack.card;

import java.util.Random;

public class DeckTest
{
	/**
	 * Used for shuffling the deck.
	 */
	private Random rand = new Random();
	/**
	 * An array that stores all of the cards should be 52 in size.
	 */
	private Card[] cards;
	private int index;
	
	/**
	 * A class that stores all of the cards in an array. I don't actually use this class I just used it to help explain
	 * the deck class to someone so there may be some errors in it.
	 * 
	 * @author Derek King
	 * @author Ethan Chase
	 * @version 1.0.0
	 */
	public DeckTest()
	{
		int i = 0;
		cards = new Card[52];
		
		for (Suit suit : Suit.values())
		{
			for (CardType cardType : CardType.values())
			{
				cards[i] = new Card(suit, cardType);
				i++;
				
				if (i > 51)
				{
					System.out.println("The deck size is invalid. Too many suits or card types?");
					return;
				}
			}
		}
	}
	
	/**
	 * Returns a card from the deck and increases the index by one.
	 * 
	 * @return The next card in the array.
	 */
	public Card draw()
	{
		return cards[index++];
	}
	
	/**
	 * Shuffles the deck by swapping cards.
	 */
	public void shuffle()
	{
		for (int i = 0; i < cards.length; i++)
		{
			int random = rand.nextInt(cards.length);
			Card indexCard = cards[i];
			Card swapCard = cards[random];
			
			cards[i] = swapCard;
			cards[random] = indexCard;
		}
	}
}
