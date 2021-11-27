package lotto.domain.value;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static lotto.fixture.LottoTicketFixture.LOTTO_TICKETS;
import static org.assertj.core.api.Assertions.assertThat;

public class LottoTicketsTest {

    @DisplayName("당첨결과를 반환한다.")
    @Test
    void test() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        WinningNumbers winningNumbers = WinningNumbers.of(numbers);
        Map<Rank, Integer> result = LOTTO_TICKETS.creatWinningRank(winningNumbers);

        assertThat(result.size()).isEqualTo(LOTTO_TICKETS.size());
    }

}
