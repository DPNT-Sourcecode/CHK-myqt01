package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutSolution {
    private static final Map<Character, Integer> prices = new HashMap<>();
    private static final Map<Character, List<SpecialOffer>> specialOffers = new HashMap<>();

    static {
        prices.put('A', 50);
        prices.put('B', 30);
        prices.put('C', 20);
        prices.put('D', 15);
        prices.put('E', 40);

        List<SpecialOffer> offersA = List.of(new SpecialOffer(3, 130), new SpecialOffer(5, 200));
        specialOffers.put('A', offersA);

        List<SpecialOffer> offersB = List.of(new SpecialOffer(2, 45));
        specialOffers.put('B', offersB);
    }

    public Integer checkout(String skus) {
        if (skus == null || skus.isEmpty()) {
            return 0;
        }

        int totalPrice = 0;
        Map<Character, Integer> itemCounts = new HashMap<>();

        for (char item : skus.toCharArray()) {
            if (!prices.containsKey(item)) {
                return -1;
            }
            itemCounts.put(item, itemCounts.getOrDefault(item, 0) + 1);
        }

        for (Map.Entry<Character, Integer> entry : itemCounts.entrySet()) {
            Character item = entry.getKey();
            int count = entry.getValue();
            totalPrice += calculateItemTotal(item, count, itemCounts);
        }

        return totalPrice;
    }

    private int calculateItemTotal(char item, int count, Map<Character, Integer> itemCounts) {
        if (count > 0 && specialOffers.containsKey(item)) {
            List<SpecialOffer> offers = specialOffers.get(item);
            int minTotal = Integer.MAX_VALUE;

            for (SpecialOffer offer : offers) {
                int offerCount = count / offer.quantity;
                int remainingCount = count % offer.quantity;
                int freeItemCount = getFreeItemCount(offer.freeItem, itemCounts);

                int currentTotal = offerCount * offer.offerPrice +
                        calculateItemTotal(offer.freeItem, freeItemCount, itemCounts) +
                        remainingCount * prices.get(item);

                minTotal = Math.min(minTotal, currentTotal);
            }
            return minTotal;
        } else {
            return count * prices.get(item);
        }
    }

    private int getFreeItemCount(char freeItem, Map<Character, Integer> itemCounts) {
        return freeItem != '\0' && itemCounts.containsKey(freeItem)
                ? itemCounts.get(freeItem)
                : 0;
    }

    private static class SpecialOffer {
        private final int quantity;
        private final int offerPrice;
        private final char freeItem;
        private final int freeItemCount;

        public SpecialOffer(int quantity, int offerPrice) {
            this.offerPrice = offerPrice;
            this.quantity = quantity;
            this.freeItem = '\0';
            this.freeItemCount = 0;
        }

        public SpecialOffer(int quantity, int offerPrice, char freeItem, int freeItemCount) {
            this.offerPrice = offerPrice;
            this.quantity = quantity;
            this.freeItem = freeItem;
            this.freeItemCount = freeItemCount;
        }
    }
}



