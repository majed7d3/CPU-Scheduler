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

    public void enqueue(PCB e) {
		if(tail == null){
			head = tail = new node(e);
		}
		else {
			tail.next = new node(e);
			tail = tail.next;
		}
		size++;
	}

    public PCB serve() {
		PCB x = head.process;
		head = head.next;
		size--;
		if(size == 0)
			tail = null;
		return x;
	}


}