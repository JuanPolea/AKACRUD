package activity.com.jfmr.akacrud.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.lang.reflect.Method;

import activity.com.jfmr.akacrud.R;
import activity.com.jfmr.akacrud.activity.UserCreateActivity;
import activity.com.jfmr.akacrud.pojo.UserPOJO;


/**
 * Created by Juan Francisco Mateos Redondo
 */

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "user_profile";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView tvId;

    private EditText tvName;
    private TextView tvDate;
    private TextView tvTime;
    private UserPOJO userPOJO;
    private ImageView ivSave;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        final View[] rootView = {inflater.inflate(R.layout.fragment_user_profile, container, false)};

        tvId = (TextView) rootView[0].findViewById(R.id.idTvIdResult);
        tvName = (EditText) rootView[0].findViewById(R.id.idEtNameResult);
        tvDate = (TextView) rootView[0].findViewById(R.id.idTvDateOfBirthResult);
        tvTime = (TextView) rootView[0].findViewById(R.id.idTvTimeOfBirthResult);
        ivSave = (ImageView) rootView[0].findViewById(R.id.idIvSave);
        RelativeLayout relativeImages = (RelativeLayout) rootView[0].findViewById(R.id.idRlUserFragment);

        tvId = (TextView) relativeImages.findViewById(R.id.idTvIdResult);

        userPOJO = (UserPOJO) getActivity().getIntent().getParcelableExtra(getString(R.string.userPOJO));


        getTvId().setText(String.valueOf(userPOJO.getI_id()));
        getTvName().setText(userPOJO.getS_name());
        getTvDate().setText(userPOJO.getDt_birthdate().getS_date());
        getTvTime().setText(userPOJO.getDt_birthdate().getS_time());

        return rootView[0];
    }

    /**
     * Filling interface for user profile
     *
     * @param userPOJO
     */
    private void fillUserProfile(UserPOJO userPOJO) {


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public ImageView getIvSave() {
        return ivSave;
    }

    public void setIvSave(ImageView ivSave) {
        this.ivSave = ivSave;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Log.e("onMenuOpened...", e.getMessage());
                }
            }
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();

        inflater.inflate(R.menu.menu_user_profile, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.miUserUpdate:


                Intent intent = new Intent(getActivity(),UserCreateActivity.class);
                intent.putExtra(getString(R.string.fragment_user_create),userPOJO);
                startActivity(intent);
                return true;

            case R.id.miUserDelete:

                deleteUserDialog(userPOJO);

                return true;
        }

            return super.onOptionsItemSelected(item);
        }

    /**
     * Asking the user if selected user is going to be deleted.
     * @param userPOJO
     */
    private void deleteUserDialog(final UserPOJO userPOJO) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

            alertDialog.setTitle(getString(R.string.delete_user_text));



            alertDialog.setPositiveButton(getString(R.string.accept),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {

                            try {
                                deleteUser(userPOJO);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(),getString(R.string.error_connection),Toast.LENGTH_SHORT).show();
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
     * Delete user from database
     * @param userModel
     */
    private void deleteUser(final UserPOJO userModel) throws JSONException {


        final String stringUrl = getString(R.string.url_delete_user);
        String url = String.format(stringUrl,userModel.getI_id());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e(getClass().getName(),response);

                        if(response.equalsIgnoreCase(null)){
                            Toast.makeText(getActivity(),getString(R.string.user_not_found),Toast.LENGTH_SHORT).show();
                        }else
                        {
                            getActivity().finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want

                        error.printStackTrace();
                        Log.e(getClass().getName(), "login " + error.getMessage());

                    }
                }) {

        };


        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state




        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);

    }


    private void activateFields() {
        getTvName().setFocusableInTouchMode(true);

        getIvSave().setVisibility(View.VISIBLE);

    }

    public TextView getTvId() {
        return tvId;
    }

    public void setTvId(TextView tvId) {
        this.tvId = tvId;
    }

    public EditText getTvName() {
        return tvName;
    }

    public void setTvName(EditText tvName) {
        this.tvName = tvName;
    }

    public TextView getTvDate() {
        return tvDate;
    }

    public void setTvDate(TextView tvDate) {
        this.tvDate = tvDate;
    }

    public TextView getTvTime() {
        return tvTime;
    }

    public void setTvTime(TextView tvTime) {
        this.tvTime = tvTime;
    }
}


