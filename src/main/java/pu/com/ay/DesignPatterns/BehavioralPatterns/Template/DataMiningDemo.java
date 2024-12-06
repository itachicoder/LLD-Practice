package pu.com.ay.DesignPatterns.BehavioralPatterns.Template;


// Abstract class defining the template method
abstract class DataMiner {
    // Template method that defines the algorithm's skeleton
    public final void mine(String filePath) {
        String fileData = openFile(filePath);
        String rawData = extractData(fileData);
        String parsedData = parseData(rawData);
        String analysis = analyzeData(parsedData);
        String report = generateReport(analysis);
        sendReport(report);
        closeFile();
    }

    // Abstract methods that must be implemented by subclasses
    protected abstract String openFile(String path);
    protected abstract String extractData(String fileData);
    protected abstract String parseData(String rawData);
    
    // Concrete methods that can be used by all subclasses
    protected String analyzeData(String data) {
        System.out.println("Analyzing data...");
        // Common analysis logic
        return "Analyzed: " + data;
    }
    
    protected String generateReport(String analysis) {
        System.out.println("Generating report...");
        // Common report generation logic
        return "Report based on: " + analysis;
    }
    
    // Hook method - can be overridden by subclasses if needed
    protected void sendReport(String report) {
        System.out.println("Sending report: " + report);
    }
    
    // Hook method with default implementation
    protected void closeFile() {
        System.out.println("Closing file...");
    }
}

// Concrete class for processing PDF files
class PDFDataMiner extends DataMiner {
    @Override
    protected String openFile(String path) {
        System.out.println("Opening PDF file: " + path);
        return "PDF file content";
    }
    
    @Override
    protected String extractData(String fileData) {
        System.out.println("Extracting data from PDF...");
        return "PDF raw data";
    }
    
    @Override
    protected String parseData(String rawData) {
        System.out.println("Parsing PDF data...");
        return "Parsed PDF data";
    }
}

// Concrete class for processing DOC files
class DOCDataMiner extends DataMiner {
    @Override
    protected String openFile(String path) {
        System.out.println("Opening DOC file: " + path);
        return "DOC file content";
    }
    
    @Override
    protected String extractData(String fileData) {
        System.out.println("Extracting data from DOC...");
        return "DOC raw data";
    }
    
    @Override
    protected String parseData(String rawData) {
        System.out.println("Parsing DOC data...");
        return "Parsed DOC data";
    }
    
    // Optional: Override hook method for custom behavior
    @Override
    protected void sendReport(String report) {
        System.out.println("Sending DOC report via email: " + report);
    }
}

// Concrete class for processing CSV files
class CSVDataMiner extends DataMiner {
    @Override
    protected String openFile(String path) {
        System.out.println("Opening CSV file: " + path);
        return "CSV file content";
    }
    
    @Override
    protected String extractData(String fileData) {
        System.out.println("Extracting data from CSV...");
        return "CSV raw data";
    }
    
    @Override
    protected String parseData(String rawData) {
        System.out.println("Parsing CSV data...");
        return "Parsed CSV data";
    }
}

// Demo class to test the implementation
public class DataMiningDemo {
    public static void main(String[] args) {
        // Create different types of miners
        DataMiner pdfMiner = new PDFDataMiner();
        DataMiner docMiner = new DOCDataMiner();
        DataMiner csvMiner = new CSVDataMiner();
        
        System.out.println("PDF Mining Process:");
        pdfMiner.mine("document.pdf");
        
        System.out.println("\nDOC Mining Process:");
        docMiner.mine("document.doc");
        
        System.out.println("\nCSV Mining Process:");
        csvMiner.mine("document.csv");
    }
}