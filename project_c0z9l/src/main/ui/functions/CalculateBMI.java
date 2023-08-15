//package ui.functions;
//
//import model.CustomStatistic;
//import ui.FunctionsCenter;
//import model.User;
//
//import java.util.Scanner;
//
//// The function that allows the user to calculate their BMI, and also add it to the list of Custom User Information.
//public class CalculateBMI {
//    private User user;
//    private FunctionsCenter functionsCenter;
//    private Scanner scanner = new Scanner(System.in);
//    private float bmi;
//
//    // EFFECTS: Construct a CalculateBMI with given User and FunctionsCenter
//    public CalculateBMI(User user, FunctionsCenter functionsCenter) {
//        this.user = user;
//        this.functionsCenter = functionsCenter;
//    }
//
//    // MODIFIES: this, user
//    // EFFECTS: updates the BMI according to the change in user's height or weight
//    public void updateBMI() {
//        bmi = user.getWeight();
//        float heightM = user.getHeight();
//        heightM /=  100;
//        bmi /= heightM;
//        bmi /= heightM;
//        bmi = (int) (bmi * 10);
//        bmi /= 10;
//        user.getCustomStatisticFromName("BMI").setValue((int) bmi);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: calculate sand prints out the current BMI
//    public void calculate() {
//        bmi = user.getWeight();
//        float heightM = user.getHeight();
//        heightM /=  100;
//        bmi /= heightM;
//        bmi /= heightM;
//        bmi = (int) (bmi * 10);
//        bmi /= 10;
//        String range = findRangeOfBMI(bmi);
//        System.out.println("Your current BMI is: " + bmi);
//        System.out.println("Which falls into the category of " + range);
//        if (!user.getCustomStatisticsNames().contains("BMI")) {
//            askToAddBmiInformation();
//        } else {
//            System.out.println("Type anything to go back to the functions page.");
//            scanner.nextLine();
//        }
//    }
//
//    // MODIFIES: user
//    // EFFECTS: asks the user if they want to add the BMI information to custom information list
//    private void askToAddBmiInformation() {
//        System.out.println("Type \"add\" to add the BMI to the custom user informations list, \n"
//                + " type anything else to go back to the functions page.");
//        String answer = scanner.nextLine();
//        if (answer.equalsIgnoreCase("add")) {
//            user.addCustomStatistic(new CustomStatistic("BMI", (int) bmi));
//            System.out.println("BMI information added.");
//        }
//        System.out.println("Going back to functions page.");
//    }
//
//    // EFFECTS: prints out which range the BMI belongs to
//    private String findRangeOfBMI(float bmi) {
//        if (bmi < 18.5) {
//            return  "underweight";
//        } else if (bmi < 25) {
//            return "healthy";
//        } else if (bmi < 30) {
//            return "overweight";
//        } else if (bmi < 35) {
//            return "class 1 obesity";
//        } else if (bmi < 40) {
//            return "class 2 obesity";
//        } else {
//            return "class 3 obesity";
//        }
//    }
//}
