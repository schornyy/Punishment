package de.schornyy.punishplugin.punishment;

import de.schornyy.punishplugin.utils.Formatting;

public class Reason {

    private String reason;
    private int seconds, minits, hours, days, month, years;

    //0.0.0.0

    public Reason(String reason) {
        this.reason = reason;
        seconds = 0;
        minits = 0;
        hours = 0;
        days = 0;
        month = 0;
        years = 0;
    }

    public String getReason() {
        return reason;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMinits() {
        return minits;
    }

    public int getHours() {
        return hours;
    }

    public int getDays() {
        return days;
    }

    public int getMonth() {
        return month;
    }

    public int getYears() {
        return years;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinits(int minits) {
        this.minits = minits;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void setYears(int years) {
        this.years = years;
    }
}
