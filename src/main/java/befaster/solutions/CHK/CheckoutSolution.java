package befaster.solutions.CHK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutSolution {

    private static final Map<Character, Integer> PRICE_TABLE = new HashMap<>();
    private static final Map<Character, List<SpecialOffer>> SPECIAL_OFFERS = new HashMap<>();

    static {
        PRICE_TABLE.put('A', 50);
        PRICE_TABLE.put('B', 30);
        PRICE_TABLE.put('C', 20);
        PRICE_TABLE.put('D', 15);
        PRICE_TABLE.put('E', 40);

        List<SpecialOffer> offersA = new ArrayList<>();
        offersA.add(new SpecialOffer(5, 130));
        offersA.add(new SpecialOffer(3, 200));
        SPECIAL_OFFERS.put('A', offersA);

        List<SpecialOffer> offersE = new ArrayList<>();
        offersE.add(new SpecialOffer(2, 1, 'B'));
        SPECIAL_OFFERS.put('E', offersE);

        List<SpecialOffer> offersB = new ArrayList<>();
        offersB.add(new SpecialOffer(2, 45));
        SPECIAL_OFFERS.put('B', offersB);
    }

    public Integer checkout(String skus) {
        if (skus == null || !isValidInput(skus)) {
            return -1;
        }

        Map<Character, Integer> itemCounts = countItems(skus);
        int totalPrice = calculateTotalPrice(itemCounts);

        return totalPrice;
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
            List<SpecialOffer> specialOffers = SPECIAL_OFFERS.get(item);

            if (specialOffers != null) {
                totalPrice += calculateSpecialOfferPrice(count, specialOffers, itemCounts);
            } else {
                totalPrice += count * price;
            }
        }
        return totalPrice;
    }

    private int calculateSpecialOfferPrice(int count, List<SpecialOffer> specialOffers, Map<Character, Integer> itemCounts) {
        int price = 0;
        for (SpecialOffer offer : specialOffers) {
            if (offer.quantity >= count) {
                if (offer.price != 0) {
                    price += count / offer.quantity * offer.price;
                    count = count % offer.quantity;
                }
            }
        }
        price += count * price;
        return price;
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
            this.price = 0;
        }
    }
}





