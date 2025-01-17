# UNO
Variables and Data Structures
1. Main Variables:
replay: A boolean to control the game loop, allowing players to play multiple rounds.
playerScore, aiScore: Integer variables to track the cumulative scores of the player and AI.

2. Arrays and Lists:
colors: An array containing the four colors used in the game ("Red", "Blue", "Green", "Yellow").
specialCards: An array holding the names of the special cards ("Draw Two", "Skip").
deck: An ArrayList<String> representing the deck of cards. It holds all cards (number, special, and wild cards).
playerHand: An ArrayList<String> representing the player's current hand of cards.
aiHand: An ArrayList<String> representing the AI's current hand of cards.

3. Game State Variables:
currentCard: A String representing the card currently in play.
playerTurn: A boolean indicating whether it is the player's turn.
gameOver: A boolean signaling the end of the current game.

Logic and Structure
1. Game Setup:
Deck Initialization: The deck is created by iterating through colors and adding number cards (0-9), special cards, and wild cards. Each card is added multiple times as per UNO rules.
Shuffling: A custom shuffle logic is implemented using random index swapping.
Dealing Cards: Both the player and AI are dealt 7 cards from the shuffled deck.

2. Starting Card Selection:
The starting card is drawn from the deck. If it is a special or wild card, it is reshuffled into the deck, ensuring the game starts with a valid number card.

3. Game Loop:
The game alternates between the player’s and AI’s turns. The loop continues until one of them has no cards left (gameOver = true).

4. Player Turn Logic:
The player can either draw a card or play a valid card. Special cards (Wild, Draw Four, Draw Two, Skip) trigger additional effects.
Wild cards allow the player to choose a new color.
The game validates if the player can play a card using the canPlay and canPlayDrawFour utility methods.

5. AI Turn Logic:
The AI checks its hand for playable cards. If no valid card is found, it draws from the deck.
Similar to the player, special cards trigger specific effects.
Wild cards enable the AI to randomly select a new color.

6. Scoring:
If a player wins, the score is calculated based on the opponent's remaining cards. Point values:
Special cards: 20 points each.
Wild cards: 50 points each.
Scores are tracked using the playerScore and aiScore variables.

7. Replay Option:
After a round, the player is prompted to replay. If "yes," the game resets; otherwise, the final scores are displayed.

Utility Methods (These methods is an older friend who taught me because it was the better way to check if the card can play or not, and calculate the score, I learned and understood how it worked. I understood and completed the programming very carefully, thanks for understanding, I do not plagiarize.)
1. canPlay(String card, String currentCard)
Checks if a card can be played based on color or number matching, or if it’s a Wild card.

2. canPlayDrawFour(List<String> hand, String currentCard)
Validates if a Wild Draw Four card can be played (only allowed if no other card in hand is playable).

3. calculateScore(List<String> hand)
Computes the score of a hand based on card values.
