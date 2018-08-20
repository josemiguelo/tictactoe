package com.example.tictactoe;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TicTacToeGameSpec {

    private TicTacToeGame game;
    private String moveResult;

    @Before
    public void beforeMethod() {
        game = new TicTacToeGame();
    }

    @Test
    public void whenGameInstancedThenNewBoard() {
        Character[][] board = {
                {GameUtils.EMPTY_CHAR, GameUtils.EMPTY_CHAR, GameUtils.EMPTY_CHAR},
                {GameUtils.EMPTY_CHAR, GameUtils.EMPTY_CHAR, GameUtils.EMPTY_CHAR},
                {GameUtils.EMPTY_CHAR, GameUtils.EMPTY_CHAR, GameUtils.EMPTY_CHAR}
        };
        Assert.assertEquals(game.getBoard(), board);
    }

    @Test
    public void whenGameInstancedThenNewPlayersAreCreated() {
        Assert.assertEquals(2, game.getNumberOfPlayers());
        Assert.assertNotNull(game.getPlayers().get(GameUtils.X));
        Assert.assertNotNull(game.getPlayers().get(GameUtils.O));
    }

    @Test
    public void getGameInstancedThenFirstPlayerIsX() {
        Assert.assertNull(game.getCurrentPlayer());
        Assert.assertEquals(GameUtils.X, game.getNextPlayer().getPlayerChar());
    }

    @Test
    public void playersMoveByTurns() {
        System.out.println(">>> playersMoveByTurns <<<");

        //Start of the game, current player is null, but nextPlayer is X
        Assert.assertNull(game.getCurrentPlayer());
        Assert.assertEquals(GameUtils.X, game.getNextPlayer().getPlayerChar());

        game.makeMove(1, 1); //X
        Assert.assertEquals(GameUtils.X, game.getCurrentPlayer().getPlayerChar());
        Assert.assertEquals(GameUtils.O, game.getNextPlayer().getPlayerChar());

        game.makeMove(2, 2); //O
        Assert.assertEquals(GameUtils.O, game.getCurrentPlayer().getPlayerChar());
        Assert.assertEquals(GameUtils.X, game.getNextPlayer().getPlayerChar());
    }

    @Test
    public void WhenFirstPlayerMovesThenOk() {
        System.out.println(">>> WhenFirstPlayerMovesThenOk <<<");

        moveResult = game.makeMove(1, 1);
        Assert.assertEquals(GameUtils.OK, moveResult);
        Assert.assertEquals(GameUtils.X, game.getCharAtBoardPosition(1, 1));
    }

    @Test
    public void whenPlayerUsesAFilledCellThenErrorMessage() {
        System.out.println(">>> whenPlayerUsesAFilledCellThenErrorMessage <<<");

        moveResult = game.makeMove(2, 2);
        Assert.assertEquals(GameUtils.OK, moveResult);
        Assert.assertEquals(GameUtils.X, game.getCharAtBoardPosition(2, 2));

        moveResult = game.makeMove(2, 2);
        Assert.assertEquals(GameUtils.ALREADY_FILLED_CELL, moveResult);
    }

    @Test
    public void WhenPlayerUsesCellBeyondMatrixLimitsThenErrorMessage() {
        System.out.println(">>> WhenPlayerUsesCellBeyondMatrixLimitsThenErrorMessage <<<");

        moveResult = game.makeMove(0, 0);
        Assert.assertEquals(GameUtils.INVALID_POSITION, moveResult);

        moveResult = game.makeMove(1, 1);
        Assert.assertEquals(GameUtils.OK, moveResult);

        moveResult = game.makeMove(2, 2);
        Assert.assertEquals(GameUtils.OK, moveResult);

        moveResult = game.makeMove(3, 3);
        Assert.assertEquals(GameUtils.OK, moveResult);

        moveResult = game.makeMove(4, 4);
        Assert.assertEquals(GameUtils.INVALID_POSITION, moveResult);
    }

    @Test
    public void WhenGameIsDrawThenDrawMessage() {
        System.out.println(">>> WhenGameIsDrawThenDrawMessage <<<");

        moveResult = game.makeMove(1, 1);//X
        Assert.assertEquals(GameUtils.OK, moveResult);

        moveResult = game.makeMove(2, 2);//O
        Assert.assertEquals(GameUtils.OK, moveResult);

        moveResult = game.makeMove(3, 3);//X
        Assert.assertEquals(GameUtils.OK, moveResult);

        moveResult = game.makeMove(2, 3);//X
        Assert.assertEquals(GameUtils.OK, moveResult);

        moveResult = game.makeMove(2, 1);//O
        Assert.assertEquals(GameUtils.OK, moveResult);

        moveResult = game.makeMove(3, 1);//X
        Assert.assertEquals(GameUtils.OK, moveResult);

        moveResult = game.makeMove(3, 2);//X
        Assert.assertEquals(GameUtils.OK, moveResult);

        moveResult = game.makeMove(1, 2);//O
        Assert.assertEquals(GameUtils.OK, moveResult);

        moveResult = game.makeMove(1, 3);//X
        Assert.assertEquals(GameUtils.DRAW_GAME, moveResult);

    }

    @Test
    public void WhenRowIsFilledThenWinnerMessage() {
        System.out.println(">>> WhenRowIsFilledThenWinnerMessage <<<");
        moveResult = game.makeMove(1, 1);//X
        moveResult = game.makeMove(2, 1);//O
        moveResult = game.makeMove(1, 2);//X
        moveResult = game.makeMove(2, 2);//O
        moveResult = game.makeMove(1, 3);//X
        Assert.assertEquals(GameUtils.WON_GAME, moveResult);
    }

    @Test
    public void WhenColumnIsFilledThenWinnerMessage() {
        System.out.println(">>> WhenColumnIsFilledThenWinnerMessage <<<");
        moveResult = game.makeMove(1, 1);//X
        moveResult = game.makeMove(1, 2);//O
        moveResult = game.makeMove(2, 1);//X
        moveResult = game.makeMove(1, 3);//O
        moveResult = game.makeMove(3, 1);//X
        Assert.assertEquals(GameUtils.WON_GAME, moveResult);
    }

    @Test
    public void WhenFirstDiagoalIsFilledThenWinnerMessage() {
        System.out.println(">>> WhenFirstDiagoalIsFilledThenWinnerMessage <<<");
        moveResult = game.makeMove(1, 1);//X
        moveResult = game.makeMove(1, 2);//O
        moveResult = game.makeMove(2, 2);//X
        moveResult = game.makeMove(1, 3);//O
        moveResult = game.makeMove(3, 3);//X
        Assert.assertEquals(GameUtils.WON_GAME, moveResult);
    }

    @Test
    public void WhenSecondDiagoalIsFilledThenWinnerMessage() {
        System.out.println(">>> WhenSecondDiagoalIsFilledThenWinnerMessage <<<");
        moveResult = game.makeMove(3, 1);//X
        moveResult = game.makeMove(1, 1);//O
        moveResult = game.makeMove(2, 2);//X
        moveResult = game.makeMove(1, 2);//O
        moveResult = game.makeMove(1, 3);//X
        Assert.assertEquals(GameUtils.WON_GAME, moveResult);
    }
}
