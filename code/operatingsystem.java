public class operatingsystem  {
    private boolean bitMod; //false is kernel mode true is user mode
    private int memory; //the memory that is allowed in MB (1024MB)
    private boolean flag[]; //for sync
    private int turn; //for the turn on the memory, 0 for allocate and 1 for deallocate
    private final Object lock = new Object();

    //Constructor
    public operatingsystem(){
        memory = 1024;
        bitMod = true;
    }

    //to switch to the kernel mode
    public void switchToKernelMode(){
        bitMod = false;
    }

    //to switch to the user mode
    public void switchToUserMode(){
        bitMod = true;
    }

    //to create a new process, if it falled it will return a null process
    public PCB create(int id, int burst, int memory){
        PCB process = null;
        if(!bitMod){
            process = new PCB(id, burst, memory);
        }
        return process;
    }

    //to Terminate a process, if it falled it will not Terminate the process 
    public void Terminate(PCB process){
        if(!bitMod){
            process.setState(state.finish);
        }
    }

    //to allocate an mount of memory, return true if successful ,false otherwise
    public boolean allocate(int memory) {
        synchronized (lock) {
            while (this.memory < memory) { // Wait only if insufficient memory
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false; // Exit if interrupted
                }
            }
            // Memory is sufficient, proceed with allocation
            this.memory -= memory;
            return true;
        }
    }
    //to deallocate an mount of memory, return true if successful ,false otherwise
    public boolean deallocate(int memory) {
        synchronized (lock) {
            this.memory += memory;
            if (this.memory > 1024) { // Cap memory at the maximum
                this.memory = 1024;
            }
            lock.notifyAll(); // Notify all waiting threads
            return true;
        }
    }
    //return the id of a process, -1 otherwise
    public int getId(PCB process){
        if(!bitMod){
            return process.getId();
        }
        return -1;
    }

    //return the state of a process, null otherwise
    public state getState(PCB process){
        if(!bitMod){
            return process.getState();
        }
        return null;
    }

    //return the burst time of a process, -1 otherwise
    public int getBurst(PCB process){
        if(!bitMod){
            return process.getBurst();
        }
        return -1;
    }

    //return the remaining time of a process, -1 otherwise
    public int getRemain(PCB process){
        if(!bitMod){
            return process.getRemain();
        }
        return -1;
    }

    //return the memory of a process, -1 otherwise
    public int getMemory(PCB process){
        if(!bitMod){
            return process.getMemory();
        }
        return -1;
    }

    //return the turnaround time of a process, -1 otherwise
    public int getTurnaround(PCB process){
        if(!bitMod){
            return process.getTurnaround();
        }
        return -1;
    }

    //return the Waiting time of a process, -1 otherwise
    public int getWait(PCB process){
        if(!bitMod){
            return process.getWait();
        }
        return -1;
    }

    //to set the state of a process
    public void setState(PCB process, state state) {
		if(!bitMod){
            process.setState(state);
        }
	}

    //to set the turnaround time of a process
    public void setTurnaround(PCB process, int turnaround) {
		if(!bitMod){
            process.setTurnaround(turnaround);
        }
	}

    //to set the Waiting time of a process
	public void setWait(PCB process, int wait) {
		if(!bitMod){
            process.setWait(wait);
        }
	}

    //to set the remaining time of a process
    public void remain(PCB process, int burstTook) {
		if(!bitMod){
            process.theRemain(burstTook);
        }
	}
}
