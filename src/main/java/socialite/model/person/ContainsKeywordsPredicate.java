package socialite.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import socialite.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class ContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public ContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    //helper functions to test person's name, tags or handles respectively.
    private boolean testName(Person person, String keyword) {
        String name = person.getName().fullName;
        return Pattern.compile("^" + Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(name).find();
    }

    private boolean testTags(Person person, String keyword) {
        for (Tag tag : person.getTags()) {
            String tagName = tag.tagName;
            boolean tagMatches = Pattern.compile("^" + Pattern.quote(keyword), Pattern.CASE_INSENSITIVE)
                    .matcher(tagName).find();
            if (tagMatches) {
                return true;
            }
        }
        //if none of the person's tags match the keyword.
        return false;
    }

    private boolean testPlatforms(Person person, String keyword) {

        switch (keyword) {
        case "facebook":
            return !(person.getFacebook().get() == null);
        case "instagram":
            return !(person.getInstagram().get() == null);
        case "telegram":
            return !(person.getTelegram().get() == null);
        case "tiktok":
            return !(person.getTiktok().get() == null);
        case "twitter":
            return !(person.getTwitter().get() == null);
        default:
            return false;
        }

    }

    @Override
    public boolean test(Person person) {
        //Tests just one person. For their name, tags or handles.
        if (keywords.size() == 0) {
            return false;
        }

        boolean matchesAll = true;

        for (String keyword : keywords) {
            if (keyword.startsWith("t/")) {
                matchesAll = matchesAll && testTags(person, keyword.substring(2));
            } else if (keyword.startsWith("p/")) {
                matchesAll = matchesAll && testPlatforms(person, keyword.substring(2));
            } else {
                matchesAll = matchesAll && testName(person, keyword);
            }
        }
        return matchesAll;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ContainsKeywordsPredicate) other).keywords)); // state check
    }

}
