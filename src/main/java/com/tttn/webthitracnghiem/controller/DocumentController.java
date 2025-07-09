package com.tttn.webthitracnghiem.controller;

import com.tttn.webthitracnghiem.model.Document;
import com.tttn.webthitracnghiem.service.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    @Autowired
    private IDocumentService documentService;

    @GetMapping("/list")
    public Page<Document> getAllDocuments(@RequestParam(value = "keyword", required = false) String keyword,
            @PageableDefault(value = 5) Pageable pageable) {
        System.out.println("API /api/document/list called with keyword: " + keyword + ", pageable: " + pageable);
        Page<Document> documents = (keyword != null && !keyword.isEmpty())
                ? documentService.findByTitle(keyword, pageable)
                : documentService.findAll(pageable);
        System.out.println("Returning " + documents.getTotalElements() + " documents");
        return documents;
    }

    @GetMapping("/edit/{id}")
    public Document getDocumentById(@PathVariable String id) {
        return documentService.findById(Integer.parseInt(id));
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDocument(@PathVariable String id) {
        Document document = documentService.findById(Integer.parseInt(id));
        if (document != null) {
            documentService.remove(document);
            return "Xóa tài liệu thành công!";
        } else {
            return "Không tìm thấy tài liệu để xóa!";
        }
    }
}