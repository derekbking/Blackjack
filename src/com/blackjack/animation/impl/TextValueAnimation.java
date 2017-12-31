package com.blackjack.animation.impl;

import javax.swing.text.JTextComponent;

import com.blackjack.animation.Animation;
import com.blackjack.animation.Callback;

/**
 * This class is an extension of the abstract animation class. This animation works on any JTextComponent.
 * 
 * @author Derek King
 * @author Ethan Chase
 * @version 1.0.0
 */
public class TextValueAnimation extends Animation
{
	/**
	 * How long the animation should last and is used to calculate how far into the animation the class is.
	 */
	private long duration;
	/**
	 * The prefix of the JTextComponent to be displayed before the value of the Component.
	 */
	private String prefix;
	/**
	 * The starting value of the animation.
	 */
	private int startValue;
	/**
	 * What the value of the component should be when it's finished.
	 */
	private int endValue;
	
	/**
	 * The difference in startValue to endValue
	 */
	private int deltaValue;
	
	/**
	 * The constructor of the TextValueAnimation which just sets the global variables of this class.
	 * 
	 * @param component The component that needs to be animated.
	 * @param duration How long this animation should take.
	 * @param prefix The prefix to be displayed before the value of that animation.
	 * @param startValue The starting value of the animation.
	 * @param endValue The value of the animation once it has been finished.
	 * @param callbacks An array of callbacks that will be told when this animation starts and finishes.
	 */
	public TextValueAnimation(JTextComponent component, long duration, String prefix, int startValue, int endValue, Callback... callbacks)
	{
		super(component, callbacks);
		
		this.duration = duration;
		this.prefix = prefix;
		this.startValue = startValue;
		this.endValue = endValue;
		
		this.deltaValue = endValue - startValue;
	}
	
	/**
	 * Called by the animation manager so that the animation can be updated.
	 */
	public void run()
	{
		super.run();
		
        float progress = (System.currentTimeMillis() - getStartTime()) / (float) duration;
        int value = (int) ((deltaValue * (progress)) + startValue);
        
        ((JTextComponent) getComponent()).setText(prefix + value);

        if (progress >= 1.0F)
        {
        	((JTextComponent) getComponent()).setText(prefix + endValue);
        	onComplete();
        }
	}
	
	/**
	 * Get the final value of the animation
	 * 
	 * @return The final value of the component.
	 */
	public int getEndValue()
	{
		return endValue;
	}
}
