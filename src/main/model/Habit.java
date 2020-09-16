package model;


import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;
import java.time.ZonedDateTime;


public class Habit implements Saveable {

    public String name;
    public int dayCount;
    public int completionCount;
    public boolean completionStatus;
    public ZonedDateTime zonedDateTime;
    public ZonedDateTime dateCreated;
    public double completionRate;

    // REQUIRES: name has nonzero string length
    // EFFECTS: set habit to the name given, initialize count to be 0 and completion status to be
    //          false (uncompleted), store date created and completion rate as 0%

    // source for zonedDateTime: https://stackoverflow.com/questions/22463062/
    // how-to-parse-format-dates-with-localdatetime-java-8
    // https://howtodoinjava.com/java/date-time/zoneddatetime-parse/

    public Habit(String name) {
        this.name = name;
        dayCount = 1;
        completionStatus = false;
        completionCount = 0;
        zonedDateTime = ZonedDateTime.now();
        String timeString = zonedDateTime.toString();
        dateCreated = ZonedDateTime.parse(timeString);
        completionRate = 0; //completion rate in %
    }

    //REQUIRES: name has nonzero string length
    //EFFECTS: constructor for file retrieval
    public Habit(String name, boolean status,int completionCount, int dayCount, double rate, ZonedDateTime date) {
        this.name = name;
        completionStatus = status;
        this.dayCount = dayCount;
        this.completionCount = completionCount;
        completionRate = rate;
        dateCreated = date;
    }

    //EFFECTS: returns name of Habit
    public String getHabitName() {
        return name;

    }

    // EFFECTS: returns rate of completion of the habit
    public double getCompletionRate() {
        completionRate = (double) completionCount / dayCount * 100;
        return completionRate;
    }

    //EFFECTS: returns date that habit was created
    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    //MODIFIES: this
    //EFFECTS: marks habit as completed
    public void markComplete() {
        completionCount++;
        completionStatus = true;
    }

    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(name);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(completionStatus);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(completionCount);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(dayCount);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(completionRate);
        printWriter.print(Reader.DELIMITER);
        printWriter.println(dateCreated);

    }


}
