import java.io.Serializable;

public class Product implements Item, Serializable {
    private String name;
    private int price;
    private String specs;
    private int warrantyMonths;
    private boolean isAvailable;

    public Product(String name, int price, String specs, int warrantyMonths, boolean isAvailable) {
        this.name = name;
        this.price = price;
        this.specs = specs;
        this.warrantyMonths = warrantyMonths;
        this.isAvailable = isAvailable;
    }
    @Override
    public String getSpecs() {
        return "Характеристики [" + name + "]: " + specs + " | Гарантія: " + warrantyMonths + " міс.";
    }
    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
    public int getWarrantyMonths() {
        return warrantyMonths;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public void setWarrantyMonths(int warrantyMonths) {
        this.warrantyMonths = warrantyMonths;
    }
}