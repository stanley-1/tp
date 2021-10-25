package socialite.logic.parser;

import org.junit.jupiter.api.Test;

import socialite.logic.commands.AddCommand;
import socialite.logic.commands.DeleteCommand;
import socialite.logic.commands.EditCommand;
import socialite.logic.commands.FindCommand;
import socialite.logic.commands.HelpCommand;
import socialite.logic.commands.RemarkCommand;
import socialite.logic.commands.ShareCommand;


public class HelpCommandParserTest {

    private HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_validNoArgs_returnsGenericHelpMessage() {
        CommandParserTestUtil.assertParseSuccess(parser, "", new HelpCommand(HelpCommand.GENERIC_RESPONSE));
    }

    @Test
    public void parse_validArgAdd_returnsHelpForAdd() {
        CommandParserTestUtil.assertParseSuccess(parser, "add", new HelpCommand(AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgDelete_returnsHelpForDelete() {
        CommandParserTestUtil.assertParseSuccess(parser, "delete", new HelpCommand(DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgEdit_returnsHelpForEdit() {
        CommandParserTestUtil.assertParseSuccess(parser, "edit", new HelpCommand(EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgFind_returnsHelpForFind() {
        CommandParserTestUtil.assertParseSuccess(parser, "find", new HelpCommand(FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgRemark_returnsHelpForRemark() {
        CommandParserTestUtil.assertParseSuccess(parser, "remark", new HelpCommand(RemarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgShare_returnsHelpForShare() {
        CommandParserTestUtil.assertParseSuccess(parser, "share", new HelpCommand(ShareCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_returnsGenericHelpMessage() {
        CommandParserTestUtil.assertParseSuccess(parser, "How do I use this",
                new HelpCommand(HelpCommand.GENERIC_RESPONSE));
        CommandParserTestUtil.assertParseSuccess(parser, "981209", new HelpCommand(HelpCommand.GENERIC_RESPONSE));
    }
}
