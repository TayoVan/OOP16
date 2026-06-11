import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShopStorage implements Serializable {
    private static ShopStorage instance;
    private List<Item> products = new ArrayList<>();
    private List<Item> cart = new ArrayList<>();
    private List<OrderObserver> observers = new ArrayList<>();

    private ShopStorage() {
        products.add(new Product("Смартфон iPhone 15", 35000, "OLED 6.1, A16 Bionic, 128GB", 12, true));
        products.add(new Product("Ноутбук ASUS ROG", 54000, "Ryzen 7, RTX 4060, 16GB RAM", 24, true));
        products.add(new Product("Навушники AirPods Pro", 9500, "Active Noise Cancellation, H2 chip", 12, true));
    }
    public static ShopStorage getInstance() {
        if (instance == null) {
            instance = new ShopStorage();
        }
        return instance;
    }
    public void addObserver(OrderObserver obs) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(obs);
    }
    public void notifyObservers(String msg) {
        if (observers != null) {
            for (OrderObserver obs : observers) {
                obs.onShopEvent(msg);
            }
        }
    }
    public Product addProductToCatalog(String name, int price, String specs, int warranty) {
        if (price <= 0) {
            throw new ShopException("Ціна товару повинна бути більшою за 0 грн!");
        }
        Product newProduct = new Product(name, price, specs, warranty, true);
        products.add(newProduct);
        notifyObservers("На склад додано новий товар: " + name + " за " + price + " грн.");
        return newProduct;
    }
    public void addToCart(int index) {
        if (index < 0 || index >= products.size()) {
            throw new ShopException("Товар за вказаним номером не знайдено!");
        }
        Product p = (Product) products.get(index);
        if (!p.isAvailable()) {
            throw new ShopException("Товару '" + p.getName() + "' наразі немає в наявності!");
        }
        cart.add(p);
        notifyObservers("Користувач додав у кошик: " + p.getName());
    }
    public void checkout(boolean hasLoyaltyCard) {
        if (cart.isEmpty()) {
            throw new ShopException("Неможливо оформити замовлення: кошик порожній!");
        }
        int total = 0;
        for (Item item : cart) {
            Product p = (Product) item;
            total = total + p.getPrice();
        }
        System.out.println("\n--- ОФОРМЛЕННЯ ЗАМОВЛЕННЯ ---");
        System.out.println("Сума без знижки: " + total + " грн.");
        if (hasLoyaltyCard) {
            int discount = (int) (total * 0.10); // 10% знижки для постійних клієнтів
            total = total - discount;
            System.out.println("Застосовано картку лояльності (Знижка 10%): -" + discount + " грн.");
        } else {
            System.out.println("Картка лояльності відсутня.");
        }
        System.out.println("Фінальна вартість до сплати: " + total + " грн.");
        cart.clear();
        notifyObservers("Успішно оформлено замовлення на суму " + total + " грн.");
    }
    public void compareProducts() {
        System.out.println("\n=== ПОРІВНЯННЯ ХАРАКТЕРИСТИК ПРИСТРОЇВ ===");
        if (products.isEmpty()) {
            System.out.println("Каталог порожній.");
        }
        for (Item item : products) {
            System.out.println(item.getSpecs());
        }
    }
    public ShopMemento saveState() {
        notifyObservers("Створено зліпок стану складу електроніки (Memento)");
        return new ShopMemento(new ArrayList<>(products));
    }
    public void restoreState(ShopMemento memento) {
        this.products = memento.getSavedProducts();
        notifyObservers("Стан складу успішно відновлено (Memento)");
    }
    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            ShopMemento memento = this.saveState();
            oos.writeObject(memento);
            notifyObservers("Контрольну точку складу збережено у файл: " + filename);
        } catch (Exception e) {
            throw new ShopException("Помилка збереження: " + e.getMessage());
        }
    }
    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            ShopMemento memento = (ShopMemento) ois.readObject();
            restoreState(memento);

            System.out.println("\n=== ВІДНОВЛЕНІ ТОВАРИ З ФАЙЛУ ===");
            for (int i = 0; i < memento.getSavedProducts().size(); i++) {
                Product p = (Product) memento.getSavedProducts().get(i);
                System.out.println(" #" + (i + 1) + " " + p.getName() + " | Ціна: " + p.getPrice() + " грн");
            }
            System.out.println("Стан десеріалізовано з файлу.");

        } catch (Exception e) {
            System.out.println("Не вдалося завантажити збереження: " + e.getMessage());
        }
    }
    public void printStatus() {
        System.out.println("\n--- АСОРТИМЕНТ МАГАЗИНУ ЕЛЕКТРОНІКИ ---");
        for (int i = 0; i < products.size(); i++) {
            Product p = (Product) products.get(i);
            String availability;
            if (p.isAvailable()) {
                availability = "В наявності";
            } else {
                availability = "Немає на складі";
            }
            System.out.println(" #" + (i + 1) + " " + p.getName() + " | Ціна: " + p.getPrice() + " грн | [" + availability + "]");
        }
        System.out.println("Кількість товарів у кошику: " + cart.size());
    }
}