public class PCB {
    private int id; //the id of the process
    private state state;//the state of the process
    private int burst; //burst time of process
    private int remain; //the remaining time of the process
    private int memory; //the memory need it for the process
    private int turnaround; //turnaround time of the process
    private int wait; //waiting time of the process

    //Constructor
    public PCB(int id, int burst, int memory){
        this.id = id;
        this.burst = remain = burst;
        this.memory = memory;
        turnaround = 0;
        state = state.waiting;
    }

    //get id
    public int getId() {
        return id;
    }

    //get state
    public state getState() {
        return state;
    }

    //get burst time
    public int getBurst() {
        return burst;
    }

    //get remaining time
    public int getRemain() {
        return remain;
    }

    //get memory
    public int getMemory() {
        return memory;
    }

    //get turnaround
    public int getTurnaround() {
        return turnaround;
    }

    //get waiting time
    public int getWait() {
        return wait;
    }

    //set state
    public void setState(state state) {
		this.state = state;
	}

    //set turnaround
    public void setTurnaround(int turnaround) {
		this.turnaround = turnaround;
	}

    //set waiting time
	public void setWait(int wait) {
		this.wait = wait;
	}

    //set remaining time by deleteing the time that has been done
    public void theRemain(int burstTook) {
        remain = remain - burstTook;
    }
}
