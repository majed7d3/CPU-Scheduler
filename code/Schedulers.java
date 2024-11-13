import java.util.ArrayList;
import java.util.List;

public class Schedulers {
    private queue finish;
    private systemcall syscall;

    public Schedulers(queue finish, systemcall syscall){
        this.finish = finish;
        this.syscall = syscall;
    }
    
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
            int remain = syscall.getRemain(job);
            
            if(remain <= 8){
                syscall.theRemain(job, remain);
                time = time + remain;
            }
            else{
                syscall.theRemain(job, 8);
                time = time + 8;
            }

            if(syscall.getRemain(job) != 0){
                waiting.enqueue(job);
            }
            else{
                syscall.TerminateProcess(job);
                syscall.setTurnaround(job, time);
                syscall.setWait(job, time - syscall.getBurst(job));
                finish.enqueue(job);
            }
        }

        while(waiting.length() > 0){
            PCB job = waiting.serve();
            int remain = syscall.getRemain(job);
            
            if(remain <= 8){
                syscall.theRemain(job, remain);
                time = time + remain;
            }
            else{
                syscall.theRemain(job, 8);
                time = time + 8;
            }

            if(syscall.getRemain(job) != 0){
                waiting.enqueue(job);
            }
            else{
                syscall.TerminateProcess(job);
                syscall.setTurnaround(job, time);
                syscall.setWait(job, time - syscall.getBurst(job));
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
