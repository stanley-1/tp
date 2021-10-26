package socialite.model.person;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import socialite.testutil.Assert;

public class DateTest {
    @Test
    public void isValidDates() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid dates
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("2020-02-01")); // no name
        assertFalse(Date.isValidDate(":2020-02-01")); // no name
        assertFalse(Date.isValidDate("date name")); // no date
        assertFalse(Date.isValidDate("date name:")); // no date

        // valid dates
        assertTrue(Date.isValidDate("date:2020-02-02"));
        // name with space character ("date 3")
        assertTrue(Date.isValidDate("date 3:2020-02-03"));

        // recurring dates
        assertTrue(Date.isValidDate("date:02-02"));
        assertTrue(Date.isValidDate("date 3:02-03"));
    }

    @Test
    public void isRecurring() {
        assertFalse(new Date("date:2020-02-02").isRecurring());
        assertTrue(new Date("date:02-02").isRecurring());
    }
}
