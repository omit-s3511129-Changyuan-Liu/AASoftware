package player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import world.World;
import world.World.Coordinate;
import world.World.ShipLocation;

/**
 * Monte Carlo guess player (task C).
 * Please implement this class.
 *
 * @author Youhan, Jeffrey
 */
public class MonteCarloGuessPlayer  implements Player{
	int numRow = 0;
	int numCol = 0;
	World world;


	ArrayList<Coordinate> shots;
	ArrayList<Coordinate> enemyShots;
	ArrayList<ShipLocation> shipLocations;
	ArrayList<Guess> guesses;
	ArrayList<Answer> AW= new ArrayList<>();
	boolean targeting;
	int[] neighbourRow = {0, 1, 0, -1};
	int[] neighbourCol = {-1, 0, 1, 0};
	int[][] score;
	ArrayList<Guess> hitGuesses;
	ArrayList<Integer> shipLength;


	@Override
	/*initial all states to player,transfer world class values to player*/
	public void initialisePlayer(World world) {
		this.numRow = world.numRow;
		this.numCol = world.numColumn;
		this.world = world;
		this.enemyShots = new ArrayList<Coordinate>();
		this.shots = this.world.shots;
		this.shipLocations = this.world.shipLocations;
		this.guesses = new ArrayList<Guess>();
		targeting = false;
		this.hitGuesses = new ArrayList<Guess>();
		
		this.shipLength = new ArrayList<Integer>();
		for(ShipLocation ship: world.shipLocations) {
			shipLength.add(ship.ship.len());
		}
		score = new int[numRow][numCol];
		
		
		
		
		// To be implemented.
	} // end of initialisePlayer()

	@Override
	public Answer getAnswer(Guess guess) {
		// To be implemented.

		// dummy return
		return null;
	} // end of getAnswer()


	@Override
	/*answer the guess from opponent*/
	public Guess makeGuess() {
		// To be implemented.
		
		for (int i = 0; i < numRow; i++) {
			for (int j = 0; j < numCol; j++) {
				score[i][j] = 0;
			}
		}
		
		Guess guess = new Guess();
		
		// get the possibilty of spots which contain ship
		for (int i = 0; i< numRow; i++) {
			for (int j = 0; j < numCol; j++){
				int count = 0;
				for(Integer length: this.shipLength) {
					for(int num = 0; num < 4; num ++) {
							if (i + (length - 1) * neighbourRow[num] >= 0 && i + (length - 1) *neighbourRow[num] < numRow) {
								if (j + (length - 1) *neighbourCol[num] >= 0 && j + (length - 1) *neighbourCol[num] < numCol) {
									count ++;
								}
							}
							
							
					}
					
						if (length - 2 > 0) {
							for(int times = 0; times < length -2 ; times ++ ) {
									if (i + (times + 1) * neighbourRow[3] >= 0 && i + (length - 2 - times) *neighbourRow[1] < numRow) {
										count ++;
									}
									if (j + (times + 1) * neighbourCol[0] >= 0 && j + (length - 2 - times) * neighbourCol[2] < numCol) {
										count ++;
									}
									
							}
						}
				}
				score[i][j] = count;
			}
		}
		
		int max = score[0][0];
		int maxRow = 0;
		int maxCol = 0;
		for (int i = 0; i < numRow; i++) {
			outer:
			for (int j = 0; j < numCol; j++) {
				if (score[i][j] > max) {
					for(Guess g: guesses) {
						if (g.row == i && g.column == j) {
							continue outer;
						}
					}
					max = score[i][j];
					maxRow = i;
					maxCol = j;
				}
				
			}
			
		}
		
		
		
		guess.row = maxRow;
		guess.column = maxCol;
		
		return guess;
	} // end of makeGuess()


	@Override
	public void update(Guess guess, Answer answer) {
		targeting = false;
		guesses.add(guess);
		
		
		for (int i = 0; i < numRow; i++) {
			for (int j = 0; j < numCol; j++) {
					if (guess.row == i) {
						score[i][j] --;
					}
					if (guess.column == j) {
						score[i][j] --;
					}
				
			}
		
		}
		







		// To be implemented.
	} // end of update()


	@Override
	/*check if there any ship left*/
	public boolean noRemainingShips() {

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

} // end of class MonteCarloGuessPlayer
