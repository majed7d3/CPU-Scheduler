public class systemcall  {
    private operatingsystem os;

    public systemcall(operatingsystem os){
        this.os = os;
    }

    //to creat a process
    public PCB createProcess(int id, int burst, int memory){ 
        os.switchMod();
        PCB process = os.create(id,burst,memory);
        os.switchMod();
        return process;
    }

    //to terminate a process
    public PCB TerminateProcess(PCB process){
        os.switchMod();
        os.Terminate(process);
        os.switchMod();
        return process;
    }

    //to allocate the memory
    public boolean allocateMemory(int memory){
        os.switchMod();
        boolean flag = os.allocate(memory);
        os.switchMod();
        return flag;
    }

    //to deallocate the memory
    public boolean deallocateMemory(int memory){
        os.switchMod();
        boolean flag = os.deallocate(memory);
        os.switchMod();
        return flag;
    }

    //get id of the process
    public int getId(PCB process){
        os.switchMod();
        int get = os.getId(process);
        os.switchMod();
        return get;
    }

    //get state of the process
    public state getState(PCB process){
        os.switchMod();
        state get = os.getState(process);
        os.switchMod();
        return get;
    }

    //get burst time of the process
    public int getBurst(PCB process){
        os.switchMod();
        int get = os.getBurst(process);
        os.switchMod();
        return get;
    }

    //get remain time of the process
    public int getRemain(PCB process){
        os.switchMod();
        int get = os.getRemain(process);
        os.switchMod();
        return get;
    }

    //get the memory of the process
    public int getMemory(PCB process){
        os.switchMod();
        int get = os.getMemory(process);
        os.switchMod();
        return get;
    }

    //get turnaround time of the process
    public int getTurnaround(PCB process){
        os.switchMod();
        int get = os.getTurnaround(process);
        os.switchMod();
        return get;
    }

    //get Waiting time of the process
    public int getWait(PCB process){
        os.switchMod();
        int get = os.getWait(process);
        os.switchMod();
        return get;
    }

    //set state of the process
    public void setState(PCB process, state state){
		os.switchMod();
        os.setState(process, state);
        os.switchMod();
	}

    //set turnaround time of the process
    public void setTurnaround(PCB process, int turnaround){
		os.switchMod();
        os.setTurnaround(process, turnaround);
        os.switchMod();
	}

    //set Waiting time of the process
	public void setWait(PCB process, int wait){
		os.switchMod();
        os.setWait(process, wait);
        os.switchMod();
	}
	
    //calculate the remain time for the process
    public void theRemain(PCB process, int burstTook){
		os.switchMod();
        os.remain(process, burstTook);
        os.switchMod();
	}

    //returns the amount of memory that is used
    public int memoryAllocate(){
        os.switchMod();
        int allocate = os.memoryAllocate();
        os.switchMod();
        return allocate;
    }

}
