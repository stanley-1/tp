package socialite.logic.parser;

import static java.util.Objects.requireNonNull;

import java.io.File;

import socialite.commons.core.index.Index;
import socialite.logic.commands.PictureCommand;
import socialite.logic.parser.exceptions.ParseException;
import socialite.ui.MainWindow;

public class PictureCommandParser implements Parser<PictureCommand> {

    @Override
    public PictureCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Index index;

        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException pe) {
            // TODO: Use proper error message
            throw new ParseException("invalid index");
        }

        MainWindow window = MainWindow.getWindow();
        File file = window.getFile();
        return new PictureCommand(index, file);
    }
}
