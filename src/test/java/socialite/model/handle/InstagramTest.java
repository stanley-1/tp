package socialite.model.handle;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import socialite.testutil.Assert;

public class InstagramTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Instagram(null));
    }

    @Test
    public void constructor_invalidInstagram_throwsIllegalArgumentException() {
        String invalidInstagram = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Instagram(invalidInstagram));
    }

    @Test
    public void isValidHandle() {
        // null Instagram handle
        Assert.assertThrows(NullPointerException.class, () -> Instagram.isValidHandle(null));

        // blank Instagram handles
        assertFalse(Instagram.isValidHandle("")); // empty string
        assertFalse(Instagram.isValidHandle(" ")); // spaces only

        // invalid Instagram handles
        assertFalse(Instagram.isValidHandle(" invalidHandle")); // leading space
        assertFalse(Instagram.isValidHandle("invalidHandle ")); // trailing space
        assertFalse(Instagram.isValidHandle("john!chapman")); // invalid token '!'
        assertFalse(Instagram.isValidHandle("john&chapman")); // invalid token '&'
        assertFalse(Instagram.isValidHandle("john#chapman")); // invalid token '#'
        assertFalse(Instagram.isValidHandle("john-chapman")); // invalid token '-'
        assertFalse(Instagram.isValidHandle("ThisIsALongHandleThatShouldBeInvalid")); // exceeds max length

        // valid Instagram handles
        assertTrue(Instagram.isValidHandle("1")); // at least 1 character
        assertTrue(Instagram.isValidHandle("jo.hn")); // full stop
        assertTrue(Instagram.isValidHandle("john_chapman")); // underscore
        assertTrue(Instagram.isValidHandle("sarahWalters1")); // lower & uppercase with digits
        assertTrue(Instagram.isValidHandle("123Steve.3")); // start with digits
        assertTrue(Instagram.isValidHandle("Nat_321")); // start with uppercase letter

    }
}
