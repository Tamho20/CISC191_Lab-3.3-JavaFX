package edu.sdccd.cisc191;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Presents the user with the game graphical user interface
 */
public class ViewGameBoard extends Application
{
    private Canvas gameCanvas;
    private ControllerGameBoard controller;
    private GameBoardLabel fishRemaining;
    private GameBoardLabel guessesRemaining;
    private GameBoardLabel message;

    public static void main(String[] args)
    {
        launch(args);
    }

    public void updateHeader() {
        fishRemaining.setText("Fish: " + controller.modelGameBoard.getFishRemaining());
        guessesRemaining.setText("Bait: " + controller.modelGameBoard.getGuessesRemaining());
        if(controller.fishWin()) {
            message.setText("Fishes win!");
        } else if(controller.playerWins()) {
            message.setText("You win!");
        } else {
            message.setText("Find the fish!");
        }
    }
    @Override
    public void start(Stage stage) throws Exception {
        controller = new ControllerGameBoard();
        gameCanvas = new Canvas();

        fishRemaining = new GameBoardLabel();
        guessesRemaining = new GameBoardLabel();
        message = new GameBoardLabel();

        BorderPane layout = new BorderPane();
        HBox header = new HBox();
        header.getChildren().addAll(fishRemaining,guessesRemaining, message);
        VBox columnContainer = new VBox(6);
        layout.setTop(header);
        updateHeader();
        for (int row=0; row < ModelGameBoard.DIMENSION; row++) {
            HBox rowContainer = new HBox(6);
            for (int col=0; col < ModelGameBoard.DIMENSION; col++) {
                GameBoardButton button = new GameBoardButton(row, col, controller.modelGameBoard.fishAt(row,col));
                int finalRow = row;
                int finalCol = col;
                button.setOnAction(e -> {
                    controller.makeGuess(finalRow, finalCol);
                    if(!controller.isGameOver()) {
                        button.handleClick();
                        updateHeader();
                    }
                });
                rowContainer.getChildren().add(button);
            }
            columnContainer.getChildren().add(rowContainer);
        }

        layout.setCenter(columnContainer);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.setTitle("Gone Fishing");
        stage.show();
    }
}
