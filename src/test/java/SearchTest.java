import com.katanox.api.flat_charges.FlatChargeService;
import com.katanox.api.price.Price;
import com.katanox.api.price.PriceService;
import com.katanox.api.search.SearchRequest;
import com.katanox.api.search.SearchResponse;
import com.katanox.api.search.SearchResult;
import com.katanox.api.search.SearchService;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchTest {
    private static final Currency EUR = Currency.getInstance("EUR");
    private static final LocalDate DATE_1 = LocalDate.of(2025, 10, 1);
    private static final LocalDate DATE_2 = LocalDate.of(2025, 10, 2);
    private static final LocalDate DATE_3 = LocalDate.of(2025, 10, 3);
    private static final Price PRICE_1 = new Price(DATE_1, 1, 1L, 100, EUR);
    private static final Price PRICE_2 = new Price(DATE_2, 1, 1L, 110, EUR);
    private static final Map<Long, List<Price>> PRICES_RESULTS = Map.of(1L, List.of(PRICE_1, PRICE_2));

    private final PriceService priceService = mock(PriceService.class);
    private final FlatChargeService flatChargeService = mock(FlatChargeService.class);
    private final SearchService searchService = new SearchService(priceService, flatChargeService);

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenSearchDateIsNotValid() {
        SearchRequest checkoutBeforeCheckin = new SearchRequest(LocalDate.of(2024, 5, 3),
                LocalDate.of(2024, 5, 1),
                1L);
        SearchRequest checkinIsInThePast = new SearchRequest(LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 5, 3),
                1L);

        assertThatThrownBy(() -> searchService.search(checkoutBeforeCheckin)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> searchService.search(checkinIsInThePast)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldNotThrowWhenSearchDateIsValid() {
        SearchRequest validSearchRequest = new SearchRequest(DATE_1, DATE_2,1L);

        assertDoesNotThrow(() -> searchService.search(validSearchRequest));
    }

    @Test
    public void shouldReturnCorrectTotalPrice() {
        when(priceService.getAvailableRoomPrices(any(), any(), anyInt(), anyLong())).thenReturn(PRICES_RESULTS);
        when(flatChargeService.calculateFlatCharges(1L, 2)).thenReturn(60d);

        SearchRequest searchRequest = new SearchRequest(DATE_1, DATE_3, 1L);
        SearchResponse searchResponse = searchService.search(searchRequest);
        assertThat(searchResponse.searchResults()).hasSize(1);

        SearchResult searchResult = searchResponse.searchResults().get(0);
        assertThat(searchResult.price()).isEqualTo(270d);
    }
}
