public class node {
    public PCB process; //the PCB of a process
    public node next; //the next in the linklist

    //Constructor
    public node(){
        process = null;
        next = null;
    }

    //copy Constructor
    public node(PCB process) {
        this.process = process;
        next = null;
    }

    //get next node
    public node getNext() {
        return next;
    }

    //get the node Process
    public PCB getProcess() {
        return process;
    }

    //set next node
    public void setNext(node next) {
        this.next = next;
    }

    //set the node Process
    public void setProcess(PCB process) {
        this.process = process;
    }

}
