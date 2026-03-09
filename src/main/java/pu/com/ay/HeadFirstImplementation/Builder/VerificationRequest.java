package pu.com.ay.HeadFirstImplementation.Builder;

public class VerificationRequest {
    private final String name;
    private final String address;
    private final String ssn;
    private final String phone;

    private VerificationRequest(Builder builder){
        this.name = builder.name;
        this.address = builder.address;
        this.ssn = builder.ssn;
        this.phone = builder.phone;
    }

    public String getFirstName() { return name; }
    public String getLastName() { return address; }
    public String getSsn() { return ssn; }

    public static class Builder {
        private final String name;
        private final String address;
        private  String ssn;
        private  String phone;

        public Builder(String fName, String fAddress){
            this.name = fName;
            this.address = fAddress;
        }

        public Builder setssnBuilder(String fssn){
            this.ssn = fssn;
            return this;
        }

        public Builder setPhone(String number){
            this.phone = number;
            return this;
        }
        public VerificationRequest build() {
            // Validation logic here
            if (name == null || name.isEmpty()) {
                throw new IllegalStateException("First name is required");
            }
            if (address == null || address.isEmpty()) {
                throw new IllegalStateException("Last name is required");
            }
            // At least one form of ID required
            if (ssn == null) {
                throw new IllegalStateException("SSN or Driver License required");
            }
            
            // Create and return the immutable product
            return new VerificationRequest(this);
        }


        
    }
}
