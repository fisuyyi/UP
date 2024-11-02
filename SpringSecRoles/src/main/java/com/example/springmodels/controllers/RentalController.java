package com.example.springmodels.controllers;

import com.example.springmodels.models.Rental;
import com.example.springmodels.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public String getAllRentals(Model model) {
        model.addAttribute("rentals", rentalRepository.findAll());
        return "rental_list";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("rental", new Rental());
        model.addAttribute("instruments", instrumentRepository.findAll());
        model.addAttribute("customers", customerRepository.findAll());
        return "rental_form";
    }

    @PostMapping
    public String addRental(@ModelAttribute Rental rental) {
        rentalRepository.save(rental);
        return "redirect:/rentals";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/{id}")
    public String getRentalById(@PathVariable Long id, Model model) {
        Optional<Rental> rental = rentalRepository.findById(id);
        if (rental.isPresent()) {
            model.addAttribute("rental", rental.get());
            return "rental_detail";
        } else {
            return "error/404";
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Rental> rental = rentalRepository.findById(id);
        if (rental.isPresent()) {
            model.addAttribute("rental", rental.get());
            model.addAttribute("instruments", instrumentRepository.findAll());
            model.addAttribute("customers", customerRepository.findAll());
            return "rental_form";
        } else {
            return "error/404";
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/{id}/update")
    public String updateRental(@PathVariable Long id, @ModelAttribute Rental rental) {
        rental.setId(id);
        rentalRepository.save(rental);
        return "redirect:/rentals";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/{id}/delete")
    public String deleteRental(@PathVariable Long id) {
        rentalRepository.deleteById(id);
        return "redirect:/rentals";
    }
}
