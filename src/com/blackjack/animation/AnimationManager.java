package com.blackjack.animation;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JComponent;

import com.blackjack.animation.impl.PositionAnimation;
import com.blackjack.animation.impl.TextValueAnimation;

/**
 * This class will call all of the registered animations when the should be called. It has the ability to run them
 * concurrently or run one at a time depending on what the animation is for.
 * 
 * @author Derek King
 * @author Ethan Chase
 * @version 1.0.0
 */
public class AnimationManager extends Thread
{
	/**
	 * A list of animations. This list only stores non concurrent animations.
	 */
    private final List<Animation> animations = new ArrayList<>();
    /**
     * This is used to prevent concurrent modification exceptions.
     */
    private final List<Animation> animationRemovalQueue = new ArrayList<>();
    
    /**
     * A map of animations that should be running at the same time. It supports running multiple animations at once, but
     * only one animation per component at time because there is not a case in my program where a component needs two
     * animations running on it.
     */
    private final Map<JComponent, List<Animation>> concurrentAnimations = new HashMap<>();
    /**
     * This is used to prevent concurrent modification exceptions.
     */
    private final List<Animation> concurrentAnimationRemovalQueue = new ArrayList<>();

    /**
     * The main method of this class. It is always running and will call animations as needed.
     */
    @Override
    public void run()
    {
        while (true)
        {
            synchronized (this)
            {
            	for (Animation animation : concurrentAnimationRemovalQueue)
            	{
            		removeConcurrentAnimation(animation.getComponent(), animation);
            	}
            	
                for (Animation animation : animationRemovalQueue)
                {
                    animations.remove(animation);
                }
                
                concurrentAnimationRemovalQueue.clear();
                animationRemovalQueue.clear();

                if (!animations.isEmpty())
                {
                    animations.get(0).run();
                }
                
                for (Entry<JComponent, List<Animation>> entry : concurrentAnimations.entrySet())
                {
                	entry.getValue().get(0).run();
                }
            }
        }
    }

    /**
     * The list of components could be in the process of being iterated over so this method is synchronized the verify that
     * list is not currently being used.
     * 
     * @param animation The animation that should be added to the animation queue.
     * @param concurrent Should the animation run while other animations are running.
     */
    public synchronized void animate(Animation animation, boolean concurrent)
    {
    	if (concurrent)
    	{
    		addConcurrentAnimation(animation.getComponent(), animation);
    	}
    	else
    	{
    		animations.add(animation);
    	}
    }

    /**
     * If an animation should be stopped or removed because it is complete this method should be called. Again this is
     * synchronized because the main loop of this class could be using the list already and a concurrent modification
     * exception would be produced.
     * 
     * @param animation The animation that will be canceled.
     */
    public synchronized void cancelAnimation(Animation animation)
    {
    	if (animations.contains(animation))
    	{
            animationRemovalQueue.add(animation);
    	}
    	else
    	{
    		concurrentAnimationRemovalQueue.add(animation);
    	}
    }
    
    /**
     * The list of components could be in the process of being iterated over so this method is synchronized the verify
     * that that list is not currently being used.
     * 
     * @param component The component to add as a concurrent animation.
     * @param animation The animation that should be applied to the component.
     */
    private synchronized void addConcurrentAnimation(JComponent component, Animation animation)
    {
    	List<Animation> animations = new ArrayList<>();
    	
    	if (concurrentAnimations.containsKey(component))
    	{
    		animations.addAll(concurrentAnimations.get(component));
    	}
    	
    	animations.add(animation);
    	
    	concurrentAnimations.put(component, animations);
    }
    
    /**
     * Used to remove a concurrent animation from the map of concurrent animations. This method is synchronized
     * because it's possible that the main loop is using the removal queue already.
     * 
     * @param component The component that needs to be removed.
     * @param animation Which animation needs to be removed from that component.
     */
    private synchronized void removeConcurrentAnimation(JComponent component, Animation animation)
    {
    	List<Animation> animations = new ArrayList<>();
    	
    	if (concurrentAnimations.containsKey(component))
    	{
    		animations.addAll(concurrentAnimations.get(component));
    	}
    	
    	animations.remove(animation);
    	
    	if (animations.isEmpty())
    	{
    		concurrentAnimations.remove(component);
    	}
    	else
    	{
    		concurrentAnimations.put(component, animations);
    	}
    }
    
    /**
     * Figure out what the text would be when the animation is complete.
     * 
     * @param component The component that the final state needs to be calculated of.
     * @return The value of the text when the animation is complete.
     */
    public int getFinalText(Component component)
    {
    	int value = Integer.MIN_VALUE;
    	
    	if (concurrentAnimations.containsKey(component))
    	{
    		List<Animation> animations = concurrentAnimations.get(component);
    		
    		for (Animation animation : animations)
    		{
    			if (animation instanceof TextValueAnimation)
    			{
        			TextValueAnimation textAnimation = (TextValueAnimation) animation;

        			if (textAnimation.getComponent() == component)
        			{
        				value = textAnimation.getEndValue();
        			}
    			}
    		}
    	}
    	    	
    	return value;
    }

    /**
     * For position animations what the final position of that component is after all animations have been applied.
     * 
     * @param component The component that you want to get the final location of.
     * @return The point that the component will be at once all of the animations have run.
     */
    public Point getFinalLocation(Component component)
    {
        Point finalLocation = null;

        for (Animation animation : animations)
        {
        	if (animation instanceof PositionAnimation)
        	{
        		PositionAnimation posAnimation = (PositionAnimation) animation;
        		
        		if (posAnimation.getComponent() == component)
                {
                    finalLocation = posAnimation.getEndPoint();
                }
        	}
        }

        return finalLocation;
    }
}
