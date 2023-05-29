package integration_tests;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import net.javaguides.sms.StudentManagementSystemApplication;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;
import net.javaguides.sms.service.StudentService;

@SpringBootTest(classes = StudentManagementSystemApplication.class)
@ActiveProfiles("test")
public class StudentServiceIntegrationTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    public void setUp() {
        // Prepare test data
        Student student1 = new Student("John", "Doe", "john@example.com");
        Student student2 = new Student("Jane", "Smith", "jane@example.com");
        studentRepository.saveAll(List.of(student1, student2));
    }

    @AfterEach
    public void tearDown() {
        // Clean up test data after each test
        studentRepository.deleteAll();
    }

    @Test
    public void testGetAllStudents() {
        // Perform the integration test
        List<Student> students = studentService.getAllStudents();

        // Verify the result
        assertEquals(2, students.size());
    }

    @Test
    public void testSaveStudent() {
        // Prepare a new student
        Student newStudent = new Student("Alice", "Johnson", "alice@example.com");

        // Perform the integration test
        Student savedStudent = studentService.saveStudent(newStudent);

        // Verify the result
        assertNotNull(savedStudent.getId());
        assertEquals("Alice", savedStudent.getFirstName());
        assertEquals("Johnson", savedStudent.getLastName());
        assertEquals("alice@example.com", savedStudent.getEmail());
    }

    @Test
    public void testUpdateStudent() {
        // Prepare an existing student
    	Student student = new Student("John", "Doe", "john@example.com");
        Student savedStudent = studentService.saveStudent(student);
        Long studentId = savedStudent.getId();

        Student existingStudent = studentRepository.findById(studentId).orElse(null);
        assertNotNull(existingStudent);
        existingStudent.setFirstName("UpdatedName");

        // Perform the integration test
        Student updatedStudent = studentService.updateStudent(existingStudent);

        // Verify the result
        assertEquals("UpdatedName", updatedStudent.getFirstName());
    }

    @Test
    public void testDeleteStudentById() {
        // Prepare a student ID for deletion
    	
    	Student student = new Student("John", "Doe", "john@example.com");
        Student savedStudent = studentService.saveStudent(student);
        Long studentId = savedStudent.getId();

        // Perform the integration test
        studentService.deleteStudentById(studentId);

        // Verify the result
        assertFalse(studentRepository.existsById(studentId));
    }
}