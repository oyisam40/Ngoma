package com.example.ngoma;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // Automatically loads index.html when you go to http://localhost:8080/
    @GetMapping("/")
    public String loginPage() {
        return "forward:/index.html";
    }

    // Loads signup.html when you go to http://localhost:8080/signup
    @GetMapping("/signup")
    public String signupPage() {
        return "forward:signup.html";
    }

    // Loads dashboard.html when you go to http://localhost:8080/dashboard
    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "forward:/dashboard.html";
    }
}
