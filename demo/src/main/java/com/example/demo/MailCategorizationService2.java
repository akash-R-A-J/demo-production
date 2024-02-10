package com.example.demo;

// import java.util.Arrays;
import java.util.ArrayList;

public class MailCategorizationService2 {

    private EmailLinkExtractor emailLinkExtractor = new EmailLinkExtractor();
    // added some keywords
    // private static final String[] FORM_KEYWORDS = { "form", "response", "today is
    // the last day" }; // "deadline"
    // private static final String[] MEETING_KEYWORDS = { "meeting", "appointment",
    // "conference", "call", "schedule",
    // "agenda", "invite", "arrange" };
    // private static final String[] EXPIRATION_KEYWORDS = { "limited time offer",
    // "expires on", "time-sensitive",
    // "act now", "valid until", "offer expires", "renewal reminder", "last chance",
    // "urgent" };

    // convert all keywords to lowercase if needed
    // private static final String[] FORM_KEYWORDS_LOWER =
    // Arrays.stream(FORM_KEYWORDS).map(String::toLowerCase)
    // .toArray(String[]::new);
    // private static final String[] MEETING_KEYWORDS_LOWER =
    // Arrays.stream(MEETING_KEYWORDS).map(String::toLowerCase)
    // .toArray(String[]::new);
    // private static final String[] EXPIRATION_KEYWORDS_LOWER =
    // Arrays.stream(EXPIRATION_KEYWORDS)
    // .map(String::toLowerCase).toArray(String[]::new);

    public String categorizeMail(String mailSubject, String mailBody) {

        ArrayList<String[]> list = emailLinkExtractor.emailLinkExtractor(mailBody);

        return list.get(0)[1]; // return url type (meeting, form)

        /* UPDATE/REFINE THE BELOW CODE */
        // Convert mailSubject and mailBody to lowercase
        // String subjectLower = mailSubject.toLowerCase();
        // String bodyLower = mailBody.toLowerCase();

        // for (String keyword : FORM_KEYWORDS_LOWER) {
        // if (subjectLower.toLowerCase().contains(keyword) ||
        // bodyLower.toLowerCase().contains(keyword)) {
        // return "Form";
        // }
        // }

        // for (String keyword : MEETING_KEYWORDS_LOWER) {
        // if (subjectLower.toLowerCase().contains(keyword) ||
        // bodyLower.toLowerCase().contains(keyword)) {
        // return "Meeting";
        // }
        // }

        // for (String keyword : EXPIRATION_KEYWORDS_LOWER) {
        // if (subjectLower.toLowerCase().contains(keyword) ||
        // bodyLower.toLowerCase().contains(keyword)) {
        // return "Expiration Mail";
        // }
        // }

        // return "Other";
    }

    // Example usage:
    public static void main(String[] args) {
        String subject = "Limited Time Offer - Expires Soon!";
        String body = "Act now to avail of this exclusive limited-time offer.";
        MailCategorizationService m = new MailCategorizationService();
        String mailCategory = m.categorizeMail(subject, body);
        System.out.println("Mail Category: " + mailCategory);
    }
}
