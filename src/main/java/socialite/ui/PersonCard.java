package socialite.ui;

import java.io.IOException;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import socialite.model.handle.Handle;
import socialite.model.handle.Handle.Platform;
import socialite.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private ImageView tiktokIcon;
    @FXML
    private Label tiktok;
    @FXML
    private ImageView twitterIcon;
    @FXML
    private Label twitter;
    @FXML
    private ImageView facebookIcon;
    @FXML
    private Label facebook;
    @FXML
    private ImageView instagramIcon;
    @FXML
    private Label instagram;
    @FXML
    private ImageView telegramIcon;
    @FXML
    private Label telegram;
    @FXML
    private FlowPane tags;
    @FXML
    private Label remark;
    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        remark.setText(person.getRemark().value);
        this.makeHandle(person.getFacebook(), Platform.FACEBOOK);
        this.makeHandle(person.getInstagram(), Platform.INSTAGRAM);
        this.makeHandle(person.getTelegram(), Platform.TELEGRAM);
        this.makeHandle(person.getTiktok(), Platform.TIKTOK);
        this.makeHandle(person.getTwitter(), Platform.TWITTER);

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private String getUrl(Platform platform, String username) {
        String url;
        String path;
        switch (platform) {
        case FACEBOOK:
            url = "http://www.facebook.com/";
            path = username;
            break;
        case INSTAGRAM:
            url = "http://www.instagram.com/";
            path = username.substring(1);
            break;
        case TELEGRAM:
            url = "https://www.t.me/";
            path = username.substring(1);
            break;
        case TIKTOK:
            url = "http://www.tiktok.com/";
            path = username;
            break;
        case TWITTER:
            url = "http://www.twitter.com/";
            path = username.substring(1);
            break;
        default:
            url = "http://www.google.com";
            path = "/";
        }
        return url + path;
    }

    private void openBrowser(String url) {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();
        try {
            if (os.indexOf("win") >= 0) {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.indexOf("mac") >= 0) {
                runtime.exec("open " + url);
            } else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
                runtime.exec("xdg-open " + url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeHandle(Handle handle, Platform platform) {
        String value = handle.get();
        switch (platform) {
        case FACEBOOK:
            if (value != null && !value.equals("")) {
                this.facebook.setText(value + " ");
                this.facebookIcon.setImage(new Image(this.getClass().getResourceAsStream("/images/facebook.png")));
                this.facebook.setOnMouseEntered(Event -> this.facebook.setUnderline(true));
                this.facebook.setOnMouseExited(Event -> this.facebook.setUnderline(false));
                this.facebook.setOnMouseClicked(Event -> this.openBrowser(
                        this.getUrl(Platform.FACEBOOK, this.facebook.getText())));
            } else {
                this.facebook.setText(null);
                this.facebookIcon.setFitWidth(0);
            }
            break;
        case INSTAGRAM:
            if (value != null && !value.equals("")) {
                this.instagram.setText("@" + value + " ");
                this.instagramIcon.setImage(new Image(this.getClass().getResourceAsStream("/images/instagram.png")));
                this.instagram.setOnMouseEntered(Event -> this.instagram.setUnderline(true));
                this.instagram.setOnMouseExited(Event -> this.instagram.setUnderline(false));
                this.instagram.setOnMouseClicked(Event -> this.openBrowser(
                        this.getUrl(Platform.INSTAGRAM, this.instagram.getText())));
            } else {
                this.instagram.setText(null);
                this.instagramIcon.setFitWidth(0);
            }
            break;
        case TELEGRAM:
            if (value != null && !value.equals("")) {
                this.telegram.setText("@" + value + " ");
                this.telegramIcon.setImage(new Image(this.getClass().getResourceAsStream("/images/telegram.png")));
                this.telegram.setOnMouseEntered(Event -> this.telegram.setUnderline(true));
                this.telegram.setOnMouseExited(Event -> this.telegram.setUnderline(false));
                this.telegram.setOnMouseClicked(Event -> this.openBrowser(
                        this.getUrl(Platform.TELEGRAM, this.telegram.getText())));
            } else {
                this.telegram.setText(null);
                this.telegramIcon.setFitWidth(0);
            }
            break;
        case TIKTOK:
            if (value != null && !value.equals("")) {
                this.tiktok.setText("@" + value + " ");
                this.tiktokIcon.setImage(new Image(this.getClass().getResourceAsStream("/images/tik-tok.png")));
                this.tiktok.setOnMouseEntered(Event -> this.tiktok.setUnderline(true));
                this.tiktok.setOnMouseExited(Event -> this.tiktok.setUnderline(false));
                this.tiktok.setOnMouseClicked(Event -> this.openBrowser(
                        this.getUrl(Platform.TIKTOK, this.tiktok.getText())));
            } else {
                this.tiktok.setText(null);
                this.tiktokIcon.setFitWidth(0);
            }
            break;
        case TWITTER:
            if (value != null && !value.equals("")) {
                this.twitter.setText("@" + value + " ");
                this.twitterIcon.setImage(new Image(this.getClass().getResourceAsStream("/images/twitter.png")));
                this.twitter.setOnMouseEntered(Event -> this.twitter.setUnderline(true));
                this.twitter.setOnMouseExited(Event -> this.twitter.setUnderline(false));
                this.twitter.setOnMouseClicked(Event -> this.openBrowser(
                        this.getUrl(Platform.TWITTER, this.twitter.getText())));
            } else {
                this.twitter.setText(null);
                this.twitterIcon.setFitWidth(0);
            }
            break;
        default:
        }

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
