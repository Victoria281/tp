package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkAttendanceCommand object
 */
public class MarkAttendanceCommandParser implements Parser<MarkAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * MarkAttendanceCommand and returns an MarkAttendanceCommand object for
     * execution.
     *
     * @throws ParseException
     *             if the user input does not conform the expected format
     */
    public MarkAttendanceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(" " + args, PREFIX_WEEK, PREFIX_ATTENDANCE_INDEX);

        if (!argMultimap.allPresent(PREFIX_WEEK, PREFIX_ATTENDANCE_INDEX)) {
            throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_WEEK);

        int week = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_WEEK).get()).getOneBased();

        if (week < MarkAttendanceCommand.START_WEEK || week > MarkAttendanceCommand.END_WEEK) {
            throw new ParseException(String.format(MarkAttendanceCommand.MESSAGE_INVALID_WEEK));
        }

        List<Index> indices = new ArrayList<>();
        for (String index : argMultimap.getAllValues(PREFIX_ATTENDANCE_INDEX)) {
            indices.add(ParserUtil.parseIndex(index));
        }

        return new MarkAttendanceCommand(week, indices);
    }
}
