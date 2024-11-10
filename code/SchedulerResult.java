import java.util.ArrayList;
import java.util.List;

public class SchedulerResult {
    private List<ProcessExecution> executions; // Stores each process's execution details
    private int totalWaitTime;
    private int totalTurnaroundTime;

    // Constructor initializes the list
    public SchedulerResult() {
        executions = new ArrayList<>();
        totalWaitTime = 0;
        totalTurnaroundTime = 0;
    }

    // Inner class to store each process's execution details
    private static class ProcessExecution {
        int processId;
        int startTime;
        int endTime;
        int waitTime;
        int turnaroundTime; // We will treat 0 as "In Progress" for turnaround time

        ProcessExecution(int processId, int startTime, int endTime, int waitTime, int turnaroundTime) {
            this.processId = processId;
            this.startTime = startTime;
            this.endTime = endTime;
            this.waitTime = waitTime;
            this.turnaroundTime = turnaroundTime;
        }
    }

    // Method to add execution details of a process
    public void addProcessExecution(int processId, int startTime, int endTime, int waitTime, int turnaroundTime) {
        executions.add(new ProcessExecution(processId, startTime, endTime, waitTime, turnaroundTime));
        totalWaitTime += waitTime;
        if (turnaroundTime != 0) { // Only add completed processes for turnaround time calculation
            totalTurnaroundTime += turnaroundTime;
        }
    }

    // Method to display the results including Gantt chart and averages
    public void displayResults() {
        System.out.println("Process Execution Details:");
        for (ProcessExecution execution : executions) {
            System.out.println("Process ID: " + execution.processId +
                               " | Start Time: " + execution.startTime +
                               " | End Time: " + execution.endTime +
                               " | Waiting Time: " + execution.waitTime +
                               " | Turnaround Time: " + (execution.turnaroundTime == 0 ? "In Progress" : execution.turnaroundTime));
        }

        // Calculate and display average waiting and turnaround times
        double averageWaitTime = (double) totalWaitTime / executions.size();
        long completedProcessesCount = executions.stream().filter(e -> e.turnaroundTime != 0).count();
        double averageTurnaroundTime = completedProcessesCount > 0 ? (double) totalTurnaroundTime / completedProcessesCount : 0;

        System.out.println("\nAverage Waiting Time: " + averageWaitTime);
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);

        // Display the Gantt chart
        System.out.println("\nGantt Chart:");
        StringBuilder ganttChart = new StringBuilder();
        for (ProcessExecution execution : executions) {
            ganttChart.append("| P").append(execution.processId)
                      .append(" (").append(execution.startTime).append(" - ").append(execution.endTime).append(") ");
        }
        ganttChart.append("|");
        System.out.println(ganttChart.toString());
    }
}
