package com.example.demo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeadlineExtractionService {

    public LocalDate extractDeadline(String mailBody) {
        String datePattern = "\\b(?:deadline|due|final\\s*deadline)\\s*:?\\s*(\\d{4}-\\d{2}-\\d{2}|\\d{1,2}-\\d{1,2}-\\d{4}|\\d{1,2}\\s*[-,]\\s*(?:January|February|March|April|May|June|July|August|September|October|November|December)\\s*[-,]?\\s*\\d{4}|\\b(?:January|February|March|April|May|June|July|August|September|October|November|December)\\s+\\d{1,2},\\s+\\d{4})\\b";
        Pattern pattern = Pattern.compile(datePattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(mailBody);
        LocalDate deadline = null;

        while (matcher.find()) {
            String dateString = matcher.group(1).trim();

            try {
                deadline = parseDeadline(dateString);
                if (deadline != null) {
                    break;
                }
            } catch (DateTimeParseException e) {
                // Log or handle parsing exceptions
            }
        }

        return deadline;
    }

    public LocalDate extractDates(String input) {
        List<LocalDate> dates = new ArrayList<>();

        String[] datePatterns = {
                "\\b\\d{4}-\\d{2}-\\d{2}\\b", // yyyy-MM-dd
                "\\b\\d{1,2}-(?:January|February|March|April|May|June|July|August|September|October|November|December)(?:,)? \\d{4}\\b", // d-Month-yyyy
                "\\b\\w{3} \\d{1,2}, \\d{4}\\b", // MMM dd, yyyy (e.g., Sep 30, 2023)
                "\\b\\d{1,2}/\\d{1,2}/\\d{4}\\b", // MM/dd/yyyy (e.g., 09/30/2023)
                "\\b(?:January|February|March|April|May|June|July|August|September|October|November|December) \\d{1,2}, \\d{4}\\b" // MMM
                                                                                                                                   // d,
                                                                                                                                   // yyyy
                                                                                                                                   // (e.g.,
                                                                                                                                   // Oct
                                                                                                                                   // 4,
                                                                                                                                   // 2023)
        };

        for (String patternString : datePatterns) {
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(input);

            while (matcher.find()) {
                String dateString = matcher.group();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getDateFormat(patternString));
                LocalDate extractedDate = LocalDate.parse(dateString, formatter);
                dates.add(extractedDate);
            }
        }

        if (dates.isEmpty()) {
            return null;
        }

        return dates.get(0);
    }

    private static String getDateFormat(String pattern) {
        switch (pattern) {
            case "\\b\\d{4}-\\d{2}-\\d{2}\\b":
                return "yyyy-MM-dd";
            case "\\b\\d{1,2}-(?:January|February|March|April|May|June|July|August|September|October|November|December)(?:,)\\s+\\d{4}\\b":
                return "d-MMMM-yyyy";
            case "\\b\\w{3} \\d{1,2}, \\d{4}\\b":
                return "MMM dd, yyyy";
            case "\\b\\d{1,2}/\\d{1,2}/\\d{4}\\b":
                return "MM/dd/yyyy";
            case "\\b(?:January|February|March|April|May|June|July|August|September|October|November|December) \\d{1,2}, \\d{4},? \\d{1,2}:\\d{2}\\s(?:AM|PM)\\b":
                return "MMM d, yyyy, h:mm a";
            default:
                return "yyyy-MM-dd";
        }
    }

    private LocalDate parseDeadline(String dateString) {
        DateTimeFormatter[] dateFormatters = {
                DateTimeFormatter.ofPattern("d-MMMM-yyyy"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("dd-MMM-yyyy"),
                DateTimeFormatter.ofPattern("MMM d, yyyy, h:mm a")
        };

        for (DateTimeFormatter formatter : dateFormatters) {
            try {
                return LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException e) {
                // Try the next formatter
            }
        }

        return null;
    }

    public static void main(String[] args) {
        String mailBody = "Please fill out the form by the deadline: 2023-06-30. Don't forget! The meeting is scheduled for 23-May, 2023.";
        DeadlineExtractionService de = new DeadlineExtractionService();
        LocalDate extractedDeadline = de.extractDeadline(mailBody);
        System.out.println("Extracted Deadline: " + extractedDeadline);

        String input = "Dates in the text: 2023-06-30, June 23, 2023, 09/30/2023, Oct 4, 2023, 7:33 PM";
        LocalDate extractedDate = de.extractDates(input);
        System.out.println("Extracted Date: " + extractedDate);
    }
}
