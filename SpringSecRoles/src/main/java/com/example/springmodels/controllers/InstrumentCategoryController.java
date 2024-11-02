package com.example.springmodels.controllers;

import com.example.springmodels.models.*;
import com.example.springmodels.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/categories")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class InstrumentCategoryController {

    @Autowired
    private InstrumentCategoryRepository instrumentCategoryRepository;

    @GetMapping
    public String getAllCategories(Model model) {
        model.addAttribute("categories", instrumentCategoryRepository.findAll());
        return "category_list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new InstrumentCategory());
        return "category_form";
    }

    @PostMapping
    public String addCategory(@ModelAttribute InstrumentCategory category) {
        instrumentCategoryRepository.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/{id}")
    public String getCategoryById(@PathVariable Long id, Model model) {
        Optional<InstrumentCategory> category = instrumentCategoryRepository.findById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "category_detail";
        } else {
            return "error/404";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<InstrumentCategory> category = instrumentCategoryRepository.findById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "category_form";
        } else {
            return "error/404";
        }
    }

    @PostMapping("/{id}/update")
    public String updateCategory(@PathVariable Long id, @ModelAttribute InstrumentCategory category) {
        category.setId(id);
        instrumentCategoryRepository.save(category);
        return "redirect:/categories";
    }

    @PostMapping("/{id}/delete")
    public String deleteCategory(@PathVariable Long id) {
        instrumentCategoryRepository.deleteById(id);
        return "redirect:/categories";
    }
}

