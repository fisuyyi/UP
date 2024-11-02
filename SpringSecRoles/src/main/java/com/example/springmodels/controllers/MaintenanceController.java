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
@RequestMapping("/maintenance")
@PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
public class MaintenanceController {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @GetMapping
    public String getAllMaintenanceRecords(Model model) {
        model.addAttribute("maintenanceRecords", maintenanceRepository.findAll());
        return "maintenance_list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("maintenance", new Maintenance());
        model.addAttribute("instruments", instrumentRepository.findAll());
        return "maintenance_form";
    }

    @PostMapping
    public String addMaintenance(@ModelAttribute Maintenance maintenance) {
        maintenanceRepository.save(maintenance);
        return "redirect:/maintenance";
    }

    @GetMapping("/{id}")
    public String getMaintenanceById(@PathVariable Long id, Model model) {
        Optional<Maintenance> maintenance = maintenanceRepository.findById(id);
        if (maintenance.isPresent()) {
            model.addAttribute("maintenance", maintenance.get());
            return "maintenance_detail";
        } else {
            return "error/404";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Maintenance> maintenance = maintenanceRepository.findById(id);
        if (maintenance.isPresent()) {
            model.addAttribute("maintenance", maintenance.get());
            model.addAttribute("instruments", instrumentRepository.findAll());
            return "maintenance_form";
        } else {
            return "error/404";
        }
    }

    @PostMapping("/{id}/update")
    public String updateMaintenance(@PathVariable Long id, @ModelAttribute Maintenance maintenance) {
        maintenance.setId(id);
        maintenanceRepository.save(maintenance);
        return "redirect:/maintenance";
    }

    @PostMapping("/{id}/delete")
    public String deleteMaintenance(@PathVariable Long id) {
        maintenanceRepository.deleteById(id);
        return "redirect:/maintenance";
    }
}

