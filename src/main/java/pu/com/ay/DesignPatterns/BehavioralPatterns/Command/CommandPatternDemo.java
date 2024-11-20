package pu.com.ay.DesignPatterns.BehavioralPatterns.Command;

// Step 1: Command Interface
interface Command {
    void execute();
    void undo();
}

// Step 2: Receiver Class
class TextEditor {
    private String text = "";

    public void write(String newText) {
        text += newText;
    }

    public void eraseLast() {
        if (!text.isEmpty()) {
            text = text.substring(0, text.length() - 1);
        }
    }

    public String getText() {
        return text;
    }
}

// Step 3: Concrete Command Classes
class WriteCommand implements Command {
    private TextEditor editor;
    private String textToWrite;

    public WriteCommand(TextEditor editor, String textToWrite) {
        this.editor = editor;
        this.textToWrite = textToWrite;
    }

    @Override
    public void execute() {
        editor.write(textToWrite);
    }

    @Override
    public void undo() {
        for (int i = 0; i < textToWrite.length(); i++) {
            editor.eraseLast();
        }
    }
}

class EraseCommand implements Command {
    private TextEditor editor;

    public EraseCommand(TextEditor editor) {
        this.editor = editor;
    }

    @Override
    public void execute() {
        editor.eraseLast();
    }

    @Override
    public void undo() {
        System.out.println("Undo of erase is not implemented in this example.");
    }
}

// Step 4: Invoker Class
class CommandInvoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void executeCommand() {
        command.execute();
    }

    public void undoCommand() {
        command.undo();
    }
}

// Step 5: Client Code
public class CommandPatternDemo {
    public static void main(String[] args) {
        // Receiver
        TextEditor editor = new TextEditor();

        // Invoker
        CommandInvoker invoker = new CommandInvoker();

        // Write command
        Command writeHelloCommand = new WriteCommand(editor, "Hello");
        Command writeWorldCommand = new WriteCommand(editor, " World");

        // Execute write commands
        invoker.setCommand(writeHelloCommand);
        invoker.executeCommand();

        invoker.setCommand(writeWorldCommand);
        invoker.executeCommand();

        System.out.println("Text after writing: " + editor.getText()); // Output: Hello World

        // Undo the last write command
        invoker.undoCommand();
        System.out.println("Text after undo: " + editor.getText()); // Output: Hello
    }
}

