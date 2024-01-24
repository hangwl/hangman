package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class HangmanConsoleUI implements IUserInputOutput {

    private Scanner scanner = new Scanner(System.in);
    private Set<Character> guessedLetters = new HashSet<>();

    public HangmanConsoleUI(Set<Character> guessedLetters) {
        this.guessedLetters = guessedLetters;
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String readInput() {
        return scanner.nextLine().trim();
    }

    @Override
    public void displayGuessedLetters(Set<Character> guessedLetters) {
        List<Character> sortedLetters = new ArrayList<>(guessedLetters);
        Collections.sort(sortedLetters);

        System.out.print("Guessed Letters: ");
        for (char letter : sortedLetters) {
            System.out.print(letter + " ");
        }
        System.out.println();
    }

    public void resetGuessedLetters() {
        guessedLetters.clear();
    }

}
