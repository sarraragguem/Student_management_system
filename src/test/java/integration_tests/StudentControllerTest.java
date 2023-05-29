package integration_tests;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

//import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.service.StudentService;
import net.javaguides.sms.StudentManagementSystemApplication;
import net.javaguides.sms.controller.StudentController;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = StudentManagementSystemApplication.class)
@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    public void testListStudents() throws Exception {
        // Prepare test data
    	Student student1 = new Student();
    	student1.setFirstName("John");
    	student1.setLastName("Doe");
    	student1.setEmail("john@example.com");

    
        Student student2 = new Student();
        student1.setFirstName("Jane");
        student1.setLastName("Smith");
        student1.setEmail("jane@example.com");
        
        List<Student> students = Arrays.asList(student1, student2);

        // Mock the service method
        when(studentService.getAllStudents()).thenReturn(students);

        // Perform the integration test
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("students"))
                .andExpect(model().attribute("students", students));
    }

    @Test
    public void testCreateStudentForm() throws Exception {
        // Perform the integration test
        mockMvc.perform(get("/students/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("create_student"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    public void testSaveStudent() throws Exception {
        // Prepare test data
    	Long studentId = 1L;
    	Student student = new Student();
    	student.setFirstName("John");
    	student.setLastName("Doe");
    	student.setEmail("john@example.com");


        // Mock the service method
        when(studentService.saveStudent(any(Student.class))).thenReturn(student);

        // Perform the integration test
        mockMvc.perform(post("/students")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("email", "john@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"));
    }

    @Test
    public void testEditStudentForm() throws Exception {
        // Prepare test data
        Long studentId = 1L;
        Student student = new Student();
    	student.setFirstName("John");
    	student.setLastName("Doe");
    	student.setEmail("john@example.com");

        // Mock the service method
        when(studentService.getStudentById(studentId)).thenReturn(student);

        // Perform the integration test
        mockMvc.perform(get("/students/edit/{id}", studentId))
                .andExpect(status().isOk())
                .andExpect(view().name("edit_student"))
                .andExpect(model().attribute("student", student));
    }

    @Test
    public void testUpdateStudent() throws Exception {
        // Prepare test data
    	Long studentId = 1L;
        Student student = new Student();
    	student.setFirstName("John");
    	student.setLastName("Doe");
    	student.setEmail("john@example.com");


        // Mock the service method
        when(studentService.getStudentById(studentId)).thenReturn(student);

        // Perform the integration test
        mockMvc.perform(post("/students/{id}", studentId)
                        .param("firstName", "UpdatedName")
                        .param("lastName", "Doe")
                        .param("email", "john@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        // Prepare test data
        Long studentId = 1L;

        // Perform the integration test
        mockMvc.perform(get("/students/{id}", studentId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"));

        // Verify the service method is called
        verify(studentService, times(1)).deleteStudentById(studentId);
    }
}
