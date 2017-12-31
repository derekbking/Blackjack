package com.blackjack.animation;

import javax.swing.JTextField;

/**
 * This component extends JTextField and assists with animations so that it's possible to figure out the actual value of
 * the component.
 * 
 * @author Derek King
 * @author Ethan Chase
 * @version 1.0.0
 */
public class ValueComponent extends JTextField
{
	/**
	 * What the value might be animating to. This variable is safe to use when checking that value of the field.
	 */
	private int value;

	/**
	 * A getter for the actual non animated value of the component.
	 * 
	 * @return The value of the component once it's completed animating
	 */
    public int getValue()
    {
        return value;
    }

    /**
     * A setter to change the value displayed in the JTextField
     * 
     * @param value The new value of the JTextField.
     */
    public void setValue(int value)
    {
        this.value = value;
    }
}
