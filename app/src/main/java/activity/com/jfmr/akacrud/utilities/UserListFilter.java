package activity.com.jfmr.akacrud.utilities;

import android.widget.Filter;

import java.util.ArrayList;

import activity.com.jfmr.akacrud.adapter.UserModelRecyclerViewAdapter;
import activity.com.jfmr.akacrud.pojo.UserPOJO;

/**
 * Created by Juan Francisco Mateos Redondo
 */

public class UserListFilter extends Filter {
        UserModelRecyclerViewAdapter adapter;
        ArrayList<UserPOJO> filterList;
        public UserListFilter(ArrayList<UserPOJO> filterList, UserModelRecyclerViewAdapter adapter)
        {
            this.adapter=adapter;
            this.filterList=filterList;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();

            if(constraint != null && constraint.length() > 0)
            {

                constraint=constraint.toString().toUpperCase();

                ArrayList<UserPOJO> filteredUserPOJOs =new ArrayList<>();
                for (int i=0;i<filterList.size();i++)
                {

                    if(filterList.get(i).getS_name().toUpperCase().contains(constraint) ||
                            String.valueOf(filterList.get(i).getI_id()).contains(constraint) ||
                            String.valueOf(filterList.get(i).getDt_birthdate().getS_date()).contains(constraint))
                    {

                        filteredUserPOJOs.add(filterList.get(i));
                    }
                }
                results.count= filteredUserPOJOs.size();
                results.values= filteredUserPOJOs;
            }else
            {
                results.count=filterList.size();
                results.values=filterList;
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.setmValues((ArrayList<UserPOJO>) results.values);

            adapter.notifyDataSetChanged();
        }
}
