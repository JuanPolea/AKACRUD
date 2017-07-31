package activity.com.jfmr.akacrud.pojo;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static org.junit.Assert.*;

/**
 * Created by Juan Francisco Mateos Redondo
 */
public class DateTimePOJOTest {

    DateTimePOJO dateTimePOJO;
    private String s_date="1984-01-01";
    private String s_time = "12:12:12";
    private String s_formattedDate;
    private int i_year = 1900;
    private int i_month =10;
    private int i_day = 11;
    private int i_hour =4;
    private int i_minutes =23;
    private int i_seconds = 22;

    @Before
    public void setUp() throws Exception {

        dateTimePOJO = new DateTimePOJO();
        dateTimePOJO.setI_minutes(i_minutes);
        dateTimePOJO.setI_seconds(i_seconds);
        dateTimePOJO.setI_hour(i_hour);
        dateTimePOJO.setI_day(i_day);
        dateTimePOJO.setI_month(i_month);
        dateTimePOJO.setI_year(i_year);
        dateTimePOJO.setS_date(s_date);
        dateTimePOJO.setS_time(s_time);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void formatDate() throws Exception {

        assertEquals("DateTimePOJO.formatDate(UserPojo)",s_date.concat(DateTimePOJO.SEPARATOR).concat(s_time),DateTimePOJO.formatDate(dateTimePOJO));
    }

    @Test
    public void insertLeftZero() throws Exception {
        int withzero = 9;
        int withouthzero = 10;
        assertEquals("insertLeftZero", String.valueOf("0").concat(String.valueOf(withzero)),dateTimePOJO.insertLeftZero(withzero));
        assertEquals("insertLeftZero", String.valueOf(withouthzero),dateTimePOJO.insertLeftZero(withouthzero));
    }

    @Test
    public void formatHour() throws Exception {
        String hour = DateTimePOJO.insertLeftZero(dateTimePOJO.getI_hour());
        String minute = DateTimePOJO.insertLeftZero(dateTimePOJO.getI_minutes());
        String second = DateTimePOJO.insertLeftZero(dateTimePOJO.getI_seconds());
        assertEquals("formatHour",hour.concat(DateTimePOJO.DOTS).concat(minute).concat(DateTimePOJO.DOTS).concat(second)  ,DateTimePOJO.formatHour(dateTimePOJO));

    }


    


}