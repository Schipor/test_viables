package com.test.viableapp.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.viableapp.R;
import com.test.viableapp.adapters.base.BaseAdapter;
import com.test.viableapp.http.models.RandomUser;
import com.test.viableapp.utils.DateUtils;
import com.test.viableapp.widget.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RandomUsersAdapter extends BaseAdapter<RandomUser> {

    private Callback mCallback;

    public interface Callback {
        void onItemClicked(RandomUser user);

        void setPhoto(RoundedImageView imageView, String photoUrl);
    }

    public RandomUsersAdapter(ArrayList<RandomUser> items, Callback callback) {
        super(items);
        this.mCallback = callback;
    }

    @Override
    public ArrayList<RandomUser> setFilterLogic(List<RandomUser> originalList, String query) {
        //can be used for local search
        final ArrayList<RandomUser> filteredModelList = new ArrayList<>();
        for (RandomUser item : originalList) {
            filteredModelList.add(item);
        }

        return filteredModelList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final RandomUser item = getItems().get(position);
        viewHolder.bind(item);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        //when holder is recycled, clear clear the bitmap from imageview to prevent memory leaks
        ((ViewHolder) holder).userPhoto.setImageBitmap(null);
    }

    public void applyFilter(ArrayList<RandomUser> list, String query, Runnable callback) {
        filter(list, query, callback);
    }


    public void refresh(ArrayList<RandomUser> list) {
        refreshList(list);
    }

    public void addItems(ArrayList<RandomUser> list) {
        int prevCount = getItemCount();
        getItems().addAll(list);
        notifyItemRangeInserted(prevCount, getItems().size());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_name)
        TextView name;
        @BindView(R.id.age)
        TextView age;
        @BindView(R.id.user_photo)
        RoundedImageView userPhoto;
        @BindView(R.id.user_flag)
        TextView userFlag;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final RandomUser user) {
            if (user.getName() != null) {
                name.setText(String.format("%1$s %2$s", user.getName().getFirst(), user.getName().getLast()));
            } else {
                name.setText("N/A");
            }
            if (user.getDob() != null) {
                age.setText(String.format("Age: %1$s",DateUtils.getUserAge(user.getDob())));
            } else {
                age.setText("Age: N/A");
            }
            userFlag.setText(getCountryFlag(user.getNat()));
            if (mCallback != null) {
                if (user.getPicture() != null)
                    mCallback.setPhoto(userPhoto, user.getPicture().getThumbnail());
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        mCallback.onItemClicked(user);
                    }
                }
            });
        }

        private String getCountryFlag(String countryCode) {
            //try to find emoji country flag
            int firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6;
            int secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6;
            return new String(Character.toChars(firstLetter)) + new String(Character.toChars(secondLetter));
        }

    }
}
