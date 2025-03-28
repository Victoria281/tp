package seedu.address.storage.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalStudents;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalStudentsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidStudentAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicateStudentAddressBook.json");

    @Test
    public void toModelType_typicalStudentsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil
                        .readJsonFile(TYPICAL_PERSONS_FILE, JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalStudentsAddressBook = TypicalStudents.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalStudentsAddressBook);
    }

    @Test
    public void toModelType_invalidStudentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil
                        .readJsonFile(INVALID_PERSON_FILE, JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateStudents_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil
                        .readJsonFile(DUPLICATE_PERSON_FILE, JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON,
                        dataFromFile::toModelType);
    }

    @Test
    public void toModelType_inclTutorials_success() throws Exception {
        final Path filename = TEST_DATA_FOLDER.resolve("typicalStudentsInclTutorials.json");
        var dataFromFile = JsonUtil.readJsonFile(filename, JsonSerializableAddressBook.class).orElseThrow();
        var modelType = dataFromFile.toModelType();

        AddressBook typicalStudentsAddressBook = TypicalStudents.getTypicalAddressBookInclTutorials();
        assertEquals(typicalStudentsAddressBook, modelType);
    }

}
