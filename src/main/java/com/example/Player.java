package com.example;

public class Player {

    public static final int DEFAULT_LIVES = 8;
    private int lives;
    
    public Player() {
        this.lives = DEFAULT_LIVES;
    }

    public void decreaseLives() {
        lives--;
    }

    public boolean isAlive() {
        return lives > 0;
    }

    public int getLives() {
        return lives;
    }

}
