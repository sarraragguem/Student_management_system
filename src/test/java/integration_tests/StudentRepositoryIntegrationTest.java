package integration_tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.javaguides.sms.StudentManagementSystemApplication;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudentManagementSystemApplication.class)
public class StudentRepositoryIntegrationTest {

	
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
    public void testFindAll() {
        // Perform the integration test
        List<Student> students = studentRepository.findAll();

        // Verify the result
        assertEquals(4, students.size());
        assertEquals("John", students.get(0).getFirstName());
        assertEquals("Doe", students.get(0).getLastName());
        assertEquals("john@example.com", students.get(0).getEmail());
        assertEquals("Jane", students.get(1).getFirstName());
        assertEquals("Smith", students.get(1).getLastName());
        assertEquals("jane@example.com", students.get(1).getEmail());
    }

    @Test
    public void testSave() {
        // Prepare a new student
        Student newStudent = new Student("Alice", "Johnson", "alice@example.com");

        // Perform the integration test
        Student savedStudent = studentRepository.save(newStudent);

        // Verify the result
        assertNotNull(savedStudent.getId());
        assertEquals("Alice", savedStudent.getFirstName());
        assertEquals("Johnson", savedStudent.getLastName());
        assertEquals("alice@example.com", savedStudent.getEmail());
        
    }

    @Test
    public void testDeleteById() {
        // Prepare a student ID for deletion
    	Student student = new Student("John", "Doe", "john@example.com");
        Student savedStudent = studentRepository.save(student);
        Long studentId = savedStudent.getId();

        // Perform the integration test
        studentRepository.deleteById(studentId);

        // Verify the result
        assertFalse(studentRepository.existsById(studentId));
    }
}

