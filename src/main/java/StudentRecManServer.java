
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Brian
 */
public class StudentRecManServer {

    private static ObjectOutputStream outStream;
    private static ObjectInputStream inStream;
    private static ServerSocket serverSocket;
    private static Socket clientSocket;

    private static ArrayList<Student> studentRecords = new ArrayList<>();

    public StudentRecManServer() {
        int port = 12345;
        try {
            System.out.println("Listening to the client request");
            serverSocket = new ServerSocket(port);

            clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
            getStreams();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void getStreams() {
        try {
            outStream = new ObjectOutputStream(clientSocket.getOutputStream());
            System.out.println("Success output stream");
            outStream.flush();
            System.out.println("Creating streams");
            inStream = new ObjectInputStream(clientSocket.getInputStream());
            System.out.println("Success input stream");

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void processClient() {

        while (true) {
            try {
//            System.out.println("Going into Arraylist");
                Object objectReceived;
                objectReceived = inStream.readObject();

                if (objectReceived instanceof Student) {

                    Student newStudent = (Student) objectReceived;
                    studentRecords.add(newStudent);
                    System.out.println("Added new Student record: " + newStudent.toString());

                } else if (objectReceived instanceof String && ((String) objectReceived).equals("Retrieve")) {

                    ArrayList studentList = (ArrayList) studentRecords.clone();
                    outStream.writeObject(studentList);
                    outStream.flush();
                } else if (objectReceived instanceof String && ((String) objectReceived).equals("Exit")) {
                    closeConnection();
                } else {
                    List<Student> searchResults = searchStudents((String) objectReceived);
                    outStream.writeObject(searchResults);
                    outStream.flush();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static List<Student> searchStudents(String query) {
        List<Student> searchStudents = new ArrayList<>();
        for (Student student : studentRecords) {
            if (student.getStudentID().toLowerCase().contains(query.toLowerCase())
                    || student.getStudentName().toLowerCase().contains(query.toLowerCase())) {
                searchStudents.add(student);

            }

        }
        return searchStudents;
    }

    public void closeConnection() {
        try {
            outStream.writeObject("About to close");
            System.out.println("Shutting down");
            inStream.close();
            outStream.close();
            clientSocket.close();
            System.exit(0);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        StudentRecManServer srms = new StudentRecManServer();
        srms.processClient();

    }
}
