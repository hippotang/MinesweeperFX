package com.hippotang;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.*;

import java.util.Timer;
import java.util.ArrayList;

public class MinesweeperFX extends Application {
    private Group root;
    private Level level;
    private Board board;
    private Rectangle[][] minefield;
    private ImageView[][] bottomLayer;
    private ImageView[][] topLayer;
    private ImageView[][] face;
    private Timer stopwatch;
    private Label numMines;
    private Button btnRestart;

    // variables concerning appearance and size
    double scale = 1.0;
    int sideLength = (int)(MineRectangle.blank.getWidth()*scale);
    int topRightCornerX = 40;
    int topRightCornerY = 40;

    // temp variables because i don't know how event handlers reference variables kek
    private int tempI;
    private int tempJ;
    private boolean isFirstMove;

    public static void main(String[] args) {
        launch(args);
    }

    public void test(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
    }

    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        board = new Board(Level.EXPERT);
        bottomLayer = new ImageView[board.rows][board.cols];
        topLayer = new ImageView[board.rows][board.cols];
        minefield = new Rectangle[board.rows][board.cols];
        btnRestart = new Button();
        isFirstMove = true;

        // set up toolbar - Game, Options, Help
        // Dropdown:
            // Game
                // Level (check)
                    // Beginner
                    // Intermediate
                    // Expert
                    // Custom (do later)
                // Opening Move (checked)
//        ToolBar toolBar = new ToolBar(g
//            new Label("Game"),
//            new Label("Options"),
//            new Label("Help")
//        );
//
//        root.getChildren().add(toolBar);

//        Test images
//        MineRectangle m = new MineRectangle();
//        ImageView[] imgViewList = new ImageView[m.imgArr.length];
//        for (int i = 0; i<m.imgArr.length; i++) {
//            imgViewList[i] = new ImageView();
//            imgViewList[i].setImage(m.imgArr[i]);
//            root.getChildren().add(imgViewList[i]);
//        }

        newGame(Level.EXPERT);

        Scene scene = new Scene(root, 800, 800, Color.LIGHTGRAY);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    // set up borders, smiley face restart button
    public void createTemplate() {
        ArrayList<ImageView> borders = new ArrayList<ImageView>();
        Image horizontalBorder = MineRectangle.horizontalBorder;
        Image verticalBorder = MineRectangle.verticalBorder;
        double height = (board.rows * sideLength) + 2 * sideLength + 3 * horizontalBorder.getHeight() * scale;
        double width = (board.cols * sideLength) + 2 * (verticalBorder.getWidth()) * scale;

        double tempHeight = horizontalBorder.getHeight() * scale;
        double tempWidth = horizontalBorder.getWidth() * scale;

        // top
        for (double x = topRightCornerX; x < topRightCornerX + width; x += tempWidth) {
            ImageView temp = new ImageView();
            temp.setImage(horizontalBorder);
            temp.setFitWidth(tempWidth);
            temp.setFitHeight(tempHeight);
            temp.setX(x);
            temp.setY(topRightCornerY);
            borders.add(temp);
        }
        // top #2
        for (double x = topRightCornerX; x < topRightCornerX + width; x += tempWidth) {
            ImageView temp = new ImageView();
            temp.setImage(horizontalBorder);
            temp.setFitWidth(tempWidth);
            temp.setFitHeight(tempHeight);
            temp.setX(x);
            temp.setY(topRightCornerY + tempHeight + 2 * sideLength);
            borders.add(temp);
        }
        // bottom
        for (double x = topRightCornerX; x < topRightCornerX + width; x += tempWidth) {
            ImageView temp = new ImageView();
            temp.setImage(horizontalBorder);
            temp.setFitWidth(tempWidth);
            temp.setFitHeight(tempHeight);
            temp.setX(x);
            temp.setY(height + 3 * tempHeight);
            borders.add(temp);
        }

        tempHeight = verticalBorder.getHeight() * scale;
        tempWidth = verticalBorder.getWidth() * scale;
        // left
        for (double y = topRightCornerY; y < topRightCornerY + height; y += tempHeight) {
            ImageView temp = new ImageView();
            temp.setImage(verticalBorder);
            temp.setFitWidth(tempWidth);
            temp.setFitHeight(tempHeight);
            temp.setX(topRightCornerX);
            temp.setY(y);
            borders.add(temp);
        }
        // right
        for (double y = topRightCornerY; y < topRightCornerY + height; y += tempHeight) {
            ImageView temp = new ImageView();
            temp.setImage(verticalBorder);
            temp.setFitWidth(tempWidth);
            temp.setFitHeight(tempHeight);
            temp.setX(topRightCornerX + board.cols * sideLength + tempWidth);
            temp.setY(y);
            borders.add(temp);
        }
        // add borders
        for (ImageView i : borders) {
            root.getChildren().add(i);
        }

        // restart button
        ImageView face = new ImageView();
        face.setImage(MineRectangle.facesmile);
        face.setX(this.topRightCornerX + width/2 - face.getFitWidth()/2);
        face.setY(this.topRightCornerY+horizontalBorder.getHeight()*scale);
        root.getChildren().add(face);
        Rectangle faceRect = new Rectangle(face.getX(), face.getY(), MineRectangle.facesmile.getWidth(),
                MineRectangle.facesmile.getHeight());
        faceRect.setFill(Color.TRANSPARENT);
        Level tempLevel = this.level;
        faceRect.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                newGame(tempLevel);
            }
        });
        root.getChildren().add(faceRect);
    }

    // start a new Game
    public void newGame(Level level) {
        // clear canvas and set first move as true
        root.getChildren().clear();
        this.isFirstMove = true;

        // set Level
        this.level = level;

        // create field
        board = new Board(this.level);

        // Debug text output
        numMines = new Label("i'm  just a label");
        numMines.setLayoutX(10);
        numMines.setLayoutY(400);
        root.getChildren().add(numMines);

        // Restart button
        btnRestart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                newGame(level);
            }
        });
        btnRestart.setText("Restart");
        btnRestart.setLayoutX(numMines.getLayoutX());
        btnRestart.setLayoutY(numMines.getLayoutY()+numMines.getHeight()-sideLength);
        root.getChildren().add(btnRestart);

        // set up board
        double boardTopRightX = topRightCornerX+MineRectangle.verticalBorder.getWidth()*scale;
        double boardTopRightY = topRightCornerY+2*MineRectangle.horizontalBorder.getHeight()*scale + sideLength*2;
        for (int i = 0; i < board.rows; i++) {
            for (int j = 0; j < board.cols; j++) {
                // bottom layer: opened cell images
                bottomLayer[i][j] = new ImageView();
                bottomLayer[i][j].setImage(MineRectangle.cellNumImgArr[board.getCell(i,j).getNumber()]);
                bottomLayer[i][j].setFitHeight(sideLength);
                bottomLayer[i][j].setFitWidth(sideLength);
                bottomLayer[i][j].setX((j*sideLength + boardTopRightX));
                bottomLayer[i][j].setY((i*sideLength + boardTopRightY));
                // bottomLayer: Mines
                if (board.getCell(i,j).getHasMine()) {
                    bottomLayer[i][j].setImage(MineRectangle.bombRevealed);
                    bottomLayer[i][j].setFitHeight(sideLength);
                    bottomLayer[i][j].setFitWidth(sideLength);
                }
                root.getChildren().add(bottomLayer[i][j]);
                // top layer: closed cell images
                topLayer[i][j] = new ImageView();
                topLayer[i][j].setImage(MineRectangle.blank);
                topLayer[i][j].setFitHeight(sideLength);
                topLayer[i][j].setFitWidth(sideLength);
                topLayer[i][j].setX(bottomLayer[i][j].getX());
                topLayer[i][j].setY(bottomLayer[i][j].getY());
//                topLayer[i][j].setVisible(false);
                root.getChildren().add(topLayer[i][j]);
                // placeholder rectangles (to set onclick functions)
                minefield[i][j] = new MineRectangle((int)bottomLayer[i][j].getX(), (int)bottomLayer[i][j].getY(),
                        sideLength, sideLength);
                minefield[i][j].setFill(Color.TRANSPARENT);
                root.getChildren().add(minefield[i][j]);
                tempI = i;
                tempJ = j;
                minefield[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    private int handlerI = tempI;
                    private int handlerJ = tempJ;

                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton().equals(MouseButton.PRIMARY)){
                            open(handlerI, handlerJ);
                            //numMines.setText(numMines.getText() + "\nhandle() called open(" + handlerI + ", " + handlerJ + ")");
                        } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                            changeState(handlerI, handlerJ);
                        }
                            else {
                            numMines.setText("Something is happening but idk what");
                        }
                    }
                });
            }
        }
        createTemplate();
    }

    public void open(int i, int j) {
        board.getCell(i,j).makeOpen();
        if (isFirstMove) {
            board.openingMove(board.getCell(i,j));
            updateBoardAfterOpeningMove();
            isFirstMove = false;
            return;
        }
        numMines.setText("you clicked a thing" + i + " and " + j);

        if (board.getCell(i,j).getHasMine()) {
            gameOver();
        }
        if (board.getCell(i,j).getState()==States.FLAGGED) {
            return;
        }
        if (board.getCell(i,j).getNumber() == 0) {
            board.openAnyResultingSquares(board.getCell(i,j));
            updateBoard();
        }
        if (board.getCell(i,j).isOpen()) {
            topLayer[i][j].setVisible(false);
        }
    }

    public void changeState(int i, int j) {
        switch (board.getCell(i,j).getState()) {
            case BLANK:
                board.getCell(i,j).setState(States.FLAGGED);
                topLayer[i][j].setImage(MineRectangle.bombFlagged);
                break;
            case FLAGGED:
                board.getCell(i,j).setState(States.UNKNOWN);
                topLayer[i][j].setImage(MineRectangle.bombQuestion);
                break;
            case UNKNOWN:
                board.getCell(i,j).setState(States.BLANK);
                topLayer[i][j].setImage(MineRectangle.blank);
                break;
            default:
                numMines.setText("method changeState doesn't know what to do");
                break;
        }
    }

    public void updateBoard() {
        for (int i = 0; i<board.rows; i++) {
            for (int j = 0; j<board.cols;j++) {
                if (board.getCell(i,j).isOpen()) {
                    topLayer[i][j].setVisible(false);
                }
            }
        }
        //ADD MORE AS YOU GO
    }

    public void updateBoardAfterOpeningMove() {
        for (int i = 0; i<board.rows; i++) {
            for (int j = 0; j<board.cols;j++) {
                // bottom layer: opened cell images
                bottomLayer[i][j].setImage(MineRectangle.cellNumImgArr[board.getCell(i,j).getNumber()]);
                bottomLayer[i][j].setFitHeight(sideLength);
                bottomLayer[i][j].setFitWidth(sideLength);
                // bottomLayer: Mines
                if (board.getCell(i,j).getHasMine()) {
                    bottomLayer[i][j].setImage(MineRectangle.bombRevealed);
                    bottomLayer[i][j].setFitHeight(sideLength);
                    bottomLayer[i][j].setFitWidth(sideLength);
                }
            }
        }
        updateBoard();
    }

    public void gameOver() {
        numMines.setText("GAME OVER");

    }

    public void updateBoardAfterGameOver() {

    }
}


