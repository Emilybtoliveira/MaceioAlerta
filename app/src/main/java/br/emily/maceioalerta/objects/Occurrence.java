package br.emily.maceioalerta.objects;

public class Occurrence {

    private String type;
    private String neighborhood;
    private String street;
    private String description;

    public Occurrence (){

    }

    public Occurrence(String type, String neighborhood, String street) {
        this.type = type;
        this.neighborhood = neighborhood;
        this.street = street;
    }

    public Occurrence(String type, String neighborhood, String street, String description) {
        this.type = type;
        this.neighborhood = neighborhood;
        this.street = street;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
