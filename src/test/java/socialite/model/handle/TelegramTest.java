package socialite.model.handle;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import socialite.testutil.Assert;

public class TelegramTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Telegram(null));
    }

    @Test
    public void constructor_invalidTelegram_throwsIllegalArgumentException() {
        String invalidTelegram = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Telegram(invalidTelegram));
    }

    @Test
    public void isValidTelegram() {
        // null telegram handle
        Assert.assertThrows(NullPointerException.class, () -> new Telegram(null));

        // blank telegram handle
        assertFalse(Telegram.isValidHandle("")); // empty string
        assertFalse(Telegram.isValidHandle(" ")); // spaces only

        // invalid handles
        assertFalse(Telegram.isValidHandle("adam")); // less than 5 characters
        assertFalse(Telegram.isValidHandle(" test_handle")); // leading space
        assertFalse(Telegram.isValidHandle("test_handle ")); // trailing space
        assertFalse(Telegram.isValidHandle("john.appleseed")); // dot instead of underscore
        assertFalse(Telegram.isValidHandle("john+appleseed")); // invalid token "+"
        assertFalse(Telegram.isValidHandle("john%appleseed")); // invalid token "%"

        // valid handles
        assertTrue(Telegram.isValidHandle("abcde")); // 5 characters
        assertTrue(Telegram.isValidHandle("ab_cd")); // 5 characters including underscore
        assertTrue(Telegram.isValidHandle("john_appleseed")); // alphanumerical and underscore

        // random names generated
        assertTrue(Telegram.isValidHandle("Jasmin_Lutz"));
        assertTrue(Telegram.isValidHandle("kayleebrady"));
        assertTrue(Telegram.isValidHandle("kumani_rowland"));
        assertTrue(Telegram.isValidHandle("rocky_jeffery"));
        assertTrue(Telegram.isValidHandle("skyla_moss"));
        assertTrue(Telegram.isValidHandle("choiyoungbae"));
    }
}
