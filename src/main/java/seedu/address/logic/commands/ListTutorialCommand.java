package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TUTORIALS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.NavigationMode;

/**
 * Lists the tutorials in the address book.
 */
public class ListTutorialCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = "Usage: tutorial list";

    public static final String MESSAGE_SUCCESS = "Listed all tutorials";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredTutorialList(PREDICATE_SHOW_ALL_TUTORIALS);

        assert model.check();
        return new CommandResult(MESSAGE_SUCCESS, NavigationMode.TUTORIAL);
    }
}
