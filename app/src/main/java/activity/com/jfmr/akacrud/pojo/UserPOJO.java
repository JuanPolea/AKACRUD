package activity.com.jfmr.akacrud.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;


/**
 * Created by Juan Francisco Mateos Redondo
 */

public class UserPOJO implements Parcelable{


    public static final String DATE_FORMAT = "yyyy-MM-dd";
    private int i_id;
    private String s_name;
    private DateTimePOJO dt_birthdate = new DateTimePOJO();
    private SimpleDateFormat dateFormat;

    public UserPOJO()
    {
        dateFormat = new SimpleDateFormat(DATE_FORMAT);

        dt_birthdate = new DateTimePOJO();
    }



    public UserPOJO(int id, String name, DateTimePOJO birthdate) {
        setDt_birthdate(new DateTimePOJO());
        this.i_id = id;
        s_name = name;
        dt_birthdate.setS_date(birthdate.getS_date());
        dt_birthdate.setS_time(birthdate.getS_time());


    }
    /**
     *
     * @param jsonObject
     * @throws JSONException
     */
    public void toUserModel(JSONObject jsonObject) throws JSONException {

        setI_id(jsonObject.getInt("id"));
        setS_name(jsonObject.getString("name"));
        String[] dateTime = jsonObject.getString("birthdate").split("T");
        getDt_birthdate().setS_date(dateTime[0]);
        getDt_birthdate().setS_time(dateTime[1]);
        getDt_birthdate().setS_formattedDate(DateTimePOJO.formatDate(getDt_birthdate()));

        showInLog();
    }


    public void showInLog(){
        Log.i(getClass().getName(),"id: "+ getI_id() +" "+"s_name: "+ getS_name()+" "+"Birthday: "+ getDt_birthdate().getS_date().concat("-").concat(getDt_birthdate().getS_time()) );
    }


    @Override
    public int describeContents() {
        return 0;
    }


    private UserPOJO(Parcel in)
    {
       this.i_id = in.readInt();
        this.s_name = in.readString();
        this.dt_birthdate.setS_date(in.readString());
        this.dt_birthdate.setS_time(in.readString());


    }

    public UserPOJO(UserPOJO userPOJO) {
        dt_birthdate = new DateTimePOJO();

        this.i_id = userPOJO.getI_id();
        s_name = userPOJO.getS_name();
        dt_birthdate.setS_date(userPOJO.getDt_birthdate().getS_date());
        dt_birthdate.setS_time(userPOJO.getDt_birthdate().getS_time());
    }



    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(getI_id());
        parcel.writeString(getS_name());
        parcel.writeString(getDt_birthdate().getS_date());
        parcel.writeString(getDt_birthdate().getS_time());




    }

    public static final Parcelable.Creator<UserPOJO> CREATOR = new Parcelable.Creator<UserPOJO>() {
        @Override
        public UserPOJO createFromParcel(Parcel source) {
            return new UserPOJO(source);
        }

        @Override
        public UserPOJO[] newArray(int size) {
            return new UserPOJO[size];
        }
    };




    public int getI_id() {
        return i_id;
    }

    public void setI_id(int i_id) {
        this.i_id = i_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public DateTimePOJO getDt_birthdate() {
        return dt_birthdate;
    }

    public void setDt_birthdate(DateTimePOJO dt_birthdate) {
        this.dt_birthdate = dt_birthdate;
    }
}
