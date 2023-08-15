package persistence;

import model.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkUser(String name, int weight, int height, int calorie,
                             ArrayList<CustomStatistic> customStatistics, ArrayList<Exercise> exercises,
                             ArrayList<Day> calender, User user) {
        assertEquals(name, user.getName());
        assertEquals(weight, user.getWeight());
        assertEquals(height, user.getHeight());
        assertEquals(calorie, user.getCalorie());
        assertEquals(customStatistics.size(), user.getCustomStatistics().size());
        assertEquals(exercises.size(), user.getExercises().size());
        assertEquals(calender.size(), user.getCalender().size());
        for (int i = 0; i < customStatistics.size(); i++) {
            assertEquals(customStatistics.get(i).getValue(), user.getCustomStatistics().get(i).getValue());
            assertEquals(customStatistics.get(i).getUnit(), user.getCustomStatistics().get(i).getUnit());
            assertEquals(customStatistics.get(i).getName(), user.getCustomStatistics().get(i).getName());
        }
        for (int i = 0; i < exercises.size(); i++) {
            assertEquals(exercises.get(i).getName(), user.getExercises().get(i).getName());
            assertEquals(exercises.get(i).getRep(), user.getExercises().get(i).getRep());
            assertEquals(exercises.get(i).getWeight(), user.getExercises().get(i).getWeight());
        }
        for (int i = 0; i < calender.size(); i++) {
            assertEquals(calender.get(i).getNumber(), user.getCalender().get(i).getNumber());
            assertEquals(calender.get(i).getUser().getName(), user.getCalender().get(i).getUser().getName());
            for (int j = 0; j < calender.get(i).getUser().getCustomStatistics().size(); j++) {
                assertEquals(calender.get(i).getUser().getCustomStatistics().get(j).getValue(),
                        user.getCalender().get(i).getUser().getCustomStatistics().get(j).getValue());
                assertEquals(calender.get(i).getUser().getCustomStatistics().get(j).getName(),
                        user.getCalender().get(i).getUser().getCustomStatistics().get(j).getName());
                assertEquals(calender.get(i).getUser().getCustomStatistics().get(j).getUnit(),
                        user.getCalender().get(i).getUser().getCustomStatistics().get(j).getUnit());
            }
            for (int j = 0; j < calender.get(i).getUser().getExercises().size(); j++) {
                assertEquals(calender.get(i).getUser().getExercises().get(j).getWeight(),
                        user.getCalender().get(i).getUser().getExercises().get(j).getWeight());
                assertEquals(calender.get(i).getUser().getExercises().get(j).getRep(),
                        user.getCalender().get(i).getUser().getExercises().get(j).getRep());
                assertEquals(calender.get(i).getUser().getExercises().get(j).getName(),
                        user.getCalender().get(i).getUser().getExercises().get(j).getName());
            }
        }
    }
}
