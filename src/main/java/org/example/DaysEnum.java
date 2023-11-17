package org.example;

public enum DaysEnum {

    MONDAY("Mon"),
    TUESDAY("Tue"),
    WEDNESDAY("Wed"),
    THURSDAY("Thu"),
    FRIDAY("Fri"),
    SATURDAY("Sat"),
    SUNDAY("Sun");

    String name;

    DaysEnum(String name) {
        this.name = name;
    }

}
