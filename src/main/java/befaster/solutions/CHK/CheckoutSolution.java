package befaster.solutions.CHK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutSolution {
    public Integer checkout(String skus) {

        Map<Character, Integer> prices = new HashMap<>();
        Map<Character, SpecialOffer> specialOffers = new HashMap<>();

        prices.put('A', 50);
        prices.put('B', 30);
        prices.put('C', 20);
        prices.put('D', 15);
        prices.put('E', 40);

        List<SpecialOffer> offersA = new ArrayList<>();
        offersA.add(new SpecialOffer(3, 130));
        offersA.add(new SpecialOffer(5, 200));

        List<SpecialOffer> offersB = new ArrayList<>();
        offersB.add(new SpecialOffer(2, 45));

        List<SpecialOffer> offersE = new ArrayList<>();
        offersA.add(new SpecialOffer(2, 30));

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
            char item = entry.getKey();
            int count = entry.getValue();

            if (specialOffers.containsKey(item)) {
                SpecialOffer offer = specialOffers.get(item);
                totalPrice +=  (count / offer.quantity) * offer.offerPrice +
                        (count % offer.quantity) * prices.get(item);
            } else {
                totalPrice += count * prices.get(item);
            }
        }

        return totalPrice;
    }


    private static class SpecialOffer {
        private int quantity;
        private int offerPrice;

        public SpecialOffer(int quantity, int offerPrice) {
            this.offerPrice = offerPrice;
            this.quantity = quantity;
        }
    }
}
