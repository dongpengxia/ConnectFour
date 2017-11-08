//A Scorable is a row, column, or diagonal (list of spaces) that can become a win for a player.
//If minimumConsecutive consecutive spaces in a Scorable are the same color, it is a win.
//Dongpeng Xia

package connect4;

import java.util.*;
import connect4.Space;

//Scorable
public class Scorable
{
	static int minimumConsecutive = 4; //the minimum number of consecutive spaces in a row, column, or diagonal of the same color for a win
	LinkedList<Space> spaceList; //if you occupy minimumConsecutive consecutive spaces in this list, you win

	// constructor, start empty list
	public Scorable()
	{
		spaceList = new LinkedList<Space>();
		
	}//end Scorable()
	
	//addSpace adds a space to end of list
	public void addSpace(Space sp)
	{
		spaceList.add(sp);
		
	}//end addSpace(Space)
	
	// return character color of winner if any, just a ' ' if no one won this Scorable
	public char getWinner()
	{
		char currentWinner = ' '; // assume no one wins unless you find one
		int numConsecutive = 0; //number of spaces of consecutive color

		if( !spaceList.isEmpty() )
		{
			currentWinner = spaceList.get(0).getColor();
			Iterator<Space> iter = spaceList.iterator();
			
			//continue reading list of spaces until end of list or a winner is found
			while ( iter.hasNext() && ( numConsecutive < minimumConsecutive ) )
			{
				Space sp = iter.next();
				if(sp.getColor() == ' ')
				{
					//space is empty
					currentWinner = ' ';
					numConsecutive = 0;
				}
				else
				{
					//space is not empty
					if(sp.getColor() == currentWinner)
					{
						//space is same color as previous
						numConsecutive++;
					}
					else
					{
						//space is occupied but a different color from previous space
						currentWinner = sp.getColor();
						numConsecutive = 1;
					}
				}
			}
			if(numConsecutive < minimumConsecutive)
			{
				//there is no winner because there isn't numConsecutive spaces of the same color in the Scorable
				currentWinner = ' ';
			}
		}
		return currentWinner;
		
	}//end getWinner()
	
	//setters and getters
	public int getMinimumConsecutive() { return minimumConsecutive; }
	public LinkedList<Space> getSpaceList() { return spaceList; }
	private void setSpaceList(LinkedList<Space> spList) { spaceList = spList; }
	
}//end Scorable