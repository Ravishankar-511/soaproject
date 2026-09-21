package soa.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import soa.entity.Result;
import soa.service.ResultService;

@RestController
@RequestMapping("/results")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    // Add result
    @PostMapping
    public ResponseEntity<Result> addResult(
            @RequestBody Result result) {

        return ResponseEntity.ok(
                resultService.addResult(result)
        );
    }

    // Get all results
    @GetMapping
    public ResponseEntity<List<Result>> getAllResults() {

        return ResponseEntity.ok(
                resultService.getAllResults()
        );
    }

    // Get result by ID
    @GetMapping("/{id}")
    public ResponseEntity<Result> getResultById(
            @PathVariable Long id) {

        Result result = resultService.getResultById(id);

        if (result == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    // Update result
    @PutMapping("/{id}")
    public ResponseEntity<Result> updateResult(
            @PathVariable Long id,
            @RequestBody Result result) {

        Result updatedResult =
                resultService.updateResult(id, result);

        if (updatedResult == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedResult);
    }

    // Delete result
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResult(
            @PathVariable Long id) {

        boolean deleted = resultService.deleteResult(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    // Get results by student ID
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Result>> getResultsByStudentId(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                resultService.getResultsByStudentId(studentId)
        );
    }
}