public class Schedulers {
    private final Thread readyThread; //the thread of the ready queue
    private queue finish; //a queue for the finished jobs
    private systemcall syscall; //for system calls
    private SchedulerResult result;
    private int startTime;
    private int endTime;
    //Constructor
    public Schedulers(queue finish, systemcall syscall, Thread readyThread){
        this.readyThread = readyThread;
        this.finish = finish;
        this.syscall = syscall;
    }
    
    // First_Come_First_Serve Algorithm
    public void First_Come_First_Serve(queue ready){
         int currenttime=0;// variable to calculate the waiting time 
         startTime =0;
         endTime =0;
         // loop for all jobs in the queue
    	 while(ready.length()>0){
    		//take the job from ready queue
    		 PCB job= ready.serve();
    		//set it as running
    		 syscall.setState(job, state.running);
    		//deallocate the memory of the job
    		 syscall.deallocateMemory(syscall.getMemory(job));
    		 //set the wait of the job
    		 syscall.setWait(job, currenttime);
    		 //set the setTurnaround of the job
    		 syscall.setTurnaround(job, currenttime + syscall.getBurst(job));
    		 // adding the wait time for every job
    		 currenttime+= syscall.getBurst(job);
    		 // terminate the procces after its done
    		 syscall.TerminateProcess(job);
    		 // put the job in the finish queue
    		 finish.enqueue(job);
              result.addProcessExecution(job.getId(), startTime, endTime, syscall.getWait(job), syscall.getTurnaround(job)); 
    	 }
    }
    //Round Robin, quantum is 8ms
    public void Round_Robin(queue ready){
        int time = 0; //for the time that has been past
        startTime = 0;
        endTime = 0;
        //check if ready queue is not empty of ready thread has not finished 
        while(ready.length() > 0 || readyThread.isAlive()){
            //to wait for the ready thread to start/continue
            while(ready.length() == 0 && readyThread.isAlive());
            //check if the ready queue is empty
            if(ready.length() != 0){
                //take the job from ready queue
                PCB job = ready.serve();
                //set it as running
                syscall.setState(job,state.running);
                //get the remaining time
                int remain = syscall.getRemain(job);
                
                //to see if the remaining is less or equle to 8
                if(remain <= 8){
                    //finish the job
                    startTime = time;
                    syscall.theRemain(job, remain);
                    time = time + remain;
                    endTime = time;
                }
                else{
                    startTime =time;
                    //finish 8ms of the job
                    syscall.theRemain(job, 8);
                    time = time + 8;
                    endTime = time;
                }

                //to check if the jab is finished or not
                if(syscall.getRemain(job) != 0){
                    //if not the job will go to the back of the queue
                    syscall.setState(job, state.ready);
                    ready.enqueue(job);
                    result.addProcessExecution(job.getId(), startTime, endTime, syscall.getWait(job), syscall.getTurnaround(job)); 
                }
                else{
                    //if it is finished then it will go to the finish queue
                    //deallocate the memory of the job
                    syscall.deallocateMemory(syscall.getMemory(job));
                    syscall.setTurnaround(job, time);
                    syscall.setWait(job, time - syscall.getBurst(job));
                    syscall.TerminateProcess(job);
                    finish.enqueue(job);
                    result.addProcessExecution(job.getId(), startTime, endTime, syscall.getWait(job), syscall.getTurnaround(job)); 
                }
            }
        }
    }

 public void Shortest_Job_First(queue ready){
        int currentTime = 0; 

        while (ready.length() > 0) {    
            PCB currentProcess = ready.serve();    
            int startTime = currentTime;
            syscall.setState(currentProcess, state.running); //set current job as running
            syscall.deallocateMemory(syscall.getMemory(currentProcess)); //deallocate the memory of the current job
            syscall.setWait(currentProcess, currentTime);     //set the waiting time for the current job
            currentTime += syscall.getBurst(currentProcess);   //add the burst time to currentTime (finshed the job) 
            int endTime = currentTime;          //set the endtime to the current time
            syscall.setTurnaround(currentProcess, endTime); 
            syscall.setState(currentProcess, state.finish);
            result.addProcessExecution(currentProcess.getId(), startTime, endTime, syscall.getWait(currentProcess), syscall.getTurnaround(currentProcess)); 

        }
    }
    }
