package com.example.tictactoe;

import java.util.HashMap;
import java.util.Map;

class TicTacToeGame {

    private Character[][] board = {
            {GameUtils.EMPTY_CHAR, GameUtils.EMPTY_CHAR, GameUtils.EMPTY_CHAR},
            {GameUtils.EMPTY_CHAR, GameUtils.EMPTY_CHAR, GameUtils.EMPTY_CHAR},
            {GameUtils.EMPTY_CHAR, GameUtils.EMPTY_CHAR, GameUtils.EMPTY_CHAR}
    };

    private Map<Character, Player> players;
    private Player lastPlayer;

    TicTacToeGame() {
        players = new HashMap<>();
        players.put(GameUtils.X, new Player(GameUtils.X));
        players.put(GameUtils.O, new Player(GameUtils.O));
    }

    /**
     * @return the board of the game
     */
    Character[][] getBoard() {
        return board;
    }

    /**
     * @param rowNumber    number of row on the board
     * @param columnNumber number of column on the board
     * @return the result of the movement. This can be an error message,
     * an ok message or a draw/winning message
     */
    String makeMove(int rowNumber, int columnNumber) {
        String result;

        // if position is valid
        if (cellPositionIsInvalid(rowNumber, columnNumber)) {
            result = GameUtils.INVALID_POSITION;
            return result;

        }

        // if the cell is already filled
        else if (cellIsFilled(rowNumber, columnNumber)) {
            result = GameUtils.ALREADY_FILLED_CELL;
            return result;
        }

        Player nextPlayer = getNextPlayer();
        this.board[rowNumber - 1][columnNumber - 1] = nextPlayer.getPlayerChar();
        lastPlayer = nextPlayer;

        // if there's winner with last movement
        if (thereIsWinner()) {
            result = GameUtils.WON_GAME;
        }
        // if there's no winner and board is full
        else if (isDrawGame()) {
            result = GameUtils.DRAW_GAME;
        }
        // if game can continue
        else {
            result = GameUtils.OK;
        }

        drawBoard();
        return result;
    }

    private void drawBoard() {
        for (int i = 0; i < GameUtils.BOARD_SIZE; i++) {
            String row = "";
            for (int j = 0; j < GameUtils.BOARD_SIZE; j++) {
                row = row.concat(getCharAtRealPosition(i, j).toString()).concat("\t");
            }
            System.out.println(row + "\n");
        }
        System.out.println("\n");
    }

    private boolean cellIsFilled(int rowNumber, int columnNumber) {
        return this.board[rowNumber - 1][columnNumber - 1] != GameUtils.EMPTY_CHAR;
    }

    private boolean cellIsEmpty(int rowNumber, int Number) {
        return this.board[rowNumber][Number] == GameUtils.EMPTY_CHAR;
    }

    /**
     * A movement cannot be made outside the board limits
     *
     * @param rowNumber    number of row on the board
     * @param columnNumber number of column on the board
     * @return boolean
     */
    private boolean cellPositionIsInvalid(int rowNumber, int columnNumber) {
        return rowNumber > GameUtils.BOARD_SIZE || columnNumber > GameUtils.BOARD_SIZE || columnNumber < 1 || rowNumber < 1;
    }

    /**
     * If the board is full and there's no winner, then the game is draw
     *
     * @return boolean
     */
    private boolean isDrawGame() {
        for (int i = 0; i < GameUtils.BOARD_SIZE; i++) {
            for (int j = 0; j < GameUtils.BOARD_SIZE; j++) {
                if (cellIsEmpty(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param byRows if the checking has to be made by rows or by columns
     * @return boolean saying if there's a winner checkint rows and columns
     */
    private boolean thereIsWinnerByLine(boolean byRows) {

        boolean thereIsWinner = false;
        Character lastCharacter;
        for (int i = 0; i < GameUtils.BOARD_SIZE; i++) {

            lastCharacter = (byRows) ? getCharAtRealPosition(i, 0) : getCharAtRealPosition(0, i);
            Character currentCharacter = GameUtils.EMPTY_CHAR;
            boolean sameLineCharacter = false;

            for (int j = 1; j < GameUtils.BOARD_SIZE; j++) {

                currentCharacter = (byRows) ? getCharAtRealPosition(i, j) : getCharAtRealPosition(j, i);
                sameLineCharacter = currentCharacter == lastCharacter;

                // if two adjacent cells has different char on the same line, then break the loop
                // and look up in the next line
                if (!sameLineCharacter) {
                    break;
                }
                lastCharacter = currentCharacter;
            }

            if (sameLineCharacter && currentCharacter != GameUtils.EMPTY_CHAR) {
                thereIsWinner = true;
                break;
            }

        }
        return thereIsWinner;
    }

    /**
     * @return boolean saying if there's a winner based on the first diagonal
     * the first diagonal starts on the top left corner and ends on the right bottom corner
     */
    private boolean thereIsWinnerByFirstDiagonal() {

        boolean thereIsWinner = false;
        Character lastCharacter;
        lastCharacter = getCharAtRealPosition(0, 0);
        boolean sameLineCharacter = false;
        Character currentCharacter = GameUtils.EMPTY_CHAR;

        for (int i = 1; i < GameUtils.BOARD_SIZE; i++) {

            currentCharacter = getCharAtRealPosition(i, i);
            sameLineCharacter = currentCharacter == lastCharacter;

            if (!sameLineCharacter) {
                break;
            }


            lastCharacter = currentCharacter;
        }

        if (sameLineCharacter && currentCharacter != GameUtils.EMPTY_CHAR) {
            thereIsWinner = true;
        }

        return thereIsWinner;
    }

    /**
     * @return boolean saying if there's a winner based on the second diagonal
     * the second diagonal starts on the left botton corner and ends on the right top corner
     */
    private boolean thereIsWinnerBySecondDiagonal() {

        Character currentCharacter = GameUtils.EMPTY_CHAR;
        boolean thereIsWinner = false;
        boolean sameCharacterInLine = false;
        int columnIndex = 1;
        int rowIndex = GameUtils.BOARD_SIZE - 2;

        Character lastCharacter = getCharAtRealPosition(GameUtils.BOARD_SIZE - 1, 0);
        for (int i = 1; i < GameUtils.BOARD_SIZE; i++) {

            currentCharacter = getCharAtRealPosition(rowIndex, columnIndex);
            sameCharacterInLine = currentCharacter == lastCharacter;

            if (!sameCharacterInLine) {
                break;
            }

            lastCharacter = currentCharacter;
            rowIndex--;
            columnIndex++;
        }

        if (sameCharacterInLine && currentCharacter != GameUtils.EMPTY_CHAR) {
            thereIsWinner = true;
        }
        return thereIsWinner;
    }

    /**
     * @return boolean saying if there's a winner checking rows, columns and diagonals
     */
    private boolean thereIsWinner() {
        return (thereIsWinnerByLine(true) || thereIsWinnerByLine(false) || thereIsWinnerByFirstDiagonal() || thereIsWinnerBySecondDiagonal());
    }


    /**
     * @param rowNumber    number of row
     * @param columnNumber number of column
     * @return character on board using a matrix position (starting always in 0)
     */
    Character getCharAtBoardPosition(int rowNumber, int columnNumber) {
        return this.board[rowNumber - 1][columnNumber - 1];
    }

    /**
     * @param rowNumber    number of row
     * @param columnNumber number of column
     * @return character on board using a real board position (starting always in 1)
     */
    private Character getCharAtRealPosition(int rowNumber, int columnNumber) {
        return this.board[rowNumber][columnNumber];
    }

    /**
     * @return next Player who has to make a movement.
     * if there's no current player, then X plays first by default
     * this method always return the next player based on the current player
     */
    Player getNextPlayer() {
        return (lastPlayer == null) ? players.get(GameUtils.X) : (getCurrentPlayer().getPlayerChar().equals(GameUtils.X)) ? players.get(GameUtils.O) : players.get(GameUtils.X);
    }

    /**
     * @return Current Player or last Player who made a movement
     */
    Player getCurrentPlayer() {
        return lastPlayer;
    }

    /**
     * @return map of players
     */
    Map getPlayers() {
        return this.players;
    }

    /**
     * @return numberOfPlayers in the game
     */
    int getNumberOfPlayers() {
        return this.players.size();
    }

}
