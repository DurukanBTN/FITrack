//package ui.functions;
//
//import ui.FunctionsCenter;
//import model.User;
//
//import java.util.Scanner;
//
//// The function that gives users calculated information about how their current calorie intake would affect their
//// weight gain over time.
//public class CalorieInformation {
//    private User user;
//    private FunctionsCenter functionsCenter;
//    private Scanner scanner = new Scanner(System.in);
//
//    // EFFECTS: construct a CalorieInformation with given User and FunctionCenter
//    public CalorieInformation(User user, FunctionsCenter functionsCenter) {
//        this.user = user;
//        this.functionsCenter = functionsCenter;
//    }
//
//    // EFFECTS: prints out calorie advice depending on the user's weight and their daily calorie intake
//    public void giveCalorieAdvice() {
//        float maintenanceCalorie = user.getWeight();
//        maintenanceCalorie *= 2.20462 * 15;
//        int neededCalorie = (int) maintenanceCalorie;
//        float weightDifference;
//        System.out.println("Your estimated maintenance calorie "
//                + "(daily calorie intake you need to maintain your current"
//                + " weight) is " + neededCalorie + " which is");
//        if (neededCalorie == user.getCalorie()) {
//            System.out.println("equal to your current daily calorie intake. This means you will maintain a"
//                    + " similar weight over time.");
//        } else if (neededCalorie > user.getCalorie()) {
//            weightDifference = neededCalorie - user.getCalorie();
//            weightDifference /= 500;
//            System.out.println((neededCalorie - user.getCalorie()) + " above your current daily calorie intake"
//                    + ", which means that you will lose estimately " + weightDifference + " lbs per week.");
//        } else {
//            weightDifference = user.getCalorie() - neededCalorie;
//            weightDifference /= 500;
//            System.out.println((user.getCalorie() - neededCalorie) + " below your current daily calorie intake"
//                    + ", which means that you will gain estimately " + weightDifference + " lbs per week.");
//        }
//        System.out.println("Type anything to return the functions page:");
//        scanner.nextLine();
//    }
//}
