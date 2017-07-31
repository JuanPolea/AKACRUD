package activity.com.jfmr.akacrud.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayDeque;
import java.util.Calendar;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import activity.com.jfmr.akacrud.R;
import activity.com.jfmr.akacrud.pojo.DateTimePOJO;
import activity.com.jfmr.akacrud.pojo.UserPOJO;

/**
 * Created by Juan Francisco Mateos Redondo
 */

public class UserCreateFragment extends Fragment {
    private static final String HYPHEN = "-";
    private Deque<String> stackTmp;
    private static final String ARG_PARAM1 = "userCreateFragment";

    private EditText etNombre;
    private TextView tvDateBirth;
    private TextView tvTimeBirth;

    private ImageView ivDateOfBirth;
    private ImageView ivAccept;

    private UserPOJO userModeloPOJO;
    private boolean bActualizar = false;
    private Deque<String> stackUndo;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UserCreateFragment() {
        // Required empty public constructor

    }
    /**
     * @param param1
     * @return
     */
    // TODO: Rename and change types and number of parameters
    public static UserCreateFragment newInstance(UserPOJO param1) {
        UserCreateFragment fragment = new UserCreateFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        stackUndo = new ArrayDeque<>();
        stackTmp = new ArrayDeque<>();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(false);
        final View[] rootView;
        if (getArguments() != null) {
            rootView = new View[]{inflater.inflate(R.layout.fragment_user_create_content, container, false)};

        } else
            rootView = new View[]{inflater.inflate(R.layout.fragment_user_create, container, false)};

        etNombre = (EditText) rootView[0].findViewById(R.id.idEtNameResult);
        tvDateBirth = (TextView) rootView[0].findViewById(R.id.idTvDateOfBirthResult);
        tvTimeBirth = (TextView) rootView[0].findViewById(R.id.idTvTimeOfBirthResult);
        ivDateOfBirth = (ImageView) rootView[0].findViewById(R.id.idIvDateOfBirth);
        ImageView ivTimeofBirth = (ImageView) rootView[0].findViewById(R.id.idIvTimeOfBirth);
        ivAccept = (ImageView) rootView[0].findViewById(R.id.ivIvAcept);

        userModeloPOJO = (UserPOJO)getActivity().getIntent().getParcelableExtra(getString(R.string.fragment_user_create));

        if (!userModeloPOJO.getS_name().isEmpty()) {

                getEtNombre().setText(userModeloPOJO.getS_name());
                getTvDateBirth().setText(userModeloPOJO.getDt_birthdate().getS_date());
                getTvTimeBirth().setText(userModeloPOJO.getDt_birthdate().getS_time());

                setHasOptionsMenu(true);
            getActivity().setTitle(getString(R.string.update_User));
        }else
            userModeloPOJO = new UserPOJO();


        /**
         * Create a DatePickerDialog.OnDateSetListener for formatting Calendar
         * *
         **/
        final DatePickerDialog.OnDateSetListener dpEventoFecha = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {



                userModeloPOJO.getDt_birthdate().setI_year(year);
                userModeloPOJO.getDt_birthdate().setI_month(month);
                userModeloPOJO.getDt_birthdate().setI_day(day);
                String date = String.valueOf(year).concat(HYPHEN).concat(String.valueOf(month)).concat(HYPHEN).concat(String.valueOf(day));

                tvDateBirth.setText(date);
            }
        };


        ivDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar CALENDAR = Calendar.getInstance();
                userModeloPOJO.getDt_birthdate().setI_year(CALENDAR.get(Calendar.YEAR));
                userModeloPOJO.getDt_birthdate().setI_month(CALENDAR.get(Calendar.MONTH));
                userModeloPOJO.getDt_birthdate().setI_day(CALENDAR.get(Calendar.DAY_OF_MONTH));


                DatePickerDialog DPD = new DatePickerDialog(getActivity(), dpEventoFecha, userModeloPOJO.getDt_birthdate().getI_year(), userModeloPOJO.getDt_birthdate().getI_month(), userModeloPOJO.getDt_birthdate().getI_day());
                DPD.getDatePicker().setMaxDate(CALENDAR.getTimeInMillis());
                DPD.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.accept), DPD);


                DPD.show();
            }
        });


        ivTimeofBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                 * Custom Time dialog composed by a TextView, two ImageViews and 3 Spinners
                 */
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.fragment_time);
                dialog.setTitle(getString(R.string.insert_time));

                final TextView tvTimeSelected = (TextView) dialog.findViewById(R.id.idTvTimeSelected);

                ImageView ivTimeAccept = (ImageView) dialog.findViewById(R.id.idIvTimeAcept);
                ImageView ivTimeCancel = (ImageView) dialog.findViewById(R.id.idIvTimeCancel);

                final Spinner spHour = (Spinner) dialog.findViewById(R.id.idSpHour);
                final Spinner spMinutes = (Spinner) dialog.findViewById(R.id.idSpMinutes);
                final Spinner spSeconds = (Spinner) dialog.findViewById(R.id.idSpSeconds);

                /**
                 * Adapters for spinners
                 */
                ArrayAdapter<String> adapterHour = createAdapter(spHour, getResources().getStringArray(R.array.hora));
                ArrayAdapter<String> adapterMinutes = createAdapter(spMinutes, getResources().getStringArray(R.array.minutos));
                ArrayAdapter<String> adapterSeconds = createAdapter(spSeconds, getResources().getStringArray(R.array.segundos));

                spHour.setAdapter(adapterHour);
                spMinutes.setAdapter(adapterMinutes);
                spSeconds.setAdapter(adapterSeconds);


                spHour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        userModeloPOJO.getDt_birthdate().setI_hour(Integer.parseInt(parent.getItemAtPosition(position).toString()));
                        tvTimeSelected.setText(DateTimePOJO.formatHour(userModeloPOJO.getDt_birthdate()));

                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spMinutes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        userModeloPOJO.getDt_birthdate().setI_minutes((Integer.parseInt(parent.getItemAtPosition(position).toString())));
                        tvTimeSelected.setText(DateTimePOJO.formatHour(userModeloPOJO.getDt_birthdate()));

                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spSeconds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        userModeloPOJO.getDt_birthdate().setI_seconds((Integer.parseInt(parent.getItemAtPosition(position).toString())));
                        tvTimeSelected.setText(DateTimePOJO.formatHour(userModeloPOJO.getDt_birthdate()));

                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                /*
                Notify is data has changed in Spinners
                 */
                adapterHour.notifyDataSetChanged();
                adapterMinutes.notifyDataSetChanged();
                adapterSeconds.notifyDataSetChanged();


                ivTimeAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tvTimeBirth.setText(DateTimePOJO.formatHour(userModeloPOJO.getDt_birthdate()));
                        dialog.dismiss();
                    }
                });

                ivTimeCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });


        /**
         * This button allow to create and update a user
         */
        ivAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                /**
                 * Checking empty values.
                 */
                if (getEtNombre().getText().toString().trim().equalsIgnoreCase(""))
                    getEtNombre().setError(getString(R.string.campo_nombre_vacio));

                else if (getTvDateBirth().getText().toString().equalsIgnoreCase(""))
                    Toast.makeText(getActivity(), getString(R.string.empty_date_field), Toast.LENGTH_SHORT).show();

                else if (getTvTimeBirth().getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), getString(R.string.empty_time_field), Toast.LENGTH_SHORT).show();
                } else {
                    userModeloPOJO.setS_name(getEtNombre().getText().toString());
                    userModeloPOJO.getDt_birthdate().setS_date(getTvDateBirth().getText().toString());
                    userModeloPOJO.getDt_birthdate().setS_time(getTvTimeBirth().getText().toString());

                    if (userModeloPOJO.getS_name().trim().equalsIgnoreCase(""))
                        etNombre.setError(getString(R.string.empty_field));
                    else

                        try {
                            if (bActualizar)
                                updateUser(userModeloPOJO);
                            else
                                createUser(userModeloPOJO);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
                        }
                }


            }
        });



        /**
         * Initializing a queue for UNDO and REDO options
         */
        etNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(!charSequence.toString().isEmpty())
                        setHasOptionsMenu(true);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                stackUndo.push(etNombre.getText().toString());

            }
        });
        return rootView[0];
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Updating user into database
     *
     * @param userModeloPOJO
     * @throws JSONException
     */
    private void updateUser(UserPOJO userModeloPOJO) throws JSONException {


        String url = getString(R.string.url_update_user);

        JSONObject js = new JSONObject();
        js.put("id", userModeloPOJO.getI_id());
        js.put("name", userModeloPOJO.getS_name());
        js.put("birthdate", DateTimePOJO.formatDate(userModeloPOJO.getDt_birthdate()));


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(getClass().getName(), response.toString());
                        if (response.toString().equalsIgnoreCase(null)) {
                            Toast.makeText(getActivity(), getString(R.string.user_not_updated), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), getString(R.string.user_updated), Toast.LENGTH_SHORT).show();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentManager.popBackStack();
                            fragmentTransaction.commit();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getActivity(), getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }


        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjReq);
    }


    /**
     * @param usermodel
     * @throws JSONException
     */
    private void createUser(final UserPOJO usermodel) throws JSONException {


        String url = getString(R.string.url_create_user);

        JSONObject js = new JSONObject();
        js.put("name", usermodel.getS_name());
        js.put("birthdate", DateTimePOJO.formatDate(usermodel.getDt_birthdate()));


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(getClass().getName(), response.toString());
                        if (response.toString().equalsIgnoreCase(null)) {
                            Toast.makeText(getActivity(), getString(R.string.user_not_created), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), getString(R.string.user_created), Toast.LENGTH_SHORT).show();

                            getActivity().finish();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getActivity(), getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }


        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjReq);
    }

    /**
     * For setting spinners.
     *
     * @param spinner
     * @param array
     */
    private ArrayAdapter<String> createAdapter(Spinner spinner, String[] array) {


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return adapter;

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_user_create,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.miUserUndo:


                if(stackUndo.size()>0){
                    getEtNombre().setText(stackUndo.pop());
                    stackTmp.push(stackUndo.pop());


                }
                else
                {
                    getEtNombre().setText("");
                    Toast.makeText(getActivity(),getString(R.string.nothing_undo),Toast.LENGTH_SHORT).show();
                }

                getEtNombre().setSelection(getEtNombre().getText().length());
                return true;
            case  R.id.miUserRedo:


                if(stackTmp.size()>0){
                    getEtNombre().setText(stackTmp.pop());
                }else
                    Toast.makeText(getActivity(),getString(R.string.nothing_redo),Toast.LENGTH_SHORT).show();

                getEtNombre().setSelection(getEtNombre().getText().length());
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);

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

    public EditText getEtNombre() {
        return etNombre;
    }

    public void setEtNombre(EditText etNombre) {
        this.etNombre = etNombre;
    }

    public TextView getTvDateBirth() {
        return tvDateBirth;
    }

    public void setTvDateBirth(TextView tvDateBirth) {
        this.tvDateBirth = tvDateBirth;
    }

    public TextView getTvTimeBirth() {
        return tvTimeBirth;
    }

    public void setTvTimeBirth(TextView tvTimeBirth) {
        this.tvTimeBirth = tvTimeBirth;
    }

    public ImageView getIvDateOfBirth() {
        return ivDateOfBirth;
    }

    public void setIvDateOfBirth(ImageView ivDateOfBirth) {
        this.ivDateOfBirth = ivDateOfBirth;
    }

    public static String getArgParam1() {
        return ARG_PARAM1;
    }
}
