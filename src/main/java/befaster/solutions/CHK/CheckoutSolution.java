package befaster.solutions.CHK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutSolution {
    public Integer checkout(String skus) {

        static Map<Character, Integer> prices = new HashMap<>();
        Map<Character, List<SpecialOffer>> specialOffers = new HashMap<>();

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
        offersE.add(new SpecialOffer(2, 0, 'B', 1));
        specialOffers.put('E', offersE);

        if (skus == null || skus.isEmpty())  return 0;

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


        }

        return totalPrice;
    }

    private static int calcualteItemTotal(char item, int count, Map<Character, Integer> itemCount) {
        if (specialOffers.containsKey(item)) {
            List<SpecialOffer> offers = specialOffers.get(item);
            int minTotal = Integer.MAX_VALUE;

            for(SpecialOffer offer : offers) {
                int offerCount = count / offer.quantity;
                int remainingCount = count % offer.quantity;

                if (offer.freeItem != '\0' && itemCounts.containsKey(offer.freeItem)) {
                    int freeItemCount = itemCounts.get(offer.freeItem);
                    int freeItems = Math.min(offer.freeItemCount, offerCount * offer.quantity);
                    remainingCount -= freeItems / offer.quantity;
                }

                int currentTotal = offerCount * offer.offerPrice + remainingCount * prices.get(item) +

            }

        } else {
            totalPrice += count * prices.get(item);
        }
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




