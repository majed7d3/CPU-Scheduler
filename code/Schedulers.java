import java.util.ArrayList;
import java.util.List;

public class Schedulers {
    private queue finish;
    public void First_Come_First_Serve(queue ready){
         int currenttime=0;
    	queue readyQueue=ready;
    	
    	 while(readyQueue.length()>0){
    		 PCB job= readyQueue.serve();
    		 job.setState(state.finish);
    		 job.setWait(currenttime);
    		 job.setTurnaround(currenttime + job.getBurst());
    		 currenttime+= job.getBurst();
    		 finish.enqueue(job);
    	 }

    }

    public void Round_Robin(queue ready){
        queue waiting = new queue();
        int time = 0;
        while(ready.length() > 0){
            PCB job = ready.serve();
            int remain = job.getRemain();
            
            if(remain <= 8){
                job.theRemain(remain);
                time = time + remain;
            }
            else{
                job.theRemain(8);
                time = time + 8;
            }

            if(job.getRemain() != 0){
                waiting.enqueue(job);
            }
            else{
                job.setState(state.finish);
                job.setTurnaround(time);
                job.setWait(time - job.getBurst());
                finish.enqueue(job);
            }
        }

        while(waiting.length() > 0){
            PCB job = ready.serve();
            int remain = job.getRemain();
            
            if(remain <= 8){
                job.theRemain(remain);
                time = time + remain;
            }
            else{
                job.theRemain(8);
                time = time + 8;
            }

            if(job.getRemain() != 0){
                waiting.enqueue(job);
            }
            else{
                job.setState(state.finish);
                job.setTurnaround(time);
                job.setWait(time - job.getBurst());
                finish.enqueue(job);
            }
        }
    }

    public void Shortest_Job_First(queue ready){
        SchedulerResult result = new SchedulerResult(); // Create result handler for SJF
        int currentTime = 0; // Tracks the current time in the scheduling simulation
        List<PCB> completedProcesses = new ArrayList<>(); // List to store completed processes

    
        while (ready.length() > 0) {    // Continue until all processes in the ready queue have been served
            
            PCB currentProcess = ready.serve();    // Serve the process with the shortest remaining burst time
            
            int startTime = currentTime;    // Record the start time of this process
            
            currentProcess.setWait(currentTime);     // Calculate and set waiting time for this process
            
            currentTime += currentProcess.getBurst();    // Simulate the process execution by adding its remaining time to current time
            
            int endTime = currentTime;     // Record the end time of this process

            currentProcess.setTurnaround(endTime);     // Set turnaround time for this process
            
            currentProcess.setState(state.finish);     // Process has now completed
            
            result.addProcessExecution(currentProcess.getId(), startTime, endTime, currentProcess.getWait(), currentProcess.getTurnaround());    // Record the execution details in SchedulerResult

        }
    
        // Display results for SJF
        result.displayResults();
    }
    }

}
