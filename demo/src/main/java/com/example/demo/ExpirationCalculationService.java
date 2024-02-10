package com.example.demo;

import java.time.LocalDate;

public class ExpirationCalculationService {
    public LocalDate expirationCalculation(MailManagementService.Mail mail) {
        String category = mail.getCategory();
        LocalDate deadline = mail.getDeadline();
        LocalDate arrivalDate = mail.getArrivalDate();

        if (category == null || category.equals("Other")) {
            System.out.println("This mail will not be deleted.");
            return null;
        }

        LocalDate expirationDate = null;

        if (category.equals("Meeting")) {
            try {
                if (deadline != null) {
                    expirationDate = deadline.plusDays(2);
                } else if (arrivalDate != null) {
                    expirationDate = arrivalDate.plusMonths(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (category.equals("Form") || category.equals("Expiration Mail")) {
            if (deadline != null) {
                try {
                    expirationDate = deadline.plusDays(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (arrivalDate != null)
                    expirationDate = arrivalDate.plusMonths(6);
                else
                    expirationDate = LocalDate.now().plusYears(1);
            }
        } else if (category.equals("OTP")) {

            if (arrivalDate != null)
                expirationDate = arrivalDate.plusDays(7);
            else
                expirationDate = LocalDate.now().plusMonths(1);
        }

        System.out.println("Expiration date: " + expirationDate);

        return expirationDate;
    }
}
