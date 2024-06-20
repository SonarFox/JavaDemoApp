package com.example.fizzbang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FizzBangController {

    private static final Logger logger = LoggerFactory.getLogger(FizzBangController.class);

    @GetMapping("/")
    public String index() {
        logger.info("Accessed index page");
        return "index";
    }

    @PostMapping("/fizzbang")
    public String fizzBang(@RequestParam("number") int number, Model model) {
        logger.info("Received number: {}", number);
        if (number < 0 || number > 100) {
            logger.error("Invalid number: {}", number);
            throw new IllegalArgumentException("Number must be between 0 and 100");
        }

        String userId = "LeeFox";
        String database = "database";

        List<String> result = new ArrayList<>();
        int fizzBangCount = 0; // Counter for "FizzBang"
        for (int i = 1; i <= number; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                result.add("FizzBang");
                fizzBangCount++; // Increment the counter
            } else if (i % 3 == 0) {
                result.add("Fizz");
            } else if (i % 5 == 0) {
                result.add("Bang");
            } else {
                result.add(String.valueOf(i));
            }
        }
        model.addAttribute("result", result);
        model.addAttribute("fizzBangCount", fizzBangCount); // Add the counter to the model
        logger.info("Processed FizzBang for number: {}. FizzBang was printed {} times.", number, fizzBangCount);
        return "result";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleError(IllegalArgumentException ex, Model model) {
        logger.error("Error occurred: {}", ex.getMessage());
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
