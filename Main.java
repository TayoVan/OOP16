import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ShopStorage storage = ShopStorage.getInstance();
        ShopLogger logger = new ShopLogger();
        storage.addObserver(logger);
        Scanner sc = new Scanner(System.in);
        System.out.println("=== ІНФОРМАЦІЙНА СИСТЕМА МАГАЗИНУ ЕЛЕКТРОНІКИ ===");
        while (true) {
            try {
                System.out.println("\n1. Показати асортимент та кошик");
                System.out.println("2. Додати товар у кошик");
                System.out.println("3. Оформити замовлення (Система лояльності)");
                System.out.println("4. Порівняти технічні характеристики пристроїв");
                System.out.println("5. Додати новий товар на склад (Factory Method)");
                System.out.println("6. Показати логі та статистику (Observer)");
                System.out.println("7. Зберегти стан складу у файл (Memento)");
                System.out.println("8. Завантажити стан складу з файлу (Memento)");
                System.out.println("9. Вихід");
                System.out.print("Оберіть дію: ");
                int choice = sc.nextInt();
                if (choice == 9) break;
                switch (choice) {
                    case 1:
                        storage.printStatus();
                        break;
                    case 2:
                        System.out.print("Введіть номер товару для кошика (починаючи з 1): ");
                        int prodIndex = sc.nextInt();
                        storage.addToCart(prodIndex);
                        System.out.println("Товар успішно додано до кошика!");
                        break;
                    case 3:
                        System.out.print("У вас є картка постійного покупця лояльності? (так/ні): ");
                        String answer = sc.nextLine();
                        sc.nextLine();
                        boolean hasCard = false;
                        if ("так".equalsIgnoreCase(answer)) {
                            hasCard = true;
                        }
                        storage.checkout(hasCard);
                        break;
                    case 4:
                        storage.compareProducts();
                        break;
                    case 5:
                        System.out.print("Введіть назву пристрою: ");
                        String name = sc.nextLine();
                        sc.nextLine();
                        System.out.print("Введіть ціну (грн): ");
                        int price = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Введіть тех. характеристики: ");
                        String specs = sc.nextLine();
                        System.out.print("Введіть гарантійний термін (місяців): ");
                        int warranty = sc.nextInt();
                        storage.addProductToCatalog(name, price, specs, warranty);
                        System.out.println("Товар успішно внесено до каталогу!");
                        break;
                    case 6:
                        logger.showLogs();
                        break;
                    case 7:
                        storage.saveToFile("shop_backup.ser");
                        System.out.println("Дані збережено до файлу");
                        break;
                    case 8:
                        storage.loadFromFile("shop_backup.ser");
                        break;
                    default:
                        System.out.println("Невірний вибір. Спробуйте ще раз.");
                }
            } catch (ShopException e) {
                System.out.println("[ПОПЕРЕДЖЕННЯ СИСТЕМИ]: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("[ПОМИЛКА ВВОДУ]: Перевірте коректність даних! " + e.getMessage());
            }
        }
        System.out.println("Роботу системи магазину завершено.");
    }
}
