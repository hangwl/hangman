package com.example;

public class Word {

    private String word;
    private String revealedWord;

    public Word(String word) {
        this.word = word;
        this.revealedWord = "_".repeat(word.length());
    }

    public String getRevealedWord() {
        return revealedWord;
    }

    public void revealLetter(char letter) {
        StringBuilder newRevealedWord = new StringBuilder(revealedWord);
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                newRevealedWord.setCharAt(i, letter);
            }
        }
        revealedWord = newRevealedWord.toString();
    }

    public boolean isFullyRevealed() {
        return !revealedWord.contains("_");
    }

    public String getWord() {
        return word;
    }

}
