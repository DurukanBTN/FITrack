package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;
    private User user2;

    @BeforeEach
    public void setup() {
        user = new User("Name Surname", 90, 190, 2000);
        user2 = new User();
    }

    @Test
    public void testConstructor() {
        assertEquals("Name Surname", user.getName());
        assertEquals(90, user.getWeight());
        assertEquals(190, user.getHeight());
        assertEquals(2000, user.getCalorie());
    }

    @Test
    public void testSetters() {
        user.setName("Example");
        assertEquals("Example", user.getName());
        user.setWeight(80);
        assertEquals(80, user.getWeight());
        user.setHeight(180);
        assertEquals(180, user.getHeight());
        user.setCalorie(1800);
        assertEquals(1800, user.getCalorie());
    }

    @Test
    public void testAddExercise() {
        assertEquals(0, user.getExercises().size());
        user.addExercise(new Exercise("Bench Press", 90, 10));
        String name = user.getExercises().get(0).getName();
        int weight = user.getExercises().get(0).getWeight();
        int repetition = user.getExercises().get(0).getRep();
        assertEquals("Bench Press", name);
        assertEquals(90, weight);
        assertEquals(10, repetition);
        assertEquals(1, user.getExercises().size());
        assertEquals(user.getExercise(0), user.getExerciseFromName("Bench Press"));
        user.resetExercises();
        assertEquals(0, user.getExercises().size());
    }

    @Test
    public void testAddCustomStatistic() {
        assertEquals(0, user.getCustomStatistics().size());
        CustomStatistic customStatistic = new CustomStatistic("Body fat", "%", 9);
        CustomStatistic customStatistic2 =new CustomStatistic("Body water", 18);
        user.addCustomStatistic(customStatistic);
        user.addCustomStatistic(customStatistic2);
        String name = user.getCustomStatistics().get(0).getName();
        String name2 = user.getCustomStatistics().get(1).getName();
        String unit = user.getCustomStatistics().get(0).getUnit();
        String unit2 = user.getCustomStatistics().get(1).getUnit();
        int value = user.getCustomStatistics().get(0).getValue();
        int value2 = user.getCustomStatistics().get(1).getValue();
        assertEquals("Body fat", name);
        assertEquals("Body water", name2);
        assertEquals("%", unit);
        assertEquals("", unit2);
        assertEquals(9, value);
        assertEquals(18, value2);
        assertEquals(user.getCustomStatistic(0).getName(), user.getCustomStatisticsNames().get(0));
        assertEquals(user.getCustomStatisticFromName("Body fat"), customStatistic);
    }

    @Test
    public void testAddCurrentDay() {
        assertEquals(0, user.getCalender().size());
        user.addCurrentDay();
        assertEquals(1, user.getCalender().size());
        user.setWeight(70);
        user.addCustomStatistic(new CustomStatistic("Body fat", "%", 9));
        user.addCurrentDay();
        assertEquals(2, user.getCalender().size());
        user.addExercise(new Exercise("Bench Press", 90, 10));
        user.addCustomStatistic(new CustomStatistic("Body water",90));
        user.addCurrentDay();
        assertEquals(3, user.getCalender().size());
        assertEquals(user.getCalender().get(0).getUser().getWeight(), 90);
        assertEquals(user.getCalender().get(0).getUser().getCustomStatistics().size(), 0);
        assertEquals(user.getCalender().get(0).getUser().getExercises().size(), 0);
        assertEquals(user.getCalender().get(1).getUser().getWeight(), 70);
        assertEquals(user.getCalender().get(1).getUser().getCustomStatistics().size(), 1);
        assertEquals(user.getCalender().get(1).getUser().getCustomStatistics().get(0).getUnit(), "%");
        assertEquals(user.getCalender().get(1).getUser().getExercises().size(), 0);
        assertEquals(user.getCalender().get(2).getUser().getExercises().size(), 1);
        assertEquals(user.getCalender().get(2).getUser().getCustomStatistics().size(), 2);
        assertEquals(user.getCalender().get(2).getUser().getExercises().get(0).getRep(), 10);
        assertEquals(user.getCalender().get(2).getUser().getCustomStatistics().get(1).getValue(), 90);
    }
}