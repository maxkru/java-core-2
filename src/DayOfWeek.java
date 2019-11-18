public enum DayOfWeek {
    MONDAY(40),
    TUESDAY(32),
    WEDNESDAY(24),
    THURSDAY(16),
    FRIDAY(8),
    SATURDAY(0),
    SUNDAY(0);

    private int remainingHours;

    DayOfWeek(int remainingHours) {
        this.remainingHours = remainingHours;
    }

    public int getRemainingHours() {
        return remainingHours;
    }
}