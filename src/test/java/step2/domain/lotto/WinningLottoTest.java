package step2.domain.lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import step2.domain.statistics.WinningStatistics;
import step2.utils.WinningStatisticsTestUtil;
import step2.vo.Rank;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WinningLottoTest {

    @Test
    @DisplayName("Bouns Number로 중복 값 넣을 시 생성 실패")
    void createWinningLottoSuccessTest() {
        final int bonusNumberValue = 6;
        final LottoNumber bonusNumber = new LottoNumber(bonusNumberValue);
        final LottoNumbers lottoNumbers = new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, bonusNumberValue));
        assertThatThrownBy(() -> new WinningLotto(lottoNumbers, bonusNumber))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("보너스 번호는 당첨 번호와 중복될 수 없습니다.");
    }

    @Test
    @DisplayName("1등(6개 일치) 3개, 총 3개 구매에 대한 통계 테스트")
    void matchSuccessTest1() {
        final LottoNumbers winningLottoValue = new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6));
        final Lottos wonLottos = new Lottos(Arrays.asList(
                new Lotto(winningLottoValue),
                new Lotto(winningLottoValue),
                new Lotto(winningLottoValue)));
        System.out.println(wonLottos);

        final WinningLotto standardLotto = new WinningLotto(winningLottoValue, new LottoNumber(45));
        final WinningStatistics actual = standardLotto.match(wonLottos);
        System.out.println(actual);

        final WinningStatistics expected = WinningStatisticsTestUtil.createWinningStatistics(Rank.FIRST, 3, 3);
        System.out.println(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("5등(3개 일치) 3개, 총 3개 구매에 대한 통계 테스트")
    void matchSuccessTest2() {
        final LottoNumbers lottoNumbers = new LottoNumbers(Arrays.asList(1, 2, 3, 10, 11, 12));
        final Lottos wonLottos = new Lottos(Arrays.asList(
                new Lotto(lottoNumbers),
                new Lotto(lottoNumbers),
                new Lotto(lottoNumbers)));
        System.out.println(wonLottos);

        final WinningLotto standardLotto = new WinningLotto(new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6)), new LottoNumber(45));
        WinningStatistics actual = standardLotto.match(wonLottos);
        System.out.println(actual);

        final WinningStatistics expected = WinningStatisticsTestUtil.createWinningStatistics(Rank.FIFTH, 3, 3);
        System.out.println(expected);

        assertThat(actual).isEqualTo(expected);
    }
}
