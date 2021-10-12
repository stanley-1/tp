package socialite.model.handle;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import socialite.testutil.Assert;

public class FacebookTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Facebook(null));
    }

    @Test
    public void constructor_invalidFacebook_throwsIllegalArgumentException() {
        String invalidFacebook = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Facebook(invalidFacebook));
    }

    @Test
    public void isValidHandle() {
        // null Facebook username
        Assert.assertThrows(NullPointerException.class, () -> Facebook.isValidHandle(null));

        // blank Facebook usernames
        assertFalse(Facebook.isValidHandle("")); // empty string
        assertFalse(Facebook.isValidHandle(" ")); // spaces only

        // invalid Facebook usernames
        assertFalse(Facebook.isValidHandle("dave")); // less than 5 characters
        assertFalse(Facebook.isValidHandle(" invalidHandle")); // leading space
        assertFalse(Facebook.isValidHandle("invalidHandle ")); // trailing space
        assertFalse(Facebook.isValidHandle("john_chapman")); // invalid token '_'
        assertFalse(Facebook.isValidHandle("john&chapman")); // invalid token '&'
        assertFalse(Facebook.isValidHandle("john#chapman")); // invalid token '#'
        assertFalse(Facebook.isValidHandle("john.com")); // invalid token ".com"
        assertFalse(Facebook.isValidHandle("john.net")); // invalid token ".net"

        // valid Facebook usernames
        assertTrue(Facebook.isValidHandle("john1")); // at least 5 characters
        assertTrue(Facebook.isValidHandle("jo.hn")); // at least 5 characters with full stop
        assertTrue(Facebook.isValidHandle("john.common")); // extends beyond ".com"
        assertTrue(Facebook.isValidHandle("john.network")); // extends beyond ".net"
        assertTrue(Facebook.isValidHandle("sarahWalters1")); // lower & uppercase with digits
        assertTrue(Facebook.isValidHandle("123Steve.3")); // start with digits
        assertTrue(Facebook.isValidHandle("Nat.321")); // start with uppercase letter

    }
    
}
