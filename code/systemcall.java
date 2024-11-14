public class systemcall  {
    private operatingsystem os;

    //Constructor
    public systemcall(operatingsystem os){
        this.os = os;
    }

    //to creat a process
    public PCB createProcess(int id, int burst, int memory){ 
        os.switchToKernelMode();
        PCB process = os.create(id,burst,memory);
        os.switchToUserMode();
        return process;
    }

    //to terminate a process
    public void TerminateProcess(PCB process){
        os.switchToKernelMode();
        os.Terminate(process);
        os.switchToUserMode();
    }

    //to allocate the memory
    public boolean allocateMemory(int memory){
        os.switchToKernelMode();
        boolean flag = os.allocate(memory);
        os.switchToUserMode();
        return flag;
    }

    //to deallocate the memory
    public boolean deallocateMemory(int memory){
        os.switchToKernelMode();
        boolean flag = os.deallocate(memory);
        os.switchToUserMode();
        return flag;
    }

    //get id of the process
    public int getId(PCB process){
        os.switchToKernelMode();
        int get = os.getId(process);
        os.switchToUserMode();
        return get;
    }

    //get state of the process
    public state getState(PCB process){
        os.switchToKernelMode();
        state get = os.getState(process);
        os.switchToUserMode();
        return get;
    }

    //get burst time of the process
    public int getBurst(PCB process){
        os.switchToKernelMode();
        int get = os.getBurst(process);
        os.switchToUserMode();
        return get;
    }

    //get remain time of the process
    public int getRemain(PCB process){
        os.switchToKernelMode();
        int get = os.getRemain(process);
        os.switchToUserMode();
        return get;
    }

    //get the memory of the process
    public int getMemory(PCB process){
        os.switchToKernelMode();
        int get = os.getMemory(process);
        os.switchToUserMode();
        return get;
    }

    //get turnaround time of the process
    public int getTurnaround(PCB process){
        os.switchToKernelMode();
        int get = os.getTurnaround(process);
        os.switchToUserMode();
        return get;
    }

    //get Waiting time of the process
    public int getWait(PCB process){
        os.switchToKernelMode();
        int get = os.getWait(process);
        os.switchToUserMode();
        return get;
    }

    //set state of the process
    public void setState(PCB process, state state){
		os.switchToKernelMode();
        os.setState(process, state);
        os.switchToUserMode();
	}

    //set turnaround time of the process
    public void setTurnaround(PCB process, int turnaround){
		os.switchToKernelMode();
        os.setTurnaround(process, turnaround);
        os.switchToUserMode();
	}

    //set Waiting time of the process
	public void setWait(PCB process, int wait){
		os.switchToKernelMode();
        os.setWait(process, wait);
        os.switchToUserMode();
	}

    //calculate the remain time for the process
    public void theRemain(PCB process, int burstTook){
		os.switchToKernelMode();
        os.remain(process, burstTook);
        os.switchToUserMode();
	}
}
