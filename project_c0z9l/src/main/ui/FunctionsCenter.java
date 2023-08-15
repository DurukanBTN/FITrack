package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.functions.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

// The center class that is used to call all the other "functions" classes depending on the user input
public class FunctionsCenter implements ActionListener {
    private static final String JSON_STORE = "./data/user.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private User user;
    private User pendingLoadUser;

    private Scanner scanner = new Scanner(System.in);
    private ViewChangeInformation viewChangeInformation;
//    private CalculateBMI calculateBMI;
//    private ExercisesFunction exercisesFunction;
//    private Calendar calendar;
//    private CalorieInformation calorieInformation;

    private JFrame jframeSavedData;
    private JFrame jframeNoSavedData;
    private JFrame jframeShowFunctions;
    private JFrame jframeVisualWelcome;

    private JPanel namePanel;
    private JPanel weightPanel;
    private JPanel heightPanel;
    private JPanel caloriePanel;

    private JLabel nameLabel;
    private JLabel weightLabel;
    private JLabel heightLabel;
    private JLabel calorieLabel;

    private JTextField nameField;
    private JTextField weightField;
    private JTextField heightField;
    private JTextField calorieField;


    // EFFECTS: Constructs a new FunctionsCenter with the given user and new instances of all the classes in the
    // functions folder
    public FunctionsCenter(User user) {
        initializeFields(user);
    }

    private void initializeFields(User user) {
        this.user = user;
        viewChangeInformation = new ViewChangeInformation(user, this);
//        calculateBMI = new CalculateBMI(user, this);
//        exercisesFunction = new ExercisesFunction(user, this);
//        calendar = new Calendar(user, this);
        // calorieInformation = new CalorieInformation(user, this);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        initializeJFrameSavedData();
        initializeJFrameNoSavedData();
        initializeJFrameShowFunctions();
        initializeVisualWelcome();
        jframeVisualWelcome.setVisible(true);
    }


    // EFFECTS: checks all the actionPerformed methods to see which one's action to execute
    public void actionPerformed(ActionEvent e) {
        actionPerformedSavedData(e);
        actionPerformedNoSavedData(e);
        actionPerformedChooseFunction(e);
        actionPerformedViewChangeInformation(e);
        actionPerformedWelcomeVisual(e);
    }

    // MODIFIES: this
    // EFFECTS: starts the program
    private void actionPerformedWelcomeVisual(ActionEvent e) {
        if (e.getActionCommand().equals("continue")) {
            jframeVisualWelcome.setVisible(false);
            lookForSavedData();
        }
    }

    // MODIFIES: this, user
    // EFFECTS: sets the user's 4 main stats according to the user input, then moves on to the view page
    private void actionPerformedNoSavedData(ActionEvent e) {
        if (e.getActionCommand().equals("enter")) {
            user.setName(nameField.getText());
            jframeNoSavedData.remove(namePanel);
            try {
                user.setWeight(validIntWithoutString(20, 500, Integer.parseInt(weightField.getText())));
                jframeNoSavedData.remove(weightPanel);
                user.setHeight(validIntWithoutString(20, 400, Integer.parseInt(heightField.getText())));
                jframeNoSavedData.remove(heightPanel);
                user.setCalorie(validIntWithoutString(0, 20000, Integer.parseInt(calorieField.getText())));
                jframeNoSavedData.remove(caloriePanel);
                jframeNoSavedData.setVisible(false);
                jframeShowFunctions.setVisible(true);
            } catch (NumberFormatException exception) {
                //
            }
        }
    }

    // MODIFIES: this, viewChangeInformation
    // EFFECTS: moves the program to the chosen viewchangeinformation function's page
    private void actionPerformedViewChangeInformation(ActionEvent e) {
        if (e.getActionCommand().equals("goBack")) {
            viewChangeInformation.getJFrameViewFunction().setVisible(false);
            jframeShowFunctions.setVisible(true);
        } else if (e.getActionCommand().equals("addInformation")) {
            viewChangeInformation.getJFrameViewFunction().setVisible(false);
            viewChangeInformation.initializeJFrameAddInformationFunction();
        } else if (e.getActionCommand().equals("removeInformation")) {
            if (user.getCustomStatistics().size() != 0) {
                viewChangeInformation.getJFrameViewFunction().setVisible(false);
                viewChangeInformation.initializeJFrameRemoveFunction();
            }
        } else if (e.getActionCommand().equals("changeInformation")) {
            if (user.getCustomStatistics().size() != 0) {
                viewChangeInformation.getJFrameViewFunction().setVisible(false);
                viewChangeInformation.initializeJFrameChangeFunction();
            }
        }
    }

    // MODIFIES: this, viewChangeInformation
    // EFFECTS: moves the program to the chosen function's page
    private void actionPerformedChooseFunction(ActionEvent e) {
        if (e.getActionCommand().equals("view")) {
            jframeShowFunctions.setVisible(false);
            viewChangeInformation.initializeJFrameViewFunction();
        } else if (e.getActionCommand().equals("save")) {
            saveUser();
        } else if (e.getActionCommand().equals("load")) {
            loadUser();
        } else if (e.getActionCommand().equals("quit")) {
            for (Event event : EventLog.getInstance()) {
                System.out.println(event.toString());
            }
            System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: moves the program to saveddata or nosaveddata jframe depending on user input
    private void actionPerformedSavedData(ActionEvent e) {
        if (e.getActionCommand().equals("yes")) {
            cloneUser(pendingLoadUser);
            jframeSavedData.setVisible(false);
            jframeShowFunctions.setVisible(true);
        } else if (e.getActionCommand().equals("no")) {
            jframeSavedData.setVisible(false);
            jframeNoSavedData.setVisible(true);
        }
    }

    // EFFECTS: directs the user to the selected function's main page
//    public void selectFunction(int selection) {
//        if (selection == 1) {
//            viewChangeInformation.viewUserInformation();
//            viewChangeInformation.askToChangeInformation();
//        } else if (selection == 2) {
//            calculateBMI.calculate();
//        } else if (selection == 3) {
//            exercisesFunction.askForInput();
//        } else if (selection == 4) {
//            calendar.mainPageCalendar();
//        } else if (selection == 5) {
//            calorieInformation.giveCalorieAdvice();
//        } else if (selection == 6) {
//            saveUser();
//            listTheFunctions();
//        } else if (selection == 7) {
//            loadUser();
//            listTheFunctions();
//        }
//    }

    // EFFECTS: saves the current user to file
    private void saveUser() {
        try {
            jsonWriter.open();
            jsonWriter.write(user);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            //
        }
    }

    // EFFECTS: loads the saved user information
    private void loadUser() {
        try {
            cloneUser(jsonReader.read());
        } catch (IOException e) {
            //
        }
    }

    // EFFECTS: looks to see if there is already saved user information
    public void lookForSavedData() {
        try {
            pendingLoadUser = jsonReader.read();
            jframeSavedData.setVisible(true);
        } catch (IOException e) {
            jframeNoSavedData.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the jframe that shows all the functions
    private void initializeJFrameShowFunctions() {
        jframeShowFunctions = new JFrame("Functions");
        jframeShowFunctions.setLayout(new BoxLayout(jframeShowFunctions.getContentPane(), BoxLayout.Y_AXIS));
        jframeShowFunctions.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JLabel label = new JLabel("Please select a function.");
        jframeShowFunctions.add(label);
        jframeShowFunctions.add(getButtonsPanel());
        jframeShowFunctions.setUndecorated(true);
        jframeShowFunctions.pack();
        jframeShowFunctions.setLocationRelativeTo(null);
        jframeShowFunctions.setVisible(false);
        jframeShowFunctions.setResizable(false);
    }

    // EFFECTS: returns the buttons necessary for the functions page
    private JPanel getButtonsPanel() {
        JPanel jpanel = new JPanel();
        JButton view = getjButtonView();
        JButton bmi = getjButtonBMI();
        JButton exercise = getjButtonExercise();
        JButton calender = getjButtonCalender();
        JButton calorieInfo = getjButtonCalorieInfo();
        JButton save = getjButtonSave();
        JButton load = getjButtonLoad();
        JButton quit = getjButtonQuit();
        jpanel.add(view);
        jpanel.add(bmi);
        jpanel.add(exercise);
        jpanel.add(calender);
        jpanel.add(calorieInfo);
        jpanel.add(save);
        jpanel.add(load);
        jpanel.add(quit);
        jpanel.setLayout(new BoxLayout(jpanel, BoxLayout.Y_AXIS));
        jpanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        return jpanel;
    }

    // EFFECTS: returns the quit button
    private JButton getjButtonQuit() {
        JButton quit = new JButton("Quit application");
        quit.setActionCommand("quit");
        quit.addActionListener(this);
        return quit;
    }

    // EFFECTS: returns the load button
    private JButton getjButtonLoad() {
        JButton load = new JButton("Load the last saved user information");
        load.setActionCommand("load");
        load.addActionListener(this);
        return load;
    }

    // EFFECTS: returns the save button
    private JButton getjButtonSave() {
        JButton save = new JButton("Save the current user information");
        save.setActionCommand("save");
        save.addActionListener(this);
        return save;
    }

    // EFFECTS: returns the calorie information button
    private JButton getjButtonCalorieInfo() {
        JButton calorieInfo = new JButton("Get advice on your current daily calorie intake");
        calorieInfo.setActionCommand("calorieInfo");
        calorieInfo.addActionListener(this);
        return calorieInfo;
    }

    // EFFECTS: returns the calender button
    private JButton getjButtonCalender() {
        JButton calender = new JButton("Calender, to keep track of your progress");
        calender.setActionCommand("calender");
        calender.addActionListener(this);
        return calender;
    }

    // EFFECTS: returns the exercise information button
    private JButton getjButtonExercise() {
        JButton exercise = new JButton("View/change current exercise weights");
        exercise.setActionCommand("exercise");
        exercise.addActionListener(this);
        return exercise;
    }

    // EFFECTS: returns the bmi calculator button
    private JButton getjButtonBMI() {
        JButton bmi = new JButton("Calculate BMI");
        bmi.setActionCommand("bmi");
        bmi.addActionListener(this);
        return bmi;
    }

    // EFFECTS: returns the view/change/add/remove information button
    private JButton getjButtonView() {
        JButton view = new JButton("View/change/add/remove user information");
        view.setActionCommand("view");
        view.addActionListener(this);
        return view;
    }

    // MODIFIES: this
    // EFFECTS: initializes the jframe that shows the welcome screen
    private void initializeVisualWelcome() {
        jframeVisualWelcome = new JFrame("Welcome!");
        jframeVisualWelcome.setLayout(new BoxLayout(jframeVisualWelcome.getContentPane(), BoxLayout.Y_AXIS));
        jframeVisualWelcome.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JButton button = new JButton("Continue");
        button.setActionCommand("continue");
        button.addActionListener(this);
        JPanel jpanel = new JPanel();
        jpanel.add(new JLabel(new ImageIcon("./src/WelcomeImage.png")));
        jpanel.add(button);
        jframeVisualWelcome.add(jpanel);
        jframeVisualWelcome.setUndecorated(true);
        jframeVisualWelcome.pack();
        jframeVisualWelcome.setLocationRelativeTo(null);
        jframeVisualWelcome.setVisible(false);
        jframeVisualWelcome.setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: initializes the jframe that shows when there is no saved data
    private void initializeJFrameNoSavedData() {
        jframeNoSavedData = new JFrame("Please enter information");
        jframeNoSavedData.setLayout(new BoxLayout(jframeNoSavedData.getContentPane(), BoxLayout.Y_AXIS));
        jframeNoSavedData.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JLabel label = new JLabel("Please enter your information.");
        JButton enter = new JButton("enter");
        enter.setActionCommand("enter");
        enter.addActionListener(this);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePanel = getNamePanel();
        weightPanel = getWeightPanel();
        heightPanel = getHeightPanel();
        caloriePanel = getCaloriePanel();
        jframeNoSavedData.add(label);
        jframeNoSavedData.add(namePanel);
        jframeNoSavedData.add(weightPanel);
        jframeNoSavedData.add(heightPanel);
        jframeNoSavedData.add(caloriePanel);
        jframeNoSavedData.add(enter);
        jframeNoSavedData.setUndecorated(true);
        jframeNoSavedData.pack();
        jframeNoSavedData.setLocationRelativeTo(null);
        jframeNoSavedData.setVisible(false);
        jframeNoSavedData.setResizable(false);
    }

    // EFFECTS: returns the panel that requests user input for the user name
    public JPanel getNamePanel() {
        JPanel namePanel = new JPanel();
        namePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        namePanel.setLayout(new FlowLayout());
        nameLabel = new JLabel("Please enter your name:");
        nameField = new JTextField(5);
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        return namePanel;
    }

    // EFFECTS: returns the panel that requests user input for the user weight
    public JPanel getWeightPanel() {
        JPanel weightPanel = new JPanel();
        weightPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        weightPanel.setLayout(new FlowLayout());
        weightLabel = new JLabel("Please enter your weight in kg:");
        weightField = new JTextField(5);
        weightPanel.add(weightLabel);
        weightPanel.add(weightField);
        return weightPanel;
    }

    // EFFECTS: returns the panel that requests user input for the user height
    public JPanel getHeightPanel() {
        JPanel heightPanel = new JPanel();
        heightPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        heightPanel.setLayout(new FlowLayout());
        heightLabel = new JLabel("Please enter your height in cm:");
        heightField = new JTextField(5);
        heightPanel.add(heightLabel);
        heightPanel.add(heightField);
        return heightPanel;
    }

    // EFFECTS: returns the panel that requests user input for the user calorie intake
    public JPanel getCaloriePanel() {
        JPanel caloriePanel = new JPanel();
        caloriePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        caloriePanel.setLayout(new FlowLayout());
        calorieLabel = new JLabel("Please enter your daily calorie intake:");
        calorieField = new JTextField(5);
        caloriePanel.add(calorieLabel);
        caloriePanel.add(calorieField);
        return caloriePanel;
    }

    // MODIFIES: this
    // EFFECTS: initializes the jframe that shows when there is saved data
    private void initializeJFrameSavedData() {
        jframeSavedData = new JFrame("Saved data found");
        jframeSavedData.setLayout(new BoxLayout(jframeSavedData.getContentPane(), BoxLayout.Y_AXIS));
        jframeSavedData.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JLabel label = new JLabel("You have previously saved data, would you like to load it?");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        buttonPanel.setLayout(new FlowLayout());
        JButton yes = new JButton("yes");
        yes.setActionCommand("yes");
        yes.addActionListener(this);
        JButton no = new JButton("no");
        no.setActionCommand("no");
        no.addActionListener(this);
        buttonPanel.add(yes);
        buttonPanel.add(no);
        jframeSavedData.add(label);
        jframeSavedData.add(buttonPanel);
        jframeSavedData.setUndecorated(true);
        jframeSavedData.pack();
        jframeSavedData.setLocationRelativeTo(null);
        jframeSavedData.setVisible(false);
        jframeSavedData.setResizable(false);
    }

    // MODIFIES: user
    // EFFECTS: clones the user in order to save the clone to the file
    private void cloneUser(User pendingUser) {
        user.setName(pendingUser.getName());
        user.setWeight(pendingUser.getWeight());
        user.setHeight(pendingUser.getHeight());
        user.setCalorie(pendingUser.getCalorie());
        ArrayList<CustomStatistic> customStatistics = user.getCustomStatistics();
        customStatistics.removeAll(customStatistics);
        for (CustomStatistic cs: pendingUser.getCustomStatistics()) {
            customStatistics.add(cs);
        }
        ArrayList<Exercise> exercises = user.getExercises();
        exercises.removeAll(exercises);
        for (Exercise exercise: pendingUser.getExercises()) {
            exercises.add(exercise);
        }
        ArrayList<Day> calender = user.getCalender();
        calender.removeAll(calender);
        for (Day day: pendingUser.getCalender()) {
            calender.add(day);
        }
    }

    // EFFECTS: Makes sure that the user input is type int and in the range of min and max. If not, ask for input again.
    public int validIntWithoutString(int min, int max, int number) throws NumberFormatException {
        if (number < min || number > max) {
            throw new NumberFormatException();
        } else {
            return number;
        }
    }

    // EFFECTS: Makes sure that the user input is in the list that is labelled "acceptable".
//    public String validString(ArrayList<String> acceptable, String selection) {
//        while (!acceptable.contains(selection)) {
//            System.out.println("Please enter a valid selection:");
//            selection = scanner.nextLine();
//        }
//        return selection;
//    }

//    public ViewChangeInformation getViewChangeInformation() {
//        return viewChangeInformation;
//    }
//
//    public ExercisesFunction getExercisesFunction() {
//        return this.exercisesFunction;
//    }
}
