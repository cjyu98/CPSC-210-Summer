package persistence;

import model.Habit;
import model.ListOfHabits;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// structure heavily structured around Reader from TellerApp


public class Reader {

    public static final String DELIMITER = ",";

    //EFFECTS: reads file contents and returns habit list
    public static ListOfHabits readHabitList(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }



    //EFFECTS: reads all contents of file, returns each line in file as a string in a list
    public static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    //EFFECTS: parse string of each line via delimiter
    private static ArrayList<String> splitLine(String line) {
        String[] split = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(split));
    }


    //EFFECTS: convert strings from file content into habit list
    private static ListOfHabits parseContent(List<String> fileContent) {
        ListOfHabits habitList = new ListOfHabits();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitLine(line);
            habitList.addHabit(parseHabit(lineComponents));
        }

        return habitList;
    }



    // EFFECTS: pieces the bits of string together into a habit
    private static Habit parseHabit(List<String> components) {
        String habitName = components.get(0);
        boolean completionStatus = Boolean.parseBoolean(components.get(1));
        int completionCount = Integer.parseInt(components.get(2));
        int dayCount = Integer.parseInt(components.get(3));
        double completionRate = Double.parseDouble(components.get(4));
        ZonedDateTime dateCreated = ZonedDateTime.parse(components.get(5));

        return new Habit(habitName,completionStatus,completionCount,dayCount,completionRate,dateCreated);
    }



}
