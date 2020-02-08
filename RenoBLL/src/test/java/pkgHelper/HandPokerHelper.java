package pkgHelper;

import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import pkgCore.Card;
import pkgCore.HandPoker;

public class HandPokerHelper {
	
	public static HandPoker SetHand(ArrayList<Card> setCards, HandPoker handpoker) {
		Object t = null;
		try {
			// Load the Class into 'c'
			Class<?> c = Class.forName("pkgCore.HandPoker");
			// Create a new instance 't' from the no-arg Deck constructor
			t = c.newInstance();
			// Load 'msetCardsInHand' with the 'Draw' method (no args);
			Method msetCardsInHand = c.getDeclaredMethod("setCards", new Class[] { ArrayList.class });
			// Change the visibility of 'setCardsInHand' to true *Good Grief!*
			msetCardsInHand.setAccessible(true);
			// invoke 'msetCardsInHand'
			Object oDraw = msetCardsInHand.invoke(t, setCards);

		} catch (ClassNotFoundException x) {
			fail ("Class Not Found Exception Thrown");
		} catch (SecurityException e) {
			fail ("Security Exception Thrown");
		} catch (IllegalArgumentException e) {
			fail ("Illegal Arugment Exception Thrown");
		} catch (InstantiationException e) {
			fail ("Instantiation Excpetion Thrown");
		} catch (IllegalAccessException e) {
			fail ("Illegal Access Exception Thrown");
		} catch (NoSuchMethodException e) {
			fail ("No Such Method Exception Thrown");
		} catch (InvocationTargetException e) {
			fail ("Invocation Target Exception Thrown");
		}
		handpoker = (HandPoker) t;
		return handpoker;
	}
}
