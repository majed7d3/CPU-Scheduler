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

    }

    public void Shortest_Job_First(queue ready){
        
    }

}
