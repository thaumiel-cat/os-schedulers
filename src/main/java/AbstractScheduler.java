import java.util.Properties;

/**
 * Minimal base class the schedulers plug into.
 */
public abstract class AbstractScheduler {

  /** Reads any settings the scheduler needs (e.g. timeQuantum). */
  public void initialize(Properties parameters) {
  }

  /** Puts a process in the ready queue. */
  public abstract void ready(Process process, boolean usedFullTimeQuantum);

  /** Takes the next process to run out of the ready queue, or null if empty. */
  public abstract Process schedule();

  /** Time slice for the selected process, or -1 to run until the burst ends. */
  public int getTimeQuantum() {
    return -1;
  }

  public boolean isPreemptive() {
    return false;
  }
}
