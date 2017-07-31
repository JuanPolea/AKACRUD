package activity.com.jfmr.akacrud.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
public class UserCreateActivity extends AppCompatActivity implements UserCreateFragment.OnFragmentInteractionListener, UserProfileFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


            ((AppCompatActivity) UserCreateActivity.this).setSupportActionBar(toolbar);

            if (toolbar != null) {

                toolbar.setVisibility(View.VISIBLE);
                toolbar.setEnabled(true);

                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


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
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

}
