package com.example;

import java.util.HashSet;
import java.util.Set;

public class HangmanGame {

    private Player player;
    private Word currentWord;
    private boolean gameOver;
    private IUserInputOutput io;
    private Set<Character> guessedLetters = new HashSet<>();
    private WordRetriever wordRetriever;

    public HangmanGame(IUserInputOutput io, WordRetriever wordRetriever) {
        this.player = new Player();
        this.gameOver = false;
        this.io = io;
        this.wordRetriever = wordRetriever;
    }

    public void startNewGame() {
        String randomWord = wordRetriever.getRandomWord();
        if (randomWord != null) {
            this.currentWord = new Word(randomWord);
            this.gameOver = false;
            guessedLetters.clear();
        } else {
            io.displayMessage("Error: Unable to retrieve a word for the game.");
        }
    }

    public void processGuess() {
        if (!gameOver && !currentWord.isFullyRevealed()) {
            io.displayGuessedLetters(guessedLetters);
            char guess = getValidGuess();

            if (guessedLetters.contains(guess)) {
                io.displayMessage("You have already guessed '" + guess + "'. Try a different letter.");
                return;
            }

            guessedLetters.add(guess);
            String initialRevealedWord = currentWord.getRevealedWord();
            currentWord.revealLetter(guess);

            if (initialRevealedWord.equals(currentWord.getRevealedWord())) {
                player.decreaseLives();

                if (player.getLives() > 0) {
                    io.displayMessage("Incorrect guess. Try again.");
                } else {
                    io.displayMessage("Incorrect guess.");
                    gameOver = true;
                }
            } else {
                io.displayMessage("Correct guess!");
            }

            if (currentWord.isFullyRevealed()) {
                gameOver = true;
            }
        }
    }

    private char getValidGuess() {
        while (true) {
            io.displayMessage("Enter your guess: ");
            String input = io.readInput().toLowerCase();
            if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
                return input.charAt(0);
            } else {
                io.displayMessage("Invalid input. Please enter a single alphabet.");
            }
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean hasWon() {
        return currentWord != null && currentWord.isFullyRevealed() && player.getLives() > 0;
    }

    public Player getPlayer() {
        return player;
    }

    public Word getCurrentWord() {
        return currentWord;
    }

}
