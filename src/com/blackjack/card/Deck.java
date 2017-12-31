package com.blackjack.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class that stores and keeps track of how many cards are in the deck.
 * 
 * @author Derek King
 * @author Ethan Chase
 * @version 1.0.0
 */
public class Deck
{
	/**
	 * The list of cards left in the deck.
	 */
	private List<Card> cards = new ArrayList<>();

	/**
	 * Used for shuffling the deck.
	 */
	private Random rand = new Random();

	/**
	 * The constructor for the deck. It adds 52 cards to the deck and then if shuffle is true the deck will be shuffled.
	 * 
	 * @param shuffle Whether or not the deck should be shuffled in the constructor. 
	 */
	public Deck(boolean shuffle)
	{
		populateCards();

		if (shuffle)
		{
			shuffle();
		}
	}

	/**
	 * Returns a card from the deck and removes it.
	 * 
	 * @return The card that was drawn from the deck.
	 */
	public Card draw()
	{
		if (cards.isEmpty())
		{
			return null;
		}

		Card card = cards.get(0);

		cards.remove(0);

		return card;
	}

	/**
	 * Loops through all of the suits and card types to populate the deck.
	 */
	private void populateCards()
	{
		for (Suit suit : Suit.values())
		{
			for (CardType cardType : CardType.values())
			{
				cards.add(new Card(suit, cardType));
			}
		}
	}

	/**
	 * Shuffles the deck.
	 */
	private void shuffle()
	{
		int deckSize = cards.size();

		List<Card> toShuffle = new ArrayList<>(cards);

		cards.clear();

		for (int i = 0; i < deckSize; i++)
		{
			Card card = toShuffle.get(rand.nextInt(toShuffle.size()));
			cards.add(card);
			toShuffle.remove(card);
		}
	}
}
