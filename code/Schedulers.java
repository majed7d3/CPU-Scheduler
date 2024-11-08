public class Schedulers {
    private queue finish;
    public void First_Come_First_Serve(queue ready){
         int currenttime=0;
    	 node N1 = new node();
    	queue readyQueue=ready;
        
    	for(int i=0;i<readyQueue.length();i++){
    	    
    		N1.process.setWait(currenttime);

    	   N1.process.setTurnaround(currenttime + N1.process.getBurst());

    	    currenttime+=N1.process.getBurst();
    	   
    	N1.process.setState( N1.process.getState().finish);

            finish.enqueue(readyQueue.serve());

    	}// for now 
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
        
    }

}
