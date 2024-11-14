import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class fileReader implements Runnable{
    private boolean isPriority; //to know if the queue is priority or not (true for priority, false for no priority)
    private String fileName; //the file name of the input file
    private PQueue priorityJobQueue; //priority job queue (the priority is on the burst time from smallest to largest)
    private queue jobQueue; //job queue
    private systemcall syscall; //for calling system calls


    //Constructor if the queue is priority
    public fileReader(PQueue priorityJobQueue,String fileName, systemcall syscall){
        isPriority = true;
        this.fileName = fileName;
        this.priorityJobQueue = priorityJobQueue;
        this.syscall = syscall;
    }

    //Constructor for a queue 
    public fileReader(queue jobQueue,String fileName, systemcall syscall){
        isPriority = false;
        this.fileName = fileName;
        this.jobQueue = jobQueue;
        this.syscall = syscall;
    }


    //for reading the file job.txt
    //the job.txt need to be in the same folder as the code
    //the job.txt need to have this strusture id:burst_time;Memory
    //the job.txt have to begin with (id:burst_time;Memory) and end with (id:burst_time;Memory)
    @Override
    public void run() {
        File file = new File(fileName);
        try (FileReader reader = new FileReader(file)) {
            BufferedReader buffer = new BufferedReader(reader);
            String line = ""; //to save the current line
            while((line = buffer.readLine()) != null){ //while the current line is not null
                //this for spliting the line to have the id, burst time, Memory
                String[] datajob = new String[3];
                datajob[0] = (line.split(":"))[0];
                datajob[1] = ((line.split(":"))[1].split(";"))[0];
                datajob[2] = ((line.split(":"))[1].split(";"))[1]; 
                int id = Integer.parseInt(datajob[0]);
                int burst = Integer.parseInt(datajob[1]);
                int memory = Integer.parseInt(datajob[2]);
                    if(!isPriority) //to check if we add the Process queue to or a priority queue
                        jobQueue.enqueue(syscall.createProcess(id, burst, memory));
                    else
                        priorityJobQueue.enqueue(syscall.createProcess(id, burst, memory), 0, syscall);
            }
        } catch (IOException e) {
            //if we can not find the file
            System.out.println("no file found.");
        }
    }
    
}
