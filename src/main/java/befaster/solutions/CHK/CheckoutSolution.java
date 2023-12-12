package befaster.solutions.CHK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutSolution {
    private static Map<Character, Integer> prices = new HashMap<>();
    private static Map<Character, List<SpecialOffer>> specialOffers = new HashMap<>();

    static {
        prices.put('A', 50);
        prices.put('B', 30);
        prices.put('C', 20);
        prices.put('D', 15);
        prices.put('E', 40);

        List<SpecialOffer> offersA = new ArrayList<>();
        offersA.add(new SpecialOffer(3, 130));
        offersA.add(new SpecialOffer(5, 200));
        specialOffers.put('A', offersA);

        List<SpecialOffer> offersB = new ArrayList<>();
        offersB.add(new SpecialOffer(2, 45));
        specialOffers.put('B', offersB);

        List<SpecialOffer> offersE = new ArrayList<>();
        offersE.add(new SpecialOffer(2, 30, 'B', 1));
        specialOffers.put('E', offersE);
    }

    public Integer checkout(String skus) {
        if (skus == null || skus.isEmpty()) return 0;

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

    private static int calculateItemTotal(char item, int count, Map<Character, Integer> itemCounts) {
        if (specialOffers.containsKey(item)) {
            List<SpecialOffer> offers = specialOffers.get(item);
            int minTotal = Integer.MAX_VALUE;

            for (SpecialOffer offer : offers) {
                int offerCount = count / offer.getQuantity();
                int remainingCount = count % offer.getQuantity();

                if (offer.getFreeItem() != '\0' && itemCounts.containsKey(offer.getFreeItem())) {
                    int freeItemCount = itemCounts.get(offer.getFreeItem());
                    int freeItems = Math.min(offer.getFreeItemCount(), offerCount);
                    remainingCount -= freeItems * offer.getQuantity();
                }

                int currentTotal = offerCount * offer.getOfferPrice() + remainingCount * prices.get(item);
                minTotal = Math.min(minTotal, currentTotal);
            }

            return minTotal;
        } else {
            return count * prices.get(item);
        }
    }

    private static class SpecialOffer {
        private final int quantity;
        private final int offerPrice;
        private final char freeItem;
        private final int freeItemCount;

        public SpecialOffer(int quantity, int offerPrice) {
            this.quantity = quantity;
            this.offerPrice = offerPrice;
            this.freeItem = '\0';
            this.freeItemCount = 0;
        }

        public SpecialOffer(int quantity, int offerPrice, char freeItem, int freeItemCount) {
            this.quantity = quantity;
            this.offerPrice = offerPrice;
            this.freeItem = freeItem;
            this.freeItemCount = freeItemCount;
        }

        public int getQuantity() {
            return quantity;
        }

        public int getOfferPrice() {
            return offerPrice;
        }

        public char getFreeItem() {
            return freeItem;
        }

        public int getFreeItemCount() {
            return freeItemCount;
        }
    }
}

