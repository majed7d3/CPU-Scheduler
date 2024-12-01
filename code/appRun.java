import java.util.Scanner;
public class appRun {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Choose the scheduling algorithm:");
        System.out.println("1. First-Come-First-Serve (FCFS).");
        System.out.println("2. Round-Robin (RR), the quantum = 8ms.");
        System.out.println("3. Shortest Job First.");
        int algorithm = input.nextInt();

        //initializing
        PQueue priorityJobQueue = new PQueue(); //priority job queue from the file
        queue jobQueue = new queue(); //job queue from the file
        queue readyQueue = new queue(); //a queue for jobs in the memory
        queue cancelQueue = new queue(); //a queue for canceled jobs (bigger then the memory)
        operatingsystem os = new operatingsystem();
        systemcall syscall = new systemcall(os);
        SchedulerResult result = new SchedulerResult(); //for printing
        fileReader reader; //reading from the file
        Thread jobThread; //thread for reading from the file
        readyJob ready; //for ready job queue (checking the memory)

        // Initialize reader and ready threads based on algorithm
        if (algorithm == 3) {
            reader = new fileReader(priorityJobQueue, "./job.txt", syscall);
            jobThread = new Thread(reader);
            jobThread.start();
            ready = new readyJob(priorityJobQueue, readyQueue, cancelQueue, syscall, jobThread);
        } else {
            reader = new fileReader(jobQueue, "./job.txt", syscall);
            jobThread = new Thread(reader);
            jobThread.start();
            ready = new readyJob(jobQueue, readyQueue, cancelQueue, syscall, jobThread);
        }
        Thread readyThread = new Thread(ready);
        readyThread.start();

        Schedulers scheduler = new Schedulers(syscall, readyThread, result);

        //chose the scheduler
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

        //print the jobs + gantt chart
        result.displayResults();

        //print any canceled jobs
        int length = cancelQueue.length();
        if(length != 0){
            System.out.println("\nThe canceled jobs:");
            
            for(int i = 0; i < length; i++){
                PCB job = cancelQueue.serve();
                if(i+1 == length){
                    System.out.print("P"+syscall.getId(job));
                    break;
                }
                System.out.print("P"+syscall.getId(job)+", ");
            }
        }
    }
}
