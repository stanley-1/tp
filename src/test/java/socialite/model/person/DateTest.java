package socialite.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DateTest {
    @Test
    public void isValidDates() {
        // null date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid dates
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("2020-02-01")); // no name
        assertFalse(Date.isValidDate(":2020-02-01")); // no name
        assertFalse(Date.isValidDate("date name")); // no date
        assertFalse(Date.isValidDate("date name:")); // no date
        assertFalse(Date.isValidDate("date:2021-02-02:hourly")); // invalid recurrence interval

        // valid dates
        assertTrue(Date.isValidDate("date:2020-02-02"));
        // name with space character ("date 3")
        assertTrue(Date.isValidDate("date 3:2020-02-03"));

        // recurring dates
        assertTrue(Date.isValidDate("date:2021-02-02:yearly"));
        assertTrue(Date.isValidDate("date 3:2021-02-03:yearly"));
        assertTrue(Date.isValidDate("date:2021-02-02:monthly"));
        assertTrue(Date.isValidDate("date 3:2021-02-03:monthly"));
    }

    @Test
    public void getRecurrenceInterval() {
        assertTrue(new Date("date:2020-02-02").getRecurrenceInterval().isEmpty());

        Date recurringDateYearly = new Date("date:2021-02-02:yearly");
        assertTrue(recurringDateYearly.getRecurrenceInterval().isPresent());
        assertEquals("yearly", recurringDateYearly.getRecurrenceInterval().get());

        Date recurringDateMonthly = new Date("date:2021-02-02:monthly");
        assertTrue(recurringDateMonthly.getRecurrenceInterval().isPresent());
        assertEquals("monthly", recurringDateMonthly.getRecurrenceInterval().get());
    }

    @Test
    public void getNextOccurrence_nonRecurring() {
        LocalDate referenceDate1 = LocalDate.of(2021, 04, 04);
        LocalDate referenceDate2 = LocalDate.of(2020, 01, 01);
        Date nonRecurring = new Date("date:2020-02-02");

        assertTrue(nonRecurring.getNextOccurrence(referenceDate1).isEmpty());
        assertTrue(nonRecurring.getNextOccurrence(referenceDate2).isPresent());
        assertEquals(LocalDate.of(2020, 02, 02), nonRecurring.getNextOccurrence(referenceDate2).get());
    }

    @Test
    public void getNextOccurrence_recurringMonthly() {
        LocalDate referenceDate1 = LocalDate.of(2021, 04, 04);
        LocalDate referenceDate2 = LocalDate.of(2020, 01, 01);
        Date recurring = new Date("date:2020-02-02:monthly");

        assertTrue(recurring.getNextOccurrence(referenceDate1).isPresent());
        assertEquals(LocalDate.of(2021, 05, 02), recurring.getNextOccurrence(referenceDate1).get());

        assertTrue(recurring.getNextOccurrence(referenceDate2).isPresent());
        assertEquals(LocalDate.of(2020, 02, 02), recurring.getNextOccurrence(referenceDate2).get());
    }

    @Test
    public void getNextOccurrence_recurringYearly() {
        LocalDate referenceDate1 = LocalDate.of(2021, 04, 04);
        LocalDate referenceDate2 = LocalDate.of(2020, 01, 01);
        Date recurring = new Date("date:2020-02-02:yearly");

        assertTrue(recurring.getNextOccurrence(referenceDate1).isPresent());
        assertEquals(LocalDate.of(2022, 02, 02), recurring.getNextOccurrence(referenceDate1).get());

        assertTrue(recurring.getNextOccurrence(referenceDate2).isPresent());
        assertEquals(LocalDate.of(2020, 02, 02), recurring.getNextOccurrence(referenceDate2).get());
    }
}
