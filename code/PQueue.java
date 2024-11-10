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
    public void enqueue(PCB job, int burstTook) { 
		node tmp = new node(job);
		job.theRemain(burstTook);
		if((size == 0) || (job.getRemain() < head.process.getRemain())) {
			tmp.next = head;
			head = tmp;
		}
		else {
			node p = head;
			node q = null;
			while((p != null) && (job.getRemain() >= p.process.getRemain())) {
				q = p;
				p = p.next;
			}
			tmp.next = p;
			q.next = tmp;
		}
		size++;
	}


    public PCB serve(){
		node node = head;
		PCB job = new PCB(node.process.getId(), node.process.getBurst(), node.process.getMemory());
		head = head.next;
		size--;
		return job;
	}

}
