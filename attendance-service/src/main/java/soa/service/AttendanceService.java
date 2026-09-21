package soa.service;

import java.util.List;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import soa.entity.Attendance;
import soa.repository.AttendanceRepository;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final RestClient.Builder restClientBuilder;

    public AttendanceService(
            AttendanceRepository attendanceRepository,
            @LoadBalanced RestClient.Builder restClientBuilder) {
        this.attendanceRepository = attendanceRepository;
        this.restClientBuilder = restClientBuilder;
    }

    public Attendance addAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public Attendance getAttendanceById(Long id) {
        return attendanceRepository.findById(id).orElse(null);
    }

    public Attendance updateAttendance(Long id, Attendance attendance) {

        Attendance existingAttendance =
                attendanceRepository.findById(id).orElse(null);

        if (existingAttendance == null) {
            return null;
        }

        existingAttendance.setStudentId(attendance.getStudentId());
        existingAttendance.setDate(attendance.getDate());
        existingAttendance.setStatus(attendance.getStatus());

        return attendanceRepository.save(existingAttendance);
    }

    public boolean deleteAttendance(Long id) {

        if (!attendanceRepository.existsById(id)) {
            return false;
        }

        attendanceRepository.deleteById(id);
        return true;
    }

    public Object getStudentFromStudentService(Long studentId) {

        return restClientBuilder.build()
                .get()
                .uri("http://student-service/students/" + studentId)
                .retrieve()
                .body(Object.class);
    }
}