import java.util.ArrayList;
import java.util.List;

public class Schedulers {
    private final Thread readyThread; //the thread of the ready queue
    private queue finish; //a queue for the finished jobs
    private systemcall syscall; //for system calls

    //Constructor
    public Schedulers(queue finish, systemcall syscall, Thread readyThread){
        this.readyThread = readyThread;
        this.finish = finish;
        this.syscall = syscall;
    }
    
    // First_Come_First_Serve Algorithm
    public void First_Come_First_Serve(queue ready){
         int currenttime=0;// variable to calculate the waiting time 
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
    	 }
    }
    //Round Robin, quantum is 8ms
    public void Round_Robin(queue ready){
        int time = 0; //for the time that has been past
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
                    syscall.theRemain(job, remain);
                    time = time + remain;
                }
                else{
                    //finish 8ms of the job
                    syscall.theRemain(job, 8);
                    time = time + 8;
                }

                //to check if the jab is finished or not
                if(syscall.getRemain(job) != 0){
                    //if not the job will go to the back of the queue
                    syscall.setState(job, state.ready);
                    ready.enqueue(job);
                }
                else{
                    //if it is finished then it will go to the finish queue
                    //deallocate the memory of the job
                    syscall.deallocateMemory(syscall.getMemory(job));
                    syscall.setTurnaround(job, time);
                    syscall.setWait(job, time - syscall.getBurst(job));
                    syscall.TerminateProcess(job);
                    finish.enqueue(job);
                }
            }
        }
    }

    public void Shortest_Job_First(queue ready){
        SchedulerResult result = new SchedulerResult(); 
        int currentTime = 0; 
        List<PCB> completedProcesses = new ArrayList<>(); 
    
        while (ready.length() > 0) {    
            PCB currentProcess = ready.serve();    
            int startTime = currentTime;    
            currentProcess.setWait(currentTime);     
            currentTime += currentProcess.getBurst();   
            int endTime = currentTime;   
            currentProcess.setTurnaround(endTime);   
            currentProcess.setState(state.finish); 
            result.addProcessExecution(currentProcess.getId(), startTime, endTime, currentProcess.getWait(), currentProcess.getTurnaround()); 

        }
        result.displayResults();
    }
    }

}
