public class operatingsystem  {
    private boolean bitMod; //false is kernel mode true is user mode
    private int memory;

    public operatingsystem(){
        memory = 1024;
        bitMod = true;
    }

    public void switchMod(){
        bitMod = !bitMod;
    }

    public PCB create(int id, int burst, int memory){
        PCB process = null;
        if(!bitMod){
            process = new PCB(id, burst, memory);
        }
        return process;
    }

    public PCB Terminate(PCB process){
        if(!bitMod){
            process.setState(state.finish);
        }
        return process;
    }

    public boolean allocate(int memory){
        if(!bitMod)
            this.memory = this.memory - memory;
        if(this.memory >= 0)
            return true;
	this.memory = this.memory + memory;
        return false;
    }

    public boolean deallocate(int memory){
        if(!bitMod)
            this.memory = this.memory + memory;
        if(this.memory <= 1024)
            return true;
	this.memory = this.memory - memory;
        return false;
    }

    public int getId(PCB process){
        if(!bitMod){
            return process.getId();
        }
        return -1;
    }

    public state getState(PCB process){
        if(!bitMod){
            return process.getState();
        }
        return null;
    }

    public int getBurst(PCB process){
        if(!bitMod){
            return process.getBurst();
        }
        return -1;
    }

    public int getRemain(PCB process){
        if(!bitMod){
            return process.getRemain();
        }
        return -1;
    }

    public int getMemory(PCB process){
        if(!bitMod){
            return process.getMemory();
        }
        return -1;
    }

    public int getTurnaround(PCB process){
        if(!bitMod){
            return process.getTurnaround();
        }
        return -1;
    }

    public int getWait(PCB process){
        if(!bitMod){
            return process.getWait();
        }
        return -1;
    }

    public void setState(PCB process, state state) {
		if(!bitMod){
            process.setState(state);
        }
	}

    public void setTurnaround(PCB process, int turnaround) {
		if(!bitMod){
            process.setTurnaround(turnaround);
        }
	}

	public void setWait(PCB process, int wait) {
		if(!bitMod){
            process.setWait(wait);
        }
	}

    public void remain(PCB process, int burstTook) {
		if(!bitMod){
            process.theRemain(burstTook);
        }
	}

    public int memoryAllocate() {
        if(!bitMod)
            return 1024 - memory;
        return -1;
    }

}
