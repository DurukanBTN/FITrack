package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciseTest {
    private Exercise exercise;

    @BeforeEach
    public void setup() {
        exercise = new Exercise("Bench press", 90, 8);
    }

    @Test
    public void testConstructor() {
        assertEquals("Bench press", exercise.getName());
        assertEquals(90, exercise.getWeight());
        assertEquals(8, exercise.getRep());
    }
}
