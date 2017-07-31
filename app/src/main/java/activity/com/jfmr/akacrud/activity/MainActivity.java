package activity.com.jfmr.akacrud.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import activity.com.jfmr.akacrud.R;
import activity.com.jfmr.akacrud.pojo.UserPOJO;
/**
 * Created by Juan Francisco Mateos Redondo
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private UserPOJO userModel;
    private ArrayList<UserPOJO> userPOJOArrayList;
    private ImageView IvSearchUser;
    private ImageView IvCreateUser;
    private ImageView IvSelectAllUser;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.main_menu));

        userModel = new UserPOJO();

        IvSearchUser = (ImageView) findViewById(R.id.bSearchUser);
        IvCreateUser = (ImageView) findViewById(R.id.bCreateUser);
        IvSelectAllUser = (ImageView) findViewById(R.id.bSelectAll);

        setListenerToImageView(IvSearchUser, getString(R.string.search_user));
        setListenerToImageView(IvCreateUser, getString(R.string.create_user));
        setListenerToImageView(IvSelectAllUser, getString(R.string.select_all_user));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userModel = new UserPOJO();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     *
     * @param ImageView
     * @param string
     */
    private void setListenerToImageView(ImageView ImageView, final String string) {


        ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (string.equalsIgnoreCase(getString(R.string.search_user))) {
                    searchUserDialog(string);
                } else if (string.equalsIgnoreCase(getString(R.string.create_user))) {
                    createUser();
                } else if (string.equalsIgnoreCase(getString(R.string.select_all_user))) {

                    getAll();

                }
            }
        });
    }

    /**
     *
     */
    private void createUser() {
        Intent intent = new Intent(MainActivity.this,UserCreateActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }


    /**
     * Creatting a dialog for search users.
     *
     * @param string
     */
    private void searchUserDialog(final String string) {

        final AlertDialog.Builder alertDialog;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialog = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        }
        else{
            alertDialog = new AlertDialog.Builder(MainActivity.this);
        }

        final EditText input = new EditText(MainActivity.this);
        input.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);

        input.setHint(getString(R.string.insert_id));
        alertDialog.setView(input);



        alertDialog.setPositiveButton(getString(R.string.accept),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        if (input.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.id_empty), Toast.LENGTH_SHORT).show();

                        } else if (!isNumeric(input.getText().toString())) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.numeric_format), Toast.LENGTH_SHORT).show();
                        } else if (string.equalsIgnoreCase(getString(R.string.search_user))) {


                            selectUser(Integer.parseInt(input.getText().toString()));

                        }


                    }
                });
        // Setting Negative "NO" ImageView
        alertDialog.setNegativeButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    /**
     * @param string
     * @return
     */
    public boolean isNumeric(String string) {

        Pattern patern = Pattern.compile("\\d*");
        Matcher m = patern.matcher(string);
        boolean b = m.matches();
        return b;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.exit) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(MainActivity.this);
            }
            builder.setMessage(getString(R.string.exit_app))
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert);

            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.idImCreateUser) {
            // Handle the camera action
            createUser();

        } else if (id == R.id.idImSearchUser) {
            searchUserDialog(getString(R.string.search_user));

        } else if (id == R.id.idImSelecAllUser) {
            getAll();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * @param id
     */
    public void selectUser(int id) {
        final String stringUrl = getString(R.string.url_search_user);
        String url = String.format(stringUrl, id);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e(getClass().getName(), response);

                        if (response.equalsIgnoreCase(getString(R.string.null_value))) {
                            Toast.makeText(getApplicationContext(), getString(R.string.user_not_found), Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                userModel.toUserModel(jsonObject);
                                Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent.putExtra(getString(R.string.userPOJO), userModel);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want

                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), getString(R.string.error_connection), Toast.LENGTH_SHORT).show();

                    }
                }) {

        };


        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    /**
     * Query for getting all user from url
     */
    private void getAll() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, getString(R.string.url_getall),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        userPOJOArrayList = new ArrayList<>();
                        ArrayList<Integer> arrId = new ArrayList<>();
                        Log.e(getClass().getName(), "login " + response);

                        if(response.equalsIgnoreCase("null")){
                            Toast.makeText(getApplicationContext(), getString(R.string.error_getAll), Toast.LENGTH_SHORT).show();
                        }else
                        {
                            JSONObject json = null;
                            try {

                                JSONArray jsonArray = new JSONArray(response);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    UserPOJO userModel1 = new UserPOJO();
                                    userModel1.toUserModel(jsonArray.getJSONObject(i));
                                    userPOJOArrayList.add(userModel1);
                                    arrId.add(userModel1.getI_id());

                                }


                                Log.e(getClass().getName(), String.valueOf(Collections.max(arrId)));

                                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                                intent.putParcelableArrayListExtra(getString(R.string.userPOJO), userPOJOArrayList);
                                startActivity(intent);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }



                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want

                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
                    }
                }) {

        };


        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
