package org.example;

public enum DaysOrder {

    MONDAY("Mon"),
    TUESDAY("Tue"),
    WEDNESDAY("Wed"),
    THURSDAY("Thu"),
    FRIDAY("Fri"),
    SATURDAY("Sat"),
    SUNDAY("Sun");

    String name;

    DaysOrder(String name) {
        this.name = name;
    }

}
