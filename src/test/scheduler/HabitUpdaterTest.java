package scheduler;

import exceptions.NotInList;
import model.Habit;
import model.ListOfHabits;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.Reader;
import persistence.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class HabitUpdaterTest {
    private static final String FILE_NAME = "./data/habitUpdaterTestFile.txt";
    private Writer testWriter;
    private Habit testHabit;
    private Habit testHabit2;
    private HabitUpdater updater = new HabitUpdater();


    @BeforeEach
    void runBefore() throws FileNotFoundException {
        testWriter = new Writer(new File(FILE_NAME));
        testHabit = new Habit("test one");
        testHabit2 = new Habit("test two");

        testWriter.write(testHabit);
        testWriter.write(testHabit2);
        testWriter.close();
    }


    @Test
    void testDailyUpdate() {
        try {
            updater.dailyCountUpdate(FILE_NAME);
        } catch (IOException e) {
            fail();
        } catch (NotInList notInList) {
            fail();
        }

        try {
            ListOfHabits habitList = Reader.readHabitList(new File(FILE_NAME));
            Habit fileTestHabit = habitList.getHabitFromIndex(0);
            assertEquals(testHabit.name,fileTestHabit.name);
            assertFalse(fileTestHabit.completionStatus);
            assertEquals(testHabit.completionCount,fileTestHabit.completionCount);
            assertEquals(2,fileTestHabit.dayCount);
            assertEquals(testHabit.completionRate,fileTestHabit.completionRate);
            assertEquals(testHabit.dateCreated,fileTestHabit.dateCreated);

            Habit fileTestHabit2 = habitList.getHabitFromIndex(1);
            assertEquals(testHabit2.name,fileTestHabit2.name);
            assertFalse(fileTestHabit2.completionStatus);
            assertEquals(testHabit2.completionCount,fileTestHabit2.completionCount);
            assertEquals(2,fileTestHabit2.dayCount);
            assertEquals(testHabit2.completionRate,fileTestHabit2.completionRate);
            assertEquals(testHabit2.dateCreated,fileTestHabit2.dateCreated);

        } catch (IOException | NotInList e) {
            fail();
        }

    }

    @Test
    void testComputeDelay() {
        ZoneId zoneId = ZoneId.of("America/Los_Angeles");
        ZonedDateTime testDate = ZonedDateTime.of(2020,8,7,7,0,0,
                0,zoneId);
        ZonedDateTime testDate2 = ZonedDateTime.of(2020,8,7,8,0,0,
                0,zoneId);

        assertEquals(3600,updater.computeDelay(testDate,testDate2));

    }

    @Test
    void testMidnightNextDay() {
        ZoneId zoneId = ZoneId.of("America/Los_Angeles");
        ZonedDateTime testDate = ZonedDateTime.of(2020,8,7,7,0,0,
                0,zoneId);
        ZonedDateTime midnight = ZonedDateTime.of(2020,8,8,0,0,0,
                0,zoneId);

        assertEquals(midnight,updater.midnightNextDay(testDate));
    }

}
