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
