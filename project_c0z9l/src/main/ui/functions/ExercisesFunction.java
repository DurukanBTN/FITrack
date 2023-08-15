//package ui.functions;
//
//import model.Exercise;
//import ui.FunctionsCenter;
//import model.User;
//
//import java.util.ArrayList;
//import java.util.Scanner;
//
//// The function that helps the user keep track of their current weight and reps on exercises.
//public class ExercisesFunction {
//    private User user;
//    private FunctionsCenter functionsCenter;
//    private Scanner scanner = new Scanner(System.in);
//    private Exercise bench;
//    private Exercise squat;
//    private Exercise deadlift;
//
//    // EFFECTS: Constructs an ExerciseFunction with given User and FunctionsCenter
//    public ExercisesFunction(User user, FunctionsCenter functionsCenter) {
//        this.user = user;
//        this.functionsCenter = functionsCenter;
//    }
//
//    // EFFECTS: asks for input if there are no current exercises, shows the current exercises if there are.
//    public void askForInput() {
//        if (user.getExercises().size() == 0) {
//            System.out.println("No current exercise information found.");
//            System.out.println("Please enter your weight (kg) and repetition information for the main exercises:");
//            askBench();
//            askSquat();
//            askDeadlift();
//        } else if (bench == null) {
//            bench = user.getExercises().get(0);
//            squat = user.getExercises().get(1);
//            deadlift = user.getExercises().get(2);
//        } else {
//            showCurrentExercises();
//        }
//        askForORM();
//    }
//
//    // MODIFIES: this, user
//    // EFFECTS: takes user input for bench press weight and repetition number
//    private void askBench() {
//        System.out.println("Bench press weight:");
//        int benchWeight = functionsCenter.validInt(1, 500);
//        System.out.println("Bench press rep:");
//        int benchRep = functionsCenter.validInt(1, 36);
//        bench = new Exercise("Bench press", benchWeight, benchRep);
//        user.addExercise(bench);
//    }
//
//    // MODIFIES: this, user
//    // EFFECTS: takes user input for squat weight and repetition number
//    private void askSquat() {
//        System.out.println("Squat weight:");
//        int squatWeight = functionsCenter.validInt(1, 500);
//        System.out.println("Squat rep:");
//        int squatRep = functionsCenter.validInt(1, 36);
//        squat = new Exercise("Squat", squatWeight, squatRep);
//        user.addExercise(squat);
//    }
//
//    // MODIFIES: this, user
//    // EFFECTS: takes user input for deadlift weight and repetition number
//    private void askDeadlift() {
//        System.out.println("Deadlift weight:");
//        int deadliftWeight = functionsCenter.validInt(1, 500);
//        System.out.println("Deaflift rep:");
//        int deadliftRep = functionsCenter.validInt(1, 36);
//        deadlift = new Exercise("Deadlift", deadliftWeight, deadliftRep);
//        user.addExercise(deadlift);
//    }
//
//    // EFFECTS: prints the current exercises' info
//    public void showCurrentExercises() {
//        System.out.println("Exercise information at this time:");
//        System.out.println(bench.getName() + ":");
//        System.out.println(bench.getWeight() + " kg for " + bench.getRep() + " repetitions.");
//        System.out.println(squat.getName() + ":");
//        System.out.println(squat.getWeight() + " kg for " + squat.getRep() + " repetitions.");
//        System.out.println(deadlift.getName() + ":");
//        System.out.println(deadlift.getWeight() + " kg for " + deadlift.getRep() + " repetitions.");
//    }
//
//    // EFFECTS: asks for user input to determine what to do next
//    private void askForORM() {
//        showCurrentExercises();
//        System.out.println("Type \"ORM\" to calculate your estimated one repetition maximum weight "
//                + "on the exercises.");
//        System.out.println("Type \"change\" to change the values of current exercises.");
//        System.out.println("Type anything else to return to the functions page.");
//        String selection = scanner.nextLine();
//        if (selection.equalsIgnoreCase("change")) {
//            askForChange();
//            askForInput();
//        } else if (selection.equalsIgnoreCase("ORM")) {
//            calculateORM();
//            functionsCenter.listTheFunctions();
//        } else {
//            functionsCenter.listTheFunctions();
//        }
//    }
//
//    // MODIFIES: user
//    // EFFECTS: determines which exercise's values to change depending on the user input
//    private void askForChange() {
//        System.out.println("Which exercise's values do you want to change? (Case sensitive)");
//        showCurrentExercises();
//        ArrayList<String> acceptable = new ArrayList<>();
//        acceptable.add(bench.getName());
//        acceptable.add(squat.getName());
//        acceptable.add(deadlift.getName());
//        String selection = scanner.nextLine();
//        selection = functionsCenter.validString(acceptable, selection);
//        user.resetExercises();
//        if (selection.equalsIgnoreCase(bench.getName())) {
//            changeBench();
//        } else if (selection.equalsIgnoreCase(squat.getName())) {
//            changeSquat();
//        } else {
//            changeDeadlift();
//        }
//    }
//
//    // MODIFIES: user
//    // EFFECTS: changes the weight and repetition number of bench press depending on user input
//    private void changeBench() {
//        Exercise squat = this.squat;
//        Exercise deadlift = this.deadlift;
//        askBench();
//        user.addExercise(squat);
//        user.addExercise(deadlift);
//    }
//
//    // MODIFIES: user
//    // EFFECTS: changes the weight and repetition number of squat depending on user input
//    private void changeSquat() {
//        Exercise bench = this.bench;
//        Exercise deadlift = this.deadlift;
//        user.addExercise(bench);
//        askSquat();
//        user.addExercise(deadlift);
//    }
//
//    // MODIFIES: user
//    // EFFECTS: changes the weight and repetition number of deadlift depending on user input
//    private void changeDeadlift() {
//        Exercise bench = this.bench;
//        Exercise squat = this.squat;
//        user.addExercise(bench);
//        user.addExercise(squat);
//        askDeadlift();
//    }
//
//    // EFFECTS: calculates and prints out the estimated one rep maximum weight on every exercise
//    private void calculateORM() {
//        System.out.println("Your potential one rep maximum values (kg) are:");
//        System.out.println(formula(bench));
//        System.out.println(formula(squat));
//        System.out.println(formula(deadlift));
//    }
//
//    // EFFECTS: makes the calculations to estimate the one rep maximum on an exercise
//    private int formula(Exercise exercise) {
//        float orm = 36;
//        float divideBy = 37;
//        divideBy -= exercise.getRep();
//        orm /= divideBy;
//        orm *= exercise.getWeight();
//        System.out.println(exercise.getName() + ":");
//        return (int) orm;
//    }
//
//    // EFFECTS: prints the exercises' info on a specific day (mainly used by the Calendar function)
//    public void showExercises() {
//        Exercise bench = user.getExercises().get(0);
//        Exercise squat = user.getExercises().get(1);
//        Exercise deadlift = user.getExercises().get(2);
//        System.out.println("Exercise information at this day:");
//        System.out.println(bench.getName() + ":");
//        System.out.println(bench.getWeight() + " kg for " + bench.getRep() + " repetitions.");
//        System.out.println(squat.getName() + ":");
//        System.out.println(squat.getWeight() + " kg for " + squat.getRep() + " repetitions.");
//        System.out.println(deadlift.getName() + ":");
//        System.out.println(deadlift.getWeight() + " kg for " + deadlift.getRep() + " repetitions.");
//    }
//}
