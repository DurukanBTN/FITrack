package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            User user = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCustomStatisticsExercisesCalender() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCustomStatisticsExercisesCalender.json");
        try {
            User user = reader.read();
            assertEquals("Durukan", user.getName());
            assertEquals(70, user.getWeight());
            assertEquals(180, user.getHeight());
            assertEquals(2000, user.getCalorie());
            assertEquals(0, user.getCustomStatistics().size());
            assertEquals(0, user.getExercises().size());
            assertEquals(0, user.getCalender().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralUser() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralUser.json");
        try {
            User user = reader.read();
            ArrayList<CustomStatistic> customStatistics = new ArrayList<>();
            customStatistics.add(new CustomStatistic("BMI", 22));
            ArrayList<Exercise> exercises = new ArrayList<>();
            exercises.add(new Exercise("Bench press", 80, 8));
            exercises.add(new Exercise("Squat", 90, 9));
            exercises.add(new Exercise("Deadlift", 100, 10));
            ArrayList<Day> calender = new ArrayList<>();
            calender.add(new Day(1, user));
            checkUser("Durukan", 80, 190, 3000, customStatistics, exercises, calender, user);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}