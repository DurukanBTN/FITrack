package ui.functions;

import model.CustomStatistic;
import ui.FunctionsCenter;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

// The function that is used to view/change/add/remove information from the user instance depending on the user's input.
public class ViewChangeInformation implements ActionListener {
    private User user;
    private FunctionsCenter functionsCenter;
    private Scanner scanner = new Scanner(System.in);
    private CustomStatistic chosenCS;

    private JPanel userInformationPanel;
    private JPanel customUserInformationPanel;
    private JPanel buttonsPanel;
    private JPanel addInformationPanel;

    private JTextField type;
    private JTextField value;
    private JTextField unit;
    private JTextField changeField;

    private JFrame jframeViewFunction;
    private JFrame jframeAddInformationFunction;
    private JFrame jframeRemoveInformationFunction;
    private JFrame jframeChangeInformationFunction;
    private JFrame jframeChangeChosenInformationFunction;

    // EFFECTS: constructs the functions with given User and FunctionsCenter
    public ViewChangeInformation(User user, FunctionsCenter functionsCenter) {
        this.user = user;
        this.functionsCenter = functionsCenter;
    }

    // MODIFIES: user, this
    // EFFECTS: checks all the actionPerformed methods to see which one's action to execute
    public void actionPerformed(ActionEvent e) {
        actionPerformedAdd(e);
        actionPerformedChange(e);
        int index = 0;
        for (CustomStatistic cs: user.getCustomStatistics()) {
            if (e.getActionCommand().equals("remove" + cs.getName() + index)) {
                user.removeCustomStatistic(index);
                jframeRemoveInformationFunction.setVisible(false);
                initializeJFrameViewFunction();
                break;
            }
            index++;
        }
        int index2 = 0;
        for (CustomStatistic cs: user.getCustomStatistics()) {
            if (e.getActionCommand().equals("change" + cs.getName() + index2)) {
                jframeChangeInformationFunction.setVisible(false);
                initializeJFrameChangeChosenFunction(index2);
                break;
            }
            index2++;
        }
    }

    // MODIFIES: this
    // EFFECTS: actionPerformed method for changing user information
    private void actionPerformedChange(ActionEvent e) {
        if (e.getActionCommand().equals("enterChange")) {
            try {
                chosenCS.changeValue(Integer.parseInt(changeField.getText()), chosenCS.getName());
                jframeChangeChosenInformationFunction.setVisible(false);
                initializeJFrameViewFunction();
            } catch (NumberFormatException exception) {
                //
            }
        }
    }

    // MODIFIES: this, user
    // EFFECTS: actionPerformed method for adding user information
    private void actionPerformedAdd(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            try {
                if (unit.getText() == null) {
                    user.addCustomStatistic(new CustomStatistic(type.getText(), Integer.parseInt(value.getText())));
                } else {
                    user.addCustomStatistic(new CustomStatistic(type.getText(), unit.getText(),
                            Integer.parseInt(value.getText())));
                }
                jframeAddInformationFunction.setVisible(false);
                initializeJFrameViewFunction();
            } catch (NumberFormatException exception) {
                //
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the jframe that asks the user which information they want to change
    public void initializeJFrameChangeFunction() {
        jframeChangeInformationFunction = new JFrame("Change information");
        jframeChangeInformationFunction.setPreferredSize(new Dimension(480, 360));
        jframeChangeInformationFunction.setLayout(new BoxLayout(jframeChangeInformationFunction.getContentPane(),
                BoxLayout.Y_AXIS));
        jframeChangeInformationFunction.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jframeChangeInformationFunction.add(getCustomUserInformationPanel());
        jframeChangeInformationFunction.add(new Label("Which custom information do you want to change:"));
        int index = 0;
        for (CustomStatistic cs: user.getCustomStatistics()) {
            JButton btn = new JButton(cs.getName());
            btn.setActionCommand("change" + cs.getName() + index);
            btn.addActionListener(this);
            jframeChangeInformationFunction.add(btn);
            index++;
        }
        jframeChangeInformationFunction.setUndecorated(true);
        jframeChangeInformationFunction.pack();
        jframeChangeInformationFunction.setLocationRelativeTo(null);
        jframeChangeInformationFunction.setVisible(true);
        jframeChangeInformationFunction.setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: initializes the jframe that asks the user the new value of the chosen user information
    private void initializeJFrameChangeChosenFunction(int index) {
        chosenCS = user.getCustomStatistic(index);
        jframeChangeChosenInformationFunction = new JFrame("Change " + chosenCS.getName());
        jframeChangeChosenInformationFunction.setPreferredSize(new Dimension(480, 360));
        jframeChangeChosenInformationFunction.setLayout(new
                BoxLayout(jframeChangeChosenInformationFunction.getContentPane(), BoxLayout.Y_AXIS));
        jframeChangeChosenInformationFunction.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jframeChangeChosenInformationFunction.add(getCustomUserInformationPanel());
        jframeChangeChosenInformationFunction.add(new Label("Please enter the new value of "
                + chosenCS.getName()));
        changeField = new JTextField(5);
        JPanel jpanel = new JPanel();
        jpanel.add(changeField);
        jpanel.add(new Label(chosenCS.getUnit()));
        JButton enterChange = new JButton("enter");
        enterChange.setActionCommand("enterChange");
        enterChange.addActionListener(this);
        jframeChangeChosenInformationFunction.add(jpanel);
        jframeChangeChosenInformationFunction.add(enterChange);
        jframeChangeChosenInformationFunction.setUndecorated(true);
        jframeChangeChosenInformationFunction.pack();
        jframeChangeChosenInformationFunction.setLocationRelativeTo(null);
        jframeChangeChosenInformationFunction.setVisible(true);
        jframeChangeChosenInformationFunction.setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: initializes the jframe that asks the user which information they want to remove
    public void initializeJFrameRemoveFunction() {
        jframeRemoveInformationFunction = new JFrame("Remove information");
        jframeRemoveInformationFunction.setPreferredSize(new Dimension(480, 360));
        jframeRemoveInformationFunction.setLayout(new BoxLayout(jframeRemoveInformationFunction.getContentPane(),
                BoxLayout.Y_AXIS));
        jframeRemoveInformationFunction.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jframeRemoveInformationFunction.add(getCustomUserInformationPanel());
        jframeRemoveInformationFunction.add(new Label("Which custom information do you want to remove:"));
        int index = 0;
        for (CustomStatistic cs: user.getCustomStatistics()) {
            JButton btn = new JButton(cs.getName());
            btn.setActionCommand("remove" + cs.getName() + index);
            btn.addActionListener(this);
            jframeRemoveInformationFunction.add(btn);
            index++;
        }
        jframeRemoveInformationFunction.setUndecorated(true);
        jframeRemoveInformationFunction.pack();
        jframeRemoveInformationFunction.setLocationRelativeTo(null);
        jframeRemoveInformationFunction.setVisible(true);
        jframeRemoveInformationFunction.setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: initializes the jframe that shows all the functions of viewchangeinformation function
    public void initializeJFrameViewFunction() {
        jframeViewFunction = new JFrame("View/change/add User Information");
        jframeViewFunction.setPreferredSize(new Dimension(480, 360));
        jframeViewFunction.setLayout(new BoxLayout(jframeViewFunction.getContentPane(), BoxLayout.Y_AXIS));
        jframeViewFunction.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jframeViewFunction.add(getUserInformationPanel());
        jframeViewFunction.add(getCustomUserInformationPanel());
        jframeViewFunction.add(getButtonsPanel());
        jframeViewFunction.setUndecorated(true);
        jframeViewFunction.pack();
        jframeViewFunction.setLocationRelativeTo(null);
        jframeViewFunction.setVisible(true);
        jframeViewFunction.setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: initializes the jframe that asks the user for the value, unit, and the name of the new information
    public void initializeJFrameAddInformationFunction() {
        jframeAddInformationFunction = new JFrame("Add information");
        jframeAddInformationFunction.setPreferredSize(new Dimension(480, 360));
        jframeAddInformationFunction.setLayout(new BoxLayout(jframeAddInformationFunction.getContentPane(),
                BoxLayout.Y_AXIS));
        jframeAddInformationFunction.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jframeAddInformationFunction.add(getAddInformationPanel());
        jframeAddInformationFunction.setUndecorated(true);
        jframeAddInformationFunction.pack();
        jframeAddInformationFunction.setLocationRelativeTo(null);
        jframeAddInformationFunction.setVisible(true);
        jframeAddInformationFunction.setResizable(false);
    }

    // EFFECTS: returns the jpanel that contains the textboxes for the user to enter the new information
    public JPanel getAddInformationPanel() {
        addInformationPanel = new JPanel();
        addInformationPanel.setLayout(new BoxLayout(addInformationPanel, BoxLayout.Y_AXIS));
        addInformationPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        addInformationPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel label = new JLabel("Type the name of the information you want to add (Body fat etc.)");
        type = new JTextField(5);
        JLabel label2 = new JLabel("Type the value of the information");
        value = new JTextField(5);
        JLabel label3 = new JLabel("Type the unit of the information, leave blank if there is no unit");
        unit = new JTextField(5);
        JButton add = new JButton("Add information");
        add.setActionCommand("add");
        add.addActionListener(this);
        addInformationPanel.add(label);
        addInformationPanel.add(type);
        addInformationPanel.add(label2);
        addInformationPanel.add(value);
        addInformationPanel.add(label3);
        addInformationPanel.add(unit);
        addInformationPanel.add(add);
        return addInformationPanel;
    }

    // EFFECTS: returns the jpanel that contains all the buttons
    public JPanel getButtonsPanel() {
        buttonsPanel = new JPanel();
        JButton goBack = new JButton("Go back to the functions page");
        goBack.setActionCommand("goBack");
        goBack.addActionListener(functionsCenter);
        JButton addInformation = new JButton("Add custom user information");
        addInformation.setActionCommand("addInformation");
        addInformation.addActionListener(functionsCenter);
        JButton removeInformation = new JButton("Remove custom user information");
        removeInformation.setActionCommand("removeInformation");
        removeInformation.addActionListener(functionsCenter);
        JButton changeInformation = new JButton("Change custom user information");
        changeInformation.setActionCommand("changeInformation");
        changeInformation.addActionListener(functionsCenter);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        buttonsPanel.add(addInformation);
        buttonsPanel.add(removeInformation);
        buttonsPanel.add(changeInformation);
        buttonsPanel.add(goBack);
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return buttonsPanel;
    }

    // EFFECTS: returns the panel that contains the user information
    public JPanel getUserInformationPanel() {
        userInformationPanel = new JPanel();
        userInformationPanel.setLayout(new BoxLayout(userInformationPanel, BoxLayout.Y_AXIS));
        userInformationPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        userInformationPanel.add(new JLabel("Name: " + user.getName()));
        userInformationPanel.add(new JLabel("Weight: " + user.getWeight() + "kg"));
        userInformationPanel.add(new JLabel("Height: " + user.getHeight() + "cm"));
        userInformationPanel.add(new JLabel("Calorie Intake: " + user.getCalorie()));
        userInformationPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return userInformationPanel;
    }

    // EFFECTS: returns the panel that contains the custom user information
    public JPanel getCustomUserInformationPanel() {
        customUserInformationPanel = new JPanel();
        customUserInformationPanel.setLayout(new BoxLayout(customUserInformationPanel, BoxLayout.Y_AXIS));
        customUserInformationPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        for (CustomStatistic customStatistic : user.getCustomStatistics()) {
            if (customStatistic.getUnit() == null) {
                customUserInformationPanel.add(new JLabel(customStatistic.getName() + ": "
                        + customStatistic.getValue()));
            } else {
                customUserInformationPanel.add(new JLabel(customStatistic.getName() + ": "
                        + customStatistic.getValue() + customStatistic.getUnit()));
            }
        }
        customUserInformationPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return customUserInformationPanel;
    }

    // EFFECTS: determines whether to change/add/remove information depending on user input.
//    public void askToChangeInformation() {
//        System.out.println("Type \"<\" to go back to the functions page, type \"change\" to change "
//                + "a user information, type \"add\" to add extra user information, \nor type \"remove\" to remove "
//                + "extra user information.");
//        String selection = scanner.nextLine();
//        ArrayList<String> acceptable = new ArrayList<>();
//        acceptable.add("<");
//        acceptable.add("change");
//        acceptable.add("add");
//        acceptable.add("remove");
//        selection = functionsCenter.validString(acceptable, selection);
//        if (selection.equalsIgnoreCase("<")) {
//            functionsCenter.listTheFunctions();
//        } else if (selection.equalsIgnoreCase("change")) {
//            changeUserInformation();
//        } else if (selection.equalsIgnoreCase("add")) {
//            addUserInformation();
//        } else if (user.getCustomStatistics().size() == 0) {
//            System.out.println("There is not any custom information to remove!\n");
//            askToChangeInformation();
//        } else {
//            removeUserInformation();
//        }
//    }

    // MODIFIES: user
    // EFFECTS: removes the selected custom user information
//    private void removeUserInformation() {
//        System.out.println("Current custom user information:");
//        getCustomUserInformationPanel();
//        System.out.println("Type the name of information you want to remove: (Case sensitive)");
//        String selection = scanner.nextLine();
//        ArrayList<String> acceptable = new ArrayList<>();
//        for (String name : user.getCustomStatisticsNames()) {
//            acceptable.add(name);
//        }
//        selection = functionsCenter.validString(acceptable, selection);
//        CustomStatistic customStatistic = user.getCustomStatistic(user.getCustomStatisticsNames().indexOf(selection));
//        user.getCustomStatistics().remove(customStatistic);
//        showNewInformationAndAskAgain();
//    }

    // MODIFIES: user
    // EFFECTS: adds the selected custom user information
//    private void addUserInformation() {
//        System.out.println("Type the name of information you want to add. "
//                + "\(Examples: body fat, muscle mass, BMI, etc:");
//        String name = scanner.nextLine();
//        ArrayList<String> existingNames = user.getCustomStatisticsNames();
//        while (existingNames.contains(name)) {
//            System.out.println("This information is already present, please enter another name:");
//            name = scanner.nextLine();
//        }
//        System.out.println("If applicable, type the unit of the information you want to add, type \"NA\" if not "
//                + "applicable:");
//        String unit = scanner.nextLine();
//        System.out.println("Type the value of the information:");
//        int value = Integer.valueOf(scanner.nextLine());
//        if (unit.equalsIgnoreCase("NA")) {
//            user.addCustomStatistic(new CustomStatistic(name, value));
//        } else {
//            user.addCustomStatistic(new CustomStatistic(name, unit, value));
//        }
//        showNewInformationAndAskAgain();
//    }

    // MODIFIES: user
    // EFFECTS: changes the selected user information
//    private void changeUserInformation() {
//        System.out.println("Which user information do you want to change? (Case sensitive)");
//        getUserInformationPanel();
//        String selection = scanner.nextLine();
//        ArrayList<String> acceptable = new ArrayList<>();
//        acceptable = acceptableUserInputsForInformation(acceptable);
//        selection = functionsCenter.validString(acceptable, selection);
//        if (selection.equalsIgnoreCase("Name")) {
//            functionsCenter.askName();
//        } else if (selection.equalsIgnoreCase("Weight")) {
//            functionsCenter.askWeight();
//        } else if (selection.equalsIgnoreCase("Height")) {
//            functionsCenter.askHeight();
//        } else if (selection.equalsIgnoreCase("Calorie Intake")) {
//            functionsCenter.askCalorieIntake();
//        } else {
//            changeCustomUserInformation(selection);
//        }
//        if (user.getCustomStatisticsNames().contains("BMI")) {
//            CalculateBMI calculateBMI = new CalculateBMI(user, functionsCenter);
//            calculateBMI.updateBMI();
//        }
//        showNewInformationAndAskAgain();
//    }

    // MODIFIES: user
    // EFFECTS: changes a custom user information depending on the user input
//    private void changeCustomUserInformation(String selection) {
//        CustomStatistic customStatistic = user.getCustomStatistic(user.getCustomStatisticsNames().indexOf(selection));
//        System.out.println("Selected custom user information:");
//        if (customStatistic.getUnit() == null) {
//            System.out.println(customStatistic.getName() + ": " + customStatistic.getValue());
//        } else {
//            System.out.println(customStatistic.getName() + ": " + customStatistic.getValue()
//                    + customStatistic.getUnit());
//        }
//        System.out.println("What should be the new value of the selected custom user information:");
//        int newValue = Integer.valueOf(scanner.nextLine());
//        customStatistic.setValue(newValue);
//    }

    // EFFECTS: prints the new values of user information, and asks for input to determine the next action
//    private void showNewInformationAndAskAgain() {
//        System.out.println("New values of user information are:\n");
//        getUserInformationPanel();
//        askToChangeInformation();
//    }

    // EFFECTS: returns the list of acceptable inputs that represent user information
//    public ArrayList<String> acceptableUserInputsForInformation(ArrayList<String> acceptable) {
//        acceptable.add("Name");
//        acceptable.add("Weight");
//        acceptable.add("Height");
//        acceptable.add("Calorie Intake");
//        for (String name : user.getCustomStatisticsNames()) {
//            acceptable.add(name);
//        }
//        return acceptable;
//    }

    public JFrame getJFrameViewFunction() {
        return jframeViewFunction;
    }

//    public JFrame getJframeAddInformationFunction() {
//        return jframeAddInformationFunction;
//    }
//
//    public JFrame getJframeRemoveInformationFunction() {
//        return jframeRemoveInformationFunction;
//    }
}
