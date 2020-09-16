package ui;


import exceptions.NotInList;
import scheduler.HabitUpdater;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


// task scheduler designed from
// https://stackoverflow.com/questions/20387881/
// how-to-run-certain-task-every-day-at-a-particular-time-using-scheduledexecutorse
// https://mkyong.com/java/java-scheduledexecutorservice-examples/
// https://www.baeldung.com/java-executor-service-tutorial

public class Main {
    public static void main(String[] args) throws IOException, NotInList {
        new HabitApp();
        // set up scheduler for midnight updates to habits
        final ScheduledExecutorService dailyUpdate = Executors.newScheduledThreadPool(1);
        HabitUpdater midnightUpdate = new HabitUpdater();
        // set up date calculations for delay
        ZonedDateTime midnight = midnightUpdate.midnightNextDay(ZonedDateTime.now());
        // definite runnable command to run tasks desired
        Runnable taskWrap = new Runnable() {
            @Override
            public void run() {
                try {
                    midnightUpdate.dailyCountUpdate("./data/habits.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NotInList notInList) {
                    notInList.printStackTrace();
                }
            }
        };
        // execute the scheduler
        dailyUpdate.schedule(taskWrap,midnightUpdate.computeDelay(ZonedDateTime.now(), midnight), TimeUnit.SECONDS);
        // shut down the scheduler
        dailyUpdate.shutdown();
        try {
            dailyUpdate.awaitTermination(1,TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
