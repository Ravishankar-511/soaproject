package soa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import soa.entity.Result;
import soa.repository.ResultRepository;

@Service
public class ResultService {

    private final ResultRepository resultRepository;

    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    // Add result
    public Result addResult(Result result) {
        return resultRepository.save(result);
    }

    // Get all results
    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    // Get result by ID
    public Result getResultById(Long id) {
        return resultRepository.findById(id).orElse(null);
    }

    // Update result
    public Result updateResult(Long id, Result result) {

        Result existingResult =
                resultRepository.findById(id).orElse(null);

        if (existingResult == null) {
            return null;
        }

        existingResult.setStudentId(result.getStudentId());
        existingResult.setSubject(result.getSubject());
        existingResult.setMarks(result.getMarks());
        existingResult.setGrade(result.getGrade());

        return resultRepository.save(existingResult);
    }

    // Delete result
    public boolean deleteResult(Long id) {

        if (!resultRepository.existsById(id)) {
            return false;
        }

        resultRepository.deleteById(id);
        return true;
    }

    // Get results by student ID
    public List<Result> getResultsByStudentId(Long studentId) {
        return resultRepository.findByStudentId(studentId);
    }
}