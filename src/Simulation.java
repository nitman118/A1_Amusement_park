import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Simulation {
	
	//random number generator
	static Random rd = new Random(1);
	
	//Current time of the simulation
	static int globTime = 0;
	
	//Simulation Parameters
	static int boatSize = 8;
	static double lambda = 0.1; //arrival rate of the groups
	
	/*Arrays used to simulate probabilities. 
	 *Repeating values will have higher probability of getting picked*/
	static int[] numGroupsProbability = new int[] {0,1,1,1,2}; // p0=0.2, p1=0.6, p2=0.2
	static int[] groupSizeProbability = new int[] {1,2,3,4,5}; // q1=q2=q3=q4=q5=0.2
	
	/*returns random element of the array*/
	public static int randomElem(int [] arrayDist) {
		int elem = arrayDist[rd.nextInt(arrayDist.length)];
		return elem;
	}
//-----Poisson process arrivals-----------------------------------------------------
	
	/*Groups arrive according to a poisson process with rate lambda, groups size probabilities given
	 * Simulation updates every minute, boat arrive every 10 minutes
	 */
	
	/*Create list with boats with predifined arrival time
	  Interarrival time is exponentialy distributed with mean 1/lambda */
	public static ArrayList<Group> generateArrivals(int maxArrivals) {
		ArrayList<Group> arrivals = new ArrayList<>();
		int time = 0;
		while (arrivals.size() < maxArrivals) {
			time = time + (int)(Math.log(1-rd.nextDouble())/(-lambda));
			arrivals.add(new Group(randomElem(groupSizeProbability), time));
		}
		
		return arrivals;
	}
	static void startSimulationPoisson(){
		
		ArrayList<Group> arrivals = generateArrivals(1000);
		ArrayList<Group> que = new ArrayList<>();
	
		//Add a group to a que when its arrival time >= current time
		while(!arrivals.isEmpty()) {
			if (arrivals.get(0).getTime() < globTime) {
				que.add(arrivals.get(0));
				arrivals.remove(0);
			}
			//Boat arrives every 10 minutes to pick up groups
			if (globTime % 10 == 0) {
				int remSeats = boatSize;
				boolean isLoading = true;
				while (isLoading && !que.isEmpty()) {
					if (que.get(0).getSize() <= remSeats) {
						remSeats -= que.get(0).getSize();
						que.remove(0);
					} else {
						isLoading = false;
					}
				}
			}
			globTime++;
			//Testing
			System.out.println(globTime + " " + que.size() );
			
		}
	}
//-----Know probability groups size and number of groups---------------------------
	
	/* Discrete time. For each unit of time there is a boat to pick up groups
	 * Probability that k groups will arrive at each time unit is given
	 * Probability of group having q people is given
	 */
	public static ArrayList<Group> generateGroups(){
		ArrayList<Group> groups = new ArrayList<>();
		int numberOfGroups = randomElem(numGroupsProbability);
		for (int i = 0; i<numberOfGroups;i++) {
			groups.add(new Group(randomElem(groupSizeProbability),globTime));
		}
		return groups;
		
	}
	
	static void startSimulationUniform(){
		
		ArrayList<Group> que = new ArrayList<>();
		
		while (globTime < 100000) {
			
			que.addAll(generateGroups());

			int remSeats = boatSize;
			
			boolean isLoading = true;
			while (isLoading && !que.isEmpty()) {
				if (que.get(0).getSize() <= remSeats) {
					remSeats -= que.get(0).getSize();
					que.remove(0);
				} else {
					isLoading = false;
				}
			}
			globTime++;
			//testing	
			System.out.println(globTime + " " + que.size() );	
		}
	}
	

	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		startSimulationUniform();
	}

	
	
	
}

