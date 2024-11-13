import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class fileReader implements Runnable{
    private boolean flag;
    private String fileName;
    private PQueue jabPQueue;
    private queue jabQueue;
    private systemcall syscall;


    public fileReader(PQueue jabPQueue,String fileName, systemcall syscall){
        flag = false;
        this.fileName = fileName;
        this.jabPQueue = jabPQueue;
        this.syscall = syscall;
    }

    public fileReader(queue jabQueue,String fileName, systemcall syscall){
        flag = true;
        this.fileName = fileName;
        this.jabQueue = jabQueue;
        this.syscall = syscall;
    }


    @Override
    public void run() {
        File file = new File(fileName);
        try (FileReader reader = new FileReader(file)) {
            BufferedReader buffer = new BufferedReader(reader);
            String line = ""; 
            while((line = buffer.readLine()) != null){
                String[] dataJab = new String[3];
                dataJab[0] = (line.split(":"))[0];
                dataJab[1] = ((line.split(":"))[1].split(";"))[0];
                dataJab[2] = ((line.split(":"))[1].split(";"))[1]; 
                int id = Integer.parseInt(dataJab[0]);
                int burst = Integer.parseInt(dataJab[1]);
                int memory = Integer.parseInt(dataJab[2]);
                    if(flag)
                        jabQueue.enqueue(syscall.createProcess(id, burst, memory));
                    else
                        jabPQueue.enqueue(syscall.createProcess(id, burst, memory), 0);
            }
        } catch (IOException e) {
            System.out.println("no file found.");
        }
    }
    
}
