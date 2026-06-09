import java.io.Serializable;
import java.util.List;

public class ShopMemento implements Serializable {
    private List<Item> savedProducts;

    public ShopMemento(List<Item> products) {
        this.savedProducts = products;
    }
    public List<Item> getSavedProducts() {
        return savedProducts;
    }
    public void setSavedProducts(List<Item> savedProducts) {
        this.savedProducts = savedProducts;
    }
}