import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Simulation {
	
	//random number generator
	static Random rd = new Random();
	
	//Current time of the simulation
	static int globTime = 0;
	
	/*Array that contains group sizes. Used for randomly picking a group size. 
	 *Repeating values will have higher probability of getting picked*/
	static int[] groupSizeDistribution = new int[] {1,2,3,4,5,6,7,8};
	
	static int boatSize = 8;

	
	/*returns random element of the array*/
	public static int getGroupSize(int [] arrayDist) {
		int groupSize = arrayDist[rd.nextInt(arrayDist.length)];
		return groupSize;
	}
	
	//returns an array with groups, number of groups is random, input upper limit
	public static Group[] generateGroups(int N) {
		int numberOfGroups = rd.nextInt(N);
		Group[] arrivedGroups = new Group[numberOfGroups];
		
		for (int i = 0; i < numberOfGroups; i++) {
			int groupSize = getGroupSize(groupSizeDistribution);
			Group arrivedGroup = new Group(groupSize, globTime);
			arrivedGroups[i] = arrivedGroup;
		}
		return arrivedGroups;
	}
	
	//Main simulation routine
	static void startSimulation(){
		
		ArrayList<Group> que = new ArrayList<>();
		
		while(globTime < 100000) {
			
			//generate and add arrived groups to the list que
			Group[] arrivedGroups = generateGroups(3);
			for (int i = 0; i < arrivedGroups.length; i++) {
				que.add(arrivedGroups[i]);
			}
			
			//Current seats left
			int leftPlaces = boatSize;
			
				boolean isLoading = true;
				
				while (isLoading) {
					//if there are groups in a que start loading
					if (!que.isEmpty()) {
						
						Group currentGroup = que.get(0);
						int currentGroupSize = currentGroup.getSize();
						
						//if enough space remove group from the que and decrement left spaces by the size of the group
						if (currentGroupSize <= leftPlaces) {
							leftPlaces -= currentGroupSize;
							que.remove(0);
						} else {
							isLoading = false;
						}
						
					} else {
						isLoading = false;
					}
					System.out.println(que.size() + " " + globTime);
				} 
			globTime++;
			
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		startSimulation();
	}

	
	
	
}

