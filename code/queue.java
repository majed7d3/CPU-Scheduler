public class queue {
    private node head, tail;
    private int size;

    public queue(){
        head = tail = null;
        size = 0;
    }

    public int length(){
        return size;
    }

    public void enqueue(PCB job) {
		if(tail == null){
			head = tail = new node(job);
		}
		else {
			tail.next = new node(job);
			tail = tail.next;
		}
		size++;
	}

    public PCB serve() {
		PCB job = head.process;
		head = head.next;
		size--;
		if(size == 0)
			tail = null;
		return job;
	}


}
