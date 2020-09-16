package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Writer {
    private PrintWriter printWriter;

    //EFFECTS: constructs a printWriter for file writing
    public Writer(File file) throws FileNotFoundException {
        printWriter = new PrintWriter(file);
    }

    //EFFECTS: write all data to file
    public void write(Saveable saveable) {
        saveable.save(printWriter);
    }

    //EFFECTS: complete writing to file and close file
    public void close() {
        printWriter.close();
    }


}
