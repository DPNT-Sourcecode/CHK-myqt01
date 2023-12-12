package befaster.solutions.SUM;

import befaster.solutions.CHK.CheckoutSolution;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SumSolutionTest {
    private SumSolution sum;
    private CheckoutSolution checkoutSolution;

    @BeforeEach
    public void setUp() {
        sum = new SumSolution();
        checkoutSolution = new CheckoutSolution();
    }

    @Test
    public void compute_sum() {
        assertThat(sum.compute(1, 1), equalTo(2));
    }

    @Test
    public void checkoutSolution_okWithPromotion() {
        assertThat(checkoutSolution.checkout("AAAA"), equalTo(180));
    }

    @Test
    public void checkoutSolution_invalid() {
        assertThat(checkoutSolution.checkout("AAAA "), equalTo(-1));
    }

    @Test
    public void checkoutSolution_allItems() {
        assertThat(checkoutSolution.checkout("ABCDE"), equalTo(155));
    }

    @Test
    public void checkoutSolution_allItemsAndPromotion() {
        assertThat(checkoutSolution.checkout("ABCAAAD"), equalTo(245));
    }

    @Test
    public void checkoutSolution_5As() {
        assertThat(checkoutSolution.checkout("AAAAA"), equalTo(200));
    }

    @Test
    public void checkoutSolution_8As() {
        assertThat(checkoutSolution.checkout("AAAAAAAA"), equalTo(330));
    }

    @Test
    public void checkoutSolution_2Es1B() {
        assertThat(checkoutSolution.checkout("EEB"), equalTo(80));
    }

    @Test
    public void checkoutSolution_FFFF() {
        assertThat(checkoutSolution.checkout("FFFF"), equalTo(30));
    }
}
