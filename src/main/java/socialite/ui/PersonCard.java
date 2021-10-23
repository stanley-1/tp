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
import socialite.model.person.Remark;

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
    private HBox handles;
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
    private FlowPane dates;
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
        remark.managedProperty().bind(remark.visibleProperty());
        this.makeRemark(person.getRemark());
        this.makeHandle(person.getFacebook(), Platform.FACEBOOK);
        this.makeHandle(person.getInstagram(), Platform.INSTAGRAM);
        this.makeHandle(person.getTelegram(), Platform.TELEGRAM);
        this.makeHandle(person.getTiktok(), Platform.TIKTOK);
        this.makeHandle(person.getTwitter(), Platform.TWITTER);
        this.handles.setSpacing(8);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        person.getDates().value.values().stream()
                .sorted(Comparator.comparing(date -> date.getDate()))
                .forEach(date -> dates.getChildren().add(new Label(date.toString())));
    }

    private void makeRemark(Remark remark) {
        String value = remark.get();
        if (value != null && !value.equals("")) {
            this.remark.setText(value);
            this.remark.setVisible(true);
        } else {
            this.remark.setText(null);
            this.remark.setVisible(false);
        }
    }


    private void makeHandle(Handle handle, Platform platform) {
        Label label = null;
        ImageView icon = null;

        switch (platform) {
        case FACEBOOK:
            label = this.facebook;
            icon = this.facebookIcon;
            break;
        case INSTAGRAM:
            label = this.instagram;
            icon = this.instagramIcon;
            break;
        case TELEGRAM:
            label = this.telegram;
            icon = this.telegramIcon;
            break;
        case TIKTOK:
            label = this.tiktok;
            icon = this.tiktokIcon;
            break;
        case TWITTER:
            label = this.twitter;
            icon = this.twitterIcon;
            break;
        default:
        }

        // if platform is correct, label and icon should not be null
        assert label != null;
        assert icon != null;

        label.managedProperty().bind(label.visibleProperty());
        icon.managedProperty().bind(icon.visibleProperty());
        renderHandle(handle, label, icon, "/images/" + platform.name() + ".png");
    }


    private void renderHandle(Handle handle, Label label, ImageView icon, String iconFilePath) {
        if (handle.get() != null && !handle.get().equals("")) {
            icon.setImage(new Image(this.getClass().getResourceAsStream(iconFilePath)));
            icon.setVisible(true);

            label.setText("@" + handle + " ");
            label.setVisible(true);
            label.setOnMouseEntered(Event -> label.setUnderline(true));
            label.setOnMouseExited(Event -> label.setUnderline(false));
            label.setOnMouseClicked(Event -> this.openBrowser(handle.getUrl()));
        } else {
            icon.setVisible(false);
            label.setText(null);
            label.setVisible(false);
        }
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
