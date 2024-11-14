public class PQueue {
    private node head; //head of the queue
    private int size; //size of the queue

	//Constructor
    public PQueue() {
		head = null;
		size = 0;
	}

	//return the size
    public int length(){
        return size;
    }

	//to add to the queue
	//burstTook is useed for calculate the remaining time, remaining = burst time - burstTook, 0 otherwise
	//syscall for calling system calls
    public void enqueue(PCB job, int burstTook, systemcall syscall) { 
		node tmp = new node(job);
		syscall.theRemain(job, burstTook);
		if((size == 0) || (syscall.getRemain(job) < syscall.getRemain(head.process))) {
			tmp.next = head;
			head = tmp;
		}
		else {
			node p = head;
			node q = null;
			while((p != null) && (syscall.getRemain(job) >= syscall.getRemain(p.process))) {
				q = p;
				p = p.next;
			}
			tmp.next = p;
			q.next = tmp;
		}
		size++;
	}

	//return the current node
    public PCB serve(){
		PCB job = head.process;
		head = head.next;
		size--;
		return job;
	}

}
