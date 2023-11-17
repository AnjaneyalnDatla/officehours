package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessHours {

    public static final String DAILY_BUSINESS_HOURS_FORMAT = "%s-%s:%s %s"; // example - Mon-Fri:8:00AM-5:00PM CST
    public static final String DEFAULT_NONWORKING_TIME = "00";
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
     * 
     * @return
     */
    public String renderToAString() {
        return StringUtils.join(mergeDaysWithSameTimmings(), ",");
    }

    /**
     * Get working hours for days with days wit same times merged
     * 
     * @return
     */
    private List<String> mergeDaysWithSameTimmings() {
        // Create a days list
        List<Day> days = getDays();

        List<String> daysWithSameWorkingHours = new ArrayList<>();

        buildWorkingHoursList(days, daysWithSameWorkingHours);
        return daysWithSameWorkingHours.stream()
                .map(workingHours -> workingHours.replace("-%s", "")) // remove any string placeholders for single day entries
                .filter(workingHours -> !workingHours.contains("00-00")) // removing any days off
                .collect(Collectors.toList());
    }

    private List<Day> getDays() {
        List<Day> days = new ArrayList<>();
        addOrDefaultIfNotExists(days, this.monday, DaysEnum.MONDAY.name);
        addOrDefaultIfNotExists(days, this.tuesday, DaysEnum.TUESDAY.name);
        addOrDefaultIfNotExists(days, this.wednesday, DaysEnum.WEDNESDAY.name);
        addOrDefaultIfNotExists(days, this.thursday, DaysEnum.THURSDAY.name);
        addOrDefaultIfNotExists(days, this.friday, DaysEnum.FRIDAY.name);
        addOrDefaultIfNotExists(days, this.saturday, DaysEnum.SATURDAY.name);
        addOrDefaultIfNotExists(days, this.sunday, DaysEnum.SUNDAY.name);
        return days;
    }

    private void addOrDefaultIfNotExists(List<Day> days, Day day, String name) {
        if (day != null) {
            days.add(day);
        } else {
            days.add(new Day(DEFAULT_NONWORKING_TIME, DEFAULT_NONWORKING_TIME, name)); // Adding a default day, which                                                                          // can be filtered out later
        }
    }

    /**
     * 
     * @param days
     * @param daysWithSameWorkingHours
     */
    private void buildWorkingHoursList(List<Day> days, List<String> daysWithSameWorkingHours) {
        // A key to look for matching working hours in a day
        String tempKey = getKey(days, 0);
        // An end day indicator. This field will be used to keep track of continuous days that have the same work hours
        String endDay = "%s"; 
        // An index field to keep track of where to update the end date
        Integer continuousTimeIndex = 1;
        // Initializing the first entry to the list of working days
        addNewWorkingDaysEntry(days, daysWithSameWorkingHours, endDay, 0,tempKey);
        for (int i = 0; i < days.size(); i++) {
            // using the open and close hours as the key to identify days with same timmings
            String key = getKey(days, i);
            // Updating the end day to reflect the final end day with a series of days having same timmings
            if (tempKey.equalsIgnoreCase(key)) {
                endDay = days.get(i).getName();
                // Condition to check if we reached the end
                if (i == days.size() - 1) {
                    updateLastContinuousWorkingHoursEntry(daysWithSameWorkingHours, endDay, continuousTimeIndex);
                }
            }
            // create a new entry for days with different open and close timmings
            else {
                tempKey = key;
                // Update last entry
                updateLastContinuousWorkingHoursEntry(daysWithSameWorkingHours, endDay, continuousTimeIndex);
                // reset the end day
                endDay = "%s";
                // Add the new working hours entry
                addNewWorkingDaysEntry(days, daysWithSameWorkingHours, endDay, i, key);
                // Reset the continous working days index
                continuousTimeIndex = daysWithSameWorkingHours.size();
            }
        }
    }

    private String getKey(List<Day> days, Integer index) {
        return days.get(index).getOpen() + "-" + days.get(index).getClose();
    }

    private void updateLastContinuousWorkingHoursEntry(List<String> daysWithSameWorkingHours, String endDay,
            Integer continuousTimeIndex) {
        String existingKey = daysWithSameWorkingHours.get(continuousTimeIndex - 1);
        daysWithSameWorkingHours.set(continuousTimeIndex - 1, String.format(existingKey, endDay));
    }

    private void addNewWorkingDaysEntry(List<Day> days, List<String> daysWithSameWorkingHours, String endDay, int i,
            String key) {
        String workingHoursEntry = String.format(DAILY_BUSINESS_HOURS_FORMAT, days.get(i).getName(), endDay, key,
                this.timeZone);
        daysWithSameWorkingHours.add(workingHoursEntry);
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
