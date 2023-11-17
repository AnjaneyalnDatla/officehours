package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessHours {

    public static final String DAILY_BUSINESS_HOURS_FORMAT = "%s-%s:%s %s"; // example - Mon-Fri:8:00AM-5:00PM CST

    @JsonProperty("tz")
    String timeZone;
    @JsonProperty("m")
    Monday monday;
    @JsonProperty("t")
    Tuesday tuesday;
    @JsonProperty("w")
    Wednesday wednesday;
    @JsonProperty("th")
    Thursday thursday;
    @JsonProperty("f")
    Friday friday;
    @JsonProperty("sat")
    Saturday saturday;
    @JsonProperty("sun")
    Sunday sunday;

    /**
     * Get working hours for days with days wit same times merged
     * @return
     */
    private List<String> mergeDaysWithSameTimmings() {
        // Create a days list
        List<Day> days = new ArrayList<>();
        days.add(this.monday);
        days.add(this.tuesday);
        days.add(this.wednesday);
        days.add(this.thursday);
        days.add(this.friday);
        days.add(this.saturday);
        days.add(this.sunday);
        List<String> daysWithSameWorkingHours = new ArrayList<>();

        buildWorkingHoursList(days, daysWithSameWorkingHours);
        return daysWithSameWorkingHours.stream().map(workingHours -> workingHours.replace("-%s", ""))
                .collect(Collectors.toList());
    }

    /**
     * 
     * @return
     */
    public String renderToAString() {
        return StringUtils.join(mergeDaysWithSameTimmings(), ",");
    }

    /**
     *  
     * @param days
     * @param daysWithSameWorkingHours
     */
    private void buildWorkingHoursList(List<Day> days, List<String> daysWithSameWorkingHours) {
        String tempKey = "";
        String endDay = "%s"; // This will keep changing, so keeping this as placeholder
        String workingHoursEntry = "";
        Integer continuousTimeIndex = 0;
        for (int i = 0; i < days.size(); i++) {
            // using the open and close hours as the key to identify days with same timmings
            String key = days.get(i).getOpen() + "-" + days.get(i).getClose();
            // merging days with same open and close days
            if (tempKey.equalsIgnoreCase(key)) {
                String existingKey = daysWithSameWorkingHours.get(continuousTimeIndex);
                daysWithSameWorkingHours.set(continuousTimeIndex, String.format(existingKey, days.get(i).getName()));
            } 
            // create a new entry for days with different open and close timmings
            else {
                workingHoursEntry = String.format(DAILY_BUSINESS_HOURS_FORMAT, days.get(i).getName(), endDay, key,
                        this.timeZone);
                daysWithSameWorkingHours.add(workingHoursEntry);
                tempKey = key;
                continuousTimeIndex = i;
            }
        }
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Day getMonday() {
        return monday;
    }

    public void setMonday(Monday monday) {
        this.monday = monday;
    }

    public Day getTuesday() {
        return tuesday;
    }

    public void setTuesday(Tuesday tuesday) {
        this.tuesday = tuesday;
    }

    public Day getWednesday() {
        return wednesday;
    }

    public void setWednesday(Wednesday wednesday) {
        this.wednesday = wednesday;
    }

    public Day getThursday() {
        return thursday;
    }

    public void setThursday(Thursday thursday) {
        this.thursday = thursday;
    }

    public Day getFriday() {
        return friday;
    }

    public void setFriday(Friday friday) {
        this.friday = friday;
    }

    public Day getSaturday() {
        return saturday;
    }

    public void setSaturday(Saturday saturday) {
        this.saturday = saturday;
    }

    public Day getSunday() {
        return sunday;
    }

    public void setSunday(Sunday sunday) {
        this.sunday = sunday;
    }

}
