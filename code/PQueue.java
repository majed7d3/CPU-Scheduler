public class PQueue {
    private node head;
    private int size;

    public PQueue() {
		head = null;
		size = 0;
	}


    public int length(){
        return size;
    }

	//burstTook is useed for calculate the remaining time, remaining = burst_time - burstTook
    public void enqueue(PCB job, int burstTook, systemcall syscall) { 
		node tmp = new node(job);
		syscall.theRemain(job, burstTook);
		if((size == 0) || (syscall.getRemain(job) > syscall.getRemain(head.process))) {
			tmp.next = head;
			head = tmp;
		}
		else {
			node p = head;
			node q = null;
			while((p != null) && (syscall.getRemain(job) <= syscall.getRemain(p.process))) {
				q = p;
				p = p.next;
			}
			tmp.next = p;
			q.next = tmp;
		}
		size++;
	}


    public PCB serve(){
		PCB job = head.process;
		head = head.next;
		size--;
		return job;
	}

}
