package lotto.view;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import lotto.domain.LotteryResults;
import lotto.domain.Lotto;
import lotto.domain.Lottos;
import lotto.domain.type.WinningType;

public class ResultView {
	private static final String NUMBER_OF_PURCHASE_LOTTO = "개를 구매했습니다.";
	private static final String WINNING_STATICS = "당첨 통계\n---------";
	private static final String DEFAULT_WINNING_RESULT = "%d개 일치 (%d원)- %d개";
	private static final String BONUS_WINNING_RESULT = "%d개 일치, 보너스 볼 일치(%d원) - %d개";
	private static final String TOTAL_YIELD = "총 수익률은 %.2f입니다.";
	private static final String FYI_TOTAL_YIELD = "(기준이 1이기 때문에 결과적으로 손해라는 의미임)";

	public static void outputPurchaseLotto(Lottos lottos) {
		int numberOfLottos = lottos.getNumberOfLottos();
		System.out.println(numberOfLottos + NUMBER_OF_PURCHASE_LOTTO);
		lottos.getLottos()
			.forEach(lotto -> System.out.println(printPurchaseLottoNumbers(lotto)));
	}

	private static List<Integer> printPurchaseLottoNumbers(Lotto lotto) {
		return lotto.getNumbers()
			.getNumberValues();
	}

	public static void outputWinningStatistics(LotteryResults results) {
		System.out.println(WINNING_STATICS);

		Map<WinningType, Integer> drawResultMap = results.getDrawResult();
		Arrays.stream(WinningType.values())
			.filter(winningType -> winningType != WinningType.MISMATCH)
			.sorted(Comparator.comparing(WinningType::getWinnings))
			.forEach(winningType -> printWinningStatistics(winningType, drawResultMap));

		float totalYield = results.getTotalYield();
		String outputTotalYield = String.format(TOTAL_YIELD, totalYield);
		if (totalYield < 1) {
			outputTotalYield += FYI_TOTAL_YIELD;
		}
		System.out.println(outputTotalYield);
	}

	private static void printWinningStatistics(WinningType winningType, Map<WinningType, Integer> drawResultMap) {
		int numberOfMatch = winningType.getNumberOfMatch();
		int winnings = winningType.getWinnings();
		int numberOfDraw = drawResultMap.get(winningType);

		String outputWinningTypes = String.format(DEFAULT_WINNING_RESULT, numberOfMatch, winnings, numberOfDraw);

		if (winningType == WinningType.SECOND) {
			outputWinningTypes = String.format(BONUS_WINNING_RESULT, numberOfMatch, winnings, numberOfDraw);
		}

		System.out.println(outputWinningTypes);
	}
}
