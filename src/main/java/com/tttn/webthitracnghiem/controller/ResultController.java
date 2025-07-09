package com.tttn.webthitracnghiem.controller;

import com.tttn.webthitracnghiem.model.Result;
import com.tttn.webthitracnghiem.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    // API để lấy 10 kết quả cao nhất
  @GetMapping("/top-ten")
    public ResponseEntity<List<Result>> getTopTen() {
        List<Result> topTenResults = resultService.getTopTen();
        if (topTenResults.isEmpty()) {
            return ResponseEntity.noContent().build(); // Trả về 204 No Content nếu không có dữ liệu
        }
        return ResponseEntity.ok(topTenResults); // Trả về 200 OK và kết quả
    }

    // API để lấy lịch sử điểm của một người dùng
    @GetMapping("/history/{id}")
    public List<Result> findByHistory(@PathVariable String id) {
        return resultService.findByHistory(id);
    }

    // API để tính tổng điểm của một người dùng
    @GetMapping("/sum/{id}")
    public Object findSum(@PathVariable String id) {
        return resultService.findSum(id);
    }

    // API để tính điểm trung bình của một người dùng
    @GetMapping("/avg/{id}")
    public Object findAvg(@PathVariable String id) {
        return resultService.findAvg(id);
    }

    // API để lấy tất cả kết quả
    @GetMapping("/all")
    public List<Result> findAll() {
        return resultService.findAll();
    }

    // API để lấy kết quả theo ID
    @GetMapping("/{id}")
    public Result findById(@PathVariable int id) {
        return resultService.findById(id);
    }

    // API để lấy kết quả cao nhất theo bài kiểm tra (ID bài kiểm tra)
    @GetMapping("/top-by-exam/{idExam}")
    public List<Result> findTopByExam(@PathVariable int idExam) {
        return resultService.findTopByExam(idExam);
    }
}
