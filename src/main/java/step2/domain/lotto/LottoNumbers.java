package step2.domain.lotto;

import step2.vo.Rank;

import java.util.*;

public class LottoNumbers {
    private static final int FIXED_NUMBER = 6;
    private static final int LIMIT_TIME = 3000;

    private final Set<LottoNumber> lottoNumbers = new HashSet<>(FIXED_NUMBER);

    public LottoNumbers(LottoNumberGenerationStrategy strategy) {
        final long startTime = System.currentTimeMillis();
        while (lottoNumbers.size() < FIXED_NUMBER) {
            lottoNumbers.add(new LottoNumber(strategy.generateNumber()));
            validCreationTime(startTime);
        }
    }

    public LottoNumbers() {
        this(new LottoNumberAutoGenerationStrategy());
    }

    LottoNumbers(LottoNumbers lottoNumbers) {
        this.lottoNumbers.addAll(lottoNumbers.lottoNumbers);
    }

    public LottoNumbers(List<Integer> lottoNumbers) {
        validLottoNumbers(lottoNumbers);

        for (int number : lottoNumbers) {
            this.lottoNumbers.add(new LottoNumber(number));
        }

        validLottoNumbers(this.lottoNumbers);
    }


    private void validCreationTime(long startTime) {
        if (System.currentTimeMillis() - startTime > LIMIT_TIME) {
            throw new RuntimeException("로또 숫자 생성 시간이 초과되었습니다.");
        }
    }

    private <T> void validLottoNumbers(Collection<T> lottoNumbers) {
        if (lottoNumbers.size() != FIXED_NUMBER) {
            throw new RuntimeException("로또 숫자는 6개여야 입니다.");
        }
    }

    public Rank countNumberOfMatch(LottoNumbers lottoNumbers) {
        int matchCount = 0;
        for (LottoNumber lottoNumber : lottoNumbers.lottoNumbers) {
            matchCount += getIfContains(lottoNumber);
        }
        return Rank.createRank(matchCount);
    }

    private int getIfContains(LottoNumber lottoNumber) {
        return this.lottoNumbers.contains(lottoNumber) ? 1 : 0;
    }

    public boolean isContains(LottoNumber lottoNumber) {
        return this.getIfContains(lottoNumber) == 1 ? true : false;
    }

    @Override
    public String toString() {
        return lottoNumbers.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LottoNumbers)) return false;
        LottoNumbers that = (LottoNumbers) o;
        return Objects.equals(lottoNumbers, that.lottoNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lottoNumbers);
    }
}
