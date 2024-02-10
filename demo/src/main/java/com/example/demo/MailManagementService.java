package com.example.demo;

import java.time.LocalDate;

public class MailManagementService {
    private MailCategorizationService mailCategory;
    private DeadlineExtractionService mailDeadline;
    private ExpirationCalculationService mailExpiration;

    public MailManagementService() {
        mailCategory = new MailCategorizationService();
        mailDeadline = new DeadlineExtractionService();
        mailExpiration = new ExpirationCalculationService();
    }

    public void displayDetails(Mail mail) {
        System.out.println("Category : " + mail.getCategory());
        System.out.println("Flag : " + mail.getFlag());
        System.out.println("Arrival Date : " + mail.getArrivalDate());
        System.out.println("Extracted Date : " + mail.getMetadata());
        System.out.println("Deadline : " + mail.getDeadline());
        System.out.println("Expiration Date : " + mail.getExpirationDate());
    }

    public Mail processMail(String mailSubject, String mailBody) {
        String category = mailCategory.categorizeMail(mailSubject, mailBody);
        LocalDate deadline = mailDeadline.extractDeadline(mailBody);

        Mail mail = new Mail(category, deadline);
        mail.setMetadata(mailDeadline.extractDates(mailBody));
        mail.setArrivalDate(mailDeadline.extractDates(mailSubject));
        mail.setExpirationDate(mailExpiration.expirationCalculation(mail));

        displayDetails(mail);

        return mail;
    }

    public String scheduleDeletion(Mail mail) {
        LocalDate currentDate = LocalDate.now();
        LocalDate expirationDate = mail.getExpirationDate();

        if (expirationDate != null) {
            if (expirationDate.isBefore(currentDate)) {
                System.out.println("The mail has been deleted successfully.");
                return "The mail has been deleted successfully.";

            } else if (expirationDate.isAfter(currentDate)) {
                System.out.println("The mail is scheduled to be deleted on " + expirationDate + ".");
                return "The mail is scheduled to be deleted on " + expirationDate + ".";
            }
        } else {
            System.out.println("This mail will not be deleted.");
            return "This mail will not be deleted.";
        }

        return "Can't be recognized.";
    }

    public static class Mail {
        private String category;
        private LocalDate deadline;
        private LocalDate expirationDate;
        private LocalDate arrivalDate;
        private LocalDate extractedDate;
        private int flag;

        public Mail(String category, LocalDate deadline) {
            this.category = category;
            this.deadline = deadline;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getFlag() {
            return this.flag;
        }

        public String getCategory() {
            return this.category;
        }

        public LocalDate getDeadline() {
            return this.deadline;
        }

        public void setMetadata(LocalDate extractedDate) {
            this.extractedDate = extractedDate;
        }

        public LocalDate getMetadata() {
            return this.extractedDate;
        }

        public void setArrivalDate(LocalDate arrivalDate) {
            this.arrivalDate = arrivalDate;
        }

        public LocalDate getArrivalDate() {
            return this.arrivalDate;
        }

        public void setExpirationDate(LocalDate expirationDate) {
            this.expirationDate = expirationDate;
        }

        public LocalDate getExpirationDate() {
            return this.expirationDate;
        }
    }
}
