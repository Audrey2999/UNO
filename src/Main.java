import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Initialize deck
        ArrayList<String> deck = new ArrayList<>();
        String[] colors = {"Red", "Blue", "Green", "Yellow"};
        String[] specialCards = {"Draw Two", "Skip"};

        // Add number cards to the deck
        for (String color : colors) {
            deck.add(color);
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
        ArrayList<String> AiHand = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            playerHand.add(deck.removeFirst());
            AiHand.add(deck.removeFirst());
        }

        // Start the game (must not be a special card)
        String currentCard = deck.removeFirst();
        while (currentCard.contains("Draw")||currentCard.contains("Skip")||currentCard.contains("Wild")) {
            deck.add(currentCard);
            currentCard = deck.removeFirst();
        }
        System.out.println("Starting card: " + currentCard);
        boolean playerTurn = true;
        boolean gameOver = false;

        //
    }
}