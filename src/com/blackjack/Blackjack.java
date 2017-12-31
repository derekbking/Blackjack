package com.blackjack;

import com.blackjack.animation.AnimationManager;
import com.blackjack.animation.Callback;
import com.blackjack.animation.CardComponent;
import com.blackjack.animation.ValueComponent;
import com.blackjack.animation.impl.PositionAnimation;
import com.blackjack.animation.impl.TextValueAnimation;
import com.blackjack.card.Card;
import com.blackjack.card.Deck;
import com.blackjack.player.Dealer;
import com.blackjack.player.Player;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URLClassLoader;

/**
 * The main class for the game. A lot of the logic for the game in here as well as the interface for the game as this class
 * extends JFrame.
 * 
 * @author Derek King
 * @author Ethan Chase
 * @version 1.0.0
 */
public class Blackjack extends JFrame implements ActionListener
{
	/**
	 * A variable representing the width of the JFrame so that if I want to change it I only need to change the width in
	 * one location. The JFrame is not resizable so it's safe to use this variable when calculating the position of
	 * elements in the container.
	 */
    private static final int WIDTH = 1000;
    /**
     * A variable representing the height of the JFrame so that if I want to change it I only need to change the height
     * in one location. The JFrame is not resizable so it's safe to use this variable when calculating the position of
     * elements in the container.
     */
    private static final int HEIGHT = 800;

    /**
     * A variable used when creating cards so that if I decided the change the height of the card I can just do it here.
     */
    private static final int CARD_HEIGHT = 160;
    /**
     * A variable used when creating cards so that if I decided the change the width of the card I can just do it here.
     */
    private static final int CARD_WIDTH = 110;

    /**
     * This variable is used when setting the title of the game and anywhere else the name of this game will be needed.
     */
    private static final String NAME = "Black Jack";

    /**
     * Used to determine how many JLabels I will need per player.
     */
    private static final int MAX_HAND = 5;

    /**
     * The verify that all colors are consistent I always use this variable for setting the color and if I need to
     * change it all I would have to do is change it here.
     */
    private static final Color FONT_COLOR = new Color(212, 255, 200);
    /**
     * The verify that all colors are consistent I always use this variable for setting the color and if I need to
     * change it all I would have to do is change it here.
     */
    private static final Color BACKGROUND_COLOR = new Color(19, 127, 63);

    /**
     * The AnimationManager is a new thread that handles all of the animations for the game.
     */
    private static final AnimationManager animationManager = new AnimationManager();

    /**
     * To avoid creating unneeded Icon objects I create the ImageIcon here and use it whenever I need to only show
     * the back side of the card.
     */
    private ImageIcon defaultIcon = new ImageIcon(URLClassLoader.getSystemResource("back.png"));

    /**
     * The container of the JFrame where all of the JComponents will be added to
     */
    private Container container;

    /**
     * This is the deck where all of the cards will be drawn from. This JLabel is used as the origin for some of
     * the animations.
     */
    private JLabel deckCard;

    /**
     * CardComponent is a class that extends JLabel that just adds some variable to assist with animations.
     * Each elements in this array is used to display the cards the dealer has in their hand.
     */
    private CardComponent[] dealerCards;
    /**
     * CardComponent is a class that extends JLabel that just adds some variable to assist with animations.
     * Each elements in this array is used to display the cards the player has in their hand.
     */
    private CardComponent[] userCards;

    /**
     * A class that keeps track of all of the cards in the deck.
     */
    private Deck deck;

    /**
     * This class keeps track of the cards the player has.
     */
    private Player user;
    /**
     * This class keeps track of the cards the dealer has.
     */
    private Dealer dealer;

    /**
     * Value component is similar to CardComponent in that it assists me with animations.
     */
    private ValueComponent userScore;
    /**
     * Value component is similar to CardComponent in that it assists me with animations.
     */
    private ValueComponent dealerScore;

    /**
     * This JTextField display information about who won the game.
     */
    private JTextField gameStatus;

    /**
     * This JButton allows the player to hit, an action listener is attached to this JButton add functionality to it.
     */
    private JButton hit;
    /**
     * This JButton allows the player to stay, an action listener is attached to this JButton add functionality to it.
     */
    private JButton stay;
    /**
     * This JButton allows the player to reset the board, a action listener is attached to this JButton add
     * functionality to it.
     */
    private JButton reset;

    /**
     * The constructor for the Blackjack class. This will setup all the necessary components of the game.
     */
    public Blackjack()
    {
        animationManager.start();

        defaultIcon = new ImageIcon(URLClassLoader.getSystemResource("back.png"));
        container = getContentPane();

        configureContentPane();
        createFrameComponents();
        configureFrameComponents();
        setupGame();
    }

    /**
     * This method will configure the content pane.
     */
    private void configureContentPane()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setBounds((int) screenSize.getWidth() / 2 - (WIDTH / 2), (int) screenSize.getHeight() / 2 - (HEIGHT / 2), WIDTH, HEIGHT);
        setLayout(null);
        setTitle(NAME);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        container.setBackground(BACKGROUND_COLOR);
    }

    /**
     * Adds all of the components to the JFrame. The components are not positioned until later
     */
    private void createFrameComponents()
    {
        deckCard = new JLabel();
        dealerCards = new CardComponent[MAX_HAND];
        userCards = new CardComponent[MAX_HAND];

        userScore = new ValueComponent();
        dealerScore = new ValueComponent();

        gameStatus = new JTextField();

        hit = new JButton();
        stay = new JButton();
        reset = new JButton();

        hit.addActionListener(this);
        stay.addActionListener(this);
        reset.addActionListener(this);

        for (int i = 0; i < MAX_HAND; i++)
        {
            CardComponent dealerCard = new CardComponent();
            CardComponent userCard =  new CardComponent();

            dealerCards[i] = dealerCard;
            userCards[i] = userCard;

            container.add(dealerCard);
            container.add(userCard);
        }

        container.add(deckCard);
        container.add(userScore);
        container.add(dealerScore);
        container.add(hit);
        container.add(stay);
        container.add(reset);
        container.add(gameStatus);
    }

    /**
     * Configures the components so that look how they are suppose to. Including color, position, background,
     * font and any other important settings.
     */
    private void configureFrameComponents()
    {
        dealerScore.setBounds(50, 50 + CARD_HEIGHT + 15, WIDTH, 20);
        dealerScore.setBackground(null);
        dealerScore.setBorder(null);
        dealerScore.setFont(new Font("Arial", 0, 20));
        dealerScore.setForeground(FONT_COLOR);
        dealerScore.setEditable(false);
        userScore.setValue(0);

        userScore.setBounds(50, HEIGHT - ((CARD_HEIGHT - 15) * 2), WIDTH, 20);
        userScore.setBackground(null);
        userScore.setBorder(null);
        userScore.setFont(new Font("Arial", 0, 20));
        userScore.setForeground(FONT_COLOR);
        userScore.setEditable(false);
        userScore.setValue(0);

        hit.setBounds(50, (HEIGHT / 2) - 40, 100, 35);
        hit.setText("Hit");
        hit.setEnabled(true);

        stay.setBounds(50, (HEIGHT / 2) + 5, 100, 35);
        stay.setText("Stay");
        stay.setEnabled(true);

        reset.setBounds(WIDTH - 150, HEIGHT - 85, 100, 35);
        reset.setText("Reset");
        reset.setVisible(false);

        gameStatus.setBounds(50, 5, WIDTH - 100, 40);
        gameStatus.setBackground(null);
        gameStatus.setBorder(null);
        gameStatus.setFont(new Font("Arial", 0, 20));
        gameStatus.setForeground(FONT_COLOR);
        gameStatus.setEditable(false);
        gameStatus.setText("");
        gameStatus.setVisible(false);

        deckCard.setBounds(WIDTH - CARD_WIDTH - 50, HEIGHT / 2 - (CARD_HEIGHT / 2), CARD_WIDTH, CARD_HEIGHT);
        deckCard.setIcon(defaultIcon);

        for (int i = 0; i < dealerCards.length; i++)
        {
            CardComponent label = dealerCards[i];

            label.setSize(CARD_WIDTH, CARD_HEIGHT);

            if (label.getLocation().getX() == 0 && label.getLocation().getY() == 0)
            {
                label.setVisible(false);
                label.setLocation(deckCard.getLocation());
            }
            else
            {
                if (label.isVisible())
                {
                    animationManager.animate(getCardAnimation(label, 200, false, new Callback()
                    {
                        @Override
                        public void onComplete()
                        {
                            label.setVisible(false);
                        }

                        @Override
                        public void onStart()
                        {

                        }
                    }), false);
                }
            }
            label.setOriginalX(50 + (i * (CARD_WIDTH + 15)));
            label.setOriginalY(50);
        }

        for (int i = 0; i < userCards.length; i++)
        {
            CardComponent label = userCards[i];

            label.setSize(CARD_WIDTH, CARD_HEIGHT);

            if (label.getLocation().getX() == 0 && label.getLocation().getY() == 0)
            {
                label.setVisible(false);
                label.setLocation(deckCard.getLocation());
            }
            else
            {
                if (label.isVisible())
                {
                    animationManager.animate(getCardAnimation(label, 200, false, new Callback()
                    {
                        @Override
                        public void onComplete()
                        {
                            label.setVisible(false);
                        }

                        @Override
                        public void onStart()
                        {

                        }
                    }), false);
                }
            }
            label.setOriginalX(50 + (i * (CARD_WIDTH + 15)));
            label.setOriginalY(HEIGHT - CARD_HEIGHT - 75);
        }
    }

    /**
     * Gives the player the next card on the deck.
     * 
     * @param player The player that will receiving the card.
     */
    public void drawCard(Player player)
    {
        player.addCard(deck.draw());      
        updateCard(player, player.getHandIndex() - 1);
        updateScore(player);
    }

    /**
     * Given an index this method will make sure that JLabel associated with that index is correctly being displayed
     * 
     * @param player Used to access the players hand of cards.
     * @param index Which card in the hand of cards is needed.
     */
    private void updateCard(Player player, final int index)
    {
        CardComponent cardComponent = (player == dealer ? dealerCards : userCards)[index];
        Card card = player.getCards()[index];
        ImageIcon newIcon = (card == null ? defaultIcon : (index == 0 && !reset.isVisible() && player == dealer ? defaultIcon : card.getIcon()));
        if (player == dealer && index == 0)
        {
        	if (reset.isVisible())
        	{
        		card.setVisible(true);
        	}
        	else
        	{
        		card.setVisible(false);
        	}
         }

        animationManager.animate(getCardAnimation(cardComponent, 200, card != null, new Callback()
        {
            @Override
            public void onComplete()
            {
                if (card == null)
                {
                    cardComponent.setIcon(null);
                    cardComponent.setVisible(false);
                }
            }

            @Override
            public void onStart()
            {
                if (card != null)
                {
                    cardComponent.setIcon(newIcon);
                    cardComponent.setVisible(true);
                }
            }
        }), false);
    }

    /**
     * Called when the score may not be up to date. An animation will be created to update the score.
     * 
     * @param player Used to determine which JLabel will need to be updated.
     */
    private void updateScore(Player player)
    {
        if (player == dealer)
        {
            getAnimationManager().animate(getTextValueAnimation(dealerScore, 350, "Dealer Score: ", dealer.getTotalCardValue()), true);
        }
        else
        {
            getAnimationManager().animate(getTextValueAnimation(userScore, 350, "Player Score: ", user.getTotalCardValue()), true);
        }
    }
    
    /**
     * Returns a TextAnimation, it takes in the needed parameters for a text animation. The purpose of this
     * method is to reduce the amount of times I redo these functions.
     * 
     * @param component The component that should receive the text animation.
     * @param duration How long the animation should last.
     * @param prefix This value will not be animated, but is appended the the beginning of the text.
     * @param endValue What the value should be once the animation is finished.
     * @param callbacks A list of things that should happen when the animation finishes.
     * @return The TextValueAnimation that should be registered to the AnimationManager
     */
    private TextValueAnimation getTextValueAnimation(ValueComponent component, int duration, String prefix, int endValue, Callback... callbacks)
    {
    	int startValue = getAnimationManager().getFinalText(component);
    	
    	if (startValue == Integer.MIN_VALUE)
    	{
    		startValue = component.getValue();
    	}
    	
    	Callback[] newCallbacks = new Callback[callbacks.length + 1];
    	
    	for (int i = 0; i < callbacks.length; i++)
    	{
    		newCallbacks[i] = callbacks[i];
    	}
    	
    	newCallbacks[newCallbacks.length - 1] = new Callback()
        {
            @Override
            public void onComplete()
            {
            	component.setValue(endValue);
            }

            @Override
            public void onStart()
            {

            }
        };
    	
    	return new TextValueAnimation(component, duration, prefix, startValue, endValue, newCallbacks);
    }

    /**
     * Returns a PositionAnimation, it takes in the needed parameters for a position animation.
     * The purpose of this method is to reduce the amount of times I redo these functions.
     * 
     * @param cardComponent The component that needs to be animated.
     * @param duration How long the animation should last.
     * @param add If the animation should start from the card position or the deck position.
     * @param callbacks A list of callbacks that should be called when the animation has finished.
     * @return The PositionAnimation that should be registered to the AnimationManger so that animation is actually run.
     */
    private PositionAnimation getCardAnimation(CardComponent cardComponent, int duration, boolean add, Callback... callbacks)
    {
        Point start = getAnimationManager().getFinalLocation(cardComponent);
        Point target;

        if (add)
        {
            target = new Point(cardComponent.getOriginalX(), cardComponent.getOriginalY());
        }
        else
        {
            target = deckCard.getLocation();
        }

        if (start == null)
        {
            if (add)
            {
                start = cardComponent.getLocation();
            }
            else
            {
                start = new Point(cardComponent.getOriginalX(), cardComponent.getOriginalY());
            }
        }

        return new PositionAnimation(cardComponent, duration, (int) start.getX(), (int) start.getY(), (int) target.getX(), (int) target.getY(), callbacks);
    }

    /**
     * Called when the game needs to be restarted.
     */
    private void setupGame()
    {
        deck = new Deck(true);
        user = new Player(MAX_HAND);
        dealer = new Dealer(MAX_HAND);

        drawCard(user);
        drawCard(user);
        drawCard(dealer);
        drawCard(dealer);
    }

    /**
     * Updates the components for when the game ends such as showing the reason that the game is over, showing the
     * reset button.
     * 
     * @param reason
     */
    public void endGame(String reason)
    {
        hit.setEnabled(false);
        stay.setEnabled(false);
        gameStatus.setText(reason);
        gameStatus.setVisible(true);
        reset.setVisible(true);

        updateCard(dealer, 0);
        updateScore(dealer);
    }

    /**
     * When the player stays execute the dealers turns.
     */
    private void stay()
    {
        while (dealer.shouldHit())
        {
            drawCard(dealer);
        }

        if (dealer.getTotalCardValue() == user.getTotalCardValue())
        {
            endGame("The dealer has won.");
        }
        else if (dealer.getTotalCardValue() > 21)
        {
            endGame("The dealer busted.");
        }
        else if (dealer.getTotalCardValue() > user.getTotalCardValue())
        {
            endGame("The dealer has won.");
        }
        else if (user.getTotalCardValue() > dealer.getTotalCardValue())
        {
            endGame("You won.");
        }
    }

    /**
     * The method is called when the program is started. All the method does is call the Blackjack constructor.
     * 
     * @param args No arguments are taken into account in this application. No arguments should be passed in.
     */
    public static void main(String... args)
    {
        new Blackjack();
    }

    /**
     * Called from other classes to access the animation manager.
     * 
     * @return The AnimationManager. There should only be one per application.
     */
    public static AnimationManager getAnimationManager()
    {
        return animationManager;
    }

    @Override
    /**
     * This method handles all of the actions produced by various components in the container.
     * 
     * @param e Information about what component has been clicked.
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() instanceof JButton)
        {
            JButton clicked = (JButton) e.getSource();

            if (clicked == hit)
            {
                drawCard(user);

                if (user.getTotalCardValue() == 21)
                {
                    stay();
                }
                else if (user.getTotalCardValue() > 21)
                {
                    endGame("You busted.");
                }
                else if (user.getHandIndex() == 5)
                {
                    stay();
                }
            }
            else if (clicked == stay)
            {
                stay();
            }
            else if (clicked == reset)
            {
                configureFrameComponents();
                setupGame();
            }
        }
    }
}
