/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com600.example.journeyplanner.model;

/**
 *
 * @author cgallen
 */
public class TimeUtilities {
    
      /**
     * Utility method to check that time string is correct format. Throws
     * illegal format exception if not
     *
     * @param timeString time string to match. must be of form HH:MM in 24 hrs
     * @return
     */
    public static String checkTimeString(String timeString) {
        //uses regular expression to match HH:MM in 24 hr format
        // see https://stackoverflow.com/questions/7536755/regular-expression-for-matching-hhmm-time-format
        String TIME_REGEX = "^(?:\\d|[01]\\d|2[0-3]):[0-5]\\d$";

        if (timeString == null || !timeString.matches(TIME_REGEX)) {
            throw new IllegalArgumentException("timeString illegal format (should be hh:mm) : " + timeString);
        }
        return timeString;
    }

    
}
