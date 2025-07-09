package com.tttn.webthitracnghiem.service;

import com.tttn.webthitracnghiem.model.Result;
import com.tttn.webthitracnghiem.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    // Lấy 10 kết quả cao nhất
    public List<Result> getTopTen() {
        return resultRepository.findTopTen();
    }

    // Tìm lịch sử điểm của một người dùng
    public List<Result> findByHistory(String id) {
        return resultRepository.findByHistory(id);
    }

    // Tính tổng điểm của một người dùng
    public Object findSum(String id) {
        return resultRepository.findSum(id);
    }

    // Tính điểm trung bình của một người dùng
    public Object findAvg(String id) {
        return resultRepository.findAvg(id);
    }

    // Lấy tất cả các kết quả
    public List<Result> findAll() {
        return resultRepository.findAll();
    }

    // Tìm kết quả theo ID
    public Result findById(int id) {
        return resultRepository.findById(id).orElse(null);
    }

    // Lấy kết quả cao nhất của một bài kiểm tra
    public List<Result> findTopByExam(int idExam) {
        return resultRepository.findTopByExam(idExam);
    }
}
