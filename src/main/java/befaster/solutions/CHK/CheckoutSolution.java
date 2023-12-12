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
        PRICE_TABLE.put('F', 10);

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
       int ECount = itemCounts.getOrDefault('E', 0);
       int BCount = itemCounts.getOrDefault('B', 0);

       int totalPrice = itemCounts.getOrDefault('C', 0) * PRICE_TABLE.getOrDefault('C', 0) +
               itemCounts.getOrDefault('D', 0) * PRICE_TABLE.getOrDefault('D', 0) +
               itemCounts.getOrDefault('E', 0) * PRICE_TABLE.getOrDefault('E', 0);

       if (BCount > 0) {
           int updatedValue = Math.max(0, BCount - ECount / 2);
           itemCounts.put('B', updatedValue);
           totalPrice += itemCounts.get('B') / 2 * 45 + itemCounts.get('B') % 2 * 30;
       }


        totalPrice += itemCounts.getOrDefault('A', 0) / 5 * 200 + (itemCounts.getOrDefault('A', 0) % 5) / 3 * 130 +
                ((itemCounts.getOrDefault('A', 0) % 5) % 3) * 50;

        int FCount = itemCounts.getOrDefault('F', 0);
        if (FCount > 0) {
            totalPrice += itemCounts.get('F') / 3 * 20 + itemCounts.get('F') % 3 * 10;
        }

        return totalPrice;
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








