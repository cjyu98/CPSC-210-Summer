package model;

import exceptions.NotInList;

import java.util.ArrayList;
import java.util.Comparator;

public class ListOfHabits {

    ArrayList habitList;

    //EFFECTS: creates an empty list of habits
    public ListOfHabits() {
        this.habitList = new ArrayList();
    }


    //MODIFIES: this
    //EFFECTS: add habit to the end of the list, returns true
    public boolean addHabit(Habit habit) {
        habitList.add(habit);
        return true;
    }


    //REQUIRES: habit to be in the list already
    //MODIFIES: this
    //EFFECTS: removes habit from list, returns true
    public boolean removeHabit(Habit habit) {
        habitList.remove(habit);
        return true;
    }

    //EFFECTS: returns size of list
    public int getSize() {
        return habitList.size();
    }

    //REQUIRES: index given must be within list
    //EFFECTS: returns habit from given index of list
    public Habit getHabitFromIndex(int index) throws NotInList {
        if (index + 1 <= habitList.size()) {
            return (Habit) habitList.get(index);
        }
        throw new NotInList();
    }

    //EFFECTS: checks whether list contains habit, returns index of habit if in list, returns -1 if not
    public int checkHabitInList(String name) {
        int arrayCount = 1;
        int index = 0;
        while (arrayCount <= habitList.size()) {
            Habit habit = (Habit) habitList.get(index);
            String habitName = habit.getHabitName();
            if (habitName.equalsIgnoreCase(name)) {
                return index;
            }
            arrayCount++;
            index++;
        }
        return -1;
    }



    //REQUIRES: habit to be within list
    //EFFECTS: find habit from given name
    public Habit getHabitFromName(String name) throws NotInList {
        int index = checkHabitInList(name);

        if (index != -1) {
            return (Habit) habitList.get(index);
        }

        throw new NotInList();
    }

    //MODIFIES: this
    //EFFECTS: takes name of habit and removes from list, returns true if successful
    //         returns false if habit could not be found in list
    public boolean removeByName(String name) {
        int index = checkHabitInList(name);

        if (index != -1) {
            habitList.remove(index);
            return true;
        }
        return false;
    }

    public boolean removeByIndex(int index) {
        habitList.remove(index);
        return true;
    }

    //REQUIRES: num to be within the size of the list
    //MODIFIES: this
    //EFFECTS: given placement number & habit name, move habit to placement desired, return true
    //         return false if habit not in list
    public boolean moveToPositionInList(int num, String name) {
        int index = checkHabitInList(name);

        if (index != -1) {
            Habit habit = (Habit) habitList.get(index);
            habitList.remove(index);
            habitList.add(num - 1,habit);
            return true;
        }

        return false;

    }

    // used https://www.baeldung.com/java-comparator-comparable for following sorting methods

    //EFFECTS: sort list by habit name, A-Z
    public void sortByName() {
        habitList.sort(Comparator.comparing(Habit::getHabitName));
    }

    //EFFECTS: sort list by date created, earliest to latest
    public void sortByDateCreated() {
        habitList.sort(Comparator.comparing(Habit::getDateCreated));
    }

    //EFFECTS: sort list by completion rate, highest to lowest
    public void sortByCompletionRate() {
        Comparator<Habit> completionRateComparator = Comparator.comparing(Habit::getCompletionRate);
        habitList.sort(completionRateComparator.reversed());
    }



}

