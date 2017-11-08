//ConnectFour consists of two Players and a Board.
//Four consecutive spaces of the same color in a row, column, or diagonal constitute a win.
//Dongpeng Xia

package connect4;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//ConnectFour
public class ConnectFour extends JFrame implements MouseListener, ActionListener
{
	Player[] players; //two players, 'r' is player[0] (red), 'b' is player[1] (black)
   	Board theBoard;   //current board
   	Board savedBoard; //saved board
   	JButton resetGame; //button to reset game
	JButton saveGame;  //button to save game
	JButton loadPreviousGame; //button to load saved game
	JButton help; //button to show instructions
   
	//main
   	public static void main ( String[] args )
   	{
   		//call constructor
   		new ConnectFour();
   		
   	}//end main()
   
   	//constructor
   	public ConnectFour()
   	{
   		//GUI settings
   		setDefaultCloseOperation(EXIT_ON_CLOSE);
   		setTitle("CONNECT FOUR");
   		setSize(Space.sideLength * (Board.boardSize + 2), Space.sideLength * (Board.boardSize + 2));
   		getContentPane().setBackground(Color.WHITE);
   		
   		//Introduce Connect4
   		showInstructions();
   		
   		//Ask for player names
   		String name1 = JOptionPane.showInputDialog("Please enter player 1's name");
   		String name2 = JOptionPane.showInputDialog("Please enter player 2's name");
   		
   		//Layout settings
   		setLayout(new FlowLayout());

   		//Buttons to reset, save, and load game
   	    JPanel panelOfButtons = new JPanel();
   	    panelOfButtons.setBackground(Color.WHITE);
   	    panelOfButtons.setLayout(new FlowLayout());
   	    resetGame = new JButton("RESET");
   	    resetGame.addActionListener(this);
   	    saveGame = new JButton("SAVE GAME");
   	    saveGame.addActionListener(this);
   	    loadPreviousGame = new JButton("LOAD GAME");
   	    loadPreviousGame.addActionListener(this);
   	    loadPreviousGame.setEnabled(false); //can not load game until a game is saved
   	    help = new JButton("HELP");
   	    help.addActionListener(this);
   	    panelOfButtons.add(resetGame);
   	    panelOfButtons.add(saveGame);
   	    panelOfButtons.add(loadPreviousGame);
   	    panelOfButtons.add(help);
   	    add(panelOfButtons);
   		
   	    //initialize two players
   		players = new Player[2];
   		players[0] = new Player( 'r' , name1);
   		players[1] = new Player( 'b' , name2);
   		
   		//initialize Board and start game
   		theBoard = new Board();
   		addMouseListener(this);
   		setVisible(true);
   		
   	}//end ConnectFour()
   	
   	//Handlers for reset, save, and load buttons
   	@Override
    public void actionPerformed( ActionEvent e )
    {
   		if ( e.getSource() == resetGame ) 
   		{
   			//reset the game
    	   		theBoard = new Board();
    	   	}
   		else if ( e.getSource() == saveGame ) 
   		{
   			//save current game
   			savedBoard = theBoard;
   			theBoard = new Board();
   			loadPreviousGame.setEnabled(true);
   		}
   		else if ( e.getSource() == loadPreviousGame ) 
   		{
   			//load saved game if available
   			if(savedBoard == null)
   			{
   				JOptionPane.showMessageDialog(null, "No saved game available.");
   			}
   			else
   			{
   				theBoard = savedBoard;
   				loadPreviousGame.setEnabled(false);
   			}
   		}
   		else if ( e.getSource() == help )
   		{
   			//show instructions for game
   	   		showInstructions();
   		}
   		repaint();
   		
    }//end actionPerformed(ActionEvent)
   	
   	//showInstructions opens up a dialog box with a description of Connect 4
   	private void showInstructions()
   	{
   		JOptionPane.showMessageDialog(null, "                             Welcome!\n" +
											"Connect Four is a game where the objective is to occupy\n" + 
											Scorable.minimumConsecutive + 
											" consecutive spaces in any row, column, or diagonal.\n" +
											"The board has " + Board.boardSize + " rows and " + Board.boardSize + " columns. " +
											"Players take turns\nputting a piece of their own color in a " +
											"column. To\nplace a piece in a column, simply click anywhere in the\ncolumn. The " + 
											"piece must occupy the lowest empty space\nin that column. " + 
											"Good luck, and may the force be with you...\n");
   	}//end showInstructions()
   
   	//MouseClick Handler
   	@Override public void mouseClicked ( MouseEvent m )
   	{	
   		//find lowest unoccupied space in clicked column
   		Space sp = theBoard.findSpace( m );
        
   		//check if there is an occupied space in clicked column
   		if ( sp != null )
   		{
   			if ( sp.getColor() == ' ' )
   			{
   				//add a piece to the board
   				sp.setColor( players[ theBoard.currentTurn() ].getColor() );
   				
   				//check for winner
   				char winner = theBoard.winner();
   				
   				//if there is a winner
   				if(winner != ' ')
   				{
   					String winnerName = "";
   					
   					//red wins
	   				if(winner == 'r')
	   				{
	   					winnerName = players[0].getName();
	   					if(winnerName == null || winnerName.equals(""))
	   					{
	   						winnerName = "Player 1";
	   					}
	   				}
	   				//black wins
	   				else if(winner == 'b')
	   				{
	   					winnerName = players[1].getName();
	   					if(winnerName == null || winnerName.equals(""))
	   					{
	   						winnerName = "Player 2";
	   					}
	   				}
	   				
	   				//show winner message
	   				JOptionPane.showMessageDialog(null, "" + winnerName + " wins!"); 
	   				
	   				//reset the game
	   				theBoard = new Board();
   				}
   				else if (theBoard.isFull())
   				{
   					//no winner and full board => stalemate
   					JOptionPane.showMessageDialog(null, "Stalemate"); 
   					//reset the game
	   				theBoard = new Board();
   				}
   			}
   		}
   		repaint();
   		
   }//end mouseClicked(MouseEvent)
   
   //Overridden paint draws the board
   @Override
   public void paint( Graphics g )
   {
      super.paint(g);
      theBoard.drawMe(g);
      
   }//end paint(Graphics)
   
   //Other Mouse Handlers
   @Override
   public void mousePressed(MouseEvent e) {}
   @Override
   public void mouseReleased(MouseEvent e) {}
   @Override
   public void mouseEntered(MouseEvent e) {}
   @Override
   public void mouseExited(MouseEvent e) {}
   
}//end ConnectFour