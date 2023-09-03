
import java.io.Serializable;

/**
 *
 * @author Brian
 */
public class Student implements Serializable {

    private String studentName;
    private String studentID;
    private double studentScore;

    public Student(String studentName, String studentID, double studentScore) {
        this.studentName = studentName;
        this.studentID = studentID;
        this.studentScore = studentScore;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public double getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(double studentScore) {
        this.studentScore = studentScore;
    }

    public String toString() {
        return studentName + " " + studentID + " " + studentScore;
    }
}
