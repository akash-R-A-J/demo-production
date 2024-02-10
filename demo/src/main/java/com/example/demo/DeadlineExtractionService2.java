package com.example.demo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeadlineExtractionService2 {

    public LocalDate extractDeadline(String mailBody) {
        String datePattern = "\\b(?:deadline|due|final\\s*deadline)\\s*:?\\s*(\\d{4}-\\d{2}-\\d{2}|\\d{1,2}-\\d{1,2}-\\d{4}|\\d{1,2}\\s*[-,]\\s*(?:January|February|March|April|May|June|July|August|September|October|November|December)\\s*[-,]?\\s*\\d{4}|\\b(?:January|February|March|April|May|June|July|August|September|October|November|December)\\s+\\d{1,2},\\s+\\d{4})\\b";

        Pattern pattern = Pattern.compile(datePattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(mailBody);

        LocalDate deadline = null;

        while (matcher.find()) {
            String dateString = matcher.group(1).trim();

            try {
                if (dateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    // Dates in yyyy-MM-dd format
                    deadline = LocalDate.parse(dateString);
                } else if (dateString.matches(
                        "\\b(?:January|February|March|April|May|June|July|August|September|October|November|December)\\s+\\d{1,2},\\s+\\d{4}")) {
                    // Dates in "month day, year" format (e.g., "September 30, 2023")
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
                    deadline = LocalDate.parse(dateString, formatter);
                } else {
                    // Parse other date formats
                    DateTimeFormatter[] dateFormatters = {
                            DateTimeFormatter.ofPattern("d-MMMM-yyyy"),
                            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                            DateTimeFormatter.ofPattern("dd-MMM-yyyy"),
                    };

                    for (DateTimeFormatter formatter : dateFormatters) {
                        try {
                            deadline = LocalDate.parse(dateString, formatter);
                            break;
                        } catch (Exception e) {
                            // Try the next formatter
                        }
                    }
                }

                if (deadline != null) {
                    break; // Stop processing if a valid deadline is found
                }
            } catch (Exception e) {
                // Handle parsing exceptions, if any
            }
        }

        return deadline;
    }

    // It will return list of dates which are extracted from the given string
    // (subject/body)
    public LocalDate extractDates(String input) {
        List<LocalDate> dates = new ArrayList<>();

        // Define date patterns for different formats
        String[] datePatterns = {
                "\\b\\d{4}-\\d{2}-\\d{2}\\b", // yyyy-MM-dd
                "\\b\\d{1,2}-(?:January|February|March|April|May|June|July|August|September|October|November|December)(?:,)? \\d{4}\\b", // d-Month-yyyy
                "\\b\\w{3} \\d{1,2}, \\d{4}\\b", // MMM dd, yyyy (e.g., Sep 30, 2023)
                "\\b\\d{1,2}/\\d{1,2}/\\d{4}\\b" // MM/dd/yyyy (e.g., 09/30/2023)
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

        if (dates.size() == 0)
            return null;

        return dates.get(0);
    }

    // helper to above function
    private static String getDateFormat(String pattern) {
        switch (pattern) {
            case "\\b\\d{4}-\\d{2}-\\d{2}\\b":
                return "yyyy-MM-dd";
            case "\\b\\d{1,2}-(?:January|February|March|April|May|June|July|August|September|October|November|December)(?:,)? \\d{4}\\b":
                return "d-MMMM-yyyy";
            case "\\b\\w{3} \\d{1,2}, \\d{4}\\b":
                return "MMM dd, yyyy";
            case "\\b\\d{1,2}/\\d{1,2}/\\d{4}\\b":
                return "MM/dd/yyyy";
            default:
                return "yyyy-MM-dd"; // Default format
        }
    }

    // public LocalDate extractDate(String subject) {
    // String DATE_FORMAT = "yyyy-MM-dd";
    // String patternString =
    // "\\b\\d{4}-\\d{2}-\\d{2}\\b|\\b\\d{1,2}-(?:January|February|March|April|May|June|July|August|September|October|November|December)(?:,)?
    // \\d{4}\\b";

    // String patternString = Sep 30, 2023, 2:21 PM (3 days ago)
    // "\\b\\d{4}-\\d{2}-\\d{2}\\b|\\b\\d{1,2}-(?:January|February|March|April|May|June|July|August|September|October|November|December)(?:,)?
    // \\d{4}\\b";
    // Pattern pattern = Pattern.compile(patternString);
    // Matcher matcher = pattern.matcher(subject);

    // if (matcher.find()) {
    // String dateString = matcher.group();

    // DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    // LocalDate extractedDate = LocalDate.parse(dateString, formatter);
    // System.out.println("Extracted Date : " + extractedDate);
    // return extractedDate;
    // }

    // return null;
    // }

    public static void main(String[] args) {
        String mailBody = "Please fill out the form by the deadline: 2023-06-30. Don't forget! The meeting is scheduled for 23-May, 2023.";
        DeadlineExtractionService2 de = new DeadlineExtractionService2();
        de.extractDeadline(mailBody);
    }
}
