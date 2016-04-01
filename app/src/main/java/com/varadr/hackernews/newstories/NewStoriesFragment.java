package com.varadr.hackernews.newstories;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.varadr.hackernews.Constants;
import com.varadr.hackernews.R;
import com.varadr.hackernews.topstories.StoriesRecyclerAdapter;

/**
 * Created by varad on 1/4/16.
 */
public class NewStoriesFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private StoriesRecyclerAdapter mAdapter;

    public NewStoriesFragment() {
        // Required empty public constructor
    }

    public static NewStoriesFragment newInstance() {
        NewStoriesFragment fragment = new NewStoriesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * Initialize UI elements
         */
        View rootView = inflater.inflate(R.layout.fragment_stories, container, false);
        initializeScreen(rootView);

        /**
         * Create Firebase reference
         */
        Firebase newStoriesRef = new Firebase(Constants.URL_NEW_STORIES);


        /**
         * Create the adapter, giving it the activity, model class (POJO), layout for each row
         * in the list and reference to the Firebase location where the list data resides.
         * Finally, set the created adapter to the mListView
         */
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new StoriesRecyclerAdapter(newStoriesRef, 30);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /**
         * cleanup removes the firebase eventlisteners
         */
        mAdapter.cleanup();
    }

    /**
     * Link the RecyclerView, and instantiate the LayoutManager
     */
    private void initializeScreen(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_top_stories);
        mLayoutManager = new LinearLayoutManager(getActivity());
    }
}
