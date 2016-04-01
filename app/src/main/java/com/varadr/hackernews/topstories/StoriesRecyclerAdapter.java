package com.varadr.hackernews.topstories;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.varadr.hackernews.Constants;
import com.varadr.hackernews.R;
import com.varadr.hackernews.model.Story;
import com.varadr.hackernews.utils.Utils;

import java.util.ArrayList;

/**
 * Created by varad on 31/3/16.
 */
public class StoriesRecyclerAdapter extends RecyclerView.Adapter<StoriesRecyclerAdapter.ViewHolder> {
    private ArrayList<Story> mStories;
    private Query mRef;
    private ChildEventListener mChildEventListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewStoryTitle;
        private TextView textViewStoryDomain;
        private TextView textViewStoryPoints;
        private View mView;
        private Story mStory;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            mView.setOnClickListener(this);
            textViewStoryTitle = (TextView) v.findViewById(R.id.text_view_story_title);
            textViewStoryDomain = (TextView) v.findViewById(R.id.text_view_story_domain);
            textViewStoryPoints = (TextView) v.findViewById(R.id.text_view_story_points);
        }

        public void bindStory(Story story) {
            this.mStory = story;
            this.textViewStoryTitle.setText(story.getTitle());
            this.textViewStoryDomain.setText(Utils.getDomain(story.getUrl()));
            this.textViewStoryPoints.setText(String.valueOf(story.getScore()) + " points");
        }

        @Override
        public void onClick(View v) {
            Log.d("StoriesRecyclerAdapter", mStory.getTitle());
        }
    }

    public StoriesRecyclerAdapter(Query ref, int limit) {
        /**
         * Sort the stories by 'key' and filter the first 'limit' number of stories
         */
        mRef = ref.orderByKey().limitToFirst(limit);
        mStories = new ArrayList<>();

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final String storyId = String.valueOf(dataSnapshot.getValue());

                /**
                 * Get the actual story details from the 'item' endpoint of the API
                 */
                Firebase ref = new Firebase(Constants.URL_ITEM).child(storyId);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            mStories.add(dataSnapshot.getValue(Story.class));
                            StoriesRecyclerAdapter.this.notifyDataSetChanged();
                        } catch (Exception e) {
                            Log.e("TopStoriesAdapter", e.getMessage() + "\n" + storyId);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //TODO
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };

        // Do not forget to add the childeventlistener to the Firebase reference >.<
        mRef.addChildEventListener(mChildEventListener);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_story, parent, false);

        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Story story = mStories.get(position);
        holder.bindStory(story);
    }

    @Override
    public int getItemCount() {
        return mStories.size();
    }

    public void cleanup() {
        /**
         * Remove eventlistener as a good practice
         */
        mRef.removeEventListener(mChildEventListener);
    }
}
