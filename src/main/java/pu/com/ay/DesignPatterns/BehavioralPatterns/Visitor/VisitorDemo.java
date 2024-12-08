package pu.com.ay.DesignPatterns.BehavioralPatterns.Visitor;

// Element interface that all nodes will implement
interface Node {
    void accept(Visitor visitor);
}

// Concrete element classes
class City implements Node {
    private String name;
    private int population;
    
    public City(String name, int population) {
        this.name = name;
        this.population = population;
    }
    
    public String getName() { return name; }
    public int getPopulation() { return population; }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visitCity(this);
    }
}

class Industry implements Node {
    private String name;
    private String type;
    private int employees;
    
    public Industry(String name, String type, int employees) {
        this.name = name;
        this.type = type;
        this.employees = employees;
    }
    
    public String getName() { return name; }
    public String getType() { return type; }
    public int getEmployees() { return employees; }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visitIndustry(this);
    }
}

class SightSeeing implements Node {
    private String name;
    private String description;
    private int annualVisitors;
    
    public SightSeeing(String name, String description, int annualVisitors) {
        this.name = name;
        this.description = description;
        this.annualVisitors = annualVisitors;
    }
    
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getAnnualVisitors() { return annualVisitors; }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visitSightSeeing(this);
    }
}

// Visitor interface
interface Visitor {
    void visitCity(City city);
    void visitIndustry(Industry industry);
    void visitSightSeeing(SightSeeing sightSeeing);
}

// Concrete Visitor for XML export
class XMLExportVisitor implements Visitor {
    private StringBuilder xml = new StringBuilder();
    
    @Override
    public void visitCity(City city) {
        xml.append("<city>\n")
           .append("    <name>").append(city.getName()).append("</name>\n")
           .append("    <population>").append(city.getPopulation()).append("</population>\n")
           .append("</city>\n");
    }
    
    @Override
    public void visitIndustry(Industry industry) {
        xml.append("<industry>\n")
           .append("    <name>").append(industry.getName()).append("</name>\n")
           .append("    <type>").append(industry.getType()).append("</type>\n")
           .append("    <employees>").append(industry.getEmployees()).append("</employees>\n")
           .append("</industry>\n");
    }
    
    @Override
    public void visitSightSeeing(SightSeeing sightSeeing) {
        xml.append("<sightseeing>\n")
           .append("    <name>").append(sightSeeing.getName()).append("</name>\n")
           .append("    <description>").append(sightSeeing.getDescription()).append("</description>\n")
           .append("    <annual_visitors>").append(sightSeeing.getAnnualVisitors()).append("</annual_visitors>\n")
           .append("</sightseeing>\n");
    }
    
    public String getXML() {
        return xml.toString();
    }
}

// Another concrete visitor for JSON export (showing extensibility)
class JSONExportVisitor implements Visitor {
    private StringBuilder json = new StringBuilder();
    
    @Override
    public void visitCity(City city) {
        json.append("{\n")
            .append("    \"type\": \"city\",\n")
            .append("    \"name\": \"").append(city.getName()).append("\",\n")
            .append("    \"population\": ").append(city.getPopulation()).append("\n")
            .append("},\n");
    }
    
    @Override
    public void visitIndustry(Industry industry) {
        json.append("{\n")
            .append("    \"type\": \"industry\",\n")
            .append("    \"name\": \"").append(industry.getName()).append("\",\n")
            .append("    \"industryType\": \"").append(industry.getType()).append("\",\n")
            .append("    \"employees\": ").append(industry.getEmployees()).append("\n")
            .append("},\n");
    }
    
    @Override
    public void visitSightSeeing(SightSeeing sightSeeing) {
        json.append("{\n")
            .append("    \"type\": \"sightseeing\",\n")
            .append("    \"name\": \"").append(sightSeeing.getName()).append("\",\n")
            .append("    \"description\": \"").append(sightSeeing.getDescription()).append("\",\n")
            .append("    \"annualVisitors\": ").append(sightSeeing.getAnnualVisitors()).append("\n")
            .append("},\n");
    }
    
    public String getJSON() {
        String result = "[\n" + json.toString();
        return result.substring(0, result.length() - 2) + "\n]"; // Remove last comma and close array
    }
}

// Demo class
public class VisitorDemo {
    public static void main(String[] args) {
        // Create nodes
        Node[] nodes = {
            new City("New York", 8400000),
            new Industry("Tech Hub", "Technology", 5000),
            new SightSeeing("Central Park", "Urban park", 42000000)
        };
        
        // Export to XML
        XMLExportVisitor xmlVisitor = new XMLExportVisitor();
        for (Node node : nodes) {
            node.accept(xmlVisitor);
        }
        System.out.println("XML Export:\n" + xmlVisitor.getXML());
        
        // Export to JSON
        JSONExportVisitor jsonVisitor = new JSONExportVisitor();
        for (Node node : nodes) {
            node.accept(jsonVisitor);
        }
        System.out.println("\nJSON Export:\n" + jsonVisitor.getJSON());
    }
}