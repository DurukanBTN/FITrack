//package ui.functions;
//
//import model.*;
//import ui.FunctionsCenter;
//
//import java.util.ArrayList;
//import java.util.Scanner;
//
//// The function that is used to keep track of user information and exercises over time.
//public class Calendar {
//    private User user;
//    private FunctionsCenter functionsCenter;
//    private Scanner scanner = new Scanner(System.in);
//
//    // EFFECTS: Constructs a calendar with the given User and FunctionsCenter
//    public Calendar(User user, FunctionsCenter functionsCenter) {
//        this.user = user;
//        this.functionsCenter = functionsCenter;
//    }
//
//    // EFFECTS: takes user input and determines the action accordingly
//    public void mainPageCalendar() {
//        if (user.getCalender().size() == 0) {
//            setupCalender();
//        } else {
//            showCalender();
//            System.out.println("Type the number of the day you want to view, type 0 to view the progression"
//                    + " information over time, type -1 to save the current user information as a new day\n"
//                    + "or type -2 to go back to the functions page.");
//            int selection = functionsCenter.validInt(-2, user.getCalender().size());
//            if (selection == -2) {
//                functionsCenter.listTheFunctions();
//            } else if (selection == -1) {
//                user.addCurrentDay();
//                mainPageCalendar();
//            } else if (selection == 0) {
//                viewProgression();
//            } else {
//                viewDay(selection);
//            }
//        }
//    }
//
//    // EFFECTS: depending on the user inputs, adds the current information as Day 1, or returns to the functions page
//    private void setupCalender() {
//        System.out.println("No days are recorded yet. Would you like to record your current information"
//                + " and exercises as \"Day 1\"? Type \"yes\", or type anything else to return to the "
//                + "functions page.");
//        String answer = scanner.nextLine();
//        if (answer.equalsIgnoreCase("yes")) {
//            user.addCurrentDay();
//            mainPageCalendar();
//        } else {
//            functionsCenter.listTheFunctions();
//        }
//    }
//
//    // EFFECTS: depending on the user input, determines which information will be observed over time
//    private void viewProgression() {
//        System.out.println("Which information do you want to observe over time? (Or type \"<\" to go back to the"
//                + " calender page)");
//        functionsCenter.getViewChangeInformation().viewUserInformation();
//        if (!(user.getExercises().size() == 0)) {
//            functionsCenter.getExercisesFunction().showCurrentExercises();
//        }
//        ArrayList<String> acceptable = new ArrayList<>();
//        acceptable = functionsCenter.getViewChangeInformation().acceptableUserInputsForInformation(acceptable);
//        acceptable.remove("Name");
//        acceptable.add("Bench press");
//        acceptable.add("Squat");
//        acceptable.add("Deadlift");
//        acceptable.add("<");
//        String answer = scanner.nextLine();
//        answer = functionsCenter.validString(acceptable, answer);
//        if (answer.equalsIgnoreCase("<")) {
//            mainPageCalendar();
//        } else {
//            lookOverTime(answer);
//            viewProgression();
//        }
//    }
//
//    // EFFECTS: prints out the progression of the selected essential information over time
//    private void lookOverTime(String answer) {
//        if (answer.equalsIgnoreCase("Weight")) {
//            for (Day day: user.getCalender()) {
//                System.out.println("Day " + day.getNumber() + ": " + day.getUser().getWeight());
//            }
//        } else if (answer.equalsIgnoreCase("Height")) {
//            for (Day day: user.getCalender()) {
//                System.out.println("Day " + day.getNumber() + ": " + day.getUser().getHeight());
//            }
//        } else if (answer.equalsIgnoreCase("Calorie Intake")) {
//            for (Day day: user.getCalender()) {
//                System.out.println("Day " + day.getNumber() + ": " + day.getUser().getCalorie());
//            }
//        } else {
//            lookOverTimeCustomAndExercises(answer);
//        }
//    }
//
//    // EFFECTS: prints out the progression of the selected custom information (or exercise) over time
//    private void lookOverTimeCustomAndExercises(String answer) {
//        for (Day day: user.getCalender()) {
//            for (CustomStatistic customStatistic: day.getUser().getCustomStatistics()) {
//                if (customStatistic.getName().equalsIgnoreCase(answer)) {
//                    System.out.println("Day " + day.getNumber()
//                            + ": " + day.getUser().getCustomStatisticFromName(answer).getValue()
//                            + day.getUser().getCustomStatisticFromName(answer).getUnit());
//                }
//            }
//            for (Exercise exercise: day.getUser().getExercises()) {
//                if (exercise.getName().equalsIgnoreCase(answer)) {
//                    System.out.println("Day " + day.getNumber()
//                            + ": " + answer + " " + day.getUser().getExerciseFromName(answer).getWeight() + "kg"
//                            + " for " + day.getUser().getExerciseFromName(answer).getRep() + " reps");
//                }
//            }
//        }
//    }
//
//    // EFFECTS: prints out the user information on selected day
//    private void viewDay(int number) {
//        Day day = user.getCalender().get(number - 1);
//        User data = day.getUser();
//        ViewChangeInformation viewChangeInformation = new ViewChangeInformation(data, new FunctionsCenter(data));
//        ExercisesFunction exercisesFunction = new ExercisesFunction(data, new FunctionsCenter(data));
//        System.out.println("Day " + number + ":\n");
//        System.out.println("Weight: " + data.getWeight());
//        System.out.println("Height: " + data.getHeight());
//        System.out.println("Calorie Intake (Daily): " + data.getCalorie() + "\n");
//        System.out.println("Custom Information:");
//        viewChangeInformation.viewCustomUserInformation();
//        if (!(data.getExercises().size() == 0)) {
//            exercisesFunction.showExercises();
//        }
//        System.out.println("Type anything to go back to the calender page.");
//        scanner.nextLine();
//        mainPageCalendar();
//    }
//
//    // EFFECTS: prints out the available (saved) days in the calendar for the user to choose from
//    public void showCalender() {
//        System.out.println("Your current calender:");
//        for (Day day: user.getCalender()) {
//            System.out.println("Day: " + day.getNumber());
//        }
//    }
//}
