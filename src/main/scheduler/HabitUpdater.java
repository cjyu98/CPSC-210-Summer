package scheduler;


// methods for scheduler to update daily count for list of habits at midnight every night

// designed from
// https://stackoverflow.com/questions/20387881/
// how-to-run-certain-task-every-day-at-a-particular-time-using-scheduledexecutorse
// https://mkyong.com/java/java-scheduledexecutorservice-examples/
// https://www.baeldung.com/java-executor-service-tutorial

import exceptions.NotInList;
import model.Habit;
import model.ListOfHabits;
import persistence.Reader;
import persistence.Writer;


import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;


public class HabitUpdater {

    //MODIFIES: ListOfHabits, habits.txt
    //EFFECTS: reads file, updates daily count of each habit, write updated list to file
    public void dailyCountUpdate(String fileName) throws IOException, NotInList {
        ListOfHabits habitList = Reader.readHabitList(new File(fileName));
        int habitCount = 1;

        while (habitCount <= habitList.getSize()) {
            Habit habit = habitList.getHabitFromIndex(habitCount - 1);
            habit.dayCount++;
            habit.completionStatus = false;
            habitCount++;
        }

        Writer writer = new Writer(new File(fileName));
        habitCount = 1;

        while (habitCount <= habitList.getSize()) {
            writer.write(habitList.getHabitFromIndex(habitCount - 1));
            habitCount++;
        }

        writer.close();

    }

    //EFFECTS: calculate delay from now to midnight needed for scheduler
    public long computeDelay(ZonedDateTime targetTime1, ZonedDateTime targetTime2) {

        Duration duration = Duration.between(targetTime1, targetTime2);

        return duration.getSeconds();
    }

    //EFFECTS: sets given time to midnight of the next day
    public ZonedDateTime midnightNextDay(ZonedDateTime current) {
        ZonedDateTime midnight = current.plusDays(1);
        midnight = midnight.withHour(0);
        midnight = midnight.withMinute(0);
        midnight = midnight.withSecond(0);
        midnight = midnight.withNano(0);

        return midnight;
    }



}


