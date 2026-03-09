package pu.com.ay.HeadFirstImplementation.Builder;

public class Main {


    public static void main(String []args){
        VerificationRequest verificationRequest = new VerificationRequest.Builder("Aman", "Kondapur").setPhone("79766121445").setssnBuilder("ssn").build();

        System.out.println(verificationRequest.getFirstName());


    }
    
}
