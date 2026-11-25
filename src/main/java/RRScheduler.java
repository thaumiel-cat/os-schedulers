import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

public class RRScheduler extends AbstractScheduler {

  private Queue<Process> readyQueue;
  private int timer;

  public RRScheduler() {
    readyQueue = new LinkedList<Process>();
  }


  public void ready(Process process, boolean usedFullTimeQuantum) {
    readyQueue.offer(process);
  }

  public Process schedule() {
    System.out.println("Scheduler selects process "+readyQueue.peek());
    return readyQueue.poll();
  }

  public int getTimeQuantum() {
    return timer;
  }

  public void initialize(Properties parameters) {
    timer = Integer.parseInt(parameters.getProperty("timeQuantum"));
  }
}
