package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.NavigationMode;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.student.Student;
import seedu.address.model.submission.Submission;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.tutorial.TutorialWithStudents;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT = "Could not save data to file %s due to "
                    + "insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and
     * {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        assert model.check();

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return model.getFilteredStudentList();
    }

    @Override
    public ObservableList<Tutorial> getFilteredTutorialList() {
        return model.getFilteredTutorialList();
    }

    @Override
    public ObservableList<Attendance> getFilteredAttendanceList() {
        return model.getFilteredAttendanceList();
    }

    @Override
    public ObservableList<Submission> getFilteredSubmissionList() {
        return model.getFilteredSubmissionList();
    }

    @Override
    public ObservableList<TutorialWithStudents> getFilteredTutorialWithStudents() {
        return model.getFilteredTutorialWithStudents();
    }

    @Override
    public ObjectProperty<Student> getSelectedStudent() {
        return model.getSelectedStudent();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public NavigationMode getNavigationMode() {
        return model.getNavigationMode();
    }

    @Override
    public void setNavigationMode(NavigationMode navigationMode) {
        model.setNavigationMode(navigationMode);
    }
}
