import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

/**
 * Small CPU-only simulation used to demonstrate the schedulers.
 *
 * Usage: java Main [RR|SJF|IdealSJF|FeedbackRR] [timeQuantum]
 */
public class Main {

  public static void main(String[] args) {
    String name = args.length > 0 ? args[0] : "RR";
    Properties parameters = new Properties();
    parameters.setProperty("timeQuantum", args.length > 1 ? args[1] : "4");
    parameters.setProperty("initialBurstEstimate", "5");
    parameters.setProperty("alphaBurstEstimate", "0.5");

    AbstractScheduler scheduler = create(name);
    scheduler.initialize(parameters);

    List<Process> processes = new ArrayList<>();
    processes.add(new Process(0, 0, 8, 3, 6));
    processes.add(new Process(1, 1, 2, 2));
    processes.add(new Process(2, 3, 12));
    processes.add(new Process(3, 5, 1, 4, 1));
    processes.add(new Process(4, 9, 5));

    System.out.println("Running " + scheduler.getClass().getSimpleName());
    simulate(scheduler, processes);
  }

  private static AbstractScheduler create(String name) {
    switch (name.toLowerCase()) {
      case "rr":         return new RRScheduler();
      case "sjf":        return new SJFScheduler();
      case "idealsjf":   return new IdealSJFScheduler();
      case "feedbackrr": return new FeedbackRRScheduler();
      default:
        throw new IllegalArgumentException("Unknown scheduler: " + name);
    }
  }

  /**
   * Runs each scheduled process for its time quantum (or the rest of its burst).
   * A process that finishes a burst goes straight back to ready; I/O is not modelled.
   */
  static void simulate(AbstractScheduler scheduler, List<Process> processes) {
    List<Process> pending = new ArrayList<>(processes);
    pending.sort(Comparator.comparingInt(Process::getArrivalTime));
    int time = 0;
    int finished = 0;

    while (finished < processes.size()) {
      time = admitArrivals(scheduler, pending, time);
      Process process = scheduler.schedule();
      if (process == null) {
        time = pending.get(0).getArrivalTime();
        continue;
      }

      int quantum = scheduler.getTimeQuantum();
      int slice = quantum > 0 ? quantum : process.getRemainingInBurst();
      int start = time;
      time += process.run(slice);
      System.out.println("  t=" + start + ".." + time + " ran process " + process);
      admitArrivals(scheduler, pending, time);

      if (process.isFinished()) {
        process.setFinishTime(time);
        finished++;
      } else {
        boolean usedFullQuantum = quantum > 0 && !process.burstJustCompleted();
        scheduler.ready(process, usedFullQuantum);
      }
    }

    System.out.println("\nid\tturnaround");
    int total = 0;
    for (Process p : processes) {
      System.out.println(p.getId() + "\t" + p.getTurnaroundTime());
      total += p.getTurnaroundTime();
    }
    System.out.printf("Average turnaround: %.2f%n", (double) total / processes.size());
  }

  private static int admitArrivals(AbstractScheduler scheduler, List<Process> pending, int time) {
    while (!pending.isEmpty() && pending.get(0).getArrivalTime() <= time) {
      scheduler.ready(pending.remove(0), false);
    }
    return time;
  }
}
