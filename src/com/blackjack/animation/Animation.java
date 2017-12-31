package com.blackjack.animation;

import javax.swing.JComponent;

import com.blackjack.Blackjack;

/**
 * All animations will extends this class. It just defines basic methods so that the AnimationManager can handle all types
 * of animations without specifically implementing it.
 * 
 * @author Derek King
 * @author Ethan Chase
 * @version 1.0.0
 */
public abstract class Animation
{
	/**
	 * All animations have a component that they apply to.
	 */
	private JComponent component;
	
	/**
	 * All animations should have to option to have things execute upon completion.
	 */
	private Callback[] callbacks;
	
	/**
	 * The time that the animation started used for calculation that state of the animation.
	 */
	private long start;
	
	/**
	 * The constructor only saves the arguments as global variables.
	 * 
	 * @param component The component that should be animating.
	 * @param callbacks An array of callbacks that should be executed upon completion.
	 */
	public Animation(JComponent component, Callback...callbacks)
	{
		this.component = component;
		this.callbacks = callbacks;
	}
	
	/**
	 * One animation tick. All animations should probably overwrite this method.
	 */
	public void run()
	{
		if (start == 0)
		{
			onStart();
			start = System.currentTimeMillis();
		}
	}

	/**
	 * A getter for the component that is animating.
	 * 
	 * @return The component that is animating.
	 */
	public JComponent getComponent()
	{
		return component;
	}
	
	/**
	 * Get the starting time of the animation.
	 * 
	 * @return The time that the animation started.
	 */
	public long getStartTime()
	{
		return start;
	}
	
	/**
	 * Notify all of the observers that the animation has started.
	 */
	protected void onStart()
	{
		for (Callback callback : callbacks)
		{
			callback.onStart();
		}
	}
	
	/**
	 * Notify all of the observers that the animation has finished.
	 */
	protected void onComplete()
	{
		for (Callback callback : callbacks)
		{
			callback.onComplete();
		}
        Blackjack.getAnimationManager().cancelAnimation(this);
	}
}
