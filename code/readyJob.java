public class readyJob implements Runnable {
    private Thread readerThread; // the thread of the reader queue
    private boolean isPriority; // true if using PQueue, false for queue
    private PQueue priorityJobQueue; // priority job queue
    private queue jobQueue; // job queue
    private queue readyQueue; // the ready queue
    private queue cancelQueue; // for canceled jobs
    private systemcall syscall; // for system calls

    // Constructor for priority queue
    public readyJob(PQueue priorityJobQueue, queue readyQueue, queue cancelQueue, systemcall syscall, Thread readerThread) {
        this.readerThread = readerThread;
        this.isPriority = true;
        this.priorityJobQueue = priorityJobQueue;
        this.readyQueue = readyQueue;
        this.cancelQueue = cancelQueue;
        this.syscall = syscall;
    }

    // Constructor for non-priority queue
    public readyJob(queue jobQueue, queue readyQueue, queue cancelQueue, systemcall syscall, Thread readerThread) {
        this.readerThread = readerThread;
        this.isPriority = false;
        this.jobQueue = jobQueue;
        this.readyQueue = readyQueue;
        this.cancelQueue = cancelQueue;
        this.syscall = syscall;
    }

    @Override
public void run() {
    while (readerThread.isAlive() || (isPriority ? priorityJobQueue.length() > 0 : jobQueue.length() > 0)) {
        PCB job = null;

        if (isPriority) {
            synchronized (priorityJobQueue) {
                if (priorityJobQueue.length() > 0) {
                    job = priorityJobQueue.serve(); // Serve the highest priority job
                }
            }
        } else {
            synchronized (jobQueue) {
                if (jobQueue.length() > 0) {
                    job = jobQueue.serve(); // Serve the next job in FCFS order
                }
            }
        }

        if (job != null) {
            if (syscall.getMemory(job) > 1024) {
                syscall.setState(job, state.canceled);
                cancelQueue.enqueue(job);
            } else {
                boolean allocated;
                synchronized (syscall) {
                    allocated = syscall.allocateMemory(syscall.getMemory(job));
                }

                if (allocated) {
                    syscall.setState(job, state.ready);
                    synchronized (readyQueue) {
                        readyQueue.enqueue(job); // Add to ready queue
                    }
                } else {
                    if (isPriority) {
                        synchronized (priorityJobQueue) {
                            priorityJobQueue.enqueue(job, 0, syscall); // Re-enqueue in priority queue
                        }
                    } else {
                        synchronized (jobQueue) {
                            jobQueue.enqueue(job); // Re-enqueue in job queue
                        }
                    }

                    try {
                        Thread.sleep(10); // Wait before retrying
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        } else {
            try {
                Thread.sleep(10); // Wait briefly if no jobs are available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

}
