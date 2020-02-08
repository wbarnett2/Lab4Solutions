package pkgCore;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import pkgEnum.eCardCount;
import pkgEnum.eCardDestination;
import pkgEnum.eCardVisibility;
import pkgEnum.eGame;
import pkgEnum.eRank;
import pkgEnum.eSuit;
import pkgException.DeckException;
import pkgException.HandException;

public class TexasHoldemTest {

 
	@Test
	public void GenerateHands() throws DeckException, HandException {
		Table t = new Table("my table");
		Player p1 = new Player("Bert");
		Player p2 = new Player("Joe");
		Player p3 = new Player("Jim");
		
		t.AddPlayerToTable(p1);
		t.AddPlayerToTable(p2);
		t.AddPlayerToTable(p3);
		
		Rule rle = new Rule(eGame.TexasHoldEm);
		GamePlay gp = new GamePlay(t, rle);
		
		
		
		gp.StartGame();

		CardDraw cd1 = new CardDraw(eCardCount.Three, eCardDestination.COMMON, eCardVisibility.EVERYONE);
		gp.Draw(null, cd1);
		gp.EvaluateGameHands();
		
		CardDraw cd2 = new CardDraw(eCardCount.One, eCardDestination.COMMON, eCardVisibility.EVERYONE);
		gp.Draw(null, cd2);
		gp.EvaluateGameHands();
		
		CardDraw cd3 = new CardDraw(eCardCount.One, eCardDestination.COMMON, eCardVisibility.EVERYONE);
		gp.Draw(null, cd3);
		
		gp.EvaluateGameHands();

		
		HandPoker hp1 = gp.GetPlayersHand(p1);
		HandPoker hp2 = gp.GetPlayersHand(p2);
		HandPoker hp3 = gp.GetPlayersHand(p3);
		/*
		try {
			hp1 = hp1.EvaluateHand(hp1);
			hp2 = hp2.EvaluateHand(hp2);
			hp3 = hp3.EvaluateHand(hp3);
		} catch (HandException e) {
			e.printStackTrace();
		}
*/
	
		PrintHand(gp.GetPlayersHand(p1).getCards(),"Player 1 Cards");
		PrintHand(gp.GetPlayersHand(p2).getCards(),"Player 2 Cards");
		PrintHand(gp.GetPlayersHand(p3).getCards(),"Player 3 Cards");
		
		PrintHand(gp.getCommonCards(),"Common Cards");
		ArrayList<HandPoker> BestPossibleHands = hp1.getGP().getBestPossibleHands(p1);
		
		/* 
		PrintHand(BestPossibleHand.getCards(), "Best Possible Hand");
		*/
				
		PrintHand(hp1.getGP().getBestMadeHand(p1).getCards(), "Best Made Hand Player 1");
		PrintHand(hp2.getGP().getBestMadeHand(p2).getCards(), "Best Made Hand Player 2");
		PrintHand(hp3.getGP().getBestMadeHand(p3).getCards(), "Best Made Hand Player 3");

 
		if (hp1.getGP().isMadeHandBestPossibleHand(p1))
		{
			System.out.println("You have the best possible hand");
		}
		
		ArrayList<Player> Winners = gp.GetGameWinners();
		for (Player p: Winners)
		{
			System.out.println(p.getPlayerName() + " is the winner");
		}
 
		
	}

	
	public void PrintHand(ArrayList<Card> cards, String strHandName)
	{
		System.out.println("************" + strHandName + "************" );
		for (Card handPC : cards)
		{
			System.out.print(handPC.geteRank() + " " + handPC.geteSuit() + " ");
		}
		System.out.println(" ");
	}
	
}
