package ma.boumlyk.onboarding.models;

public class Operation {
    private int id;
    private long date;
    private double price;

    public Operation(int id, long date, double price) {
        this.id = id;
        this.date = date;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }
}

