import java.util.Scanner;

public class appRun {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("choose the scheduling algorithm");
        System.out.println("1. First-Come-First-Serve (FCFS).");
        System.out.println("2. Round-Robin (RR), the quantum = 8ms");
        System.out.println("3. Shortest Job First");
        int algorithm = input.nextInt();

        //initializing
        PQueue priorityJobQueue = new PQueue();
        queue jobQueue = new queue();
        queue readyQueue = new queue();
        queue finishQueue = new queue();
        queue cancelQueue = new queue();
        operatingsystem os = new operatingsystem();
        systemcall syscall = new systemcall(os);
        
        fileReader reader;
        Thread jobThread;
        readyJob ready;
        
        //to see if it need a priority queue or not
        if(algorithm == 3){
            //initializing the reader thread
            reader = new fileReader(priorityJobQueue,"job.txt", syscall);
            jobThread = new Thread(reader);
            //starting the reader thread
            jobThread.start();
            //initializing the ready thread
            ready = new readyJob(priorityJobQueue, readyQueue, cancelQueue, syscall, jobThread);
        }
        else{
            //initializing the reader thread
            reader = new fileReader(jobQueue,"job.txt", syscall);
            jobThread = new Thread(reader);
            //starting the reader thread
            jobThread.start();
            //initializing the ready thread
            ready = new readyJob(jobQueue, readyQueue, cancelQueue, syscall, jobThread);
        }
        Thread readyThread = new Thread(ready);
        //starting the ready thread
        readyThread.start();
        //starting the cpu scheduler
        Schedulers scheduler = new Schedulers(finishQueue, syscall, readyThread);
        switch (algorithm) {
            case 1:
                scheduler.First_Come_First_Serve(readyQueue);
                break;
            
            case 2:
                scheduler.Round_Robin(readyQueue);
                break;
            
            case 3:
                scheduler.Shortest_Job_First(readyQueue);
                break;
        }



        //TO-DO the print of the jobs
        System.out.println("the app is fin");
        int i = 0;
        while (finishQueue.length() > 0) {
            System.out.println("id: "+ finishQueue.serve().getId());
            i++;
        }
        System.out.println(i);
        i = 0;
        while (cancelQueue.length() > 0) {
            System.out.println("id: "+ cancelQueue.serve().getId());
            i++;
        }
        System.out.println(i);
    }
}
