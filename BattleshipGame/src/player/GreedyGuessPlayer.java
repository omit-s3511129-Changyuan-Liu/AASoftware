package player;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import world.World;
import world.World.Coordinate;
import world.World.ShipLocation;

/**
 * Greedy guess player (task B).
 * Please implement this class.
 *
 * @author Youhan, Jeffrey
 */
public class GreedyGuessPlayer  implements Player{
	int numRow = 0;
	int numCol = 0;
	World world;
	
	
	
	ArrayList<Coordinate> enemyShots;
	ArrayList<ShipLocation> shipLocations;
	ArrayList<Guess> guesses;
	ArrayList<Coordinate> parityList;
	
	boolean targeting;
	int[] neighbourRow = {1, 0, 0, -1};
	int[] neighbourCol = {0, -1, 1, 0};
	ArrayList<Guess> hitGuesses;
	/*initial all states to player,transfer world class values to player*/
    @Override
    public void initialisePlayer(World world) {
        // To be implemented.
    	this.numRow = world.numRow;
    	this.numCol = world.numColumn;
    	this.world = world;
    	this.enemyShots = new ArrayList<Coordinate>();
    	/*this.shots = this.world.shots;*/
    	this.shipLocations = this.world.shipLocations;
    	this.guesses = new ArrayList<Guess>();
    	this.parityList = new ArrayList<Coordinate>();
    	targeting = false;
    	this.hitGuesses = new ArrayList<Guess>();
    	fillParityList();
    	
    
    	
    	
    } // end of initialisePlayer()
    /*get the coordinates that one skip one*/
    public void fillParityList(){
    	
    	for(int i=0;i<numRow;i++){
    		if(i%2==0){
    			for(int j=0;j<numCol;j++){
    				Coordinate cdn = world.new Coordinate();
    				if(j%2==0){
    					cdn.row=i;
    					cdn.column=j;
    					parityList.add(cdn);
    				}
    			}
    			
    		}
    		else{
    			for(int j=0;j<numCol;j++){
    				Coordinate cdn = world.new Coordinate();
    				if(j%2!=0){
    					cdn.row=i;
    					cdn.column=j;
    					parityList.add(cdn);
    					
    				}
    				
    				
    			}
    		}
    		
    		
    	}
    	
    	
    		
    }
    /*answer the guess from opponent*/
    @Override
    public Answer getAnswer(Guess guess) {

    	int row = guess.row;
    	int col = guess.column;
    	Coordinate enemyShot = world.new Coordinate();
    	enemyShot.column = col;
    	enemyShot.row = row;
    	
    	//store enemy's shot
    	enemyShots.add(enemyShot);
    	Answer a = new Answer();

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
    	
    	Guess guess = null;
    	// if there is a hit, go targeting mode
    	if (targeting) {
    		//loop for all hits
    		for(Guess hitGuess: hitGuesses) {
    		outerLoop:
    			//shot the hits around
    			for (int i = 0; i < 4; i++) {
    				if (hitGuess.row + neighbourRow[i] >= 0 && hitGuess.row + neighbourRow[i] < numRow) {
    					if (hitGuess.column + neighbourCol[i] >= 0 && hitGuess.column + neighbourCol[i] < numCol) {



    						guess = new Guess();
    						guess.row = hitGuess.row + neighbourRow[i];
    						guess.column = hitGuess.column + neighbourCol[i];
    						//if the hit have hit before,search another around
    						for(Guess guessItem: guesses) {
    							if (guessItem.row == guess.row && guessItem.column == guess.column) {
    								continue outerLoop;
    							}
    						}
    						Coordinate coor = null;
    						for(Coordinate coordinate: parityList) {
    							if (guess.row == coordinate.row && guess.column == coordinate.column) {
    								coor = coordinate;
    							}
    						}
    						if (coor != null) {
    							parityList.remove(coor);
    						}
    						
    						return guess;
    					}
    				}
    			}
    		}
    	}
    	
    	//clear hits if all possible targeting cells exhausted
    	if (guess == null) {
    		targeting = false;
    		hitGuesses.clear();
    	}
    	
    	
    	
    	// hunting mode
    	guess = new Guess();	
    		while(true){
    			Random random = new Random();
    			int index = random.nextInt(parityList.size());
    			int randomRow = parityList.get(index).row;
    			int randomColoumn = parityList.get(index).column;
    			boolean same = false;
    		//loop all guesses
			for (Guess shot: guesses) {
				// if the guess we have guessed before, to make random guess again
				if (shot.column == randomColoumn && shot.row == randomRow) {
						same = true;
					}
				}
			//if the guess never guess before, return the guess
				if(!same){
					guess.column=randomColoumn;
					guess.row=randomRow;
		
					return guess;
				}
			}
    	
    	
    	
        // dummy return
        
    } // end of makeGuess()


    @Override
    public void update(Guess guess, Answer answer) {
    	//add all guesses
    	guesses.add(guess);
    	
    	//if there is a hit, go targeting mode and store the hit
    	if (answer.isHit) {
    		targeting = true;
    		hitGuesses.add(guess);
    	}
    	
    	/*AW.add(answer);*/
    	
    	
        // To be implemented.
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
    }
    
     // end of noRemainingShips()
    

} // end of class GreedyGuessPlayer
