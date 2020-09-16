package ui.gui;

import exceptions.NotInList;
import model.Habit;
import model.ListOfHabits;
import persistence.Reader;
import persistence.Writer;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static com.sun.javafx.fxml.expression.Expression.add;

// designed largely after ListDemo tutorial
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java

// GUI to display list
public class DisplayList extends JPanel implements ListSelectionListener {
    ListOfHabits habitList;
    DefaultListModel listModel;
    JList list;

    private static final String addString = "Add";
    private static final String removeString = "Remove";
    private static final String saveString = "Save";
    private static final String FILE_NAME = "./data/habits.txt";
    public JButton removeButton;
    public JButton addButton;
    public JButton saveButton;
    public JTextField habitName;
    public Font listFont = new Font("list font",Font.PLAIN,40);
    public Font buttonFont = new Font("button font", Font.PLAIN,20);
    public Font textFont = new Font("text font", Font.PLAIN,20);
    public JScrollPane listScrollPane;

    // constructor
    public DisplayList() throws IOException, NotInList {
        // add fields, set up border layout
        super(new BorderLayout());
        listModel = new DefaultListModel();
        list = new JList(listModel);

        //load initial values
        loadList(); //habitList is defined in this method

        // set up parameters for Jlist + scroll bar
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        list.setFont(listFont);
        listScrollPane = new JScrollPane(list);
        initializeFields();

    }


    //EFFECTS: initializes buttons and text field for GUI
    private void initializeFields() {
        // initialize add button
        addButton = initializeButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);

        // initialize remove button
        removeButton = initializeButton(removeString);
        removeButton.addActionListener(new RemoveListener());

        //initialize save button
        saveButton = initializeButton(saveString);
        saveButton.addActionListener(new SaveListener());

        // initialize text bar for entering habit name
        habitName = new JTextField(10);
        habitName.addActionListener(addListener);
        habitName.getDocument().addDocumentListener(addListener);
    }

    //EFFECTS: initialize button with name and command string
    private JButton initializeButton(String name) {
        JButton button = new JButton(name);
        button.setActionCommand(name);
        return button;
    }

    //EFFECTS: create panel for the buttons and text field
    public JPanel createPanel() {
        //Create a panel that uses BoxLayout to hold buttons and text field
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(removeButton);
        removeButton.setPreferredSize(new Dimension(200,40));
        removeButton.setFont(buttonFont);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(habitName);
        habitName.setPreferredSize(new Dimension(600,40));
        habitName.setFont(textFont);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(addButton);
        addButton.setPreferredSize(new Dimension(200,40));
        addButton.setFont(buttonFont);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(saveButton);
        saveButton.setPreferredSize(new Dimension(200,40));
        saveButton.setFont(buttonFont);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        buttonPanel.setPreferredSize(new Dimension(1000,100));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);
        return this;
    }

    //MODIFIES: this
    //EFFECTS: disable remove button if nothing is in the list
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {

            //nothing in list, disable button
            if (list.getSelectedIndex() == -1) {
                removeButton.setEnabled(false);
            } else {
                removeButton.setEnabled(true);
            }
        }

    }

    //MODIFIES: this
    //EFFECTS: loads habit list automatically when application is running
    public void loadList() throws IOException, NotInList {
        habitList = Reader.readHabitList(new File(FILE_NAME));
        int index = 1;
        while (index <= habitList.getSize()) {
            String name = habitList.getHabitFromIndex(index - 1).getHabitName();
            listModel.addElement(name);
            index++;
        }
    }

    // designed largely from this source
    // http://suavesnippets.blogspot.com/2011/06/add-sound-on-jbutton-click-in-java.html

    //EFFECTS: plays clicking sound
    public void clickSound() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File soundFile = new File("./data/click.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }


    // listener class for remove button
    class RemoveListener implements ActionListener {

        //EFFECTS: removes element based on actions of user
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                clickSound();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            int index = list.getSelectedIndex();
            listModel.remove(index);
            habitList.removeByIndex(index);

            // empty list, disable remove button
            if (listModel.size() == 0) {
                removeButton.setEnabled(false);
            } else {
                // for selecting the last item on list to ensure selection returns to previous item
                if (index == listModel.size()) {
                    index--;
                }

                // reselect the habit above after removal
                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }

        }
    }

    // listener class for add button and text field
    class AddListener implements ActionListener, DocumentListener {
        JButton addButton;
        String name;
        Habit habit;

        // constructor
        public AddListener(JButton button) {
            addButton = button;
        }

        //EFFECTS: plays clicking sound when add button is clicked, checks for repetitive habits added,
        //         adds new habit to list if conditions satisfied by user
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                clickSound();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            name = habitName.getText();

            // to avoid repeats
            if (name.equals("") || listModel.contains(name)) {
                Toolkit.getDefaultToolkit().beep();
                habitName.requestFocusInWindow();
                habitName.selectAll();
                return;
            }


            // add to list model and ListOfHabits
            listModel.addElement(name);

            habit = new Habit(name);
            habitList.addHabit(habit);

            //Reset text field
            habitName.requestFocusInWindow();
            habitName.setText("");

            //Select the newly added habit
            int index = habitList.getSize() - 1;
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);


        }

        //EFFECTS: sensing input in text bar
        @Override
        public void insertUpdate(DocumentEvent e) {
            addButton.setEnabled(true);
        }

        //EFFECTS: handles empty text bar
        @Override
        public void removeUpdate(DocumentEvent e) {
            emptyTextBar(e);
        }

        //EFFECTS: check for any changed inputs from user
        @Override
        public void changedUpdate(DocumentEvent e) {
            if (!emptyTextBar(e)) {
                addButton.setEnabled(true);
            }
        }

        //EFFECTS: enabling add button if text field is not empty
        public boolean emptyTextBar(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                addButton.setEnabled(false);
                return true;
            } else {
                return false;
            }
        }


    }

    // listener class for save button
    class SaveListener implements ActionListener {

        //EFFECTS: saves list of habits to text file
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                clickSound();
                Writer writer = new Writer(new File(FILE_NAME));
                int habitCount = 1;

                while (habitCount <= habitList.getSize()) {
                    writer.write(habitList.getHabitFromIndex(habitCount - 1));
                    habitCount++;
                }

                writer.close();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }



}