package socialite.model.handle;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import socialite.testutil.Assert;

public class TikTokTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TikTok(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new TikTok(""));
    }

    @Test
    public void isValidHandle() {
        // check null
        Assert.assertThrows(NullPointerException.class, () -> TikTok.isValidHandle(null));

        // check empty string and spaces
        assertFalse(TikTok.isValidHandle(""));
        assertFalse(TikTok.isValidHandle("  "));

        // test invalid names, wrong lengths and non alphanumerical or '_'
        assertFalse(TikTok.isValidHandle("bob^by"));
        assertFalse(TikTok.isValidHandle("bob$%"));

        // valid names
        assertTrue(TikTok.isValidHandle("bobb"));
        assertTrue(TikTok.isValidHandle("BOBBY"));
        assertTrue(TikTok.isValidHandle("bobby_boy"));
        assertTrue(TikTok.isValidHandle("bobby.boy"));
        assertTrue(TikTok.isValidHandle("bObbY_boy99"));
        assertTrue(TikTok.isValidHandle("bObbY.boy99"));
        assertTrue(TikTok.isValidHandle("12345"));
    }
}
