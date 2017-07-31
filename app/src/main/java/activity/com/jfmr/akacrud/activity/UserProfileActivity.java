package activity.com.jfmr.akacrud.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import activity.com.jfmr.akacrud.R;
import activity.com.jfmr.akacrud.fragment.UserCreateFragment;
import activity.com.jfmr.akacrud.fragment.UserProfileFragment;
/**
 * Created by Juan Francisco Mateos Redondo
 */
public class UserProfileActivity extends AppCompatActivity implements UserProfileFragment.OnFragmentInteractionListener, UserCreateFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


            ((AppCompatActivity) UserProfileActivity.this).setSupportActionBar(toolbar);

            if (toolbar != null) {

                toolbar.setVisibility(View.VISIBLE);
                toolbar.setEnabled(true);

                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //do something you want

                        onBackPressed();


                    }
                });
            } else {

            }
        } catch (NullPointerException e) {
            Log.e(getClass().getName(), e.getMessage());
        }

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
