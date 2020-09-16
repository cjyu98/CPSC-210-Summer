package persistence;

import exceptions.NotInList;
import model.Habit;
import model.ListOfHabits;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class WriterTest {

    private static final String FILE_NAME = "./data/writerTestFile.txt";
    private Writer testWriter;
    private Habit testHabit;
    private Habit testHabit2;


    @BeforeEach
    void runBefore() throws FileNotFoundException {
        testWriter = new Writer(new File(FILE_NAME));
        testHabit = new Habit("test habit");
        testHabit2 = new Habit("test habit2");

    }

    @Test
    void testWriteHabits() {
        // write to file
        testWriter.write(testHabit);
        testWriter.write(testHabit2);
        testWriter.close();

        //test if correctly written
        try {
           ListOfHabits habitList = Reader.readHabitList(new File(FILE_NAME));
            Habit fileTestHabit = habitList.getHabitFromIndex(0);
            assertEquals(testHabit.name,fileTestHabit.name);
            assertEquals(testHabit.completionStatus,fileTestHabit.completionStatus);
            assertEquals(testHabit.completionCount,fileTestHabit.completionCount);
            assertEquals(testHabit.dayCount,fileTestHabit.dayCount);
            assertEquals(testHabit.completionRate,fileTestHabit.completionRate);
            assertEquals(testHabit.dateCreated,fileTestHabit.dateCreated);

            Habit fileTestHabit2 = habitList.getHabitFromIndex(1);
            assertEquals(testHabit2.name,fileTestHabit2.name);
            assertEquals(testHabit2.completionStatus,fileTestHabit2.completionStatus);
            assertEquals(testHabit2.completionCount,fileTestHabit2.completionCount);
            assertEquals(testHabit2.dayCount,fileTestHabit2.dayCount);
            assertEquals(testHabit2.completionRate,fileTestHabit2.completionRate);
            assertEquals(testHabit2.dateCreated,fileTestHabit2.dateCreated);

        } catch (IOException | NotInList e) {
            fail();
        }

    }




}
