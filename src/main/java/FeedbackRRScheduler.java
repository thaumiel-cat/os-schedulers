import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;

public class FeedbackRRScheduler extends AbstractScheduler {

  private int timeQuantum;
  private ArrayList<LinkedList<Process>> processLevels;
  private HashMap<Process, Integer> processLevelMap;
  private int currentLevel;

  public FeedbackRRScheduler() {
    processLevels = new ArrayList<>();
    processLevelMap = new HashMap<>();
    LinkedList<Process> levelZero = new LinkedList<Process>();
    LinkedList<Process> levelOne = new LinkedList<Process>();
    LinkedList<Process> levelTwo = new LinkedList<Process>();
    processLevels.add(levelZero);
    processLevels.add(levelOne);
    processLevels.add(levelTwo);
    currentLevel = 0;
  }
  public void ready(Process process, boolean usedFullTimeQuantum) {
    int level;
    if (!processLevelMap.containsKey(process)) {
      level = 0;
      processLevelMap.put(process, level);
    } else {
      level = processLevelMap.get(process);
      if (usedFullTimeQuantum && level < processLevels.size() - 1) {
        level++;
        processLevelMap.put(process, level);
      }
    }
    processLevels.get(level).offer(process);
  }

  public Process schedule() {
    for (int i = 0; i < processLevels.size(); i++) {
      if  (!processLevels.get(i).isEmpty()) {
        Process selectedProcess = processLevels.get(i).poll();
        System.out.println("Scheduler selects process " + selectedProcess);
        currentLevel =  i;
        return selectedProcess;
      }
    }
    return null;
  }

  public void initialize(Properties parameters) {
    timeQuantum = Integer.parseInt(parameters.getProperty("timeQuantum"));
  }

  public int  getTimeQuantum() {
    switch (currentLevel) {
      case 0:
        return timeQuantum;
      case 1:
        return timeQuantum * 2;
      case 2:
        return -1;
    }
      return -1;
  }

  public boolean isPreemptive() {
    return true;
  }
}
