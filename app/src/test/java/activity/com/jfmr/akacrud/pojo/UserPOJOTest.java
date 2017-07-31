package activity.com.jfmr.akacrud.pojo;

import android.os.Parcel;
import android.util.Log;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Juan Francisco Mateos Redondo
 */
public class UserPOJOTest {
    
    UserPOJO userPOJO;
    private int i_id = 1;
    private String s_name ="prueba";
    private String s_date = "1980-12-12";
    private String s_time = "19:12:12";
    private DateTimePOJO dateTimePOJO;



    @Before
    public void setUp() throws Exception {

        dateTimePOJO = new DateTimePOJO(s_date, s_time);
        userPOJO = new UserPOJO(i_id,s_name,dateTimePOJO);

    }



}