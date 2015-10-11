package Assignement6;

import java.util.Arrays;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Assignement6
{

   static int MAX_CARDS_PER_HAND = 56;
   static int NUM_PLAYERS = 2;
   
   public static void main(String[] args)
   {
      int numPacksPerDeck = 1;
      int numJokersPerPack = 0;
      int numUnusedCardsPerPack = 0;
      int numOfPlayers = 2;
      int cardsPerHand = 7;
      Card[] unusedCardsPerPack = null;
      
      CardGameFramework highCardGame = new CardGameFramework( 
            numPacksPerDeck, numJokersPerPack,  
            numUnusedCardsPerPack, unusedCardsPerPack, 
            numOfPlayers, cardsPerHand);
      

   }

}


/*------------------------------------------------------
 * class GameModel
 *---------------------------------------------------- */

class GameModel
{
   private CardGameFramework highCardGame;
   private int cpuPlayer, humanPlayer;
   private int cpuCantPlay, humanCantPlay;
   private String cpuName, humanName;
   private int firstPlayer;
   private Card cpuPlayedCard, humanPlayedCard;
   private Hand leftStack, rightStack;
   
   public GameModel()
   {
      this.highCardGame = null;
      cpuName = "";
      cpuPlayer = 0;
      cpuCantPlay = 0;
      humanName = "";
      humanPlayer = 1;
      humanCantPlay = 0;
   }
   
   public GameModel(CardGameFramework highCardGame, String cpuName, 
         String humanName)
   {
      this.highCardGame = highCardGame;
      this.cpuName = cpuName;
      cpuPlayer = 0;
      cpuCantPlay = 0;
      this.humanName = humanName;
      humanPlayer = 1;
      humanCantPlay = 0;
   }
   
   public void resetGame()
   {
   }
   
   public void dealCards()
   {
   }
   
   public Hand getCpuHand()
   {
      return highCardGame.getHand(cpuPlayer);
   }
   
   public int getCpuCantPlay()
   {
      return cpuCantPlay;
   }
   
   public boolean setCpuCantPlay()
   {
      cpuCantPlay++;
      return true;
   }
   
   public Hand getHumanHand()
   {
      return highCardGame.getHand(humanPlayer);
   }
   
   public int getHumanCantPlay()
   {
      return humanCantPlay;
   }
   
   public boolean setHumanCantPlay()
   {
      humanCantPlay++;
      return true;
   }
   
   public int getNumCardsPerHand()
   {
      return highCardGame.geNumCardsPerHand();
   }
   
   public boolean setFirstPlayer(int playerIndex)
   {
      
      firstPlayer = playerIndex;
      return true;
   }
   
   public int getFirstPlayer()
   {     
      return firstPlayer;
   }
   
   public String getCpuName()
   {
      return cpuName;
   }
   
   public String getHumanName()
   {
      return humanName;
   }
   
   public Card getHumanPlayedCard()
   {
      return humanPlayedCard;
   }
   
   public boolean setHumanPlayedCard(Card card)
   {
      if(card == null)
         return false;
      
      humanPlayedCard = new Card(card.getValue(), card.getSuit());
      return true;
   }
   
   public Card getCpuPlayedCard()
   {
      return cpuPlayedCard;
   }
   
   public boolean setCpuPlayedCard(Card card)
   {
      if(card == null)
         return false;
      
      cpuPlayedCard = new Card(card.getValue(), card.getSuit());
      return true;
   }
}

/*------------------------------------------------------
 * end of GameModel
 *---------------------------------------------------- */


/*------------------------------------------------------
 * GUICard
 *---------------------------------------------------- */
class GUICard
{
   private static Icon[][] iconCards = new ImageIcon[14][4];
   private static Icon iconBack;
   private static Icon iconBlank;
   static boolean iconsLoaded = false;


   /*
    * loads the  Icons for each card value
    * */
   static void loadCardIcons()
   {
      char[] value = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T',
                      'J', 'Q', 'K', 'X'};
      char[] suit = {'C', 'D', 'H', 'S'};
      String fileName = "";

      for (int i = 0; i < suit.length; i++)
         for (int j = 0; j < value.length; j++)
         {
            fileName = "";
            fileName += String.valueOf(value[j]) + String.valueOf(suit[i])
               + ".gif";
            iconCards[j][i] = new ImageIcon("images/" + fileName);
         }
      iconBack = new ImageIcon("images/BK.gif");
      iconBlank = new ImageIcon("images/blank.gif");
      iconsLoaded = true;
   }

   /*
    * getter to get an Icon for a requested card
    * */
   static public Icon getIcon(Card card)
   {

      if (!iconsLoaded)
         loadCardIcons();

      int cardValue = valueAsInt(card.getValue());
      int suitValue = suitAsInt(card.getSuit());

      return iconCards[cardValue][suitValue];
   }//end method getIcon

   /*
    * getter to get a back of card icon
    * */
   static public Icon getBackCardIcon()
   {
      if (!iconsLoaded)
         loadCardIcons();

      return iconBack;
   }

   /*
    * getter to get a blank card holder
    * */
   static public Icon getBlankCardIcon()
   {
      if (!iconsLoaded)
         loadCardIcons();

      return iconBlank;
   }

   /*
    * returns a cards value as an int
    * */
   static int valueAsInt(char cardValue)
   {
      int value = 0;

      for (int i = 0; i < Card.cardValue.length; i++)
         if (cardValue == Card.cardValue[i])
         {
            value = i;
            break;
         }

      return value;
   }//end method valueAsInt

   /*
    * returns a cards suit as an int
    * */
   static int suitAsInt(Card.Suit suit)
   {
      if (suit.equals(Card.Suit.CLUBS))
         return 0;
      else if (suit.equals(Card.Suit.DIAMONDS))
         return 1;
      else if (suit.equals(Card.Suit.HEARTS))
         return 2;
      else
         return 3;
   }//end method suitAsInt

}
/*------------------------------------------------------
 * end GUICard
 *---------------------------------------------------- */



/*
 * 
 * 
 * From here down is all the classes that represent objects
 * used by the model to create it's data. Card, Hand, Deck,
 * etc.
 * 
 * 
 * */


/*------------------------------------------------------
 * Card
 *---------------------------------------------------- */
class Card
{
   // mappings for valid cards
   public enum Suit{CLUBS, DIAMONDS, HEARTS, SPADES};
   public static final char[] cardValue = {'A', '2', '3', '4', '5', '6', '7',
      '8', '9', 'T', 'J', 'Q', 'K', 'X'};
   public static char[] valuRanks = {'A', '2', '3', '4', '5', '6', '7',
         '8', '9', 'T', 'J', 'Q', 'K', 'X'};

   private char value;
   private Suit suit;
   private boolean errorFlag;

   /*
    * constructor with parameters takes a value and Suit
    * */
   public Card(char value, Suit suit)
   {
      set(value, suit);
   }

   /*
    * default constructor sets value to "A" and suit to spades
    * */
   public Card()
   {
      set('A', Suit.SPADES);
   }

   /*
    * public method to return value and suit as a string
    * */
   public String toString()
   {
      if (errorFlag == true)
         return("[invalid]");
      return String.valueOf(value) + " of " + suit;
   }

   /*
    * isValid takes a char value and Suit and
    * returns true if card value is valid
    */
   private boolean isValid(char value, Suit suit)
   {

      for (int i = 0; i < cardValue.length; i++)
         if (value == cardValue[i])
            return true;

      return false;
   }

   /*
    * set takes a value and Suit, assigns them to a Card and
    * returns true if successful
    */
   public boolean set(char value, Suit suit)
   {
      if (isValid(value, suit))
      {
         this.value = value;
         this.suit = suit;
         errorFlag = false;
         return true;
      }
      else
      {
         errorFlag = true;
         return false;
      }
   }

   /*
    * getValue returns a Card's value
    * */
   public char getValue()
   {
      return value;
   }

   /*
    * getSuit returns a Card's suit
    * */
   public Suit getSuit()
   {
      return suit;
   }

   /*
    * getErrorFlag returns errorFlag
    * */
   public boolean getErrorFlag()
   {
      return errorFlag;
   }

   /*
    *equals takes a Card and returns true if it matches the current
    *value and Suit
    */
   public boolean equals(Card card)
   {
      return (this.suit.equals(card.getSuit()) &&
            this.value == card.getValue());

   }//end method equal


   /*
    * returns the index of a given card
    */
   private static int valueIndex(Card card)
   {

      for(int i = 0; i < valuRanks.length; i++)
      {
         if(card.getValue() == valuRanks[i])
            return i;
      }

      return -1;
   }

   /*
    * bubble sort for sorting the array
    */
   public static void arraySort(Card[] cardArray, int arraySize)
   {
      Card tempCard;

      for(int i = 0; i < arraySize; i++){
         for(int j = 1; j < arraySize - i; j++){
            if(valueIndex(cardArray[j-1]) > valueIndex(cardArray[j])){
               tempCard = cardArray[j-1];
                cardArray[j-1] = cardArray[j];
                cardArray[j] = tempCard;
            }
        }

      }

   }//end method arraySort
   
}
/*------------------------------------------------------
 * end of Card
 *---------------------------------------------------- */

/*------------------------------------------------------
 * Hand Class
 *---------------------------------------------------- */

class Hand
{

   // max number of cards allowed in hand
   public static final int MAX_CARDS = 100;

   private Card[] myCards = new Card[MAX_CARDS];
   private int numCards;

   // default constructor
   public Hand()
   {
      this.numCards = 0;
   }

   /*
    * resetHand takes no parameters and fills the array with null
    * then sets the numCards back to 0
    * */
   public void resetHand()
   {

      Arrays.fill(myCards, null);
      numCards = 0;

   }

   /*
    * takeCard takes a Card and adds it to hand
    * returns true if successful. (makes new copy of card)
    * */
   public boolean takeCard(Card card)
   {

      if(numCards < MAX_CARDS)
      {
         Card addCard = new Card(card.getValue(), card.getSuit());
         myCards[numCards] = addCard;
         numCards++;
         return true;
      }

      return false;
   }

   /*
    * playCard plays card on top of the deck
    * and returns that card to the caller
    * */
   public Card playCard()
   {

      if(numCards == 0)
         return null;

      Card card = new Card(myCards[numCards -1].getValue(),
            myCards[numCards -1].getSuit());
      myCards[numCards -1] = null;
      numCards--;

      return card;

   }

   /*
    * playCard plays card on top of the deck
    * and returns that card to the caller
    */
   public Card playCard(int cardIndex)
   {
      if ( numCards == 0 ) //error
      {
         //Creates a card that does not work
         return new Card('M', Card.Suit.SPADES);
      }
      //Decreases numCards.
      Card card = myCards[cardIndex];

      numCards--;
      for(int i = cardIndex; i < numCards; i++)
      {
         myCards[i] = myCards[i+1];
      }

      myCards[numCards] = null;

      return card;
    }

   /*
    * toString Displays the hand as a string
    * */
   public String toString()
   {

      String handOfCards = "(";

      for(int i = 0; i < numCards; i++)
      {
         handOfCards += (i == numCards - 1) ? myCards[i].toString() :
            myCards[i].toString() + ", ";
      }

      handOfCards += ")";

      return handOfCards;

   }

   /*
    * getNumCards returns the number of cards in the hand
    * */
   public int getNumCards()
   {

      return numCards;

   }

   /*
    * inspectCard returns the card asked for,
    * if the card is out of bounds it returns a card with
    * errorFlag true
    * */
   public Card inspectCard(int k)
   {

      if(k >= 0 && k < numCards)
         return myCards[k];

      // send a bad card so error flag is set
      return new Card('e', Card.Suit.CLUBS);

   }

   /*
    * sorts the hand. Uses Cards bubble sort
    */
   public void sort()
   {
      Card.arraySort(myCards, numCards);
   }

}

/*------------------------------------------------------
 * end of hand
 *---------------------------------------------------- */



/*------------------------------------------------------
 * Deck
 *---------------------------------------------------- */
class Deck
{

   //setting maximum number of packs to six
   public static final int MAX_CARDS = 6*56;

   private static final int NUMBER_OF_CARDS = 56;
   private static Card[] masterPack = new Card[NUMBER_OF_CARDS];

   private Card cards[];//array of card object
   private int topCard;// index of next card to be dealt
   private int numPacks;// number of packs

   //constructor initialize number of packs
   public Deck()
   {
      this.numPacks = 1;

      // populate masterPack
      allocateMasterPack();

      // create the deck
      init(numPacks);
   }

   /*
    * constructor populates array masterPack and
    * assign initial values
    */
   public Deck(int numPacks)
   {
      // check it isn't over MAX_CARD limit
      if(NUMBER_OF_CARDS * numPacks > MAX_CARDS)
         numPacks = 1;

      // populate masterPack
      allocateMasterPack();

      // create the deck
      init(numPacks);
   }

   /*
    * method init re-populates array cards with
    * new number of cards using new number of packs
    */
   public void init(int numPacks)
   {
      cards = new Card[NUMBER_OF_CARDS * numPacks];
      this.topCard = 0;
      this.numPacks = numPacks;
      int count = 0;

      for(int i = 0; i < cards.length; i++)
      {
         cards[i] = new Card(masterPack[count].getValue(),
               masterPack[count].getSuit());
         topCard++;
         count++;

         // count is 52 reset it back to 0 to start over
         if(count == NUMBER_OF_CARDS)
            count = 0;
      }
   }

   //method shuffle() mixes up cards in a deck of cards
   public void shuffle()
   {
      for (int i = 0 ; i < cards.length; i++)
      {
         Card temp;
         Random randomGenerator = new Random();
         int randomCard = randomGenerator.nextInt(NUMBER_OF_CARDS * numPacks);

         temp = cards[i];
         cards[i] = cards[randomCard];
         cards[randomCard] = temp;
      }
   }

   /*
    * method dealCard() deals number of cards
    * by checking the availability of cards
    */
   public Card dealCard()
   {
      if(topCard == 0)
         return null;

      Card card = new Card(cards[topCard -1].getValue(),
            cards[topCard -1].getSuit());
      cards[topCard -1] = null;
      topCard--;

      return card;
   }

   /*
    * accessor to get index of top card
    * in cards array
    */
   public int getTopCard()
   {
      return topCard;
   }

   /*
    * Inspects a card at k index
    * returns the card or a bad card if k is not a good index
    */
   public Card inspectCard(int k)
   {

      if(k < cards.length)
         return cards[k];

      // send a bad card so error flag is set
      return new Card('e', Card.Suit.CLUBS);

   }


   /*
    * private method to allocate the master deck
    * master deck is used on all deck instances
    */
   private static void allocateMasterPack()
   {
      // if last card in masterPack isn't null,
      // it's already been initiated so return early
      if(masterPack[NUMBER_OF_CARDS -1] != null)
         return;

      Card.Suit suit;

      for(int i = 0; i < masterPack.length; i++)
      {
         if(i < 14)
            suit = Card.Suit.SPADES;
         else if(i >= 14 && i < 28)
            suit = Card.Suit.CLUBS;
         else if(i >= 28 && i < 42)
            suit = Card.Suit.HEARTS;
         else
            suit = Card.Suit.DIAMONDS;

         masterPack[i] = new Card(Card.cardValue[ i % 14 ], suit);
      }
   }


   /*
    * remove card from deck. Takes a card as a value and searches to
    * remove that card
    */
   public boolean removeCard(Card card)
   {
      for(int i = 0; i < topCard; i++)
      {
         if(cards[i].equals(card))
         {
            cards[i].set(cards[topCard].getValue(), cards[topCard].getSuit());
            cards[topCard] = null;
            topCard--;
            return true;
         }
      }

      return false;
   }

   /*
    * add card to deck.
    */
   public boolean addCard(Card card)
   {
      int numOfInstances = 0;
      for(int i = 0; i < topCard; i++)
      {
         if(cards[i].equals(card))
            numOfInstances++;
      }

      if(numOfInstances >= numPacks)
         return false;

      cards[topCard].set(card.getValue(), card.getSuit());
      topCard++;

      return true;
   }

   /*
    * returns the number of cards
    */
   public int getNumCards()
   {
      return topCard;
   }

   /*
    * sorts the deck. Uses Cards bubble sort
    */
   public void sort()
   {
      Card.arraySort(cards, topCard);
   }

}

/*------------------------------------------------------
 * end of Deck
 *---------------------------------------------------- */


/*------------------------------------------------------
 * CardGameFramework
 *---------------------------------------------------- */
class CardGameFramework
{
   private static final int MAX_PLAYERS = 50;

   private int numPlayers;
   private int numPacks;            // # standard 52-card packs per deck
                                  // ignoring jokers or unused cards
   private int numJokersPerPack;    // if 2 per pack & 3 packs per deck, get 6
   private int numUnusedCardsPerPack;  // # cards removed from each pack
   private int numCardsPerHand;        // # cards to deal each player
   private Deck deck;               // holds the initial full deck and gets
                                  // smaller (usually) during play
   private Hand[] hand;             // one Hand for each player
   private Card[] unusedCardsPerPack;   // an array holding the cards not used
                                      // in the game.  e.g. pinochle does not
                                      // use cards 2-8 of any suit

   public CardGameFramework( int numPacks, int numJokersPerPack,
          int numUnusedCardsPerPack,  Card[] unusedCardsPerPack,
          int numPlayers, int numCardsPerHand)
   {
       int k;

       // filter bad values
       if (numPacks < 1 || numPacks > 6)
          numPacks = 1;
       if (numJokersPerPack < 0 || numJokersPerPack > 4)
          numJokersPerPack = 0;
       if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) //  > 1 card
          numUnusedCardsPerPack = 0;
       if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
          numPlayers = 4;
       // one of many ways to assure at least one full deal to all players
       if  (numCardsPerHand < 1 ||
             numCardsPerHand >  numPacks * (52 - numUnusedCardsPerPack)
             / numPlayers )
          numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;

       // allocate
       this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
       this.hand = new Hand[numPlayers];
       for (k = 0; k < numPlayers; k++)
          this.hand[k] = new Hand();
       deck = new Deck(numPacks);

       // assign to members
       this.numPacks = numPacks;
       this.numJokersPerPack = numJokersPerPack;
       this.numUnusedCardsPerPack = numUnusedCardsPerPack;
       this.numPlayers = numPlayers;
       this.numCardsPerHand = numCardsPerHand;
       for (k = 0; k < numUnusedCardsPerPack; k++)
          this.unusedCardsPerPack[k] = unusedCardsPerPack[k];

       // prepare deck and shuffle
       newGame();
   }

    // constructor overload/default for game like bridge
   public CardGameFramework()
   {
      this(1, 0, 0, null, 4, 13);
   }

   public Hand getHand(int k)
   {
      // hands start from 0 like arrays

      // on error return automatic empty hand
      if (k < 0 || k >= numPlayers)
         return new Hand();

      return hand[k];
   }

   public int geNumCardsPerHand() { return numCardsPerHand; }
   
   public Card getCardFromDeck() { return deck.dealCard(); }

   public int getNumCardsRemainingInDeck() { return deck.getNumCards(); }

   public void newGame()
   {
    int k, j;

    // clear the hands
    for (k = 0; k < numPlayers; k++)
       hand[k].resetHand();

    // restock the deck
    deck.init(numPacks);

    // remove unused cards
    for (k = 0; k < numUnusedCardsPerPack; k++)
       deck.removeCard( unusedCardsPerPack[k] );

    // add jokers
    for (k = 0; k < numPacks; k++)
       for ( j = 0; j < numJokersPerPack; j++)
          deck.addCard( new Card('X', Card.Suit.values()[j]) );

    // shuffle the cards
    deck.shuffle();
   }

   public boolean deal()
   {
    // returns false if not enough cards, but deals what it can
    int k, j;
    boolean enoughCards;

    // clear all hands
    for (j = 0; j < numPlayers; j++)
       hand[j].resetHand();

    enoughCards = true;
    for (k = 0; k < numCardsPerHand && enoughCards ; k++)
    {
       for (j = 0; j < numPlayers; j++)
          if (deck.getNumCards() > 0)
             hand[j].takeCard( deck.dealCard() );
          else
          {
             enoughCards = false;
             break;
          }
    }

    return enoughCards;
   }

   public void sortHands()
   {
      int k;

      for (k = 0; k < numPlayers; k++)
         hand[k].sort();
   }

   public Card playCard(int playerIndex, int cardIndex)
   {
      // returns bad card if either argument is bad
      if (playerIndex < 0 ||  playerIndex > numPlayers - 1 || 
          cardIndex < 0 || cardIndex > numCardsPerHand - 1)
      {
         //Creates a card that does not work
         return new Card('M', Card.Suit.SPADES);      
      }
   
      // return the card played
      return hand[playerIndex].playCard(cardIndex);
   
   }
   
   
   public boolean takeCard(int playerIndex, int cardIndex)
   {
      // returns false if either argument is bad
      if (playerIndex < 0 ||  playerIndex > numPlayers - 1 ||
          cardIndex < 0 || cardIndex > numCardsPerHand - 1)
      {
         return false;      
      }
           
      // Are there enough Cards?
      if (deck.getNumCards() <= 0)
         return false;
         
      return hand[playerIndex].takeCard(deck.dealCard());
   }
}
/*------------------------------------------------------
 * end of CardGameFramework
 *---------------------------------------------------- */