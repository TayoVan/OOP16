import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShopLogger implements OrderObserver, Serializable {
    private List<String> logs = new ArrayList<>();

    @Override
    public void onShopEvent(String message) {
        logs.add(message);
    }

    public void showLogs() {
        System.out.println("\n=== МОНІТОРИНГ ПОДІЙ МАГАЗИНУ (OBSERVER) ===");
        if (logs.isEmpty()) {
            System.out.println("Журнал подій порожній.");
        }
        for (int i = 0; i < logs.size(); i++) {
            System.out.println(" [" + (i + 1) + "] " + logs.get(i));
        }
    }
    public List<String> getLogs() {
        return logs;
    }
    public void setLogs(List<String> logs) {
        this.logs = logs;
    }
}
