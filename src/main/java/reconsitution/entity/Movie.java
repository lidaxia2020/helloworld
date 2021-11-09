package reconsitution.entity;

/**
 * @author daxia li
 * @desc
 * @date 2021/11/8 17:45（
 */
public class Movie {

    public static final int CHILDRENS = 2;

    public static final int REGULAR = 0;

    public static final int NEW_RELEASE = 1;

    private String _title;
    private int _priceCode;

    private Price _price;

    public Movie(String _title, int _priceCode) {
        this._title = _title;
        set_priceCode(_priceCode);
    }


    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public int get_priceCode() {
        return _price.getPriceCode();
    }

    public void set_priceCode(int arg) {
        switch (arg) {
            case Movie.REGULAR:
                _price = new RegularPrice();
                break;
            case Movie.NEW_RELEASE:
                _price = new ChildrensPrice();
                break;
            case Movie.CHILDRENS:
               _price = new NewReleasePrice();
                break;
            default:
                throw new IllegalArgumentException("Incorrenct Price Code");
        }
    }

    int getFrequentRenterPoints(int daysRented) {
        return _price.getFrequentRenterPoints(daysRented);
    }

    double getCharge(int daysRented) {
        return _price.getCharge(daysRented);
    }
}
