package activity.com.jfmr.akacrud.activity;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import activity.com.jfmr.akacrud.R;
import activity.com.jfmr.akacrud.adapter.UserModelRecyclerViewAdapter;
import activity.com.jfmr.akacrud.pojo.UserPOJO;
/**
 * Created by Juan Francisco Mateos Redondo
 */

public class ListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private UserModelRecyclerViewAdapter mAdapter;
    private ArrayList<UserPOJO> arrUserModel;


    public ListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        arrUserModel = getIntent().getParcelableArrayListExtra(getString(R.string.userPOJO));
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new UserModelRecyclerViewAdapter(this, arrUserModel);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


        try {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


            ((AppCompatActivity) ListActivity.this).setSupportActionBar(toolbar);

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
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /*
        Options to filter:
         ID -> Ascendent and descendent
         DATE -> Ascendent and descendent
          */
        switch (item.getItemId()) {

            case R.id.miIdIDAsc:

                Collections.sort(arrUserModel, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        UserPOJO p1 = (UserPOJO) o1;
                        UserPOJO p2 = (UserPOJO) o2;
                        return String.valueOf(p1.getI_id()).compareToIgnoreCase(String.valueOf(p2.getI_id()));
                    }
                });

                refreshWindow(arrUserModel);
                return true;


            case R.id.miIdIDDesc:

                Collections.sort(arrUserModel, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        UserPOJO p1 = (UserPOJO) o1;
                        UserPOJO p2 = (UserPOJO) o2;
                        return String.valueOf(p1.getI_id()).compareToIgnoreCase(String.valueOf(p2.getI_id()));
                    }
                });

                Collections.reverse(arrUserModel);
                refreshWindow(arrUserModel);

                return true;


            case R.id.miIdDateAsc:

                Collections.sort(arrUserModel, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        UserPOJO p1 = (UserPOJO) o1;
                        UserPOJO p2 = (UserPOJO) o2;
                        return p1.getDt_birthdate().getS_date().compareToIgnoreCase(p2.getDt_birthdate().getS_date());
                    }
                });


                refreshWindow(arrUserModel);
                return true;


            case R.id.miIdDateDesc:

                Collections.sort(arrUserModel, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        UserPOJO p1 = (UserPOJO) o1;
                        UserPOJO p2 = (UserPOJO) o2;
                        return p1.getDt_birthdate().getS_date().compareToIgnoreCase(p2.getDt_birthdate().getS_date());
                    }
                });

                Collections.reverse(arrUserModel);
                refreshWindow(arrUserModel);
                return true;

        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * Refreshing UI
     *
     * @param u
     */
    private void refreshWindow(ArrayList<UserPOJO> u) {
        Intent intent = new Intent(ListActivity.this, ListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra(getString(R.string.userPOJO), u);
        startActivity(intent);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }


}
