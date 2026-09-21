package soa.service;

import java.util.List;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import soa.entity.Student;
import soa.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final RestClient.Builder restClientBuilder;

    public StudentService(
            StudentRepository studentRepository,
            @LoadBalanced RestClient.Builder restClientBuilder) {

        this.studentRepository = studentRepository;
        this.restClientBuilder = restClientBuilder;
    }

    // Add student
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get student by ID
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    // Update student
    public Student updateStudent(Long id, Student student) {

        Student existingStudent = studentRepository.findById(id).orElse(null);

        if (existingStudent == null) {
            return null;
        }

        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setDepartment(student.getDepartment());
        existingStudent.setBatch(student.getBatch());

        return studentRepository.save(existingStudent);
    }

    // Delete student
    public boolean deleteStudent(Long id) {

        if (!studentRepository.existsById(id)) {
            return false;
        }

        studentRepository.deleteById(id);
        return true;
    }

    // Student Service → Result Service
    public Object getResultsFromResultService(Long studentId) {

        return restClientBuilder.build()
                .get()
                .uri("http://result-service/results/student/" + studentId)
                .retrieve()
                .body(Object.class);
    }
}