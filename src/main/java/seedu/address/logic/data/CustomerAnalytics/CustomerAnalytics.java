package seedu.address.logic.data.CustomerAnalytics;

import seedu.address.logic.data.Analytics;
import seedu.address.model.money.Money;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDateTime;
import java.util.Comparator;

public class CustomerAnalytics extends Analytics {
    /**
     * Orders person list by total spending across all orders, from smallest to largest
     * @param personList
     */
    public static void orderBySpending(ArrayList<Person> personList) {
        //Sort by spending, from smallest to biggest
        Collections.sort(personList, (person1, person2) -> {
            Money p1Spending = new Money();
            Money p2Spending = new Money();
            ArrayList<Order> p1Orders = person1.getOrders();
            ArrayList<Order> p2Orders = person2.getOrders();

            for(Order order : p1Orders)
                p1Spending = p1Spending.plus(order.getOrderTotal());
            for(Order order : p2Orders)
                p2Spending = p2Spending.plus(order.getOrderTotal());

            return p1Spending.compareTo(p2Spending);
        });
    }

    /**
     * Orders person list by total spending since time, from smallest to largest
     * @param personList
     * @param time
     */
    public static void orderBySpendingSinceTime(ArrayList<Person> personList, LocalDateTime time) {
        //Sort by spending since time, from smallest to biggest
        Collections.sort(personList, (person1, person2) -> {
            Money p1Spending = new Money();
            Money p2Spending = new Money();
            ArrayList<Order> p1Orders = person1.getOrders();
            ArrayList<Order> p2Orders = person2.getOrders();

            for (Order order : p1Orders)
                if (order.getTime().compareTo(time) >= 0)
                    p1Spending = p1Spending.plus(order.getOrderTotal());
            for (Order order : p2Orders)
                if (order.getTime().compareTo(time) >= 0)
                    p2Spending = p2Spending.plus(order.getOrderTotal());

            return p1Spending.compareTo(p2Spending);
        });
    }

    /**
     * Orders person list by time since last order, from most recent to least recent
     * @param personList
     */
    public static void orderByLastActive(ArrayList<Person> personList) {
        //Sort by last order time, from biggest to smallest
        Collections.sort(personList, Comparator.comparing(Person::getLastOrderTime));
    }
}
