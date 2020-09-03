package com.example.projectapp.ui.main;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectapp.LeaderboardsActivity;
import com.example.projectapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int mIndex;
    private List<LeaderboardItem> mSkillIQ = new ArrayList<>();
    private List<LeaderboardItem> mLearning = new ArrayList<>();
    private int[] images = {R.drawable.top_learner1,R.drawable.skill_iq_trimmed1};
    private static final String TAG = "PlaceholderFragment";
    private RecyclerView mRecyclerView;
    private RequestQueue mQueue;
    private LearningboardRecyclerAdapter mLearningboardRecyclerAdapter;
    private SkilIQRecyclerAdapter mSkilIQRecyclerAdapter;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndex = 1;
        if (getArguments() != null) {
            mIndex = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        mQueue = Volley.newRequestQueue(getContext());
        mLearningboardRecyclerAdapter = new LearningboardRecyclerAdapter(getContext(), mLearning);
        mSkilIQRecyclerAdapter = new SkilIQRecyclerAdapter(getContext(), mSkillIQ);
    }

    @Override
    public void onStart() {
        super.onStart();
        mLearning.clear();
        mSkillIQ.clear();
        Log.d(TAG,"onStart: Size of learning"+mLearning.size() );
        Log.d(TAG,"onStart: Size of skilliq"+mSkillIQ.size() );
        initializeLearningData();
        initializeSkillIQData();
    }

    @Override
    public void onStop() {
        Log.d(TAG,"onStop: Size of learning"+mLearning.size() );
        Log.d(TAG,"onStop: Size of skilliq"+mSkillIQ.size() );
        super.onStop();
    }

    private void setupRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(mIndex ==1){
            mRecyclerView.setAdapter(mLearningboardRecyclerAdapter);
        }
        else{
            mRecyclerView.setAdapter(mSkilIQRecyclerAdapter);
        }
    }
    private void initializeSkillIQData(){
        VolleyLog.DEBUG = true;
        String url = "https://gadsapi.herokuapp.com/api/skilliq";

        mQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i =0;i<response.length();i++){
                    try {
                        JSONObject object = (JSONObject) response.get(i);
                        String name = object.getString("name");
                        String score = object.getString("score");
                        String country = object.getString("country");
                        mSkillIQ.add(new PlaceholderFragment.LeaderboardItem(images[1],name,score+" skill IQ Score, "+country));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mSkilIQRecyclerAdapter.notifyDataSetChanged();
                Log.d(TAG,"jsonArrayRequest: Response"+response.toString() );
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.d(TAG,"jsonArrayRequest: Response"+error );
                Toast.makeText(getContext(),"Response: Error connecting to the network ",Toast.LENGTH_SHORT).show();

            }
        });
        mQueue.add(jsonArrayRequest);
    }

    private void initializeLearningData(){
        VolleyLog.DEBUG = true;
        String url = "https://gadsapi.herokuapp.com/api/hours";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i =0;i<response.length();i++){
                    try {
                        JSONObject object = (JSONObject) response.get(i);
                        String name = object.getString("name");
                        String hours = object.getString("hours");
                        String country = object.getString("country");
                        mLearning.add(new PlaceholderFragment.LeaderboardItem(images[0],name,hours+" learning hours, "+country));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mLearningboardRecyclerAdapter.notifyDataSetChanged();
                Log.d(TAG,"jsonArrayRequest: Response"+response.toString() );
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.d(TAG,"jsonArrayRequest: Response"+error );
                Toast.makeText(getContext(),"Response: Error connecting to the network ",Toast.LENGTH_SHORT).show();

            }
        });
        mQueue.add(jsonArrayRequest);

    }



    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_leaderboards, container, false);
        setupRecyclerView(root);
        return root;
    }

    public class LeaderboardItem{
        public int image;
        public String name;
        public String details;

        public LeaderboardItem(int image, String name, String details) {
            this.image = image;
            this.name = name;
            this.details = details;
        }
    }

}