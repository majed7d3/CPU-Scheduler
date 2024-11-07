public class Schedulers {
    private queue finish;
    public void First_Come_First_Serve(queue ready){
int currenttime=0;
    	 node N1 = new node();
    	 int memoerycalc=0;
    	 queue readyQueue=ready;


    	for(int i=0; i<readyQueue.length();i++){
    	    if(N1.process.getMemory()>1024){
    	    N1.setProcess(null);
    	    }
    	    readyQueue.serve();
    	   
    	}
    	for(int i=0;i<readyQueue.length();i++){
    		N1.process.setWait(currenttime);

            N1.process.setTurnaround(currenttime + N1.process.getBurst());

    	    currenttime+=N1.process.getBurst();
    	    
    	    memoerycalc+= N1.process.getMemory();


    	    readyQueue.serve();

    	}// for now 
    }

    public void Round_Robin(queue ready){

    }

    public void Shortest_Job_First(queue ready){
        
    }

}
