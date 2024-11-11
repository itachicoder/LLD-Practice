package pu.com.ay.DesignPatterns.StructuralPatterns.Decorator;

// Step 1: Define the Notifier interface
interface Notifier {
    void send(String message);
}

// Step 2: Implement the basic EmailNotifier class as the Concrete Component
class EmailNotifier implements Notifier {
    private String email;

    public EmailNotifier(String email) {
        this.email = email;
    }

    @Override
    public void send(String message) {
        System.out.println("Sending email to " + email + ": " + message);
    }
}

// Step 3: Create the NotifierDecorator abstract class to serve as the base decorator
abstract class NotifierDecorator implements Notifier {
    protected Notifier wrappedNotifier;

    public NotifierDecorator(Notifier notifier) {
        this.wrappedNotifier = notifier;
    }

    @Override
    public void send(String message) {
        wrappedNotifier.send(message);
    }
}

// Step 4: Create Concrete Decorators for additional notification channels

class SMSNotifier extends NotifierDecorator {
    private String phoneNumber;

    public SMSNotifier(Notifier notifier, String phoneNumber) {
        super(notifier);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("Sending SMS to " + phoneNumber + ": " + message);
    }
}

class FacebookNotifier extends NotifierDecorator {
    private String username;

    public FacebookNotifier(Notifier notifier, String username) {
        super(notifier);
        this.username = username;
    }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("Sending Facebook message to " + username + ": " + message);
    }
}

class SlackNotifier extends NotifierDecorator {
    private String slackChannel;

    public SlackNotifier(Notifier notifier, String slackChannel) {
        super(notifier);
        this.slackChannel = slackChannel;
    }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("Sending Slack message to channel " + slackChannel + ": " + message);
    }
}

// Step 5: Demonstrate the Decorator pattern in action
public class Main {
    public static void main(String[] args) {
        // Basic email notification
        Notifier notifier = new EmailNotifier("user@example.com");

        // Wrap it with SMS, Facebook, and Slack notifications
        notifier = new SMSNotifier(notifier, "+123456789");
        notifier = new FacebookNotifier(notifier, "userFacebookUsername");
        notifier = new SlackNotifier(notifier, "#general");

        // Send a message that will go through all notification channels
        notifier.send("System alert: Critical issue detected!");
    }
}

