package com.thirds.kuri.unit;

public class FormattedTime {
    private final Day day;
    private final int hours;
    private final int minutes;
    public final float seconds;

    public enum Month {
        HAMMER(1, "Ham"),
        ALTURIAK(2, "Alt"),
        CHES(3, "Che"),
        TARSAKH(4, "Tar"),
        MIRTUL(5, "Mir"),
        KYTHORN(6, "Kyt"),
        FLAMERULE(7, "Fla"),
        ELEASIS(8, "Els"),
        ELEINT(9, "Elt"),
        MARPENOTH(10, "Mar"),
        UKTAR(11, "Ukt"),
        NIGHTAL(12, "Nig"),

        MIDWINTER(-1, "Midwtr"),
        GREENGRASS(-1, "Grngrs"),
        MIDSUMMER(-1, "Midsmr"),
        SHIELDMEET(-1, "Shldmt"),
        HIGHHARVESTTIDE(-1, "Hiharv"),
        FEAST_OF_THE_MOON(-1, "Feastm")
        ;

        private final int monthNumber;
        /**
         * For months with days, this is a 3-letter month code.
         * Else, this is a 6-letter holiday code.
         */
        private final String abbreviation;

        Month(int monthNumber, String abbreviation) {
            this.monthNumber = monthNumber;
            this.abbreviation = abbreviation;
        }
    }

    public static class Day {
        private final int year;
        private final Month month;
        private final int dayNumber;

        public Day(int absoluteDay) {

            int fourYearGroup = absoluteDay / (365 + 365 + 365 + 366);
            int dayWithinGroup = absoluteDay % (365 + 365 + 365 + 366);

            if (dayWithinGroup < 365) {
                year = fourYearGroup * 4 + 1;
            } else if (dayWithinGroup < 365 * 2) {
                year = fourYearGroup * 4 + 2;
            } else if (dayWithinGroup < 365 * 3) {
                year = fourYearGroup * 4 + 3;
            } else {
                year = fourYearGroup * 4 + 4;
            }

            if (dayWithinGroup == 365 * 4) {
                month = Month.NIGHTAL;
                dayNumber = 30;
                return;
            }

            int dayWithinYear = dayWithinGroup % 365;
            if (dayWithinYear < 30) {
                month = Month.HAMMER;
                dayNumber = dayWithinYear + 1;
            } else if (dayWithinYear == 30) {
                month = Month.MIDWINTER;
                dayNumber = -1;
            } else if (dayWithinYear < 61) {
                month = Month.ALTURIAK;
                dayNumber = dayWithinYear - 30;
            } else if (dayWithinYear < 91) {
                month = Month.CHES;
                dayNumber = dayWithinYear - 60;
            } else if (dayWithinYear < 121) {
                month = Month.TARSAKH;
                dayNumber = dayWithinYear - 90;
            } else if (dayWithinYear == 121) {
                month = Month.GREENGRASS;
                dayNumber = -1;
            } else if (dayWithinYear < 152) {
                month = Month.MIRTUL;
                dayNumber = dayWithinYear - 121;
            } else if (dayWithinYear < 182) {
                month = Month.KYTHORN;
                dayNumber = dayWithinYear - 151;
            } else if (dayWithinYear < 212) {
                month = Month.FLAMERULE;
                dayNumber = dayWithinYear - 181;
            } else if (dayWithinYear == 212) {
                month = Month.MIDSUMMER;
                dayNumber = -1;
            } else {
                // Are we on a leap year?
                boolean leapYear = dayWithinGroup >= 365 * 3;
                if (leapYear && dayWithinYear == 213) {
                    month = Month.SHIELDMEET;
                    dayNumber = -1;
                } else {
                    if (leapYear) {
                        dayWithinYear--;
                    }

                    if (dayWithinYear < 243) {
                        month = Month.ELEASIS;
                        dayNumber = dayWithinYear - 212;
                    } else if (dayWithinYear < 273) {
                        month = Month.ELEINT;
                        dayNumber = dayWithinYear - 242;
                    } else if (dayWithinYear == 273) {
                        month = Month.HIGHHARVESTTIDE;
                        dayNumber = -1;
                    } else if (dayWithinYear < 304) {
                        month = Month.MARPENOTH;
                        dayNumber = dayWithinYear - 273;
                    } else if (dayWithinYear < 334) {
                        month = Month.UKTAR;
                        dayNumber = dayWithinYear - 303;
                    } else if (dayWithinYear == 334) {
                        month = Month.FEAST_OF_THE_MOON;
                        dayNumber = -1;
                    } else {
                        month = Month.NIGHTAL;
                        dayNumber = dayWithinYear - 334;
                    }
                }
            }
        }

        /**
         * Converts the Day to a Year-Month-Day string.
         */
        public String toYMD() {
            String monthDay;
            switch (month) {
                case MIDWINTER:
                case GREENGRASS:
                case MIDSUMMER:
                case SHIELDMEET:
                case HIGHHARVESTTIDE:
                case FEAST_OF_THE_MOON:
                    monthDay = month.abbreviation;
                    break;
                default:
                    monthDay = month.abbreviation + "-" + String.format("%02d", dayNumber);
            }
            return String.format("%03d-%s", year, monthDay);
        }
    }

    public FormattedTime(int day, int hours, int minutes, float seconds) {
        this.day = new Day(day);
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return toIso8601();
    }

    /**
     * Returns an ISO 8601-like string suitable for rendering Kuri dates.
     */
    public String toIso8601() {
        return String.format("%s %02d:%02d:%02d", day.toYMD(), hours, minutes, (int) seconds);
    }
}
