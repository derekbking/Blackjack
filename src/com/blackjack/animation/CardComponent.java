package com.blackjack.animation;

import javax.swing.*;

/**
 * This component extends JLabel and assists with animations so that it's possible to figure out the origin of animations.
 * 
 * @author Derek King
 * @author Ethan Chase
 * @version 1.0.0
 */
public class CardComponent extends JLabel
{
	/**
	 * The original x value of the component.
	 */
    private int originalX;
    /**
     * The original y value of the component.
     */
    private int originalY;

    /**
     * The getter for the original x value of the component.
     * 
     * @return T The original x value of the component.
     */
    public int getOriginalX()
    {
        return originalX;
    }

    /**
     * Allows you to change the original location of the component.
     * 
     * @param originalX The new origin for the x value of the component.
     */
    public void setOriginalX(int originalX)
    {
        this.originalX = originalX;
    }

    /**
     * The getter for the original y value of the component.
     * 
     * @return T The original y value of the component.
     */
    public int getOriginalY()
    {
        return originalY;
    }

    /**
     * Allows you to change the original location of the component.
     * 
     * @param originalY The new origin for the y value of the component.
     */
    public void setOriginalY(int originalY)
    {
        this.originalY = originalY;
    }
}
