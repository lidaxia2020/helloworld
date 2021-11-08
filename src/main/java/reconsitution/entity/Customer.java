package reconsitution.entity;

import java.util.Enumeration;
import java.util.Vector;

/**
 * @author lijiannan
 * @desc
 * @date 2021/11/8 17:48（
 */
public class Customer {

    private String _name;
    private Vector _rentals = new Vector();

    public Customer(String _name) {
        this._name = _name;
    }

    public void addRental(Rental arg) {
        _rentals.add(arg);
    }

    public String getName() {
        return _name;
    }

    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;

        Enumeration enumeration = _rentals.elements();
        String result = "Rental Record for " + getName() + " \n";
        while (enumeration.hasMoreElements()) {
            double thisAmount = 0;
            Rental rental = (Rental) enumeration.nextElement();

            switch (rental.get_movie().get_priceCode()) {
                case Movie.REGULAR:
                    thisAmount += 2;
                    if (rental.get_daysRented() > 2) {
                        thisAmount += (rental.get_daysRented() - 2) * 1.5;
                    }
                    break;
                case Movie.NEW_RELEASE:
                    thisAmount += rental.get_daysRented() * 3;

                    break;
                case Movie.CHILDRENS:
                    thisAmount += 1.5;
                    if (rental.get_daysRented() > 3) {
                        thisAmount += (rental.get_daysRented() - 3) * 1.5;
                    }
                    break;
            }

            frequentRenterPoints++;
           // if ()
        }

        result += "";
        return result;
    }
}
