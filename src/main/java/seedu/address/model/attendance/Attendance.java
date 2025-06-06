package seedu.address.model.attendance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.model.student.Student;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.uniquelist.Identifiable;

/**
 * Represents an attendance record
 */
public record Attendance(Tutorial tutorial, Student student,
                List<Integer> attendances) implements Identifiable<Attendance> {
    private static final int NUMBER_OF_WEEKS = 11;
    private static final int ABSENT = 0;
    private static final int PRESENT = 1;

    public Attendance(Tutorial tutorial, Student student) {
        this(tutorial, student, new ArrayList<>(Collections.nCopies(NUMBER_OF_WEEKS, ABSENT)));
    }

    public Attendance(Attendance other) {
        this(other.tutorial, other.student, other.attendances);
    }

    public Attendance setTutorial(Tutorial t) {
        return new Attendance(t, student, attendances);
    }

    public Attendance setStudent(Student s) {
        return new Attendance(tutorial, s, attendances);
    }

    /**
     * Marks or unmarks attendance for the specific week
     *
     * @param week
     *            Week of attendance
     * @param isPresent
     *            Value is true if mark as present, false if mark as absent
     */
    public void setAttendance(int week, boolean isPresent) {
        if (isPresent) {
            attendances.set(week - 3, PRESENT);
        } else {
            attendances.set(week - 3, ABSENT);
        }
    }

    public void setAttendances(List<Integer> attendances) {
        this.attendances.clear();
        this.attendances.addAll(attendances);
    }

    @Override
    public boolean hasSameIdentity(Attendance other) {
        if (other == null) {
            return false;
        }

        return this.tutorial.hasSameIdentity(other.tutorial) && this.student.hasSameIdentity(other.student);
    }
}
