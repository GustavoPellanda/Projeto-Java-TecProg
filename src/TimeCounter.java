/**
 * This class is responsible for keeping track of the year the application is in.
 */

public class TimeCounter {
    int currentYear;
    public TimeCounter() { this.currentYear = 0; }
    public int getCurrentYear() { return currentYear; }
    public void incrementYear() { currentYear++; }
}