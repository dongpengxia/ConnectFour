//Board consists of a boardSize x boardSize 2D array made of Spaces
//Board has a list of Scorables called possibleWins which are the rows, columns, 
//and diagonals of the board that could result in a win.
//Dongpeng Xia

package connect4;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import connect4.Scorable;

//Board
public class Board
{
	static int boardSize = 7; //# rows and columns on board
	Space[][] theSpaces; //boardSize x boardSize array.
	LinkedList<Scorable> possibleWins; //list of rows, columns, and diagonals that could result in a win
   
	//constructor
	public Board()
	{
		possibleWins = new LinkedList<Scorable>();
      
		//initialize empty board
		theSpaces = new Space[boardSize][boardSize];
		for ( int row = 0; row < boardSize; row++ )
		{
			for ( int col = 0; col < boardSize; col++ )
			{
				theSpaces[row][col] = new Space(row, col);
			}
		}
      
		// add spaces to the Scorables in the possibleWins list
		
		// go through the rows
		for ( int row = 0; row < boardSize; row++ )
		{
			Scorable s = new Scorable(); // one row
			for ( int col = 0; col < boardSize; col++ )
			{
				s.addSpace(theSpaces[row][col]);
			}
			possibleWins.add(s);
		}
		
		// go through the columns
		for ( int col = 0; col < boardSize; col++ )
		{
			Scorable s = new Scorable(); // one column
			for ( int row = 0; row < boardSize; row++ )
			{
				s.addSpace(theSpaces[row][col]);
			}
			possibleWins.add(s);
		}
		
		//diagonals going from upper-left to bottom-right
		for( int i = 0; i <= ( boardSize - Scorable.minimumConsecutive ); i++ )
		{
			Scorable s = new Scorable(); // one diagonal
			int row = 0;
			int col = i;
			while( row < boardSize && col < boardSize )
			{
				s.addSpace(theSpaces[row][col]);
				row++;
				col++;
			}
			possibleWins.add(s);
		}
		for( int i = 1; i <= ( boardSize - Scorable.minimumConsecutive ); i++ )
		{
			Scorable s = new Scorable(); // one diagonal
			int row = i;
			int col = 0;
			while( row < boardSize && col < boardSize )
			{
				s.addSpace(theSpaces[row][col]);
				row++;
				col++;
			}
			possibleWins.add(s);
		}
		
		//diagonals going from upper-right to bottom-left
		for( int i = boardSize - 1; i >= ( boardSize - Scorable.minimumConsecutive ); i-- )
		{
			Scorable s = new Scorable(); // one diagonal
			int row = 0;
			int col = i;
			while( row < boardSize && col >= 0 )
			{
				s.addSpace(theSpaces[row][col]);
				row++;
				col--;
			}
			possibleWins.add(s);
		}
		for( int i = ( boardSize - Scorable.minimumConsecutive ); i >= 1; i-- )
		{
			Scorable s = new Scorable(); // one diagonal
			int row = i;
			int col = boardSize - 1;
			while( row < boardSize && col >= 0 )
			{
				s.addSpace(theSpaces[row][col]);
				row++;
				col--;
			}
			possibleWins.add(s);
		}   
	}//end Board()	
   
	// take the mouse event and return the bottom-most empty space in the same column as the click
	//return null if no spaces available in that column
	public Space findSpace( MouseEvent m )
	{
		Space sp = null;
      
		int col = ( m.getX() / Space.getSideLength() ) - 1; //find column that was clicked
      
		if( 0 <= col && col < boardSize)
		{
			boolean foundSpace = false;
			
			//get lowest empty space in column
			for( int row = boardSize - 1; row >= 0 && !foundSpace; row-- )
			{
				if( theSpaces[row][col].getColor() == ' ' )
				{
					foundSpace = true;
					sp = theSpaces[row][col];
				}
			}
		}
		return sp;
	}//end findSpace(MouseEvent)
   
	// return index of player whose turn it is.  
	// how: count # filled spaces, then take mod 2
	public int currentTurn()
	{
		int count = 0;
		for ( int row = 0; row < boardSize; row++ )
		{
			for ( int col = 0; col < boardSize; col++ )
			{
				if( theSpaces[row][col].getColor() != ' ' ) 
				{
					count++;
				}
			}
		}
		return ( count % 2 );
	}//end currentTurn()
   
	//drawMe method calls drawMe for each space
	public void drawMe( Graphics g )
	{
		for ( int row = 0; row < boardSize; row++ )
		{
			for ( int col = 0; col < boardSize; col++ )
			{
				theSpaces[row][col].drawMe(g);
			}
		}
	}//end drawMe(Graphics)
	
	//isFull returns true if the board is full and false if there are empty spaces left
	public boolean isFull()
	{
		boolean full = true;
		for( int row = 0; row < boardSize; row++ )
		{
			for( int col = 0; col < boardSize; col++ )
			{
				if( theSpaces[row][col].getColor() == ' ' )
				{
					full = false;
				}
			}
		}
		return full;
	}//end isFull()
   
	// return the color of the winner if there is one
	public char winner()
	{
		char winner = ' ';
      
		Iterator<Scorable> it = possibleWins.iterator();
		
		//check each Scorable for a winner
		while ( winner == ' ' && it.hasNext() )
		{
			Scorable s = it.next();
			winner = s.getWinner();
		}
		return winner;
		
	}//end winner()
   
	//setters and getters
	public static int getBoardSize() { return boardSize; }  
	public Space[][] getTheSpaces() { return theSpaces; }
	public LinkedList<Scorable> getPossibleWins() { return possibleWins; }
	private void setTheSpaces( Space[][] sp ) { theSpaces = sp; }
	private void setPossibleWins( LinkedList<Scorable> pw ) { possibleWins = pw; }
	
}//end Board