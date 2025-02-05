public class Schedulers {
    private final Thread readyThread; //the thread of the ready queue
    private systemcall syscall; //for system calls
    private SchedulerResult result;
    private int startTime;
    private int endTime;

    //Constructor
    public Schedulers(systemcall syscall, Thread readyThread,SchedulerResult result){
        this.readyThread = readyThread;
        this.syscall = syscall;
        this.result = result;
    }
    
    //for First_Come_First_Serve Algorithm
    public void First_Come_First_Serve(queue ready) {
        int currentTime = 0; //the current time
    
        while (ready.length() > 0 || readyThread.isAlive()) {
            // Wait for jobs to be added to the ready queue
            while (ready.length() == 0 && readyThread.isAlive()) {
                try {
                    Thread.sleep(10); // Avoid busy waiting
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
    
            //check if ready queue has jobs
            if (ready.length() > 0) {
                PCB currentProcess = ready.serve();
                int startTime = currentTime; //the start time of the job
    
                syscall.setState(currentProcess, state.running); //running the job
                syscall.deallocateMemory(syscall.getMemory(currentProcess)); //deallocate the job from the memory
                syscall.setWait(currentProcess, currentTime); //seting the waiting time 
                currentTime += syscall.getBurst(currentProcess); //increase the time
                int endTime = currentTime; //the end time for the job
    
                syscall.setTurnaround(currentProcess, endTime);//seting the turnaround time
                syscall.TerminateProcess(currentProcess);//Terminateing the job
    
                // Log the process execution
                result.addProcessExecution(currentProcess.getId(), startTime, endTime, syscall.getWait(currentProcess), syscall.getTurnaround(currentProcess));
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    //for Round_Robin
    public void Round_Robin(queue ready) {
        int time = 0; // Tracks the total time passed
        startTime = 0; //start time for the job
        endTime = 0; //end time for the job
    
        // Continue while jobs are in the ready queue or the ready thread is alive
        while (ready.length() > 0 || readyThread.isAlive()) {
            while (ready.length() == 0 && readyThread.isAlive()) {
                // Wait if the ready queue is empty
                try {
                    Thread.sleep(10); // Allow readyJob to enqueue jobs
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Scheduler thread interrupted.");
                    return; // Exit the thread gracefully
                }
            }
    
            if (ready.length() > 0) {
                // Take the next process from the ready queue
                PCB job = ready.serve();
    
                // Set the process to running
                syscall.setState(job, state.running);
                int remain = syscall.getRemain(job);
                startTime = time;
    
                if (remain <= 8) { // Process finishes within this quantum
                    syscall.theRemain(job, remain); // Reduce remaining burst time to 0
                    time += remain; // Advance the clock by the remaining burst time
                    endTime = time;
    
                    // Update turnaround and waiting times
                    syscall.deallocateMemory(syscall.getMemory(job));
                    syscall.setTurnaround(job, time);
                    syscall.setWait(job, time - syscall.getBurst(job));
                    syscall.TerminateProcess(job);
    
                    // Log the completed process
                    result.addProcessExecution(job.getId(), startTime, endTime, syscall.getWait(job), syscall.getTurnaround(job));
                } else { // Process needs more than this quantum
                    syscall.theRemain(job, 8); // Reduce remaining burst time by 8ms
                    time += 8; // Advance the clock by the quantum
                    endTime = time;
    
                    // Place the process back in the ready queue
                    syscall.setState(job, state.ready);
                    ready.enqueue(job);
    
                    // Log the partial execution
                    result.addProcessExecution(job.getId(), startTime, endTime, syscall.getWait(job), 0); // Turnaround time is incomplete
                }
            }
    
            // Add a small delay to simulate the scheduling behavior
            try {
                Thread.sleep(10); // Allow readyJob to enqueue jobs into readyQueue
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Scheduler thread interrupted.");
                return; // Exit the thread gracefully
            }
        }
    }
    
    //for Shortest_Job_First
    public void Shortest_Job_First(queue ready) {
        int currentTime = 0; //tracking the current time
    
        while (ready.length() > 0 || readyThread.isAlive()) { //Continue while there are jobs in the ready queue or the reader thread is active
            while (ready.length() == 0 && readyThread.isAlive()) {//If the ready queue is empty but the reader thread is still active wait for jobs to be added
                try {
                    Thread.sleep(10); //Avoid busy waiting
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); //Restore interrupted status
                    return; //Exit if interrupted
                }
            }
            if (ready.length() > 0) {   //If queue not empty
                PCB currentProcess = ready.serve(); //Serve the job with the shortest burst time
                int startTime = currentTime; //save the starting time of the current process
                syscall.setState(currentProcess, state.running);//Set the process state to 'running'
                syscall.deallocateMemory(syscall.getMemory(currentProcess));//Deallocate the memory used by the current process
                syscall.setWait(currentProcess, currentTime); //Set the waiting time for the current process
                currentTime += syscall.getBurst(currentProcess); //Simulate the process execution
                int endTime = currentTime; //save the end time of the current process
                syscall.setTurnaround(currentProcess, endTime);//Set the turnaround time for the process
                syscall.TerminateProcess(currentProcess); //Set the process state to 'finish'
                result.addProcessExecution(currentProcess.getId(), startTime, endTime, syscall.getWait(currentProcess), syscall.getTurnaround(currentProcess)); //send the execution details to the printing class
            } 
            }
        }
    }
    
    
