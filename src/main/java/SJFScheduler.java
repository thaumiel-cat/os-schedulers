import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Properties;

public class SJFScheduler extends AbstractScheduler {

  private HashMap<Process, Double> estimates;
  private ArrayList<Process> processesList;
  private double initialBurstEstimate;
  private double alpha;
  public SJFScheduler() {
    processesList = new ArrayList<>();
    estimates = new HashMap<>();
  }


  public void initialize(Properties parameters) {
    initialBurstEstimate = Double.parseDouble(parameters.getProperty("initialBurstEstimate"));
    alpha = Double.parseDouble(parameters.getProperty("alphaBurstEstimate"));
  }
  public void ready(Process process, boolean usedFullTimeQuantum) {

    if (estimates.containsKey(process)) {
      estimates.put(process, alpha * process.getRecentBurst() + (1-alpha) * estimates.get(process));
    } else {
      estimates.put(process, initialBurstEstimate);
    }
    processesList.add(process);

  }

  public Process schedule() {
    Process shortestProcess = processesList.stream().min(Comparator.comparingDouble(process -> estimates.get(process))).orElse(null);
    if (shortestProcess != null) {
      System.out.println("Scheduler selects process " + shortestProcess);
      processesList.remove(shortestProcess);
      return shortestProcess;
    }

    return null;
  }
}
