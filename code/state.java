//the states of a process
public enum state{
    waiting //in the job queue
    , ready //in the ready queue
    , running //if the process is running
    , finish //if the process finished/Terminated
    , canceled //if the process to big
}
