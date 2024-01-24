package com.example;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AppTest {

    @Mock
    private IUserInputOutput mockIo;

    @Mock
    private WordRetriever mockWordRetriever;

    private HangmanGame game;

    @BeforeEach
    public void setUp() {
        game = new HangmanGame(mockIo, mockWordRetriever);
    }

    @Test
    public void testWordInitialization() {
        Word word = new Word("Example");
        assertEquals("_______", word.getRevealedWord());
    }

    @Test
    public void testRevealLetter() {
        Word word = new Word("example");
        word.revealLetter('a');
        assertEquals("__a____", word.getRevealedWord());
    }

    @Test
    public void testRevealLetterMultipleOccurrences() {
        Word word = new Word("example");
        word.revealLetter('e');
        assertEquals("e_____e", word.getRevealedWord());
    }

    @Test
    public void testRevealNonExistingLetter() {
        Word word = new Word("example");
        word.revealLetter('z');
        assertEquals("_______", word.getRevealedWord());
    }

    @Test
    public void testPlayerInitialization() {
        Player player = new Player();
        assertEquals(Player.DEFAULT_LIVES, player.getLives());
    }

    @Test
    public void testDecreaseLives() {
        Player player = new Player();
        player.decreaseLives();
        assertEquals(Player.DEFAULT_LIVES - 1, player.getLives());
    }

    @Test
    public void testIsAlive() {
        Player player = new Player();
        assertTrue(player.isAlive());
    }

    @Test
    public void testGameInitialization() {
        assertNotNull(game.getPlayer());
        assertNull(game.getCurrentWord());
        assertFalse(game.isGameOver());
    }

    @Test
    public void testProcessGuessWithCorrectLetter() {
        when(mockWordRetriever.getRandomWord()).thenReturn("apple");
        game.startNewGame();
        when(mockIo.readInput()).thenReturn("a");
        game.processGuess();
        assertEquals("a____", game.getCurrentWord().getRevealedWord());
    }

    @Test
    public void testProcessGuessWithIncorrectLetter() {
        when(mockWordRetriever.getRandomWord()).thenReturn("apple");
        game.startNewGame();
        when(mockIo.readInput()).thenReturn("z");
        game.processGuess();
        assertEquals("_____", game.getCurrentWord().getRevealedWord());
        assertEquals(Player.DEFAULT_LIVES - 1, game.getPlayer().getLives());
    }

    @Test
    public void testProcessGuessWithRepeatedLetter() {
        when(mockWordRetriever.getRandomWord()).thenReturn("apple");
        game.startNewGame();
        when(mockIo.readInput()).thenReturn("a", "a");
        game.processGuess();
        game.processGuess();
        verify(mockIo, times(1)).displayMessage("You have already guessed 'a'. Try a different letter.");
    }

    @Test
    public void testInvalidUserInputs() {
        when(mockWordRetriever.getRandomWord()).thenReturn("apple");
        game.startNewGame();
    
        String[] invalidInputs = {"", "12", "#", "ab"};
        for (String input : invalidInputs) {
            when(mockIo.readInput()).thenReturn(input, "a");
            game.processGuess();
            verify(mockIo).displayMessage("Invalid input. Please enter a single alphabet.");
            reset(mockIo);
        }
        assertEquals("a____", game.getCurrentWord().getRevealedWord());
    }

    @Test
    public void testWinConditionWithMock() {
        when(mockWordRetriever.getRandomWord()).thenReturn("cat");
        game.startNewGame();
        when(mockIo.readInput()).thenReturn("c", "a", "t");
        game.processGuess();
        game.processGuess();
        game.processGuess();
        assertTrue(game.hasWon());
        assertTrue(game.isGameOver());
    }

    @Test
    public void testLoseConditionWithMock() {
        when(mockWordRetriever.getRandomWord()).thenReturn("cat");
        game.startNewGame();
        when(mockIo.readInput()).thenReturn("d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
                "s", "u", "v", "w", "x", "y", "z");
        for (int i = 0; i < Player.DEFAULT_LIVES; i++) {
            game.processGuess();
        }
        assertFalse(game.getPlayer().isAlive());
        assertTrue(game.isGameOver());
    }

    @Test
    public void testGameRestart() {
        when(mockWordRetriever.getRandomWord()).thenReturn("apple");
        game.startNewGame();
        when(mockIo.readInput()).thenReturn("a", "p", "l", "e");
        while (!game.isGameOver()) {
            game.processGuess();
        }
        assertTrue(game.hasWon());


        when(mockWordRetriever.getRandomWord()).thenReturn("banana");
        game.startNewGame();
        assertNotNull(game.getCurrentWord());
        assertEquals("______", game.getCurrentWord().getRevealedWord());
        assertEquals(Player.DEFAULT_LIVES, game.getPlayer().getLives());
        assertFalse(game.isGameOver());

        when(mockIo.readInput()).thenReturn("b", "n");
        game.processGuess();
        game.processGuess();
        assertEquals("b_n_n_", game.getCurrentWord().getRevealedWord());
    }

}
