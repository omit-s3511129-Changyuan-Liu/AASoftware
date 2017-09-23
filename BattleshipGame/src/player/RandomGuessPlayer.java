package player;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import world.World;
import world.World.Coordinate;
import world.World.ShipLocation;

/**
 * Random guess player (task A).
 * Please implement this class.
 *
 * @author Youhan, Jeffrey
 */
public class RandomGuessPlayer implements Player{
	
	int numRow = 0;
	int numCol = 0;
	World world;

	/*ArrayList<Coordinate> shots;*/
	
	ArrayList<Coordinate> enemyShots;
	ArrayList<ShipLocation> shipLocations;
	ArrayList<Guess> guesses;
	/**/
    @Override
    /*initial all states to player,transfer world class values to player*/
    public void initialisePlayer(World world) {
        // To be implemented.
    	this.numRow = world.numRow;
    	this.numCol = world.numColumn;
    	this.world = world;
    	this.enemyShots = new ArrayList<Coordinate>();
    	/*this.shots = this.world.shots;*/
    	this.shipLocations = this.world.shipLocations;
    	this.guesses = new ArrayList<Guess>();
    	
    	
    } // end of initialisePlayer()

    @Override
   /*answer the guess from opponent*/
    public Answer getAnswer(Guess guess) {
        // To be implemented.

    	int row = guess.row;
    	int col = guess.column;
    	Coordinate enemyShot = world.new Coordinate();
    	enemyShot.column = col;
    	enemyShot.row = row;
    	Answer a = new Answer();
    	
    	//store enemy's shot
    	enemyShots.add(enemyShot);
    	
    	//loop all ship locations and its coordinate, if enemy shot same as ship coordinate there is a hit 	
    	for (ShipLocation shipLocation: shipLocations) {
    		for (Coordinate coordinate: shipLocation.coordinates) {
    			if (coordinate.row == row && coordinate.column == col) {
    				a.isHit = true;
    				
    				//if enemy shots contain a whole ship coordinates, there is a ship sunk
    	    		if (enemyShots.containsAll(shipLocation.coordinates)) {
    	    			a.shipSunk = shipLocation.ship;
    	    		}
    				
    				break;
    			}
    		}
    	}
    	
        // dummy return
        return a;
    } // end of getAnswer()

    
    @Override
    /*player to make a guess*/
    public Guess makeGuess() {
        // To be implemented.
    	
    	Guess guess = new Guess();
    	
        while(true) {
        	Random random = new Random();
        	int randomRow = random.nextInt(numRow);
        	int randomCol = random.nextInt(numCol);
        	
        	boolean same = false;
        	//loop all guesses
        	for (Guess shot: guesses) {
        		// if the guess we have guessed before, to make random guess again
        		if (shot.column == randomCol && shot.row == randomRow) {
        			same = true;
        		}
        	}
        	//if the guess never guess before, return the guess
        	if (!same) {
        		guess.column = randomCol;
            	guess.row = randomRow;
            	return guess;
        	}
        }

    } // end of makeGuess()


    @Override
    public void update(Guess guess, Answer answer) {
        //add all guesses
    	guesses.add(guess);

    } // end of update()
   

    @Override
    /*check if there any ship left*/
    public boolean noRemainingShips() {
        // To be implemented.
    	int count = 0;
    	//loop to get if the ship locations got shoot
    	for(ShipLocation shipLocation: shipLocations) {
    		if (enemyShots.containsAll(shipLocation.coordinates)) {
    			count ++;
    		}
    	}
    	//check if all ship locations got shot, if yes return true
    	if (count == shipLocations.size()) {
    		return true;
    	}

        // dummy return
        return false;
    } // end of noRemainingShips()

} // end of class RandomGuessPlayer
