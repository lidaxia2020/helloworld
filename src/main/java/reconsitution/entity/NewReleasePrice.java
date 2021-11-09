package reconsitution.entity;

/**
 * @author daxia li
 * @desc
 * @date 2021/11/9 10:58（
 */
public class NewReleasePrice extends Price {

    @Override
    int getPriceCode() {
        return Movie.NEW_RELEASE;
    }

    @Override
    double getCharge(int daysRented) {
        return daysRented * 3;
    }

    @Override
    int getFrequentRenterPoints(int dasRented) {
        return dasRented > 1 ? 2 : 1;
    }
}
