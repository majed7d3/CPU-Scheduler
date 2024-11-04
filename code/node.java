public class node {
    public PCB process;
    public node next;

    public node(){
        process = null;
        next = null;
    }

    public node(PCB process) {
        this.process = new PCB(process.getId(), process.getBurst(), process.getMemory());
        next = null;
    }

    public node getNext() {
        return next;
    }

    public PCB getProcess() {
        return process;
    }

    public void setNext(node next) {
        this.next = next;
    }

    public void setProcess(PCB process) {
        this.process = new PCB(process.getId(), process.getBurst(), process.getMemory());
    }

}
