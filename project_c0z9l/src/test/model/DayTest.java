package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DayTest {
    private Day day;
    private User user;
    
    @BeforeEach
    public void setup() {
        user = new User();
        day = new Day(2, user);
    }

    @Test
    public void testConstructor() {
        assertEquals(2, day.getNumber());
        assertEquals(user, day.getUser());
    }
}
