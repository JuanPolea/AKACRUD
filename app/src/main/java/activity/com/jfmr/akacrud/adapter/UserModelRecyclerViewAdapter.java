package activity.com.jfmr.akacrud.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import activity.com.jfmr.akacrud.pojo.UserPOJO;
import activity.com.jfmr.akacrud.utilities.UserListFilter;
import activity.com.jfmr.akacrud.R;
import activity.com.jfmr.akacrud.activity.UserProfileActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan Francisco Mateos Redondo
 */

public class UserModelRecyclerViewAdapter extends RecyclerView.Adapter<UserModelRecyclerViewAdapter.ViewHolder>
implements Filterable{



    private List<UserPOJO> mValues;
    private ArrayList<UserPOJO> arrayFilter;
    private Activity activity;
    UserListFilter filter;


    public UserModelRecyclerViewAdapter(Activity act, List<UserPOJO> items) {

        setHasStableIds(false);
        mValues = items;
        activity = act;
        arrayFilter = (ArrayList<UserPOJO>) items;

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_usermodel, parent, false);



        return new ViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.tvIdCard.setText(String.valueOf(mValues.get(position).getI_id()));
        holder.tvNameCard.setText(mValues.get(position).getS_name());
        holder.tvDateCard.setText(mValues.get(position).getDt_birthdate().getS_date().
                concat(" ").
                concat(mValues.get(position).getDt_birthdate().getS_time()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Snackbar.make(v,mValues.get(position).getS_name(),Snackbar.LENGTH_SHORT).show();

                Intent intent = new Intent(activity,UserProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra(activity.getString(R.string.userPOJO),mValues.get(position));
                activity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new UserListFilter(arrayFilter,this);
        }
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvIdCard;
        public final TextView tvNameCard;
        public final TextView tvDateCard;
        public UserPOJO mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;

            tvIdCard = (TextView) view.findViewById(R.id.idTvIdCard);
            tvNameCard = (TextView) view.findViewById(R.id.idTvNameCard);
            tvDateCard = (TextView) view.findViewById(R.id.idTvDateCard);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvDateCard.getText() + "'";
        }
    }

    public void setmValues(List<UserPOJO> mValues) {
        this.mValues = mValues;
    }

}
