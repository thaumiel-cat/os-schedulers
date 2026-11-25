/**
 * A process made of a sequence of CPU bursts.
 */
public class Process {

  private final int id;
  private final int arrivalTime;
  private final int[] bursts;
  private int burstIndex = 0;      // burst currently due to run
  private int remaining;           // time left in that burst
  private int recentBurst = -1;    // length of the last completed burst
  private int finishTime = -1;

  public Process(int id, int arrivalTime, int... bursts) {
    this.id = id;
    this.arrivalTime = arrivalTime;
    this.bursts = bursts;
    this.remaining = bursts.length > 0 ? bursts[0] : 0;
  }

  public int getId() {
    return id;
  }

  public int getArrivalTime() {
    return arrivalTime;
  }

  /** Length of the most recently completed CPU burst, or -1 if none yet. */
  public int getRecentBurst() {
    return recentBurst;
  }

  /** Length of the next CPU burst, or -1 if the process has no more. */
  public int getNextBurst() {
    return isFinished() ? -1 : bursts[burstIndex];
  }

  public int getRemainingInBurst() {
    return remaining;
  }

  public boolean isFinished() {
    return burstIndex >= bursts.length;
  }

  /** Runs the current burst for up to {@code time} units; returns time actually used. */
  public int run(int time) {
    int used = Math.min(time, remaining);
    remaining -= used;
    if (remaining == 0) {
      recentBurst = bursts[burstIndex];
      burstIndex++;
      remaining = isFinished() ? 0 : bursts[burstIndex];
    }
    return used;
  }

  public boolean burstJustCompleted() {
    return isFinished() || remaining == bursts[burstIndex];
  }

  public void setFinishTime(int finishTime) {
    this.finishTime = finishTime;
  }

  public int getTurnaroundTime() {
    return finishTime - arrivalTime;
  }

  @Override
  public String toString() {
    return "" + id;
  }
}
