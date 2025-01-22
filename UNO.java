//Audrey Chen
import java.util.ArrayList;
import java.util.Scanner;

public class UNO {
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
            System.out.println("Welcome to UNO!!!");
            System.out.println("Starting card: " + currentCard);

            boolean playerTurn = true;
            boolean gameOver = false;

            //game loop
            while (!gameOver) {
                while (playerTurn) {
                    System.out.println("Your turn. Current card: " + currentCard);
                    System.out.println("\nYour hand size: " + playerHand.size() + " cards   V.S.   AI's hand size: " + aiHand.size() + " cards");
                    System.out.println("Your hand: " + playerHand);

                    System.out.println("Enter the index of the card to play (or -1 to draw): ");
                    int choice = scanner.nextInt();
                    if (choice == -1) {
                        String drawnCard = deck.removeFirst();
                        System.out.println("You draw: " + drawnCard);
                        boolean playable = false;
                        // Simplified card comparison
                        if (drawnCard.contains("Wild")) {
                            playable = true;
                        } else {
                            int spaceIndexCard = drawnCard.indexOf(' ');
                            int spaceIndexCurrent = currentCard.indexOf(' ');
                            String currentColor = currentCard.substring(0, spaceIndexCurrent);
                            String cardColor = drawnCard.substring(0, spaceIndexCard);
                            if (drawnCard.contains("Draw Two")) {
                                if (cardColor.equals(currentColor)) {
                                    playable = true;
                                }
                            } else if (drawnCard.contains("Skip")) {
                                if (cardColor.equals(currentColor)) {
                                    playable = true;
                                }
                            } else {
                                int spaceIndexCard2 = drawnCard.length();
                                int spaceIndexCurrent2 = currentCard.length();
                                if (spaceIndexCard != -1 && spaceIndexCurrent != -1) {
                                    String cardValue = drawnCard.substring(spaceIndexCard, spaceIndexCard2);
                                    String currentValue = currentCard.substring(spaceIndexCurrent, spaceIndexCurrent2);

                                    if (cardColor.equals(currentColor) || cardValue.equals(currentValue)) {
                                        playable = true;
                                    }
                                }
                            }
                        }
                        if (playable) {
                            if (drawnCard.equals("Wild") || drawnCard.equals("Wild Draw Four")) {
                                currentCard = drawnCard;
                                System.out.println("You play: " + drawnCard);
                                if (drawnCard.contains("Wild Draw Four")) {
                                    System.out.println("You make your opponent draw 4!");
                                    for (int i = 0; i < 4; i++) {
                                        if (!deck.isEmpty()) {
                                            aiHand.add(deck.removeFirst());
                                        }
                                    }
                                }
                                System.out.println("Select a new color (0: Red, 1: Blue, 2: Green, 3: Yellow): ");
                                int newColor = scanner.nextInt();
                                if (newColor < 0 || newColor > 3) {
                                    currentCard = colors[newColor] + " card";
                                    System.out.println("You chose " + currentCard + "!");
                                    playerTurn = false; // Alternate turns
                                }
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
                            System.out.println("You cannot play the drawn card");
                            playerTurn = false;
                        }
                    }else if (choice >= 0 && choice < playerHand.size()) {
                            String chosenCard = playerHand.get(choice);
                            currentCard = chosenCard;
                            playerHand.remove(choice);
                            System.out.println("You played: " + chosenCard);
                            if (playerHand.size() == 0) {
                                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ (: CONGRATULATIONS, YOU WIN!!!!! :) ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                                gameOver = true;
                                for (String card : aiHand) {
                                    if (card.contains("Draw Two") || card.contains("Skip") || card.contains("Wild")) {
                                        playerScore += 50;
                                    } else {
                                        playerScore += 20;
                                    }
                                    System.out.println("Scores:\nPlayer: " + playerScore + " V.S. AI: " + aiScore);
                                    playerTurn = false;
                                }
                            } else if (chosenCard.contains("Wild Draw Four")) {
                                System.out.println("You make your opponent draw 4!");
                                for (int i = 0; i < 4; i++) {
                                    if (!deck.isEmpty()) {
                                        aiHand.add(deck.removeFirst());
                                    }
                                }
                                System.out.println("Select a new color (0: Red, 1: Blue, 2: Green, 3: Yellow): ");
                                int newColor = scanner.nextInt();
                                if (newColor < 0 || newColor > 3) {
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
                            }
                        }
                        if (!gameOver) {
                            System.out.println("AI's turn. Current card: " + currentCard);
                            System.out.println("\nAI's hand size: " + aiHand.size() + " cards");
                            boolean played = false;
                            for (int i = 0; i < aiHand.size(); i++) {
                                String aiCard = aiHand.get(i);

                                boolean playable = false;
                                // Simplified card comparison
                                if (aiCard.contains("Wild")) {
                                    playable = true;
                                } else {
                                    int spaceIndexCard = aiCard.indexOf(' ');
                                    int spaceIndexCurrent = currentCard.indexOf(' ');
                                    String currentColor = aiCard.substring(0, spaceIndexCurrent);
                                    String cardColor = currentCard.substring(0, spaceIndexCard);
                                    if (aiCard.contains("Draw Two")) {
                                        if (cardColor.equals(currentColor)) {
                                            playable = true;
                                        }
                                    } else if (aiCard.contains("Skip")) {
                                        if (cardColor.equals(currentColor)) {
                                            playable = true;
                                        }
                                    } else {
                                        int spaceIndexCard2 = aiCard.length();
                                        int spaceIndexCurrent2 = currentCard.length();
                                        if (spaceIndexCard != -1 && spaceIndexCurrent != -1) {
                                            String cardValue = aiCard.substring(spaceIndexCard, spaceIndexCard2);
                                            String currentValue = currentCard.substring(spaceIndexCurrent, spaceIndexCurrent2);

                                            if (cardColor.equals(currentColor) || cardValue.equals(currentValue)) {
                                                playable = true;
                                            }
                                        }
                                    }
                                }
                                if (playable) {
                                    currentCard = aiCard;
                                    aiHand.remove(i);
                                    System.out.println("AI played: " + aiCard);
                                    if (aiHand.size() == 0) {
                                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ): OH NO, AI WINS!!!!! :( ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                                        gameOver = true;
                                        for (String card : playerHand) {
                                            if (card.contains("Draw Two") || card.contains("Skip") || card.contains("Wild")) {
                                                playerScore += 50;
                                            } else {
                                                playerScore += 20;
                                            }
                                            System.out.println("Scores:\nPlayer: " + playerScore + " V.S. AI: " + aiScore);
                                        }
                                    } else if (currentCard.contains("Wild Draw Four")) {
                                        System.out.println("Opponent makes you draw 4!");
                                        for (int j = 0; j < 4; j++) {
                                            if (!deck.isEmpty()) {
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
                                                aiHand.add(deck.removeFirst());
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
                                }
                                String aiDrawnCard = deck.remove(0);
                                System.out.println("AI drew a card.");
                                currentCard = aiDrawnCard;

                                boolean playable = false;
                                // Simplified card comparison
                                if (aiDrawnCard.contains("Wild")) {
                                    playable = true;
                                } else {
                                    int spaceIndexCard = aiDrawnCard.indexOf(' ');
                                    int spaceIndexCurrent = currentCard.indexOf(' ');
                                    String currentColor = currentCard.substring(0, spaceIndexCurrent);
                                    String cardColor = aiDrawnCard.substring(0, spaceIndexCard);
                                    if (aiDrawnCard.contains("Draw Two")) {
                                        if (cardColor.equals(currentColor)) {
                                            playable = true;
                                        }
                                    } else if (aiDrawnCard.contains("Skip")) {
                                        if (cardColor.equals(currentColor)) {
                                            playable = true;
                                        }
                                    } else {
                                        int spaceIndexCard2 = aiDrawnCard.length();
                                        int spaceIndexCurrent2 = currentCard.length();
                                        if (spaceIndexCard != -1 && spaceIndexCurrent != -1) {
                                            String cardValue = aiDrawnCard.substring(spaceIndexCard, spaceIndexCard2);
                                            String currentValue = currentCard.substring(spaceIndexCurrent, spaceIndexCurrent2);

                                            if (cardColor.equals(currentColor) || cardValue.equals(currentValue)) {
                                                playable = true;
                                            }
                                        }
                                    }
                                }
                                if (playable) {
                                    System.out.println("AI played: " + aiDrawnCard);
                                    //After AI plays, check if they won
                                    if (aiHand.isEmpty()) {
                                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ): OH NO, AI WINS!!!!! :( ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                                        gameOver = true;
                                        for (String card : playerHand) {
                                            if (card.contains("Draw Two") || card.contains("Skip") || card.contains("Wild")) {
                                                aiScore += 50;
                                            } else {
                                                aiScore += 20;
                                            }
                                            System.out.println("Scores:\nPlayer: " + playerScore + " V.S. AI: " + aiScore);
                                        }
                                    } else if (currentCard.contains("Wild")) {
                                        if (currentCard.contains("Wild Draw Four")) {
                                            System.out.println("Opponent makes you draw 4!");
                                            for (int j = 0; j < 4; j++) {
                                                if (!deck.isEmpty()) {
                                                    playerHand.add(deck.remove(0));
                                                }
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
                                            if (!deck.isEmpty()) {
                                                aiHand.add(deck.removeFirst());
                                            }
                                        }
                                        playerTurn = false;
                                    } else if (currentCard.contains("Skip")) {
                                        System.out.println("Your opponent skipped your turn!");
                                        playerTurn = false;
                                    }
                                } else {
                                    aiHand.add(aiDrawnCard);
                                    System.out.println("AI cannot play the drawn card.");
                                    playerTurn = true;
                                }
                            }
                        }
                    playerTurn = true;
                }
                //The replay option
                System.out.println("Do you want to play again? (yes or no)");
                String response = scanner.next();
                if (!response.equalsIgnoreCase("yes")) {
                    replay = false;
                }
                System.out.println("Final Scores: Player: " + playerScore + ", AI: " + aiScore);
                System.out.println("Thanks for playing!");
            }
        }
    }
}
