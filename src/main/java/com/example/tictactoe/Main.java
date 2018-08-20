package com.example.tictactoe;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);

        TicTacToeGame game = new TicTacToeGame();
        int row;
        int column;
        String movementResult;

        do {

            System.out.println("Next Player: " + game.getNextPlayer().getPlayerChar());

            System.out.print("Row Number: ");
            row = keyboard.nextInt();

            System.out.print("Column Number: ");
            column = keyboard.nextInt();

            movementResult = game.makeMove(row, column);

            System.out.println("Result : " + movementResult + "\n");
            System.out.println("---------------------------------\n");

        } while (!movementResult.equals(GameUtils.WON_GAME) && !movementResult.equals(GameUtils.DRAW_GAME));

    }
}
