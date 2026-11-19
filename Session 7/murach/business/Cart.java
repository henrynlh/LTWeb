package murach.business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Cart implements Serializable {
    public static final int MAX_QUANTITY = 999;
    private ArrayList<LineItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public synchronized ArrayList<LineItem> getItems() {
        return new ArrayList<>(items);
    }

    public synchronized int getCount() {
        return items.size();
    }

    public synchronized void addItem(Product product) {
        // If the CD is already in the cart, increase its quantity.
        for (int i = 0; i < items.size(); i++) {
            LineItem item = items.get(i);
            String code = item.getProduct().getCode();

            if (code.equals(product.getCode())) {
                int quantity = item.getQuantity() + 1;
                updateItem(code, quantity);
                return;
            }
        }

        LineItem item = new LineItem(product, 1);
        items.add(item);
    }

    public synchronized void updateItem(String productCode, int quantity) {
        if (quantity < 1 || quantity > MAX_QUANTITY) {
            throw new IllegalArgumentException("Quantity must be between 1 and 999.");
        }

        for (int i = 0; i < items.size(); i++) {
            LineItem item = items.get(i);
            String code = item.getProduct().getCode();

            if (code.equals(productCode)) {
                LineItem updatedItem = new LineItem(item.getProduct(), quantity);
                items.set(i, updatedItem);
                return;
            }
        }

        throw new IllegalArgumentException("This CD is no longer in your cart.");
    }

    public synchronized void removeItem(String productCode) {
        for (int i = 0; i < items.size(); i++) {
            LineItem item = items.get(i);
            String code = item.getProduct().getCode();

            if (code.equals(productCode)) {
                items.remove(i);
                return;
            }
        }
    }

    public synchronized BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (LineItem item : items) {
            total = total.add(item.getTotal());
        }

        return total;
    }

    public synchronized String getTotalCurrencyFormat() {
        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
        return currency.format(getTotal());
    }
}
