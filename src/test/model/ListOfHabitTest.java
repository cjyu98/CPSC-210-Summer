package model;

import exceptions.NotInList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ListOfHabitTest {

    Habit habit;
    Habit habitA;
    Habit habitB;
    Habit habitC;

    ListOfHabits emptyList;
    ListOfHabits list1;
    ListOfHabits list2;
    ListOfHabits list3;

    @BeforeEach
    public void runBefore() {
        habit = new Habit("test");
        habitA = new Habit("A");
        habitB =  new Habit ("B");
        habitC = new Habit("C");

        emptyList = new ListOfHabits();

        list1 = new ListOfHabits();
        list1.addHabit(habit);

        list2 = new ListOfHabits();
        list2.addHabit(habit);
        list2.addHabit(habitA);

        list3 = new ListOfHabits();
        list3.addHabit(habitC);
        list3.addHabit(habitA);
        list3.addHabit(habitB);
    }

    @Test
    public void addHabitTest() {
        assertTrue(emptyList.addHabit(habit));
        assertEquals(1,emptyList.getSize());
    }

    @Test
    public void removeHabitTest() {
        assertTrue(list3.removeHabit(habitB));
        assertEquals(2,list3.getSize());
    }

    @Test
    public void getHabitFromIndexTest() throws NotInList {
        try{
            list3.getHabitFromIndex(0);
        }
        catch (NotInList e) {
            fail();
        }
        assertEquals(habitC,list3.getHabitFromIndex(0));

        try {
            list3.getHabitFromIndex(3);
            fail();
        }
        catch (NotInList e) {
        }

    }

    @Test
    public void getHabitFromNameTest() throws NotInList {
        try{
            list3.getHabitFromName("A");
        }
        catch (NotInList e) {
            fail();
        }

        try {
            list3.getHabitFromName("D");
            fail();
        }
        catch (NotInList e) {
        }

    }

    @Test
    public void removeByNameTest() throws NotInList {
        assertTrue(list3.removeByName("A"));

        assertEquals(2,list3.getSize());
        assertEquals(habitC,list3.getHabitFromIndex(0));
        assertEquals(habitB,list3.getHabitFromIndex(1));


        assertFalse(list2.removeByName("D"));

    }

    @Test
    public void removeByIndex() throws NotInList {
        assertTrue(list3.removeByIndex(0));

        assertEquals(habitA,list3.getHabitFromIndex(0));
        assertEquals(habitB,list3.getHabitFromIndex(1));
    }

    @Test
    public void moveToPositionInListTest() throws NotInList {
        assertTrue(list3.moveToPositionInList(3,"C"));

        assertEquals(habitA,list3.getHabitFromIndex(0));
        assertEquals(habitB,list3.getHabitFromIndex(1));
        assertEquals(habitC,list3.getHabitFromIndex(2));

        assertFalse(list2.moveToPositionInList(2, "D"));
    }



    // testing comparators, using guide from https://www.baeldung.com/java-8-comparator-comparing

    @Test
    public void sortByNameTest() throws NotInList {
        list3.sortByName();

        assertEquals(habitA,list3.getHabitFromIndex(0));
        assertEquals(habitB,list3.getHabitFromIndex(1));
        assertEquals(habitC,list3.getHabitFromIndex(2));
    }


    @Test
    public void sortByDateCreatedTest() throws NotInList {
        Habit testHabit = new Habit("D");
        list1.addHabit(testHabit);
        list1.sortByDateCreated();

        assertEquals(habit,list1.getHabitFromIndex(0));
        assertEquals(testHabit,list1.getHabitFromIndex(1));
    }

    @Test
    public void sortByCompletionRate() throws NotInList {
        habitB.markComplete();
        list3.sortByCompletionRate();

        assertEquals(habitB,list3.getHabitFromIndex(0));
        assertEquals(habitC,list3.getHabitFromIndex(1));
        assertEquals(habitA,list3.getHabitFromIndex(2));
    }

}
