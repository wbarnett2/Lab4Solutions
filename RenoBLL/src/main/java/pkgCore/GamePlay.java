package pkgCore;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

import pkgEnum.eCardDestination;
import pkgEnum.eDrawCount;
import pkgEnum.eRank;
import pkgEnum.eSuit;
import pkgException.DeckException;
import pkgException.HandException;

public class GamePlay { 

	private Rule rle;
	private ArrayList<Player> GamePlayers = new ArrayList<Player>();
	private HashMap<UUID, HandPoker> GameHand = new HashMap<UUID, HandPoker>();
	private ArrayList<Card> CommonCards = new ArrayList<Card>();
	private HashMap<UUID, HandPoker> BestMadeHand = new HashMap<UUID, HandPoker>();
	private HashMap<UUID, ArrayList<HandPoker>> BestPossibleHands = new HashMap<UUID, ArrayList<HandPoker>>();
	private Player PlayerButton;
	private LinkedList PlayerBetPosition;
	private Deck GameDeck;

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * PutGameHand - puts a hand to the GameHand map
	 * @return
	 */

	private void PutGameHand(UUID PlayerID, HandPoker hp)
	{
		GameHand.put(PlayerID,  hp);
	}
	
	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * setCommonCards - set the common cards.
	 * @param cards
	 */
	private void setCommonCards(ArrayList<Card> cards)
	{
		this.CommonCards.clear();
		this.CommonCards.addAll(cards);
	}
	
	
	/**
	 * GamePlay - Create an instance of GamePlay. For every player in the table, add
	 * them to the game Set the GameDeck.
	 * 
	 * @param t
	 * @param rle
	 */
	public GamePlay(Table t, Rule rle) {
		GamePlayers.addAll(t.getTablePlayers());
		GameDeck = new Deck();
	}

	
	public void Draw(Player p, CardDraw CD) throws DeckException, HandException {

		for (int crdCnt = 0; crdCnt < CD.getCardCount().getCardCount(); crdCnt++) {
			if (CD.getCardDestination() == eCardDestination.COMMON) {
				CommonCards.add(GameDeck.Draw());
			} else {
				GameHand.get(p.getPlayerID()).Draw(GameDeck);
			}
		}
	}

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * EvaluateGameHands - Find every hand in the GameHand map and 
	 * evaluate it.  
	 * 
	 * @throws HandException
	 */
	public void EvaluateGameHands() throws HandException
	{
		Iterator<Map.Entry<UUID, HandPoker>> itr = GameHand.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<UUID, HandPoker> entry = itr.next();
			
			HandPoker hp = entry.getValue();
			hp = hp.EvaluateHand(hp);		
		}
	}

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * getBestMadeHand - Return the best made hand for the player
	 * @param player
	 * @return
	 */
	public HandPoker getBestMadeHand(Player player) {
		return BestMadeHand.get(player.getPlayerID());
	}

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * getBestPossibleHands - return a list of best possible hands for a player
	 * Could be more than one (example, same straight, but different suits)
	 * @param player
	 * @return
	 */
	public ArrayList<HandPoker> getBestPossibleHands(Player player) {
		return this.BestPossibleHands.get(player.getPlayerID());
	}

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * getCommonCards - returns the common cards for the game.
	 * There's a bit of a cheat- return 'jokers' for cards that are 
	 * not yet dealt.  If there are supposed to be 5 community cards,
	 * and in the current state of the game there are 3, return the
	 * three + two jokers. 
	 * 
	 * @return - list of community cards.
	 */
	public ArrayList<Card> getCommonCards() {
		int iSize = CommonCards.size();
		ArrayList<Card> commonCards = (ArrayList<Card>) CommonCards.clone();
		for (int i = iSize; i < this.getRle().getCommunityCardsMax() ; i++) {
			commonCards.add(new Card(eSuit.JOKER, eRank.JOKER));
		}
		return commonCards;
	}

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * GetGamePlayer - return the Player object for a given PlayerID
	 * @param PlayerID - ID for the Player
	 * @return - Player object
	 */
	private Player GetGamePlayer(UUID PlayerID)
	{
		for (Player p: GamePlayers)
		{
			if (p.getPlayerID() == PlayerID)
				return p;
		}
		return null;
	}

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * GetPlayersHand - return the Hand in the GameHand hashmap for a given player
	 * @param player 
	 * @return
	 */
	public HandPoker GetPlayersHand(Player player) {
		return GameHand.get(player.getPlayerID());
	}

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * getRle - Get the rule for the game.  It's set in the constructor
	 * @return
	 */
	public Rule getRle() {
		return rle;
	}

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * isMadeHandBestPossibleHand - return 'true' if the BestMadeHand is
	 * one of the BestPossibleHands
	 * @param player
	 * @return
	 */
	public boolean isMadeHandBestPossibleHand(Player player) {
		return BestPossibleHands.containsValue(BestMadeHand);
	}

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * SetBestMadeHand - set the BestMadeHand for a given player
	 * @param PlayerID
	 * @param HandPoker
	 */
	protected void SetBestMadeHand(UUID PlayerID, HandPoker HandPoker) {
		BestMadeHand.put(PlayerID, HandPoker);
	}

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * SetBestPossibleHands - set the BestPossibleHands for a given player
	 * @param PlayerID
	 * @param BestHands
	 */
	protected void SetBestPossibleHands(UUID PlayerID, ArrayList<HandPoker> BestHands) {
		this.BestPossibleHands.put(PlayerID, BestHands);
	}
	
	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * StartGame - Create a new HandPoker for each player, put it in the 
	 * GameHand map, execute the first Draw
	 * 
	 * @throws DeckException
	 * @throws HandException
	 */
	public void StartGame() throws DeckException, HandException {
		for (Player p : GamePlayers) {
			HandPoker hp = new HandPoker(p, this);
			GameHand.put(p.getPlayerID(), hp);
			Draw(p, this.rle.getCardDraw(eDrawCount.FIRST));
		}
	}
	
	
	
	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * GetGameWinners - Return an ArrayList of players with the winning
	 * hand.  Could be a tie...
	 * @return
	 */
	public ArrayList<Player> GetGameWinners() {
				
		ArrayList<Player> WinningPlayers = new ArrayList<Player>();
		ArrayList<HandPoker> GameHands = new ArrayList<HandPoker>();
		for (Player a : GamePlayers) {
            HandPoker bmh = getBestMadeHand(a);
            GameHands.add(bmh);
            if (isMadeHandBestPossibleHand(a)) {
                WinningPlayers.add(a);
            }
        }
        return WinningPlayers;
    }
	
		


}
