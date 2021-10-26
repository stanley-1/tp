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

        // valid date sequences
        assertTrue(Date.isValidDate("date:2020-02-02"));
        // name with space character ("date 3")
        assertTrue(Date.isValidDate("date 3:2020-02-03"));
    }
}
