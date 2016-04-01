package com.varadr.hackernews.storydetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.varadr.hackernews.R;
import com.varadr.hackernews.model.Story;
import com.varadr.hackernews.utils.Utils;

import java.io.IOException;

public class StoryDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_STORY = "com.varadr.hackernews.story";
    private static final String EXTRA_CUSTOM_TABS_SESSION = "android.support.customtabs.extra.SESSION";

    private static final ObjectMapper mapper = new ObjectMapper();

    private Story mStory;

    public static Intent newIntent(Context context, Story story) throws JsonProcessingException {
        Intent i = new Intent(context, StoryDetailsActivity.class);
        String storyStr = mapper.writeValueAsString(story);
        i.putExtra(EXTRA_STORY, storyStr);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_details);

        // Set the Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String storyStr = getIntent().getStringExtra(EXTRA_STORY);
        try {
            mStory = mapper.readValue(storyStr, Story.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        initializeScreen();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeScreen() {
        TextView storyTitle = (TextView) findViewById(R.id.text_view_story_details_title);
        TextView storyDomain = (TextView) findViewById(R.id.text_view_story_details_domain);
        TextView storyScore = (TextView) findViewById(R.id.text_view_story_details_score);

        storyTitle.setText(mStory.getTitle());
        storyTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = StoryDetailsActivity.this;
                // Create Chrome Custom Tab using the handy Builder
                new CustomTabsIntent.Builder()
                        .setShowTitle(true)
                        .setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary))
                        .setStartAnimations(activity, R.anim.slide_in_right, R.anim.static_anim)
                        .setExitAnimations(activity, R.anim.static_anim, R.anim.slide_out_right)
                        .build()
                        .launchUrl(activity, Uri.parse(mStory.getUrl()));
            }
        });

        storyDomain.setText(Utils.getDomain(mStory.getUrl()));
        int s = mStory.getScore();
        storyScore.setText(s==1? s+" point":s+" points");
    }
}
