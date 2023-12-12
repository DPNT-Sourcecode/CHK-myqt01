package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.Map;

public class CheckoutSolution {

    private static final Map<Character, Integer> PRICE_TABLE = new HashMap<>();
    private static final Map<Character, SpecialOffer> SPECIAL_OFFERS = new HashMap<>();

    static {
        PRICE_TABLE.put('A', 50);
        PRICE_TABLE.put('B', 30);
        PRICE_TABLE.put('C', 20);
        PRICE_TABLE.put('D', 15);
        PRICE_TABLE.put('E', 40);

        SPECIAL_OFFERS.put('A', new SpecialOffer(3, 130));
        SPECIAL_OFFERS.put('A', new SpecialOffer(5, 200));
        SPECIAL_OFFERS.put('B', new SpecialOffer(2, 45));
        SPECIAL_OFFERS.put('E', new SpecialOffer(2, 1, 'B'));
    }

    public Integer checkout(String skus) {
        if (skus == null || !isValidInput(skus)) {
            return -1;
        }

        Map<Character, Integer> itemCounts = countItems(skus);

        return calculateTotalPrice(itemCounts);
    }

    private boolean isValidInput(String skus) {
        for (char item : skus.toCharArray()) {
            if (!PRICE_TABLE.containsKey(item)) {
                return false; // Invalid item
            }
        }
        return true;
    }

    private Map<Character, Integer> countItems(String skus) {
        Map<Character, Integer> itemCounts = new HashMap<>();
        for (char item : skus.toCharArray()) {
            itemCounts.put(item, itemCounts.getOrDefault(item, 0) + 1);
        }
        return itemCounts;
    }

    private int calculateTotalPrice(Map<Character, Integer> itemCounts) {
        int totalPrice = 0;
        for (Map.Entry<Character, Integer> entry : itemCounts.entrySet()) {
            char item = entry.getKey();
            int count = entry.getValue();

            int price = PRICE_TABLE.getOrDefault(item, 0);
            SpecialOffer specialOffer = SPECIAL_OFFERS.get(item);

            if (specialOffer != null) {
                totalPrice += calculateSpecialOfferPrice(count, price, specialOffer, itemCounts);
            } else {
                totalPrice += count * price;
            }
        }
        return totalPrice;
    }

    private int calculateSpecialOfferPrice(int count, int price, SpecialOffer specialOffer, Map<Character, Integer> itemCounts) {
        if (specialOffer.quantity > 0 && count >= specialOffer.quantity) {
            int specialPrice = (count / specialOffer.quantity) * specialOffer.price;
            int remainingCount = count % specialOffer.quantity;

            if (specialOffer.freeItemCount > 0 && itemCounts.containsKey(specialOffer.freeItem)) {
                int freeItemCount = Math.min(itemCounts.get(specialOffer.freeItem), remainingCount);
                remainingCount -= freeItemCount;
            }

            return specialPrice + remainingCount * price;
        }
        return count * price;
    }

    private static class SpecialOffer {
        int quantity;
        int price;
        int freeItemCount;
        char freeItem;

        SpecialOffer(int quantity, int price) {
            this.quantity = quantity;
            this.price = price;
        }

        SpecialOffer(int quantity, int freeItemCount, char freeItem) {
            this.quantity = quantity;
            this.freeItemCount = freeItemCount;
            this.freeItem = freeItem;
        }
    }
}



