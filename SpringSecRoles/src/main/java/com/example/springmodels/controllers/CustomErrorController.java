package com.example.springmodels.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == 403) {
                model.addAttribute("errorMessage", "Доступ запрещен: у вас нет прав для просмотра этой страницы.");
                return "error-403"; // Страница ошибки 403
            }
        }

        model.addAttribute("errorMessage", "Произошла ошибка. Пожалуйста, попробуйте позже.");
        return "error"; // Общая страница ошибки
    }


}

