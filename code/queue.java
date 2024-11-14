public class queue {
    private node head, tail; //head and taill of the queue
    private int size; //size of the queue

	//Constructor
    public queue(){
        head = tail = null;
        size = 0;
    }

	//return the size
    public int length(){
        return size;
    }

	//to add to the queue
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

	//return the currnet node
    public PCB serve() {
		PCB job = head.process;
		head = head.next;
		size--;
		if(size == 0)
			tail = null;
		return job;
	}


}
