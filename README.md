# Phishing-Link-Checker

A Java-based tool to help identify phishing emails and malicious links by analyzing text for common phishing indicators.

## Features

### ✨ New Features Added:
- **Multi-check Mode**: Check multiple links/messages in one session
- **Advanced Keyword Detection**: Categorized keywords into HIGH and MEDIUM risk levels
- **Shortened URL Detection**: Identifies suspicious shortened URLs (bit.ly, tinyurl, etc.)
- **Suspicious Domain Database**: Built-in list of known phishing domain patterns
- **Homoglyph Detection**: Identifies lookalike characters (0 vs O, 1 vs l)
- **Urgency Indicator Analysis**: Counts excessive exclamation marks
- **Risk Score System**: 0-15 point scale with clear risk levels:
  - **0-2**: LOW RISK ✅
  - **3-5**: MEDIUM RISK ⚠️
  - **6-9**: HIGH RISK ⚠️
  - **10+**: CRITICAL RISK 🚨
- **Security Tips**: Context-aware advice based on detected threats
- **Better Feedback**: Clear risk levels and actionable recommendations

### 🐛 Bug Fixes:
- Fixed negative risk score issue
- Improved input validation (empty message handling)
- Better keyword categorization
- Proper HTTP vs HTTPS scoring

## How to Run

```bash
javac PhishingChecker.java
java PhishingChecker
```

## Usage Example

```
Type a suspicious message or link (or 'quit' to exit):
Click here to verify your PayPal account urgently!!

--- Analysis Results ---
Risk Score: 8/15
Risk Level: ⚠️  HIGH RISK
Recommendation: Do not click any links. Be extremely cautious.
```

## Detection Methods

- **Keyword Analysis**: Detects common phishing phrases
- **Protocol Checking**: Flags insecure HTTP links
- **URL Pattern Recognition**: Identifies shortened and suspicious URLs
- **Urgency Detection**: Analyzes tone and pressure tactics
- **Character Analysis**: Detects lookalike domain names

## Safety Tips

- Never click links from unexpected emails
- Always verify the sender's email address
- Hover over links to see the actual URL
- Banks and services won't ask for passwords via email
- Be suspicious of urgency and unusual requests