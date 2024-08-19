import com.katanox.api.flat_charges.FlatCharge;
import com.katanox.api.flat_charges.FlatChargeService;
import com.katanox.api.flat_charges.FlatChargeType;
import com.katanox.api.flat_charges.FlatChargesRepository;
import org.junit.Test;

import java.util.Collections;
import java.util.Currency;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FlatChargeTest {
    private static final Currency EUR = Currency.getInstance("EUR");
    private static final FlatCharge FLAT_CHARGE_1 =
            new FlatCharge(FlatChargeType.ONCE, 25, EUR, 1L);
    private static final FlatCharge FLAT_CHARGE_2 =
            new FlatCharge(FlatChargeType.PER_NIGHT, 5, EUR, 1L);
    private static final List<FlatCharge> FLAT_CHARGES =
            List.of(FLAT_CHARGE_1, FLAT_CHARGE_2);
    private static final List<FlatCharge> NO_FLAT_CHARGES = Collections.emptyList();

    private final FlatChargesRepository flatChargesRepository = mock(FlatChargesRepository.class);
    private final FlatChargeService flatChargeService = new FlatChargeService(flatChargesRepository);

    @Test
    public void shouldReturnZeroWhenFlatChargesDoNotExist() {
        when(flatChargesRepository.getExtraFlatChargesWithHotelID(anyLong())).thenReturn(NO_FLAT_CHARGES);
        assertThat(flatChargeService.calculateFlatCharges(1L, 1)).isZero();
    }

    @Test
    public void shouldReturnCorrectValueWhenFlatChargesExist() {
        when(flatChargesRepository.getExtraFlatChargesWithHotelID(anyLong())).thenReturn(FLAT_CHARGES);
        assertThat(flatChargeService.calculateFlatCharges(1L, 1)).isEqualTo(30);
        assertThat(flatChargeService.calculateFlatCharges(1L, 2)).isEqualTo(35);
    }
}
