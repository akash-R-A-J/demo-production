package com.example.demo;

import java.util.ArrayList;

public class EmailLinkExtractor {

    public ArrayList<String[]> emailLinkExtractor(String emailBody) {
        String[] words = emailBody.split("\\s+");
        ArrayList<String[]> res = new ArrayList<>();

        for (String word : words) {
            if (isValidURL(word)) {
                String urlType = determineURLType(word);
                String[] urlInfo = new String[] { word, urlType };
                res.add(urlInfo);
            } else if (isOTP(word)) {
                String[] otpInfo = new String[] { word, "OTP" };
                res.add(otpInfo);
            }
        }

        return res;
    }

    private boolean isValidURL(String url) {
        return url.matches("^(https?|ftp)://.*$");
    }

    private String determineURLType(String url) {
        if (url.contains("meet.google.com") || url.contains("zoom.us")) {
            return "Meeting";
        } else if (url.contains("docs.google.com/forms") || url.contains("forms.office.com")) {
            return "Form";
        }
        return "Other";
    }

    private boolean isOTP(String word) {
        return word.matches("\\b\\d{6}\\b");
    }

    public static void main(String[] args) {
        EmailLinkExtractor ele = new EmailLinkExtractor();
        String emailContent = "Email Subject: Request to Contribute to Shared Google Drive for Collective Academic Benefit\n"
                +
                "...\n" +
                "Here's how you can contribute:\n" +
                "* Access the Google Drive Folder: https://drive.google.com/drive/folders/19qKzlH9lB6OmK16ugsYVRLsxsnLpqr5i?usp=sharing\n"
                +
                "* Use the OTP: 123456 to verify your identity\n" +
                "...\n" +
                "If you have any queries or suggestions, please feel free to reply to this email or reach out to me directly.";

        ArrayList<String[]> urlInfos = ele.emailLinkExtractor(emailContent);

        for (int i = 0; i < urlInfos.size(); i++) {
            String[] urlInfo = urlInfos.get(i);
            System.out.println("URL: " + urlInfo[0]);
            System.out.println("Type: " + urlInfo[1]);
        }
    }
}
