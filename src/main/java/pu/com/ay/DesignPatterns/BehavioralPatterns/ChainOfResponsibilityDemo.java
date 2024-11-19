package pu.com.ay.DesignPatterns.BehavioralPatterns;

// Base Handler Interface
interface Handler {
    void setNext(Handler next);
    void handle(Request request);
}

// Base Handler Class
abstract class AbstractHandler implements Handler {
    private Handler next;

    @Override
    public void setNext(Handler next) {
        this.next = next;
    }

    @Override
    public void handle(Request request) {
        if (next != null) {
            next.handle(request);
        }
    }
}

// Concrete Handler: Authentication Handler
class AuthenticationHandler extends AbstractHandler {
    @Override
    public void handle(Request request) {
        if (request.isAuthenticated()) {
            System.out.println("Authentication passed.");
            super.handle(request);
        } else {
            System.out.println("Authentication failed. Request denied.");
        }
    }
}

// Concrete Handler: Authorization Handler
class AuthorizationHandler extends AbstractHandler {
    @Override
    public void handle(Request request) {
        if (request.hasPermission()) {
            System.out.println("Authorization passed.");
            super.handle(request);
        } else {
            System.out.println("Authorization failed. Request denied.");
        }
    }
}

// Concrete Handler: Data Validation Handler
class ValidationHandler extends AbstractHandler {
    @Override
    public void handle(Request request) {
        if (request.isValidData()) {
            System.out.println("Validation passed.");
            super.handle(request);
        } else {
            System.out.println("Validation failed. Request denied.");
        }
    }
}

// Request class
class Request {
    private boolean authenticated;
    private boolean permission;
    private boolean validData;

    public Request(boolean authenticated, boolean permission, boolean validData) {
        this.authenticated = authenticated;
        this.permission = permission;
        this.validData = validData;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public boolean hasPermission() {
        return permission;
    }

    public boolean isValidData() {
        return validData;
    }
}

// Client code
public class ChainOfResponsibilityDemo {
    public static void main(String[] args) {
        // Create the handlers
        Handler authHandler = new AuthenticationHandler();
        Handler authorizationHandler = new AuthorizationHandler();
        Handler validationHandler = new ValidationHandler();

        // Build the chain: authHandler -> authorizationHandler -> validationHandler
        authHandler.setNext(authorizationHandler);
        authorizationHandler.setNext(validationHandler);

        // Create a request
        Request request = new Request(true, true, true);

        // Pass the request through the chain
        System.out.println("Processing request:");
        authHandler.handle(request);

        System.out.println();

        // Another request with failed validation
        Request failedRequest = new Request(true, false, true);
        System.out.println("Processing failed request:");
        authHandler.handle(failedRequest);
    }
}
