package Teacher_lec20;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GUI_TicTacToe extends JFrame implements ActionListener, TicTacToeInterface{
	private JPanel jpMain;
	private JPanel jpBoard;
	private JPanel jpIO;
	private JLabel displayOut;
	private JButton [][] board;// = new JButton[3][3];
	private String currPlayer = "X";


	public GUI_TicTacToe(){
		this.setTitle("TIC TAC TOE");
		jpMain = new JPanel();
		jpMain.setLayout(new BorderLayout());

		jpIO = new JPanel();
		displayOut = new JLabel();
		updateOut("Let's play ! \'"+currPlayer+"\' goes first");
		jpIO.add(displayOut);
		jpMain.add(jpIO, BorderLayout.NORTH);

		jpBoard = new JPanel();
		jpBoard.setLayout(new GridLayout(3,3));
		displayBoard();

		jpMain.add(jpBoard, BorderLayout.CENTER);
		add(jpMain);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(400, 400);
		this.setVisible(true);
	}


	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater( new Runnable(){
			public void run() {
				GUI_TicTacToe gui = new GUI_TicTacToe();
			}	
		});
	}

	public void updateOut(String msg){
		displayOut.setText("<HTML><H1 color=red>"+msg+"</H1></HTML>");
	}
	public void playAnotherGame(){
		int yesNo = JOptionPane.showConfirmDialog(null, "Do you want to play another game?");
		if(yesNo == 0){
			clearBoard();
			updateOut(currPlayer+" goes first!");
		}
		else{
			updateOut("Thanks for playing");
			JOptionPane.showMessageDialog(null, "BYE");
			System.exit(EXIT_ON_CLOSE);
		}
		System.out.println(yesNo);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btnClicked = (JButton) e.getSource();
		btnClicked.setText(currPlayer);
		//display color
		if(currPlayer.equalsIgnoreCase("X")){
			btnClicked.setBackground(Color.pink);
		}
		else{
			btnClicked.setBackground(Color.yellow);
		}
		btnClicked.setEnabled(false);
		if(isWinner(currPlayer) || isFull()){
			displayWinner();
			playAnotherGame();
		}
		updateCurrPlayer();
		updateOut(currPlayer + " goes");

	}
	@Override
	public void displayBoard() {
		board = new JButton[3][3];
		for(int row=0; row<board.length; row++){
			for(int col=0; col<board[row].length; col++){
				board[row][col] = new JButton();
				board[row][col].setFont(new Font(Font.SANS_SERIF,Font.BOLD,60));
				board[row][col].addActionListener(this);
				board[row][col].setEnabled(true);
				jpBoard.add(board[row][col]);
			}
		}
	}
	@Override
	public void clearBoard() {
		for(int row=0; row<board.length; row++){
			for(int col=0; col<board[row].length; col++){
				board[row][col].setText("");
				board[row][col].setEnabled(true);
				board[row][col].setBackground(null);
			}
		}

	}
	@Override
	public void displayWinner() {
		if(this.isWinner("X")){
			updateOut("X is the winner");
		}
		else if(this.isWinner("O")){
			updateOut("O is the winner");
		}
		else{
			updateOut("DRAW!!!");
		}
	}
	@Override
	public void takeATurn() {


	}
	@Override
	public void updateCurrPlayer() {
		if(currPlayer.equalsIgnoreCase("X")){
			currPlayer = "O";
		}
		else{
			currPlayer = "X";
		}

	}


	public boolean isWinner(String player) {
		//check rows
		for(int row=0; row<board.length; row++){
			int rowCount=0;//row match counter, resets on next row
			for(int col=0; col<board[row].length; col++){
				if(board[row][col].getText().contains(player)){
					rowCount++;//increment counter
					if(rowCount == 3){
						return true;//found 3 in same row
					}
				}
			}
		}
		//check columns
		for(int col=0; col<3; col++){
			int colCount=0;
			for(int row=0; row<3; row++){
				if(board[row][col].getText().contains(currPlayer)){
					colCount++;
					if(colCount == 3){
						return true;//found 3 in same column
					}
				}
			}
		}
		//check main diagonal [0][0],[1][1],[2][2]
		int diagCount=0;
		for(int i=0; i<board.length; i++){
			if(board[i][i].getText().contains(currPlayer)){
				diagCount++;
				if(diagCount ==3){
					return true;//found 3 same across main diagonal
				}
			}	
		}
		//check secondary diagonal
		int diag2Count=0;
		int row=board.length-1;
		int col = 0;
		while(row >=0 && col< board.length){
			if(board[row][col].getText().contains(currPlayer)){
				diag2Count++;
				if(diag2Count ==3){
					return true;//found 3 same across secondary diagonal
				}
			}
			row--;
			col++;
		}

		return false;
	}
	@Override
	public boolean isFull() {
		for(int row=0; row<board.length; row++){
			for(int col=0; col<board[row].length; col++){
				String text = board[row][col].getText();
				if( !(text.contains("X")) && !(text.contains("O")) ){
					return false;
				}
			}
		}
		return true;
	}

}


