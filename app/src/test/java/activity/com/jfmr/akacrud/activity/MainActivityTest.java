package activity.com.jfmr.akacrud.activity;

import org.junit.Before;
import org.junit.Test;

import activity.com.jfmr.akacrud.pojo.DateTimePOJO;

import static org.junit.Assert.*;

/**
 * Created by Juan Francisco Mateos Redondo
 */
public class MainActivityTest {


    MainActivity mainActivity;

    @Before
    public void setUp() throws Exception {

       mainActivity = new MainActivity();
    }

    @Test
    public void isNumeric() throws Exception {

        assertEquals(false,mainActivity.isNumeric("easdf"));
        assertEquals(false,mainActivity.isNumeric("ea23sdf"));
        assertEquals(true,mainActivity.isNumeric("123"));
        assertEquals(false,mainActivity.isNumeric("123adf"));

    }


}