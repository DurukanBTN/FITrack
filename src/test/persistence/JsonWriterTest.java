package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            User wr = new User();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            User user = new User("Durukan", 70, 180, 2000);
            JsonWriter writer =
                    new JsonWriter("./data/testWriterEmptyCustomStatisticsExercisesCalender.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCustomStatisticsExercisesCalender.json");
            user = reader.read();
            assertEquals("Durukan", user.getName());
            assertEquals(70, user.getWeight());
            assertEquals(180, user.getHeight());
            assertEquals(2000, user.getCalorie());
            assertEquals(0, user.getCustomStatistics().size());
            assertEquals(0, user.getExercises().size());
            assertEquals(0, user.getCalender().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            User user = new User("Durukan", 80, 190, 3000);
            user.addCustomStatistic(new CustomStatistic("BMI", 22));
            user.addCustomStatistic(new CustomStatistic("Body fat", "%", 22));
            user.addExercise(new Exercise("Bench press", 80, 8));
            user.addExercise(new Exercise("Squat", 90, 9));
            user.addExercise(new Exercise("Deadlift", 100, 10));
            user.addDay(1, user);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralUser.json");
            writer.open();
            writer.write(user);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralUser.json");
            User readUser = reader.read();
            checkUser(user.getName(), user.getWeight(), user.getHeight(), user.getCalorie(), user.getCustomStatistics(),
                    user.getExercises(), user.getCalender(), readUser);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}