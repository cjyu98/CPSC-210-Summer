package ui;

import exceptions.NotInList;
import model.Habit;
import model.ListOfHabits;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import persistence.Reader;
import persistence.Writer;
import ui.gui.DisplayList;

import javax.swing.*;
import java.util.Scanner;

// heavily styled around the TellerApp class given in CPSC 210

public class HabitApp {
    private static final String FILE_NAME = "./data/habits.txt";
    private ListOfHabits list;
    private Scanner input;

    //EFFECTS: runs the habit application
    public HabitApp() throws IOException, NotInList {
        runHabit();
    }

    //MODIFIES: this
    //EFFECTS: process user input
    private void runHabit() throws IOException, NotInList {
        boolean keepRunning = true;
        String command = null;
        input = new Scanner(System.in);

        loadHabitList();
        createAndShowGUI();


        while (keepRunning) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepRunning = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nHave a nice day!");
    }



    //MODIFIES: this
    //EFFECTS: load habit list from before if file exists, if not initialize an empty list
    private void loadHabitList() {
        try {
            ListOfHabits loadList = Reader.readHabitList(new File(FILE_NAME));
            this.list = loadList;
        } catch (IOException e) {
            initialize();
        }
    }

    //EFFECTS: initialize an empty habit list
    private void initialize() {
        list = new ListOfHabits();
    }

    //MODIFIES: this
    //EFFECTS: saves list of habits to file
    private void saveHabitList() {
        try {
            Writer writer = new Writer(new File(FILE_NAME));
            int habitCount = 1;

            while (habitCount <= list.getSize()) {
                writer.write(list.getHabitFromIndex(habitCount - 1));
                habitCount++;
            }

            writer.close();
            System.out.println("Habits saved!");

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (NotInList notInList) {
            notInList.printStackTrace();
        }


    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add habit");
        System.out.println("\tb -> remove habit");
        System.out.println("\tc -> mark habit as complete");
        System.out.println("\td -> display list of habits");
        System.out.println("\te -> prioritize habit in desired spot");
        System.out.println("\tf -> sort list ");
        System.out.println("\ti -> check habit completion status");
        System.out.println("\ts -> save habit list");
        System.out.println("\tq -> quit");
    }



    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addHabitToList();
        } else if (command.equals("b")) {
            removeHabitFromList();
        } else if (command.equals("c")) {
            markHabitCompleted();
        } else if (command.equals("d")) {
            displayList();
        } else if (command.equals("e")) {
            prioritizeHabit();
        } else if (command.equals("f")) {
            sortSelection();
        } else if (command.equals("i")) {
            checkCompletionStatus();
        } else if (command.equals("s")) {
            saveHabitList();
        } else {
            System.out.println("Selection not valid...");
        }
    }



    //MODIFIES: this
    //EFFECTS: add habit to end of list
    private void addHabitToList() {
        System.out.println("Enter habit name to be added:");
        input.nextLine();

        String habitName = input.nextLine();

        Habit habit = new Habit(habitName);
        list.addHabit(habit);

        System.out.println("Added!");
    }

    //MODIFIES: this
    //EFFECTS: remove habit from list
    private void removeHabitFromList() {
        System.out.println("Enter habit name to be removed:");
        input.nextLine();
        String habitName = input.nextLine();

        if (list.removeByName(habitName)) {
            System.out.println("Removed from list.");
        } else {
            System.out.println("Habit could not be found.");
        }
    }

    //MODIFIES: this
    //EFFECTS: mark given habit as completed
    private void markHabitCompleted() {
        System.out.println("Enter habit name to be marked completed:");
        input.nextLine();
        String habitName = input.nextLine();
        try {
            Habit habit = list.getHabitFromName(habitName);
            habit.markComplete();
            System.out.println("Habit marked completed.");
        } catch (NotInList notInList) {
            System.out.println("Habit cannot be found.");
        }
    }

    //EFFECTS: prints out full list of habits
    private void displayList() {
        int index = 0;
        while (index + 1 <= list.getSize()) {
            Habit habit = null;
            try {
                habit = list.getHabitFromIndex(index);
            } catch (NotInList notInList) {
                notInList.printStackTrace();
            }
            System.out.println((index + 1) + "." + habit.getHabitName());
            index++;
        }
    }


    //MODIFIES:this
    //EFFECTS: prioritize habit to desired slot
    private void prioritizeHabit() {
        System.out.println("Enter habit name to be prioritized:");
        input.nextLine();
        String habitName = input.nextLine();
        try {
            Habit habit = list.getHabitFromName(habitName);
        } catch (NotInList notInList) {
            System.out.println("Habit cannot be found.");
            return;
        }


        System.out.println("Enter spot for habit to be moved");
        int num = input.nextInt();

        if (num > list.getSize()) {
            System.out.println("Invalid slot number.");
        } else {
            list.moveToPositionInList(num, habitName);
            System.out.println("Moved.");
        }

    }

    //MODIFIES: this
    //EFFECTS: selection which sort function for sorting
    private void sortSelection() {
        String selection = "";

        while (!(selection.equalsIgnoreCase("n") || selection.equalsIgnoreCase("d")
                || selection.equalsIgnoreCase("c"))) {
            System.out.println("n for sort by name");
            System.out.println("d for sort by date created");
            System.out.println("c for sort by completion rate");

            selection = input.next();
            selection.toLowerCase();
        }

        if (selection.equalsIgnoreCase("n")) {
            sortListByName();
        } else if (selection.equalsIgnoreCase("d")) {
            sortListByDateCreated();
        } else {
            sortListByCompletionRate();
        }

    }

    //MODIFIES: this
    //EFFECTS: sort list by name
    private void sortListByName() {
        list.sortByName();
        System.out.println("Sorted!");
        displayList();
    }

    //MODIFIES: this
    //EFFECTS: sort list by date created
    private void sortListByDateCreated() {
        list.sortByDateCreated();
        System.out.println("Sorted!");
        displayList();
    }

    //MODIFIES: this
    //EFFECTS: sort list by completion rate
    private void sortListByCompletionRate() {
        list.sortByCompletionRate();
        System.out.println("Sorted!");
        displayList();
    }

    //EFFECTS: returns completion status of habit
    private void checkCompletionStatus() {
        System.out.println("Enter habit name to be checked:");
        input.nextLine();
        String habitName = input.nextLine();

        try {
            Habit habit = list.getHabitFromName(habitName);
            if (habit.completionStatus) {
                System.out.println("Completed");
            } else {
                System.out.println("Uncompleted");
            }
        } catch (NotInList notInList) {
            System.out.println("Habit cannot be found.");
        }

    }


    //MODIFIES: this
    //EFFECTS: creates the Jframe for the GUI
    private void createAndShowGUI() throws IOException, NotInList {
        //Create and set up window
        JFrame frame = new JFrame("Habit List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up content pane
        DisplayList displayList = new DisplayList();
        JComponent newContentPane = displayList.createPanel();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.setPreferredSize(new Dimension(1000,1000));


        //Display window
        frame.pack();
        frame.setVisible(true);
    }

}
