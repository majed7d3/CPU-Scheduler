public class PCB {
    private int id;
    private state state;
    private int burst; //burst time
    private int remain; //the renainging burst time
    private int memory; //in MB
    private int turnaround; //turnaround time
    private int wait; //waiting time

    public PCB(int id, int burst, int memory){
        this.id = id;
        this.burst = remain = burst;
        this.memory = memory;
        turnaround = burst;
        state = state.waiting;
    }

    public int getId() {
        return id;
    }

    public state getState() {
        return state;
    }

    public int getBurst() {
        return burst;
    }

    public int getRemain() {
        return remain;
    }

    public int getMemory() {
        return memory;
    }

    public int getTurnaround() {
        return turnaround;
    }

    public int getWait() {
        return wait;
    }
	
	public void setState(state state) {
		this.state = state;
	}
    
public void setTurnaround(int turnaround) {
		this.turnaround = turnaround;
	}

	public void setWait(int wait) {
		this.wait = wait;
	}
    
    public void theRemain(int burstTook) {
        remain = remain - burstTook;
    }
}
