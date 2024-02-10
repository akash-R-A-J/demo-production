package com.example.demo;

import java.util.ArrayList;

public class MailCategorizationService {
    private EmailLinkExtractor emailLinkExtractor = new EmailLinkExtractor();

    public String categorizeMail(String mailSubject, String mailBody) {
        ArrayList<String[]> list = emailLinkExtractor.emailLinkExtractor(mailBody);
        return getType(list);
    }

    private String getType(ArrayList<String[]> list) {
        boolean containsMeeting = false;
        boolean containsForm = false;
        boolean containsOTP = false;

        for (String[] urlInfo : list) {
            String urlType = urlInfo[1];

            if (urlType.equals("Meeting")) {
                containsMeeting = true;
            } else if (urlType.equals("Form")) {
                containsForm = true;
            } else if (urlType.equals("OTP")) {
                containsOTP = true;
            }
        }

        if (containsMeeting) {
            return "Meeting";
        } else if (containsForm) {
            return "Form";
        } else if (containsOTP) {
            return "OTP";
        } else {
            return "Other";
        }
    }

    public static void main(String[] args) {
        String subject = "Limited Time Offer - Expires Soon!";
        String body = "Act now to avail of this exclusive limited-time offer.";
        MailCategorizationService m = new MailCategorizationService();
        String mailCategory = m.categorizeMail(subject, body);
        System.out.println("Mail Category: " + mailCategory);
    }
}
