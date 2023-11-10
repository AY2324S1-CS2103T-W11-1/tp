package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_TITLE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddLeaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leave.Description;
import seedu.address.model.leave.Range;
import seedu.address.model.leave.Title;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddLeaveCommandParser implements Parser<AddLeaveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLeaveCommand parse(String args) throws ParseException {
        Index index;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LEAVE_TITLE, PREFIX_LEAVE_DATE_START,
                        PREFIX_LEAVE_DATE_END, PREFIX_LEAVE_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_LEAVE_TITLE, PREFIX_LEAVE_DATE_START,
                PREFIX_LEAVE_DATE_END)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLeaveCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LEAVE_TITLE, PREFIX_LEAVE_DATE_START,
                PREFIX_LEAVE_DATE_END, PREFIX_LEAVE_DESCRIPTION);

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLeaveCommand.MESSAGE_USAGE), pe);
        }

        Title title;

        try {
            title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_LEAVE_TITLE).get());
        } catch (IllegalArgumentException e) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }

        Range dateRange = ParserUtil.parseNonNullRange(argMultimap.getValue(PREFIX_LEAVE_DATE_START).get(),
                argMultimap.getValue(PREFIX_LEAVE_DATE_END).get());


        Description description = argMultimap.getValue(PREFIX_LEAVE_DESCRIPTION)
                    .map(ParserUtil::parseDescription)
                    .orElse(new Description(Description.DESCRIPTION_PLACEHOLDER));
        return new AddLeaveCommand(index, title, dateRange, description);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
