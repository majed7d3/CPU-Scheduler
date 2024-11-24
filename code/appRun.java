import java.util.Scanner;
public class appRun {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Choose the scheduling algorithm:");
        System.out.println("1. First-Come-First-Serve (FCFS).");
        System.out.println("2. Round-Robin (RR), the quantum = 8ms.");
        System.out.println("3. Shortest Job First.");
        int algorithm = input.nextInt();

        PQueue priorityJobQueue = new PQueue();
        queue jobQueue = new queue();
        queue readyQueue = new queue();
        queue finishQueue = new queue();
        queue cancelQueue = new queue();
        operatingsystem os = new operatingsystem();
        systemcall syscall = new systemcall(os);
        SchedulerResult result = new SchedulerResult();
        fileReader reader;
        Thread jobThread;
        readyJob ready;

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

        Schedulers scheduler = new Schedulers(finishQueue, syscall, readyThread, result);

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

        result.displayResults();
    }
}
