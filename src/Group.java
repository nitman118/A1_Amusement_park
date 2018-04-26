class Group {
	private int arrivalTime;
	private int groupSize;
	Group(int groupSize, int arrivalTime) {
		// TODO Auto-generated method stub
		this.groupSize = groupSize;
		this.arrivalTime = arrivalTime;
	}
	
	public int getSize() {
		return this.groupSize;
	}
	
	public int getTime() {
		return this.arrivalTime;
	}
}