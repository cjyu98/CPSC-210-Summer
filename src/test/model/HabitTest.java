package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HabitTest {

    Habit habit1;

    @BeforeEach
    public void runBefore() {
        habit1 = new Habit("test");
    }

    @Test
    public void getHabitNameTest() {
        assertEquals("test", habit1.getHabitName());

    }

    @Test
    public void completionRateTest() {
        assertEquals(0,habit1.getCompletionRate());
        habit1.markComplete();
        assertEquals(100, habit1.getCompletionRate());
    }

    @Test
    public void getDateCreatedTest() {
        assertEquals(habit1.dateCreated, habit1.getDateCreated());
    }

    @Test
    public void markCompleteTest() {
        habit1.markComplete();

        assertEquals(1, habit1.completionCount);
        assertTrue(habit1.completionStatus);
    }
}