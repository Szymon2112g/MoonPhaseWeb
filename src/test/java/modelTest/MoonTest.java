/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelTest;

import java.time.LocalDate;
import java.util.List;
import model.Moon;
import model.PhaseException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Unit tests for Moon class
 *
 * @author Szymon Godziński
 * @version 1.3
 */
public class MoonTest {

    /**
     * Object represent moon
     */
    private Moon moon;

    /**
     * function used to set up data before each test function
     */
    @BeforeEach
    public void setUp() {
        moon = new Moon();
    }

    /**
     * Tests function calculatePhaseOfMoon for correct values and result phase
     * of moon should be in range between 0 and 1
     *
     * @param dateString correct date
     */
    @ParameterizedTest
    @ValueSource(strings = {"21 08 1996", "14 11 2020"})
    @DisplayName("calculatePhaseOfMoon Should Be Between Zero And One")
    public void calculatePhaseOfMoonShouldBeBetweenZeroAndOne(String dateString) {
        String[] dateStringArray = dateString.split(" ");
        int day = Integer.parseInt(dateStringArray[0]);
        int month = Integer.parseInt(dateStringArray[1]);
        int year = Integer.parseInt(dateStringArray[2]);
        LocalDate localDate = LocalDate.of(year, month, day);

        double phaseOfMoon = 0;

        try {
            phaseOfMoon = moon.calculatePhaseOfMoon(localDate);
        } catch (PhaseException exc) {
            fail("Shouldn't throw exception for good date");
        }

        assertTrue(phaseOfMoon >= 0 && phaseOfMoon <= 1,
                "Phase of Moon should be in range between 1 and 0 for correct date");
    }

    /**
     * Test function calculatePhaseOfMoon for specific date we require specific
     * phase
     */
    @Test
    @DisplayName("claculatePhaseOfMoon Should Return Specific Value for specific date")
    public void claculatePhaseOfMoonShouldReturnSpecificValueForSpecificDate() {
        LocalDate date = LocalDate.of(2008, 8, 25);

        double phase = 0;
        try {
            phase = moon.calculatePhaseOfMoon(date);
        } catch (PhaseException ex) {
            fail("Shouldn't throw exception for good date");
        }

        assertEquals(phase, 0.7885961702850182,
                "Phase of moon should be specific value for specific date");
    }

    /**
     * Test function calculatePhaseOfMoon should throw exception for null date
     */
    @Test
    @DisplayName("calculatePhaseOfMoon Should Throw Exception If Date Is Null")
    public void calculatePhaseOfMoonShouldThrowExceptionIfDateIsNull() {
        assertThrows(PhaseException.class,
                () -> moon.calculatePhaseOfMoon(null),
                "Function calculatePhaseOfMoon should throw exception for null date");
    }

    /**
     * Test function getHistory which should return date which we use in
     * calculatePhaseOfMoon
     */
    @Test
    @DisplayName("get History Should Have Data After Call calculatePhaseOfMoon")
    public void getHistoryShouldHaveDataAfterCallCalculatePhaseOfMoon() {
        LocalDate date1 = LocalDate.of(2008, 8, 25);
        LocalDate date2 = LocalDate.of(2007, 6, 25);

        try {
            moon.calculatePhaseOfMoon(date1);
            moon.calculatePhaseOfMoon(date2);
        } catch (PhaseException ex) {
            fail("Shouldn't throw exception for good date");
        }

        assertEquals(List.of(date1, date2), moon.getHistory(),
                "Function calculatePhaseOfMoon should add date to history");
    }

    /**
     * Test function getHistory which should return empty list if we don't use
     * calculatePhaseOfMoon
     */
    @Test
    @DisplayName("get History Should Be Empty If We Dont Call calculatePhaseOfMoon")
    public void getHistoryShouldBeEmptyIfWeDontCallCalculatePhaseOfMoon() {
        assertEquals(List.of(), moon.getHistory(),
                "If we didn't use calculatePhaseOfMoon history shpuld be empty");
    }
}
