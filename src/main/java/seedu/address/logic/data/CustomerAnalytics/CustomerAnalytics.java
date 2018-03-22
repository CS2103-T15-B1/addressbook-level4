package seedu.address.logic.data.CustomerAnalytics;

import seedu.address.logic.data.Analytics;
import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDateTime;

public class CustomerAnalytics extends Analytics {
    public static void orderBySpending(ArrayList<Person> personList) {
        //Sort by spending, from smallest to biggest
        Collections.sort(personList, (person1, person2) -> {
            Money p1Spending = new Money();
            Money p2Spending = new Money();
            ArrayList<Order> p1Orders = person1.getOrders();
            ArrayList<Order> p2Orders = person2.getOrders();

            for(Order order : p1Orders)
                p1Spending = p1Spending.add(order.getOrderTotal());
            for(Order order : p2Orders)
                p2Spending = p2Spending.add(order.getOrderTotal());

            return p1Spending.compareTo(p2Spending);
        });
    }

    public static void orderBySpendingSinceTime(ArrayList<Person> personList, LocalDateTime time) {
        Collections.sort(personList, (person1, person2) -> {
            Money p1Spending = new Money();
            Money p2Spending = new Money();
            ArrayList<Order> p1Orders = person1.getOrders();
            ArrayList<Order> p2Orders = person2.getOrders();

            for (Order order : p1Orders)
                if (order.getTime() >= time)
                    p1Spending = p1Spending.add(order.getOrderTotal());
            for (Order order : p2Orders)
                if (order.getTime() >= time)
                    p2Spending = p2Spending.add(order.getOrderTotal());

            return p1Spending.compareTo(p2Spending);
        });
    }
}
