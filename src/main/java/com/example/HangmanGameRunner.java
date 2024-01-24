package com.example;

import java.util.HashSet;
import java.util.Set;

public class HangmanGameRunner {

    public static void main(String[] args) {
        Set<Character> guessedLetters = new HashSet<>();
        IUserInputOutput ui = new HangmanConsoleUI(guessedLetters);
        WordRetriever wordRetriever = new FileWordRetriever("src/main/resources/words.txt");
        HangmanGame game = new HangmanGame(ui, wordRetriever);

        ui.displayMessage("Welcome to Hangman!");
        game.startNewGame();

        while (!game.isGameOver()) {
            ui.displayMessage("Current Word: " + game.getCurrentWord().getRevealedWord());
            ui.displayMessage("Guesses Remaining: " + game.getPlayer().getLives());
            game.processGuess();
            
            if (game.isGameOver()) {
                if (game.hasWon()) {
                    ui.displayMessage("Congratulations! You've guessed '" + game.getCurrentWord().getWord() + "' correctly!");
                } else {
                    ui.displayMessage("You're completely hung. The word was '" + game.getCurrentWord().getWord() + "'.");
                }
            }

            System.out.println("-----------------------");
        }
    }
    
}
