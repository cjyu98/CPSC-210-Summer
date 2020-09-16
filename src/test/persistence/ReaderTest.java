package persistence;

import exceptions.NotInList;
import model.Habit;
import model.ListOfHabits;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {

    @Test
    void testReaderFile() {
        try{
            ListOfHabits habitList = Reader.readHabitList(new File("./data/readerTestFile.txt"));
            Habit testHabit = habitList.getHabitFromIndex(0);
            assertEquals("test habit",testHabit.name);
            assertFalse(testHabit.completionStatus);
            assertEquals(0,testHabit.completionCount);
            assertEquals(1,testHabit.dayCount);
            assertEquals(0,testHabit.completionRate);

            ZonedDateTime testDate = ZonedDateTime.parse("2020-05-21T10:15:30+01:00[Europe/Paris]");
            assertEquals(testDate,testHabit.dateCreated);


            Habit testHabit2= habitList.getHabitFromIndex(1);
            assertEquals("test habit2",testHabit2.name);
            assertTrue(testHabit2.completionStatus);
            assertEquals(25,testHabit2.completionCount);
            assertEquals(1000,testHabit2.dayCount);
            assertEquals(2.5,testHabit2.completionRate);

            ZonedDateTime testDate2 = ZonedDateTime.parse("2020-08-02T20:22:05.198-07:00[America/Los_Angeles]");
            assertEquals(testDate2,testHabit2.dateCreated);

        } catch (IOException | NotInList e) {
            fail();
        }
    }

    @Test
    void testIOException() {
        try{
            ListOfHabits habitList = Reader.readHabitList(new File("./data/notHere.txt"));
        } catch(IOException e){
            // expected
        }
    }

    // dummy test to use constructor for autobot purposes as described as acceptable by TA in Piazza post 448
    @Test
    void testConstructor() {
        Reader reader = new Reader();
    }


}
