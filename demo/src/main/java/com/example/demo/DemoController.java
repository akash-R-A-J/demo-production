package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DemoController {

    @RequestMapping("/")
    public String index() {
        return "index"; // Returns the name of the HTML template (index.html)
    }

    @PostMapping("/process")
    public String processMail(@RequestParam("emailHeader") String emailHeader,
            @RequestParam("emailBody") String emailBody, Model model) {

        MailManagementService managementService = new MailManagementService();
        MailManagementService.Mail mail = managementService.processMail(emailHeader, emailBody);

        String messageToUser = managementService.scheduleDeletion(mail);

        model.addAttribute("category", mail.getCategory());
        if (mail.getArrivalDate() != null) {
            model.addAttribute("arrivalDate", mail.getArrivalDate());
        } else {
            model.addAttribute("arrivalDate", "Not Found");
        }

        if (mail.getMetadata() != null) {
            model.addAttribute("extractedData", mail.getMetadata());
        } else {
            model.addAttribute("extractedData", "Not Found");
        }

        if (mail.getExpirationDate() != null) {
            model.addAttribute("expirationDate", mail.getExpirationDate());
        } else {
            model.addAttribute("expirationDate", "Not Found");
        }

        model.addAttribute("messageToUser", messageToUser);

        return "result"; // Returns the name of the HTML template (result.html)
    }
}
