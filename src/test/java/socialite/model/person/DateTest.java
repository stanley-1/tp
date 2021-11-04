package socialite.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DateTest {
    @Test
    public void isValidDate() {
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
        assertFalse(Date.isValidDate("date:2021-02-29")); // non-existent date
        assertFalse(Date.isValidDate("date:2020-03-32")); // non-existent date

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
        LocalDate referenceDate1 = LocalDate.of(2021, 4, 4);
        LocalDate referenceDate2 = LocalDate.of(2020, 1, 1);
        Date nonRecurring = new Date("date:2020-02-02");

        assertTrue(nonRecurring.getNextOccurrence(referenceDate1).isEmpty());
        assertTrue(nonRecurring.getNextOccurrence(referenceDate2).isPresent());
        assertEquals(LocalDate.of(2020, 2, 2), nonRecurring.getNextOccurrence(referenceDate2).get());
    }

    @Test
    public void getNextOccurrence_recurringMonthly() {
        LocalDate referenceDate1 = LocalDate.of(2021, 4, 4);
        LocalDate referenceDate2 = LocalDate.of(2020, 1, 1);
        Date recurring = new Date("date:2020-02-02:monthly");

        assertTrue(recurring.getNextOccurrence(referenceDate1).isPresent());
        assertEquals(LocalDate.of(2021, 5, 2), recurring.getNextOccurrence(referenceDate1).get());

        assertTrue(recurring.getNextOccurrence(referenceDate2).isPresent());
        assertEquals(LocalDate.of(2020, 2, 2), recurring.getNextOccurrence(referenceDate2).get());
    }

    @Test
    public void getNextOccurrence_recurringYearly() {
        LocalDate referenceDate1 = LocalDate.of(2021, 4, 4);
        LocalDate referenceDate2 = LocalDate.of(2020, 1, 1);
        Date recurring = new Date("date:2020-02-02:yearly");

        assertTrue(recurring.getNextOccurrence(referenceDate1).isPresent());
        assertEquals(LocalDate.of(2022, 2, 2), recurring.getNextOccurrence(referenceDate1).get());

        assertTrue(recurring.getNextOccurrence(referenceDate2).isPresent());
        assertEquals(LocalDate.of(2020, 2, 2), recurring.getNextOccurrence(referenceDate2).get());
    }

    @Test
    public void equals_success() {
        Date date = new Date("date:2020-02-02:yearly");

        assertEquals(date, new Date("date:2020-02-02:yearly"));
        assertNotEquals(date, new Date("date:2020-02-02"));
    }

    @Test
    public void hashCode_success() {
        Date date = new Date("date:2020-02-02:yearly");

        assertEquals(date.hashCode(), new Date("date:2020-02-02:yearly").hashCode());
        assertNotEquals(date.hashCode(), new Date("date:2020-02-02").hashCode());
    }
}
