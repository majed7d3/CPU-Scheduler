public class readyJob implements Runnable{
    private Thread readerThread; //the thread of the reader queue
    private boolean isPriority; //to know if the queue is priority or not (true for priority, false for no priority)
    private PQueue priorityJobQueue; //priority job queue from the file
    private queue jobQueue; //job queue from the file
    private queue readyQueue; //the ready queue
    private systemcall syscall; //for calling system calls
    
    //Constructor if the queue is priority
    public readyJob(PQueue priorityJobQueue,queue readyQueue, systemcall syscall, Thread readerThread){
        this.readerThread = readerThread;
        isPriority = true;
        this.priorityJobQueue = priorityJobQueue;
        this.readyQueue = readyQueue;
        this.syscall = syscall;
    }

    //Constructor for a queue 
    public readyJob(queue jobQueue,queue readyQueue, systemcall syscall, Thread readerThread){
        this.readerThread = readerThread;
        isPriority = false;
        this.jobQueue = jobQueue;
        this.readyQueue = readyQueue;
        this.syscall = syscall;
    }

    //read from job queue and add to ready queue if there is enough memory
    //if process is bigger then the memory the process will be canceled
    @Override
    public void run() {
        PCB job = null;
        if(!isPriority){ //to see if the queue is priority
            //check if the reader Thread is still up or not
            //or check if job queue is empty or not
            //or if the job queue is empty but the last job has not been add it
            while(readerThread.isAlive() || jobQueue.length() > 0 || (jobQueue.length() == 0 && job != null)){
                //to wait for the reader thread to start
                while(jobQueue.length() == 0 && readerThread.isAlive()); 
                
                //check if the job queue not empty and there is no old job
                if( jobQueue.length() != 0 && job == null){
                    job = jobQueue.serve();
                }

                //to check if the job is bigger then the memory
                if(syscall.getMemory(job) > 1024){
                    //set it as canceled
                    syscall.setState(job, state.canceled);
                    //add it to the canceled queue
                    cancelQueue.enqueue(job);
                    job = null;
                }
                
                //check if there is a new/old job and there is space in memory for it
                if(job != null && syscall.allocateMemory(syscall.getMemory(job))){
                    syscall.setState(job, state.ready);
                    readyQueue.enqueue(job);
                    //to make the job empty
                    job = null;
                }
            }
        }
        //same as the one above but for priority queue
        else{
            //check if the reader Thread is still up or not
            //or check if priority job queue is empty or not
            //or if the priority job queue is empty but the last job has not been add it
            while(readerThread.isAlive() || priorityJobQueue.length() > 0 || (priorityJobQueue.length() == 0 && job != null)){
                //to wait for the reader thread to start
                while(priorityJobQueue.length() == 0 && readerThread.isAlive()); 
                //check if the priority job queue not empty and there is no old job
                if( priorityJobQueue.length() != 0 && job == null){
                    job = priorityJobQueue.serve();
                }

                //to check if the job is bigger then the memory
                if(syscall.getMemory(job) > 1024){
                    //set it as canceled
                    syscall.setState(job, state.canceled);
                    //add it to the canceled queue
                    cancelQueue.enqueue(job);
                    job = null;
                }

                //check if there is a new/old job and there is space in memory for it
                if(job != null && syscall.allocateMemory(syscall.getMemory(job))){
                    syscall.setState(job, state.ready);
                    readyQueue.enqueue(job);
                    job = null;
                }
            }
        }
    }
    
}
