import com.katanox.api.extra_charges.percentage_charges.PercentageChargeRepository;
import com.katanox.api.extra_charges.percentage_charges.PercentageChargeService;
import com.katanox.impl.percentage_charges.PercentageCharge;
import com.katanox.impl.percentage_charges.PercentageChargeType;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PercentageChargeTest {
    private static final PercentageCharge PERCENTAGE_CHARGE_1 =
            new PercentageCharge(PercentageChargeType.FIRST_NIGHT, 20, 1L);
    private static final PercentageCharge PERCENTAGE_CHARGE_2 =
            new PercentageCharge(PercentageChargeType.TOTAL_AMOUNT, 50, 1L);
    private static final List<PercentageCharge> PERCENTAGE_CHARGES =
            List.of(PERCENTAGE_CHARGE_1, PERCENTAGE_CHARGE_2);
    private static final List<PercentageCharge> NO_PERCENTAGE_CHARGES = Collections.emptyList();

    private final PercentageChargeRepository percentageChargeRepository = mock(PercentageChargeRepository.class);
    private final PercentageChargeService percentageChargeService
            = new PercentageChargeService(percentageChargeRepository);

    @Test
    public void shouldReturnZeroWhenFlatChargesDoNotExist() {
        when(percentageChargeRepository.getExtraPercentageChargesWithHotelID(anyLong())).thenReturn(NO_PERCENTAGE_CHARGES);
        assertThat(percentageChargeService.calculatePercentageCharges(1L, 100, 300)).isZero();
    }

    @Test
    public void shouldReturnCorrectValueWhenFlatChargesExist() {
        when(percentageChargeRepository.getExtraPercentageChargesWithHotelID(anyLong())).thenReturn(PERCENTAGE_CHARGES);
        assertThat(percentageChargeService.calculatePercentageCharges(1L, 100, 300)).isEqualTo(170);
        assertThat(percentageChargeService.calculatePercentageCharges(1L, 100, 100)).isEqualTo(70);
    }
}
