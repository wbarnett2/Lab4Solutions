package pkgGame;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import pkgCore.Card;
import pkgCore.GamePlay;
import pkgCore.HandPoker;
import pkgCore.Player;
import pkgCore.Rule;
import pkgCore.Table;
import pkgEnum.eGame;
import pkgEnum.eRank;
import pkgEnum.eSuit;
import pkgException.DeckException;
import pkgException.HandException;
import pkgHelper.GamePlayHelper;
import pkgHelper.HandPokerHelper;

public class GamePlayTest {

	@Test
	public void GamePlay_Test1() {
		Table t = new Table("Table 1");
		t.AddPlayerToTable(new Player("Bert"));
		t.AddPlayerToTable(new Player("Joe"));
		t.AddPlayerToTable(new Player("Jim"));
		t.AddPlayerToTable(new Player("Jane"));

		Rule rle = new Rule(eGame.TexasHoldEm);
		GamePlay gp = new GamePlay(t, rle);
		
		ArrayList<Card> p1Cards = new ArrayList<Card>();
		p1Cards.add(new Card(eSuit.HEARTS, eRank.TWO));
		p1Cards.add(new Card(eSuit.HEARTS, eRank.THREE));
		
		Player p1 = new Player("Bert");
		Player p2 = new Player("Joe");
		
		ArrayList<Card> p2Cards = new ArrayList<Card>();
		p2Cards.add(new Card(eSuit.HEARTS, eRank.ACE));
		p2Cards.add(new Card(eSuit.DIAMONDS, eRank.ACE));
		
		ArrayList<Card> commonCards = new ArrayList<Card>();
		commonCards.add(new Card(eSuit.HEARTS, eRank.THREE));
		commonCards.add(new Card(eSuit.HEARTS, eRank.FOUR));
		commonCards.add(new Card(eSuit.HEARTS, eRank.FIVE));

		
		gp = GamePlayHelper.setCommonCards(gp,  commonCards);
		
		HandPoker hp1 = HandPokerHelper.SetHand(p1Cards, new HandPoker(p1,gp));
		HandPoker hp2 = HandPokerHelper.SetHand(p2Cards, new HandPoker(p2,gp));
		
		gp = GamePlayHelper.PutGamePlay(gp, p1.getPlayerID(), hp1);		
		gp = GamePlayHelper.PutGamePlay(gp, p2.getPlayerID(), hp2);
		
		try {
			gp.EvaluateGameHands();
		} catch (HandException e) {
			fail("Evaluate hands failed");
		}

	}

}

