package com.tttn.webthitracnghiem.controller;

import com.tttn.webthitracnghiem.model.News;
import com.tttn.webthitracnghiem.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
@RestController
@RequestMapping("/api/news")
public class NewsController {
   @Autowired
    private INewsService newsService;

    @GetMapping("/list")
    public Page<News> getNewsList(@RequestParam(value = "keyword", required = false) String keyword,
                                  @PageableDefault(value = 5) Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            return newsService.findByTitle(keyword, pageable);
        } else {
            return newsService.findAll(pageable);
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("news", new News());
        return "news/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        News news = newsService.findById(id);
        if (news == null) {
            // Nếu không tìm thấy tin tức, có thể redirect đến danh sách
            return "redirect:/news/list";
        }
        model.addAttribute("news", news);
        return "news/edit";
    }

    @GetMapping("/delete/{id}")
    public String remove(@PathVariable Integer id, RedirectAttributes ra) {
        News news = newsService.findById(id);
        if (news != null) {
            newsService.remove(news);
            ra.addFlashAttribute("message", "Xóa tin tức thành công!");
        } else {
            ra.addFlashAttribute("message", "Không tìm thấy tin tức để xóa!");
        }
        return "redirect:/news/list";
    }
}
