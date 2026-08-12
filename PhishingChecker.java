import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class PhishingChecker {

    // Common phishing keywords with risk levels
    private static final String[] HIGH_RISK_KEYWORDS = {
        "verify account", "confirm identity", "update payment", "suspicious activity",
        "account locked", "urgent action", "act now", "immediate action required"
    };

    private static final String[] MEDIUM_RISK_KEYWORDS = {
        "verify", "confirm", "update", "click here", "click now", "login",
        "password", "reset password", "reactivate", "action required"
    };

    // Known phishing domains (partial list)
    private static final Set<String> SUSPICIOUS_DOMAINS = new HashSet<>();
    static {
        SUSPICIOUS_DOMAINS.add("paypa1.com");
        SUSPICIOUS_DOMAINS.add("amaz0n.com");
        SUSPICIOUS_DOMAINS.add("go0gle.com");
        SUSPICIOUS_DOMAINS.add("microsft.com");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean continueChecking = true;

        System.out.println("========================================");
        System.out.println("Welcome to the Phishing Link Checker");
        System.out.println("========================================\n");

        while (continueChecking) {
            System.out.println("Type a suspicious message or link (or 'quit' to exit):");
            String userMessage = input.nextLine().trim();

            if (userMessage.equalsIgnoreCase("quit")) {
                continueChecking = false;
                break;
            }

            if (userMessage.isEmpty()) {
                System.out.println("Please enter a valid message.\n");
                continue;
            }

            // Calculate risk score
            int riskScore = analyzeMessage(userMessage);

            // Provide feedback
            displayResults(userMessage, riskScore);
            System.out.println();
        }

        System.out.println("Thank you for using the Phishing Link Checker. Stay safe!");
        input.close();
    }

    private static int analyzeMessage(String message) {
        int riskScore = 0;
        String lowerMessage = message.toLowerCase();

        // Check for high-risk keywords (3 points each)
        for (String keyword : HIGH_RISK_KEYWORDS) {
            if (lowerMessage.contains(keyword)) {
                riskScore += 3;
            }
        }

        // Check for medium-risk keywords (1 point each)
        for (String keyword : MEDIUM_RISK_KEYWORDS) {
            if (lowerMessage.contains(keyword)) {
                riskScore += 1;
            }
        }

        // Check for unsecure HTTP links (2 points)
        if (message.contains("http://")) {
            riskScore += 2;
        }

        // Check for shortened URLs (1 point)
        if (message.contains("bit.ly") || message.contains("tinyurl") || message.contains("short.link")) {
            riskScore += 1;
        }

        // Check for suspicious domains (2 points)
        if (containsSuspiciousDomain(message)) {
            riskScore += 2;
        }

        // Check for suspicious characters that resemble legitimate domains
        if (containsHomoglyphs(message)) {
            riskScore += 1;
        }

        // Check for excessive urgency indicators (1 point per indicator)
        int urgencyCount = countOccurrences(lowerMessage, "!");
        riskScore += Math.min(urgencyCount, 2);

        // Slight penalty reduction for having HTTPS (but not a bonus to avoid negative scores)
        if (message.contains("https://") && riskScore > 0) {
            riskScore = Math.max(0, riskScore - 1);
        }

        return Math.max(0, riskScore); // Ensure non-negative score
    }

    private static boolean containsSuspiciousDomain(String message) {
        for (String domain : SUSPICIOUS_DOMAINS) {
            if (message.toLowerCase().contains(domain)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsHomoglyphs(String message) {
        // Check for homoglyph patterns (0 instead of O, 1 instead of l, etc.)
        Pattern homoglyphPattern = Pattern.compile("[0O1l]{2,}"); // Multiple similar chars
        return homoglyphPattern.matcher(message).find();
    }

    private static int countOccurrences(String text, String character) {
        return text.length() - text.replace(character, "").length();
    }

    private static void displayResults(String message, int riskScore) {
        System.out.println("\n--- Analysis Results ---");
        System.out.println("Risk Score: " + riskScore + "/15");

        String riskLevel;
        String recommendation;

        if (riskScore >= 10) {
            riskLevel = "🚨 CRITICAL RISK";
            recommendation = "DO NOT CLICK ANY LINKS! This is very likely a phishing attempt.";
        } else if (riskScore >= 6) {
            riskLevel = "⚠️  HIGH RISK";
            recommendation = "Do not click any links. Be extremely cautious.";
        } else if (riskScore >= 3) {
            riskLevel = "⚠️  MEDIUM RISK";
            recommendation = "Be cautious. Verify the sender before clicking any links.";
        } else {
            riskLevel = "✅ LOW RISK";
            recommendation = "Appears safer, but always verify unexpected requests.";
        }

        System.out.println("Risk Level: " + riskLevel);
        System.out.println("Recommendation: " + recommendation);

        // Additional security tips
        displaySecurityTips(message);
    }

    private static void displaySecurityTips(String message) {
        System.out.println("\nSecurity Tips:");
        if (message.toLowerCase().contains("password") || message.toLowerCase().contains("verify")) {
            System.out.println("  • Never share passwords via email or links");
        }
        if (message.contains("http://")) {
            System.out.println("  • This link uses HTTP (insecure). Avoid clicking!");
        }
        if (message.contains("bit.ly") || message.contains("tinyurl")) {
            System.out.println("  • Shortened URLs hide the real destination. Be suspicious!");
        }
        System.out.println("  • Hover over links to see the actual URL before clicking");
    }
}