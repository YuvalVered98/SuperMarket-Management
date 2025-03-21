public class Address {
    private String street;
    private int houseNumber;
    private String city;
    private String state;
    private long zip;

    public Address(String street, int houseNumber, String city, String state, long zip) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
    public Address(Address address) {
        this.street = address.street;
        this.houseNumber = address.houseNumber;
        this.city = address.city;
        this.state = address.state;
        this.zip = address.zip;
    }

    public String getStreet() {
        return street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public long getZip() {
        return zip;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }
}
