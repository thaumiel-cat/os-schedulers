# OS Schedulers

CPU scheduling algorithms written in Java:

| Class | Algorithm |
| --- | --- |
| `RRScheduler` | Round robin |
| `SJFScheduler` | Shortest job first, using exponentially averaged burst estimates |
| `IdealSJFScheduler` | Shortest job first, with known burst lengths |
| `FeedbackRRScheduler` | Three-level feedback round robin (quantum, 2x quantum, then run to completion) |

`AbstractScheduler`, `Process` and `Main` are small support classes: `Main` runs a
fixed set of example processes through a simple CPU-only simulation and prints the
scheduling decisions and turnaround times.

## Running

Needs a JDK (Java 8 or newer).

```bash
./run.sh RR 4
./run.sh SJF
./run.sh IdealSJF
./run.sh FeedbackRR 2
```

The second argument is the time quantum (default 4) for `RR` and `FeedbackRR`.
