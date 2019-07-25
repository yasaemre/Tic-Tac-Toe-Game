import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Ellipse;

//********************************************************************
//XsAndOs -    Tic Tac Toe Game
//@authors     Emre Yasa, Helen(Minyi) Zhao, Katherine Davey
//@version     5.00
//@since       2018-10-31
//********************************************************************
/**
 * Algorithm:
 * 1. Create a XsAndOs class
 * 2. Create an array of [3][3] and initialize character '#' in each cell
 * 3. Take 'X' for X’s turn and 'O' for O’s turn as values in chosen cell
 * 4. Check for possible draw
 * 5. Validate if the chosen cell is already taken 
 * 6. Check each row, column and diagonals for the possible winner
 * 7. If there is a winner, will tell who won, program is done and exits manually
      by user. 
 */

public class XsAndOs extends Application {

 private boolean gameOver = false; //flag
 private char whoseTurn = 'X'; // 'X' or 'O' but 'X' starts 
 private Cell[][] board = new Cell[3][3]; //the board for playing
 private Label statusLabel = new Label("X's turn to play"); //let user know status of game

 @Override
 // Sets the grid
 public void start(Stage primaryStage) {
  GridPane pane = new GridPane();
  for (int i = 0; i < 3; i++)
   for (int j = 0; j < 3; j++)
    pane.add(board[i][j] = new Cell(), j, i);

  BorderPane borderPane = new BorderPane();
  borderPane.setCenter(pane);
  borderPane.setBottom(statusLabel);

  Scene scene = new Scene(borderPane, 300, 300);
  primaryStage.setTitle("XsAndOs");
  primaryStage.setScene(scene);
  primaryStage.show();
 }

 // check the board either full or not, if the cell is blank, keep playing the game.
 // If true, then isFull() will be true and will give a draw based in handleMouseClick().
 public boolean isFull() {
  for (int i = 0; i < 3; i++)
   for (int j = 0; j < 3; j++)
    if (board[i][j].getToken() == ' ')
     return false;
  return true;
 }

 // Boolean loop, first for loop checks the horizontal cells within i.
 // Second for loop checks the vertical cells within j.
 // Third for loop checks the diagonal starting from (0,0) going southeast.
 // Fourth for loop checks diagonal starting from (0,0) going southwest.
 // If any of the four for loops is triggered, will result in someone winning.
 // Otherwise, gameover is still false and game is continued. 
 public boolean hasWon(char tkn) {
  for (int i = 0; i < 3; i++)
   if (board[i][0].getToken() == tkn &&
    board[i][1].getToken() == tkn &&
    board[i][2].getToken() == tkn) {
    return true;
   }

  for (int j = 0; j < 3; j++)
   if (board[0][j].getToken() == tkn &&
    board[1][j].getToken() == tkn &&
    board[2][j].getToken() == tkn) {
    return true;
   }

  if (board[0][0].getToken() == tkn &&
   board[1][1].getToken() == tkn &&
   board[2][2].getToken() == tkn) {
   return true;
  }

  if (board[0][2].getToken() == tkn &&
   board[1][1].getToken() == tkn &&
   board[2][0].getToken() == tkn) {
   return true;
  }
  return false;
 }

 //HERE IS INNER CLASS REPRESENTING ONE CELL IN BOARD
 //The inner class has access to all of the outer classes data/methods
 public class Cell extends Pane {

  private char token = ' '; // one of blank, X, or O
  public Cell() {
   setStyle("-fx-border-color: black");
   setPrefSize(100, 100);

   //lambda notation to access private handleMouseClick()
   setOnMouseClicked(e -> handleMouseClick());
  }

  // Returns char token, starts out as blank, then will begin with X and then
  // O, continues for getToken() between char ‘X’ and char ‘O’
  public char getToken() {
   return token;
  }

  // Draws the two line to form an X
  public void drawX() {
   double w = getWidth();
   double h = getHeight();
   Line line1 = new Line(10.0, 10.0, w - 10.0, h - 10.0);
   Line line2 = new Line(10.0, h - 10.0, w - 10.0, 10.0);
   line1.setStroke(Color.BLUE);
   line2.setStroke(Color.BLUE);
   line1.setStrokeWidth(5);
   line2.setStrokeWidth(5);
   getChildren().addAll(line1, line2);
  }

  // Draws the circle using ellipse 
  public void drawO() {
   double w = getWidth();
   double h = getHeight();
   Ellipse ellipse = new Ellipse(w / 2, h / 2, w / 2 - 10.0, h / 2 - 10.0);
   ellipse.setStroke(Color.RED);
   ellipse.setFill(null);
   ellipse.setStrokeWidth(5);
   getChildren().add(ellipse);
  }

  // Sets char c, first will be X’s turn, then goes to O’s turn 
  // after, switches back to X’s turn and continues the loop.
  // UPDATE token TO VALUE OF ARGUMENT ‘c’
  public void setToken(char c) {
   if (c == 'X')
    drawX();
   else
    drawO();
   token = c;
  }

  /*Game logic each time mouse click occurs. Game over flag starts as false
  to check whose turn is it until either isFull() or hasWon() is triggered.
  Once triggered, hasWon() will check the cells with the combination. If
  someone did win, will display that person at the status label. Otherwise,
  will display a draw. If user clicks an existing X or O in a cell, status label 
  will say "Cell is taken!" and will not overwrite the existing cell.
  Program ends after user closes out the program them self.
    */
  private void handleMouseClick() {
   String s = "";
   // If cell is empty and game is not over
   if (!gameOver) {
    if (token == ' ') {
     setToken(whoseTurn); // Set token in the cell
     if (hasWon(whoseTurn)) {
      gameOver = true;
      s = whoseTurn + " won! The game is over.";
     } else if (isFull()) {
      gameOver = true;
      s = "Draw! The game is over.";
     } else {
      // Change the turn
      whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
      // Display whose turn
      s = whoseTurn + "'s turn.";
     }
     statusLabel.setText(s);

    } else {
     statusLabel.setText("The Cell has been taken!");
    }
   }
  } // handleMouseClick class
 } //Cell class 

 // Launches the program. 
 public static void main(String[] args) {
  launch(args);
 }
} // XsAndOs class


