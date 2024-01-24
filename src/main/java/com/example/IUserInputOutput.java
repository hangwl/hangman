package com.example;

import java.util.Set;

public interface IUserInputOutput {
    void displayMessage(String message);
    String readInput();
    void displayGuessedLetters(Set<Character> guessedLetters);
}
