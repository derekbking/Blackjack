package com.blackjack.animation.impl;

import java.awt.Point;

import javax.swing.JComponent;

import com.blackjack.animation.Animation;
import com.blackjack.animation.Callback;

/**
 * The is an actual implementation of the animation class. This animation will move a component to a target location
 * over a given period of time.
 * 
 * @author Derek King
 * @author Ethan Chase
 * @version 1.0.0
 */
public class PositionAnimation extends Animation
{
	/**
	 * How long the animation should last in milliseconds.
	 */
	private long duration;
	/**
	 * The start x location.
	 */
	private int startX;
	/**
	 * The start y location.
	 */
	private int startY;
	/**
	 * The x value of where the component should end up.
	 */
	private int endX;
	/**
	 * The y value of where the component should end up.
	 */
	private int endY;

	/**
	 * Calculate the change in x from the start to end.
	 */
	private int totalDeltaX;
	/**
	 * Calculate the change in y from the start to end.
	 */
	private int totalDeltaY;

	/**
	 * The constructor for the PositionAnimation class.
	 * 
	 * @param component The component that will be animated.
	 * @param duration How long the animation should last.
	 * @param startX What the x value of the component should be at deltaTime = 0.
	 * @param startY What the y value of the component should be at deltaTime = 0.
	 * @param endX What the x value should be once the deltaTime = duration.
	 * @param endY What the y value should be once the deltaTime = duration.
	 * @param callbacks A list of observers that should be notified when the animation starts and ends.
	 */
	public PositionAnimation(JComponent component, long duration, int startX,
			int startY, int endX, int endY, Callback... callbacks)
	{
		super(component, callbacks);

		this.duration = duration;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;

		this.totalDeltaX = endX - startX;
		this.totalDeltaY = endY - startY;
	}
	
	/**
	 * Called by the AnimationManager so that the position is update frequently.
	 */
	public void run()
    {
		super.run();
		
        float progress = (System.currentTimeMillis() - getStartTime()) / (float) duration;
        float x = (totalDeltaX * (progress)) + startX;
        float y = (totalDeltaY * (progress)) + startY;

        getComponent().setLocation((int) (totalDeltaX > 0 ? Math.min(x, endX) : Math.max(x, endX)), (int) (totalDeltaY > 0 ? Math.min(y, endY) : Math.max(y, endY)));

        if (progress >= 1.0F)
        {
            getComponent().setLocation(endX, endY);
        	onComplete();
        }
    }

	/**
	 * The AnimationMananger will use this method to figure out where an component will be after all of the animations finish.
	 * 
	 * @return
	 */
    public Point getEndPoint()
    {
        return new Point(endX, endY);
    }
}
