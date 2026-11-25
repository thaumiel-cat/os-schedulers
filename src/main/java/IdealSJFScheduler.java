import java.util.ArrayList;
import java.util.Comparator;

public class IdealSJFScheduler extends AbstractScheduler {

  private ArrayList<Process> processesList;

  public IdealSJFScheduler() {
    processesList = new ArrayList<>();
  }

  public void ready(Process process, boolean usedFullTimeQuantum) {
    processesList.add(process);
  }

  public Process schedule() {
    Process shortestProcess = processesList.stream().filter(process -> process.getNextBurst() > -1).min(Comparator.comparing(Process::getNextBurst)).orElse(null);
    if (shortestProcess != null) {
      System.out.println("Scheduler selects process " + shortestProcess);
      processesList.remove(shortestProcess);
      return shortestProcess;
    }

    return null;
  }
}
