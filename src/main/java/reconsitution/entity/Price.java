package reconsitution.entity;

/**
 * @author daxia li
 * @desc
 * @date 2021/11/9 11:10（
 */
public abstract class Price {

    /**
     * 获得价格编码
     * @return
     */
    abstract int getPriceCode();

    /**
     * 获得价格
     * @param daysRented
     * @return
     */
    abstract double getCharge(int daysRented);

    int getFrequentRenterPoints(int dasRented) {
        return 1;
    }
}
