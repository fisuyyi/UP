package com.example.springmodels.controllers;

import com.example.springmodels.models.*;
import com.example.springmodels.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/payments")
@PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @GetMapping
    public String getAllPayments(Model model) {
        model.addAttribute("payments", paymentRepository.findAll());
        return "payment_list";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String showAddForm(Model model) {
        model.addAttribute("payment", new Payment());
        model.addAttribute("rentals", rentalRepository.findAll());
        return "payment_form";
    }

    @PostMapping
    public String addPayment(@ModelAttribute Payment payment) {
        paymentRepository.save(payment);
        return "redirect:/payments";
    }

    @GetMapping("/{id}")
    public String getPaymentById(@PathVariable Long id, Model model) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isPresent()) {
            model.addAttribute("payment", payment.get());
            return "payment_detail";
        } else {
            return "error/404";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isPresent()) {
            model.addAttribute("payment", payment.get());
            model.addAttribute("rentals", rentalRepository.findAll());
            return "payment_form";
        } else {
            return "error/404";
        }
    }

    @PostMapping("/{id}/update")
    public String updatePayment(@PathVariable Long id, @ModelAttribute Payment payment) {
        payment.setId(id);
        paymentRepository.save(payment);
        return "redirect:/payments";
    }

    @PostMapping("/{id}/delete")
    public String deletePayment(@PathVariable Long id) {
        paymentRepository.deleteById(id);
        return "redirect:/payments";
    }
}