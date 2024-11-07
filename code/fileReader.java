import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class fileReader implements Runnable{
    private boolean flag;
    private String fileName;
    private PQueue jabPQueue;
    private queue jabQueue;


    public fileReader(PQueue jabPQueue,String fileName){
        flag = false;
        this.fileName = fileName;
        this.jabPQueue = jabPQueue;
    }

    public fileReader(queue jabQueue,String fileName){
        flag = true;
        this.fileName = fileName;
        this.jabQueue = jabQueue;
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
                PCB jab = new PCB(id, burst, memory);
                    if(flag)
                        jabQueue.enqueue(jab);
                    else
                        jabPQueue.enqueue(jab, 0);
            }
        } catch (IOException e) {
            System.out.println("no file found.");
        }
    }
    
}
