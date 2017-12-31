package com.blackjack.player;

import com.blackjack.card.Card;
import com.blackjack.card.CardType;

/**
 * The player class stores the cards in the players hand and has a method to calculate the value of those cards.
 * 
 * @author Derek King
 * @author Ethan Chase
 * @version 1.0.0
 */
public class Player
{
	/**
	 * How big the hand should be.
	 */
	private final int HAND_SIZE;

	/**
	 * The index that the next cards should be put into.
	 */
	private int handIndex;

	/**
	 * An array that stores all of the players cards.
	 */
	private Card[] cards;

	/**
	 * The constructor for the player class which set some global variables for later use.
	 * 
	 * @param handSize How big the players hand should be.
	 */
	public Player(int handSize)
	{
		this.HAND_SIZE = handSize;
		cards = new Card[HAND_SIZE];
	}

	/**
     * Adds a card to the players hand if the players hand and verifies that it can actually be added to the
     * hand by checking the handIndex variable.
	 * 
	 * @param card The card that is to be added to the hand
	 */
	public void addCard(Card card)
	{
		if (handIndex < 5)
		{
			cards[handIndex] = card;
			handIndex++;
		}
	}

	/**
	 * A getter for the hand index.
	 * 
	 * @return The index of where the next card is going to go.
	 */
	public int getHandIndex()
	{
		return handIndex;
	}

	/**
	 * Returns all of the cards that the player has.
	 * 
	 * @return The array of all cards in the players hand.
	 */
	public Card[] getCards()
	{
		return cards;
	}

	/**
	 * Calculates the total value of the player's hand.
	 * 
	 * @return The value of the player's hand.
	 */
	public int getTotalCardValue()
	{
		int aceIndex = -1;
		int total = 0;

		for (int i = 0; i < cards.length; i++)
		{
			Card card = cards[i];

			if (card != null)
			{
				if (card.isVisible())
				{
					if (card.getType() == CardType.ACE)
					{
						aceIndex = i;
						continue;
					}

					total += card.getType().getValues()[0];
				}
			}
		}

		if (aceIndex != -1)
		{
			Card card = cards[aceIndex];
			int valueToAdd = 1;

			for (Integer value : card.getType().getValues())
			{
				if (total + value == 21)
				{
					valueToAdd = value;
					break;
				}
			}
			
			System.out.println("Ace Value: " + valueToAdd);
			
			total += valueToAdd;
		}

		return total;
	}
}
