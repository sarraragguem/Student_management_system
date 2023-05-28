package net.javaguides.sms;



import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;
import net.javaguides.sms.service.impl.StudentServiceImpl;




class StudentManagementSystemApplicationTests {

	@Test
	void contextLoads() {
	}

	@Mock 
	private StudentRepository studentrepo;

	private StudentServiceImpl studentService;
	@BeforeEach
	void setUp()
	{
		this.studentrepo = Mockito.mock(StudentRepository.class);
		this.studentService= new StudentServiceImpl(this.studentrepo);
	}

	@Test
	void getAllStudents()
	{
		List<Student> students = Arrays.asList(
			    new Student("Sarra", "Ragguem", "sarra.ragguem@insat.ucar.tn"),
			    new Student("Ilef", "Rjiba", "ilef.rjiba@insat.ucar.tn"),
			    new Student("Arij", "Habbechi", "Arij.habbechi@insat.ucar.tn")
			);

        when(studentrepo.findAll()).thenReturn(students);
        List<Student> result = studentService.getAllStudents();

        assertEquals(students.size(), result.size());
        assertEquals(students.get(0).getFirstName(), result.get(0).getFirstName());
        assertEquals(students.get(1).getLastName(), result.get(1).getLastName());
        assertEquals(students.get(2).getEmail(), result.get(2).getEmail());
	}

	@Test
	void saveStudent()
	{
		Student student = new Student("Sarra", "Ragguem", "sarra.ragguem@insat.ucar.tn");

		when(studentrepo.save(student)).thenReturn(student);

		Student  created = studentService.saveStudent(student);

		assertEquals(student.getFirstName(), created.getFirstName());
		assertEquals(student.getLastName(), created.getLastName());
		assertEquals(student.getEmail(), created.getEmail());
	}

	@Test 
	void getStudentById()
	{
		Student student = new Student("Sarra", "Ragguem", "sarra.ragguem@insat.ucar.tn");
		student.setId(89L);
		when(studentrepo.findById(student.getId())).thenReturn(Optional.of(student));
		Student  result = studentService.getStudentById(student.getId());

		assertEquals(student.getFirstName(), result.getFirstName());
		assertEquals(student.getLastName(), result.getLastName());
		assertEquals(student.getEmail(), result.getEmail());

	}

	@Test
	void updateStudent()
	{
		Student student = new Student("Sarra", "Ragguem", "sarra.ragguem@insat.ucar.tn");
		student.setId(89L);

		Student newStudent = new Student("Sarra", "Ragguem", "sarah.ragguem@gmail.com");
		newStudent.setId(89L);


		when(studentrepo.save(newStudent)).thenReturn(newStudent);

		student = studentService.updateStudent(newStudent);

		assertEquals(student.getFirstName(), newStudent.getFirstName());
		assertEquals(student.getLastName(), newStudent.getLastName());
		assertEquals(student.getEmail(), newStudent.getEmail());
	}

	@Test
	void deleteStudentById() {
		Student student = new Student("Sarra", "Ragguem", "sarra.ragguem@insat.ucar.tn");
		student.setId(89L);
		studentService.deleteStudentById(student.getId());
		verify(studentrepo).deleteById(student.getId());
	}
	}

