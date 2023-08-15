package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomStatisticTest {
    private CustomStatistic customStatistic;

    @BeforeEach
    public void setup() {
        customStatistic = new CustomStatistic("Body fat", "kg", 8);
    }

    @Test
    public void testConstructor() {
        assertEquals("Body fat", customStatistic.getName());
        assertEquals("kg", customStatistic.getUnit());
        assertEquals(8, customStatistic.getValue());
    }

    @Test
    public void testSetters() {
        customStatistic.setName("Body water");
        customStatistic.setUnit("gram");
        customStatistic.setValue(800);
        assertEquals("Body water", customStatistic.getName());
        assertEquals("gram", customStatistic.getUnit());
        assertEquals(800, customStatistic.getValue());
    }
}
