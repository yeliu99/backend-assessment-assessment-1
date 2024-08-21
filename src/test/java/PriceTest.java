import com.katanox.api.price.PriceRepository;
import com.katanox.api.price.PriceService;
import com.katanox.impl.price.Price;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PriceTest {
    private static final Currency EUR = Currency.getInstance("EUR");
    private static final LocalDate DATE_1 = LocalDate.of(2022, 3, 1);
    private static final LocalDate DATE_2 = LocalDate.of(2022, 3, 2);
    private static final LocalDate DATE_3 = LocalDate.of(2022, 3, 3);
    private static final Price PRICE_1 = new Price(DATE_1, 1, 1L, 100, EUR);
    private static final Price PRICE_2 = new Price(DATE_2, 1, 1L, 110, EUR);
    private static final Price PRICE_3 = new Price(DATE_3, 1, 1L, 120, EUR);
    private static final Price PRICE_4 = new Price(DATE_2, 1, 0L, 110, EUR);

    private final PriceRepository priceRepository = mock(PriceRepository.class);
    private final PriceService priceService = new PriceService(priceRepository);

    @Test
    public void shouldReturnPricesWhenAvailableRoomExists() {
        when(priceRepository.getAvailableRoomPriceAtHotelBetweenDates(any(), any(), anyLong()))
                .thenReturn(List.of(PRICE_1, PRICE_2));

        Map<Long, List<Price>> result = priceService.getAvailableRoomPrices(DATE_1, DATE_3, 2, 1L);
        assertThat(result).hasSize(1);
    }

    @Test
    public void shouldReturnEmptyWhenRoomIsNotAlwaysAvailableBetweenSelectedDates() {
        when(priceRepository.getAvailableRoomPriceAtHotelBetweenDates(any(), any(), anyLong()))
                .thenReturn(List.of(PRICE_1, PRICE_3));

        Map<Long, List<Price>> result = priceService.getAvailableRoomPrices(DATE_1, DATE_3, 2, 1L);
        assertThat(result).hasSize(0);
    }

    @Test
    public void shouldReturnEmptyWhenAvailableRoomIsZero() {
        when(priceRepository.getAvailableRoomPriceAtHotelBetweenDates(any(), any(), anyLong()))
                .thenReturn(List.of(PRICE_1, PRICE_4));

        Map<Long, List<Price>> result = priceService.getAvailableRoomPrices(DATE_1, DATE_3, 2, 1L);
        assertThat(result).hasSize(0);
    }
}
