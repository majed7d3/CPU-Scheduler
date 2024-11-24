import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class fileReader implements Runnable {
    private boolean isPriority;
    private String fileName;
    private PQueue priorityJobQueue;
    private queue jobQueue;
    private systemcall syscall;

    public fileReader(PQueue priorityJobQueue, String fileName, systemcall syscall) {
        this.isPriority = true;
        this.fileName = fileName;
        this.priorityJobQueue = priorityJobQueue;
        this.syscall = syscall;
    }

    public fileReader(queue jobQueue, String fileName, systemcall syscall) {
        this.isPriority = false;
        this.fileName = fileName;
        this.jobQueue = jobQueue;
        this.syscall = syscall;
    }

    @Override
    public void run() {
        File file = new File(fileName);
        try (BufferedReader buffer = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = buffer.readLine()) != null) {
                String[] datajob = new String[3];
                datajob[0] = line.split(":")[0];
                datajob[1] = line.split(":")[1].split(";")[0];
                datajob[2] = line.split(":")[1].split(";")[1];

                int id = Integer.parseInt(datajob[0]);
                int burst = Integer.parseInt(datajob[1]);
                int memory = Integer.parseInt(datajob[2]);
                PCB process = syscall.createProcess(id, burst, memory);

                if (process != null) {
                    if (isPriority) {
                        priorityJobQueue.enqueue(process, 0, syscall);
                    } else {
                        jobQueue.enqueue(process);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("File not found: " + fileName);
        }
    }
}
