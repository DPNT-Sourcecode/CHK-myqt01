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
        PRICE_TABLE.put('G', 20);
        PRICE_TABLE.put('H', 10);
        PRICE_TABLE.put('I', 35);
        PRICE_TABLE.put('J', 60);
        PRICE_TABLE.put('K', 70);
        PRICE_TABLE.put('L', 90);
        PRICE_TABLE.put('M', 15);
        PRICE_TABLE.put('N', 40);
        PRICE_TABLE.put('O', 10);
        PRICE_TABLE.put('P', 50);
        PRICE_TABLE.put('Q', 30);
        PRICE_TABLE.put('R', 50);
        PRICE_TABLE.put('S', 20);
        PRICE_TABLE.put('T', 20);
        PRICE_TABLE.put('U', 40);
        PRICE_TABLE.put('V', 50);
        PRICE_TABLE.put('W', 20);
        PRICE_TABLE.put('X', 17);
        PRICE_TABLE.put('Y', 20);
        PRICE_TABLE.put('Z', 21);

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

        totalPrice += nonOfferItemsSum('G', itemCounts) + nonOfferItemsSum('I', itemCounts) + nonOfferItemsSum('J', itemCounts) +
                nonOfferItemsSum('L', itemCounts) + nonOfferItemsSum('O', itemCounts) +
                nonOfferItemsSum('W', itemCounts);

        totalPrice += itemCounts.getOrDefault('H', 0) / 10 * 80 + (itemCounts.getOrDefault('H', 0) % 10) / 5 * 45 +
                ((itemCounts.getOrDefault('H', 0) % 10) % 5) * 10;

        totalPrice += itemCounts.getOrDefault('K', 0) / 2 * 120 +
                itemCounts.getOrDefault('K', 0) % 2 * 70;

        int NCount = itemCounts.getOrDefault('N', 0);
        int MCount = itemCounts.getOrDefault('M', 0);

        if (MCount > 0) {
            int updatedValue = Math.max(0, MCount - NCount / 3);
            itemCounts.put('M', updatedValue);
            totalPrice += itemCounts.get('M') * 15;
        }

        totalPrice += nonOfferItemsSum('N', itemCounts);

        totalPrice += itemCounts.getOrDefault('P', 0) / 5 * 200 +
                itemCounts.getOrDefault('P', 0) % 5 * 50;

        int RCount = itemCounts.getOrDefault('R', 0);
        int QCount = itemCounts.getOrDefault('Q', 0);

        if (QCount > 0) {
            int updatedValue = Math.max(0, QCount - RCount / 3);
            itemCounts.put('Q', updatedValue);
        }

        totalPrice += itemCounts.getOrDefault('Q', 0) / 3 * 80 +
                itemCounts.getOrDefault('Q', 0) % 3 * 30;

        totalPrice += nonOfferItemsSum('R', itemCounts);

        int UCount = itemCounts.getOrDefault('U', 0);
        if (UCount > 0) {
            totalPrice += itemCounts.get('U') / 4 * 120 + itemCounts.get('U') % 4 * 40;
        }

        totalPrice += itemCounts.getOrDefault('V', 0) / 3 * 130 + (itemCounts.getOrDefault('V', 0) % 3) / 2 * 90 +
                ((itemCounts.getOrDefault('V', 0) % 3) % 2) * 50;

        totalPrice +=  handleGroupDiscount(itemCounts, 'S', 'T', 'X', 'Y', 'Z');
        return totalPrice;
    }

    private int handleGroupDiscount(Map<Character, Integer> itemCounts, char... items) {
        int groupCount = 0;

        for (char item : items) {
            groupCount += itemCounts.getOrDefault(item, 0);
        }

        int groupDiscounts = groupCount / 3;

        // Deduct items for the group discount
        for (char item : items) {
            Integer itemCount = itemCounts.get(item);
            if (itemCount != null) {
                int deducted = Math.min(itemCount, groupDiscounts * 3);
                itemCounts.put(item, itemCount - deducted);
                groupDiscounts -= deducted / 3;
            }
        }

        int groupPrice = groupDiscounts * 45;

        groupPrice += itemCounts.getOrDefault('Z', 0) * PRICE_TABLE.getOrDefault('Z', 0);
        groupPrice += itemCounts.getOrDefault('S', 0) * PRICE_TABLE.getOrDefault('S', 0);
        groupPrice += itemCounts.getOrDefault('T', 0) * PRICE_TABLE.getOrDefault('T', 0);
        groupPrice += itemCounts.getOrDefault('Y', 0) * PRICE_TABLE.getOrDefault('Y', 0);
        groupPrice += itemCounts.getOrDefault('X', 0) * PRICE_TABLE.getOrDefault('X', 0);

        return groupPrice;
    }

    private int nonOfferItemsSum(char product, Map<Character, Integer> itemCounts) {
        return itemCounts.getOrDefault(product, 0) * PRICE_TABLE.getOrDefault(product, 0);
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




