package com.varadr.hackernews.storieslist;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoriesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoriesListFragment extends Fragment {

    private static final String KEY_STORIES_URL = "key_stories_list_stories_url";

    private String mUrl;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private StoriesRecyclerAdapter mAdapter;

    public StoriesListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TopStoriesFragment.
     */
    public static StoriesListFragment newInstance(String url) {
        StoriesListFragment fragment = new StoriesListFragment();

        Bundle args = new Bundle();
        args.putString(KEY_STORIES_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(KEY_STORIES_URL);
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
        Firebase topStoriesRef = new Firebase(mUrl);


        /**
         * Create the adapter, giving it the activity, model class (POJO), layout for each row
         * in the list and reference to the Firebase location where the list data resides.
         * Finally, set the created adapter to the mListView
         */
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new StoriesRecyclerAdapter(topStoriesRef, 30);
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
