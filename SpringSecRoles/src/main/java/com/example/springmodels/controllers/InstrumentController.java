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
@RequestMapping("/instruments")
public class InstrumentController {

    @Autowired
    private InstrumentRepository instrumentRepository;

    @GetMapping
    public String getAllInstruments(Model model) {
        model.addAttribute("instruments", instrumentRepository.findAll());
        return "instrument_list";
    }


    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String showAddForm(Model model) {
        model.addAttribute("instrument", new Instrument());
        return "instrument_form";
    }

    @PostMapping
    public String addInstrument(@ModelAttribute Instrument instrument) {
        instrumentRepository.save(instrument);
        return "redirect:/instruments";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String getInstrumentById(@PathVariable Long id, Model model) {
        Optional<Instrument> instrument = instrumentRepository.findById(id);
        if (instrument.isPresent()) {
            model.addAttribute("instrument", instrument.get());
            return "instrument_detail";
        } else {
            return "error/404";
        }
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Instrument> instrument = instrumentRepository.findById(id);
        if (instrument.isPresent()) {
            model.addAttribute("instrument", instrument.get());
            return "instrument_form";
        } else {
            return "error/404";
        }
    }

    @PostMapping("/{id}/update")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String updateInstrument(@PathVariable Long id, @ModelAttribute Instrument instrument) {
        instrument.setId(id);
        instrumentRepository.save(instrument);
        return "redirect:/instruments";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String deleteInstrument(@PathVariable Long id) {
        instrumentRepository.deleteById(id);
        return "redirect:/instruments";
    }
}
