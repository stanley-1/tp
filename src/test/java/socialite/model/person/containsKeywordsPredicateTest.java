package socialite.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import socialite.testutil.PersonBuilder;

public class containsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        containsKeywordsPredicate firstPredicate = new containsKeywordsPredicate(firstPredicateKeywordList);
        containsKeywordsPredicate secondPredicate = new containsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        containsKeywordsPredicate firstPredicateCopy = new containsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One matching keyword
        containsKeywordsPredicate predicate = new containsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple matching keywords
        predicate = new containsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // One non-matching keyword, should return false
        predicate = new containsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new containsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        containsKeywordsPredicate predicate = new containsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new containsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new containsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .build()));
    }

    @Test
    public void test_personHasHandles() {
        containsKeywordsPredicate predicate = new containsKeywordsPredicate(Arrays.asList("p/instagram"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withInstagram("dummy_link").build()));
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").withInstagram(null).build()));
    }

    @Test
    public void test_tagContainsKeywords() {
        containsKeywordsPredicate predicate = new containsKeywordsPredicate(Arrays.asList("t/colleagues"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTags("colleagues","friends").build()));
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").withTags("friends").build()));
    }


}
