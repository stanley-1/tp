package socialite.model.person;

import java.util.List;
import java.util.function.Predicate;

import socialite.commons.util.StringUtil;
import socialite.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    private boolean testName(Person person, String keyword) {
        return StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword);
    }

    private boolean testTags(Person person, String keyword) {
        for (Tag tag : person.getTags()){
            if (StringUtil.containsWordIgnoreCase(tag.tagName, keyword)){
                return true;
            }
        }
        return false;
    }

    private boolean testPlatforms(Person person, String keyword) {

        switch (keyword){
        case "facebook":
            return !person.getFacebook().value.equals("");
        case "instagram":
            return !person.getInstagram().value.equals("");
        case "telegram":
            return !person.getTelegram().value.equals("");
        case "tiktok":
            return !person.getTiktok().value.equals("");
        case "twitter":
            return !person.getTwitter().value.equals("");
        default:
            return false;
        }

    }

    @Override
    public boolean test(Person person) {
        //Tests just one person. For their name, tags or handles.
        for (String keyword : keywords){
            if (keyword.equals("all/")){
                return true;
            } else if(keyword.startsWith("t/")){
                return testTags(person, keyword.substring(2));
            } else if (keyword.startsWith("p/")){
                return testPlatforms(person, keyword.substring(2));
            } else{
                return testName(person, keyword);
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
