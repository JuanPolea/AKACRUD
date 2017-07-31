package activity.com.jfmr.akacrud.pojo;

/**
 * Created by Juan Francisco Mateos Redondo
 */

public class DateTimePOJO {


    public static final String SEPARATOR = "T";
    public static final String DUMMYZERO = "0";
    public static final String DOTS = ":";
    private String s_date;
    private String s_time;
    private static String s_formattedDate;
    private int i_year;
    private int i_month;
    private int i_day;
    private int i_hour;
    private int i_minutes;
    private int i_seconds;

    public DateTimePOJO(){

    }

    public DateTimePOJO(String s_date, String s_time) {
        this.s_date = s_date;
        this.s_time = s_time;

    }


    /**
     *
     * @param dateTimePOJO
     * @return
     */
    public static String formatDate(DateTimePOJO dateTimePOJO){

        s_formattedDate = dateTimePOJO.getS_date().concat(SEPARATOR).concat(dateTimePOJO.getS_time());
        return s_formattedDate;

    }
    /**
     * Formatting data in order to show it in TextView
     * @return
     */
    public static String formatHour(DateTimePOJO date) {

        String hour = insertLeftZero(date.getI_hour());
        String minutes = insertLeftZero(date.getI_minutes());
        String seconds = insertLeftZero(date.getI_seconds());
        return hour.concat(DOTS).concat(minutes).concat(DOTS).concat(seconds);
    }    /**
     *
     * @param number
     * @return
     */
    public static String insertLeftZero(int number){
        return (number <10 ?String.valueOf(DUMMYZERO.concat(String.valueOf(number))): String.valueOf(number));

    }

    public String getS_date() {
        return s_date;
    }

    public void setS_date(String s_date) {
        this.s_date = s_date;
    }

    public String getS_time() {
        return s_time;
    }

    public void setS_time(String s_time) {
        this.s_time = s_time;
    }
    public int getI_year() {
        return i_year;
    }

    public void setI_year(int i_year) {
        this.i_year = i_year;
    }

    public int getI_month() {
        return i_month;
    }

    public void setI_month(int i_month) {
        this.i_month = i_month;
    }

    public int getI_day() {
        return i_day;
    }

    public void setI_day(int i_day) {
        this.i_day = i_day;
    }

    public int getI_hour() {
        return i_hour;
    }

    public void setI_hour(int i_hour) {
        this.i_hour = i_hour;
    }

    public int getI_minutes() {
        return i_minutes;
    }

    public void setI_minutes(int i_minutes) {
        this.i_minutes = i_minutes;
    }

    public int getI_seconds() {
        return i_seconds;
    }

    public void setI_seconds(int i_seconds) {
        this.i_seconds = i_seconds;
    }

    public void setS_formattedDate(String s_formattedDate) {
        this.s_formattedDate = s_formattedDate;
    }
    public String getS_formattedDate() {
        return s_formattedDate;
    }

}
