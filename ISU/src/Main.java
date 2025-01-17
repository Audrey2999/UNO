import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main { //Audrey Chen
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean replay = true;
        int playerScore = 0;
        int aiScore = 0;
        while (replay) {
            // Initialize deck
            ArrayList<String> deck = new ArrayList<>();
            String[] colors = {"Red", "Blue", "Green", "Yellow"};
            String[] specialCards = {"Draw Two", "Skip"};

            // Add number cards to the deck
            for (String color : colors) {
                deck.add(color + " 0");
                for (int i = 1; i <= 9; i++) {
                    deck.add(color + " " + i);
                    deck.add(color + " " + i);
                }
                for (String special : specialCards) {
                    deck.add(color + " " + special);
                    deck.add(color + " " + special);
                }
            }

            // Add wild cards
            for (int i = 0; i < 4; i++) {
                deck.add("Wild");
                deck.add("Wild Draw Four");
            }

            // Shuffle the deck manually
            for (int i = 0; i < deck.size(); i++) {
                int swapIndex = (int) (Math.random() * deck.size());
                String temp = deck.get(i);
                deck.set(i, deck.get(swapIndex));
                deck.set(swapIndex, temp);
            }

            // Deal cards to players
            ArrayList<String> playerHand = new ArrayList<>();
            ArrayList<String> aiHand = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                playerHand.add(deck.removeFirst());
                aiHand.add(deck.removeFirst());
            }

            // Start the game (must not be a special card)
            String currentCard = deck.removeFirst();
            while (currentCard.contains("Draw") || currentCard.contains("Skip") || currentCard.contains("Wild")) {
                deck.add(currentCard);
                currentCard = deck.removeFirst();
            }
            System.out.println("================================================================================================================");
            System.out.println("Welcome to UNO!!!");
            System.out.println("Starting card: " + currentCard);

            boolean playerTurn = true;
            boolean gameOver = false;

            //game loop
            while (!gameOver) {
                while (playerTurn) {
                    System.out.println("================================================================================================================");
                    System.out.println("Your turn. Current card: " + currentCard);
                    System.out.println("\nYour hand size: " + playerHand.size() + " cards   V.S.   AI's hand size: " + aiHand.size() + " cards");
                    System.out.println("Your hand: " + playerHand);

                    if (canPlayDrawFour(playerHand, currentCard)) {
                        if (deck.isEmpty()) {
                            System.out.println("No cards left to draw.");
                            continue;
                        }
                        String drawnCard = deck.remove(0);
                        System.out.println("You drew: " + drawnCard);
                        if (canPlay(drawnCard, currentCard)) {
                            currentCard = drawnCard;
                            System.out.println("You played: " + drawnCard);
                            if (playerHand.isEmpty()) {
                                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ (: CONGRATULATIONS, YOU WIN!!!!! :) ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                                gameOver = true;
                                //Add points for player depending on AI's hand
                                playerScore += calculateScore(aiHand);
                                System.out.println("Scores:\nPlayer: " + playerScore + " V.S. AI: " + aiScore);
                                playerTurn = false; // Alternate turns
                            } else if (drawnCard.startsWith("Wild")) {
                                if (drawnCard.contains("Draw Four")) {
                                    System.out.println("You make your opponent draw 4!");
                                    for (int i = 0; i < 4; i++) {
                                        if (!deck.isEmpty())
                                            aiHand.add(deck.remove(0));
                                    }
                                }
                                System.out.println("Select a new color (0: Red, 1: Blue, 2: Green, 3: Yellow): ");
                                int newColor;
                                do {
                                    newColor = scanner.nextInt();
                                    if (newColor < 0 || newColor > 3) {
                                        System.out.println("Invalid choice. Choose between 0 and 3.");
                                    }
                                } while (newColor < 0 || newColor > 3);
                                currentCard = colors[newColor] + " card";
                                System.out.println("You chose " + currentCard + "!");
                                playerTurn = false; // Alternate turns
                            } else if (drawnCard.contains("Draw Two")) {
                                System.out.println("Opponent draws 2 and skips their turn!");
                                for (int i = 0; i < 2; i++) {
                                    if (!deck.isEmpty())
                                        aiHand.add(deck.remove(0));
                                }
                            } else if (drawnCard.contains("Skip")) {
                                System.out.println("You skipped your opponent's turn!");
                            } else {
                                playerTurn = false; // Alternate turns
                            }
                        } else {
                            playerHand.add(drawnCard);
                            System.out.println("You cannot play the drawn card.");
                            playerTurn = false; // Alternate turns
                        }
                    } else {
                        System.out.println("Enter the index of the card to play: ");
                        int choice = scanner.nextInt();

                        if (choice >= 0 && choice < playerHand.size()) {
                            String chosenCard = playerHand.get(choice);

                            if (canPlay(chosenCard, currentCard)) {
                                currentCard = chosenCard;
                                playerHand.remove(choice);
                                System.out.println("You played: " + chosenCard);
                                if (playerHand.isEmpty()) {
                                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ (: CONGRATULATIONS, YOU WIN!!!!! :) ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                                    gameOver = true;
                                    //Add points for player depending on AI's hand
                                    playerScore += calculateScore(aiHand);
                                    System.out.println("Scores:\nPlayer: " + playerScore + " V.S. AI: " + aiScore);
                                    playerTurn = false; // Alternate turns
                                } else if (chosenCard.startsWith("Wild")) {
                                    if (chosenCard.contains("Draw Four")) {
                                        System.out.println("You make your opponent draw 4!");
                                        for (int i = 0; i < 4; i++) {
                                            if (!deck.isEmpty())
                                                aiHand.add(deck.remove(0));
                                        }
                                    }
                                    System.out.println("Select a new color (0: Red, 1: Blue, 2: Green, 3: Yellow): ");
                                    int newColor;
                                    do {
                                        newColor = scanner.nextInt();
                                        if (newColor < 0 || newColor > 3) {
                                            System.out.println("Invalid choice. Choose between 0 and 3.");
                                        }
                                    } while (newColor < 0 || newColor > 3);
                                    currentCard = colors[newColor] + " card";
                                    System.out.println("You chose " + currentCard + "!");
                                    playerTurn = false; // Alternate turns
                                } else if (chosenCard.contains("Draw Two")) {
                                    System.out.println("Opponent draws 2 and skips their turn!");
                                    for (int i = 0; i < 2; i++) {
                                        if (!deck.isEmpty())
                                            aiHand.add(deck.remove(0));
                                    }
                                } else if (chosenCard.contains("Skip")) {
                                    System.out.println("You skipped your opponent's turn!");
                                } else {
                                    playerTurn = false; // Alternate turns
                                }
                            } else {
                                System.out.println("Invalid card. Try again.");
                            }
                        } else {
                            System.out.println("Invalid choice. Try again.");
                        }
                    }
                }

                if (!gameOver) {
                    System.out.println("================================================================================================================");
                    System.out.println("AI's turn. Current card: " + currentCard);
                    System.out.println("\nAI's hand size: " + aiHand.size() + " cards");

                    boolean played = false;
                    for (int i = 0; i < aiHand.size(); i++) {
                        String aiCard = aiHand.get(i);
                        if (canPlay(aiCard, currentCard)) {
                            currentCard = aiCard;
                            aiHand.remove(i);
                            System.out.println("AI played: " + aiCard);

                            //After AI plays, check if they won
                            if (aiHand.isEmpty()) {
                                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ): OH NO, AI WINS!!!!! :( ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                                gameOver = true;
                                //Add points for player depending on AI's hand
                                playerScore += calculateScore(aiHand);
                                System.out.println("Scores:\nPlayer: " + playerScore + " V.S. AI: " + aiScore);
                            } else if (currentCard.startsWith("Wild")) {
                                if (currentCard.contains("Draw Four")) {
                                    System.out.println("Opponent makes you draw 4!");
                                    for (int j = 0; j < 4; j++) {
                                        if (!deck.isEmpty())
                                            playerHand.add(deck.remove(0));
                                    }
                                }

                                //AI randomly chooses a color to change to
                                String[] aiColors = {"Red", "Blue", "Green", "Yellow"};
                                String chosenColor = aiColors[(int) (Math.random() * 4)];
                                currentCard = chosenColor + " card";
                                System.out.println("Opponent chose: " + currentCard);
                                playerTurn = true;
                            } else if (currentCard.contains("Draw Two")) {
                                System.out.println("Opponent makes you draw 2 and skips your turn!");
                                for (int k = 0; k < 2; k++) {
                                    if (!deck.isEmpty())
                                        aiHand.add(deck.remove(0));
                                }
                                playerTurn = false;
                            } else if (currentCard.contains("Skip")) {
                                System.out.println("Your opponent skipped your turn!");
                                playerTurn = false;
                            } else {
                                playerTurn = true;
                            }

                            played = true;
                            break;
                        }
                    }
                    if (!played) {
                        if (deck.isEmpty()) {
                            System.out.println("AI cannot draw; no cards left.");
                            continue;
                        }
                        String drawnCard = deck.remove(0);
                        System.out.println("AI drew a card.");
                        if (canPlay(drawnCard, currentCard)) {
                            currentCard = drawnCard;
                            System.out.println("AI played: " + drawnCard);

                            //After AI plays, check if they won
                            if (aiHand.isEmpty()) {
                                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ): OH NO, AI WINS!!!!! :( ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                                gameOver = true;
                                //Add points for AI depending on player's hand
                                aiScore += calculateScore(playerHand);
                                System.out.println("Scores:\nPlayer: " + playerScore + " V.S. AI: " + aiScore);
                            } else if (currentCard.startsWith("Wild")) {
                                if (currentCard.contains("Draw Four")) {
                                    System.out.println("Opponent makes you draw 4!");
                                    for (int j = 0; j < 4; j++) {
                                        if (!deck.isEmpty())
                                            playerHand.add(deck.remove(0));
                                    }
                                }

                                //AI randomly chooses a color to change to
                                String[] aiColors = {"Red", "Blue", "Green", "Yellow"};
                                String chosenColor = aiColors[(int) (Math.random() * 4)];
                                currentCard = chosenColor + " card";
                                System.out.println("Opponent chose: " + currentCard);
                                playerTurn = true;
                            } else if (currentCard.contains("Draw Two")) {
                                System.out.println("Opponent makes you draw 2 and skips your turn!");
                                for (int k = 0; k < 2; k++) {
                                    if (!deck.isEmpty())
                                        aiHand.add(deck.remove(0));
                                }
                                playerTurn = false;
                            } else if (currentCard.contains("Skip")) {
                                System.out.println("Your opponent skipped your turn!");
                                playerTurn = false;
                            } else {
                                playerTurn = true;
                            }

                        } else {
                            aiHand.add(drawnCard);
                            System.out.println("AI cannot play the drawn card.");
                            playerTurn = true;
                        }
                    }
                }
            }

            System.out.println("Do you want to play again? (yes/no)");
            replay = scanner.next().equalsIgnoreCase("yes");
        }
        System.out.println("Thanks for playing!");
        System.out.println("Final Scores:\nPlayer: " + playerScore + " V.S. AI: " + aiScore);
        scanner.close();
    }

    //Utility to check if a card can be played
    static boolean canPlay(String card, String currentCard) {
        if (card.contains("Wild")) {
            return true;
        } else if (currentCard.length() == 1) {
            String[] cardParts = card.split(" ", 2);
            return cardParts[0].equals(currentCard);
        } else if (card.contains("Draw Two")) {
            String[] cardParts = card.split(" ", 3);
            String[] currentParts = currentCard.split(" ", 3);
            return cardParts[0].equals(currentParts[0]);
        } else {
            String[] cardParts = card.split(" ", 2);
            String[] currentParts = currentCard.split(" ", 2);
            return cardParts[0].equals(currentParts[0]) || cardParts[1].equals(currentParts[1]);
        }
    }
    //Utility to check if wild draw four can be played
    static boolean canPlayDrawFour(List<String> hand, String currentCard) {
        for (String card : hand) {
            if (canPlay(card, currentCard) && !card.contains("Draw Four")) {
                return false; // Found a playable card that's not Draw Four
            }
        }
        return true;
    }
    //Utility to calculate score
    static int calculateScore(List<String> hand) {
        int score = 0;
        for (String card : hand) {
            if (card.contains("Draw Two") || card.contains("Skip")) {
                score += 20;
            } else if (card.contains("Wild")) {
                score += 50;
            } else {
                String[] parts = card.split(" ");
                score += Integer.parseInt(parts[1]);
            }
        }
        return score;
    }
}