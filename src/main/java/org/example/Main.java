package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

    public static void main(String[] args) throws Exception {

        String jsonString = "{\"tz\": \"CT\", " +
                "\"m\": {\"open\": \"8:00AM\", \"close\": \"4:30PM\"}, " +
                "\"t\": {\"open\": \"8:00AM\", \"close\": \"3:30PM\" }, " +
                "\"w\": {\"open\": \"8:00AM\", \"close\": \"4:30PM\" }, " +
                "\"th\": {\"open\": \"8:00AM\", \"close\": \"4:30PM\" }, " +
                "\"f\": {\"open\": \"8:00AM\", \"close\": \"4:30PM\" }, " +
             
                "\"sun\": {\"open\": \"8:00AM\", \"close\": \"4:30PM\" }  }";
        // Build a structure out of the string
        BusinessHours businessHours = new ObjectMapper().readValue(jsonString, BusinessHours.class);
        // return the string
        System.out.println(businessHours.renderToAString());

    }

}