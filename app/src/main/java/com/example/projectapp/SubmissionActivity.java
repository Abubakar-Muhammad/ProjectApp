package com.example.projectapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.VolleyLog;

import java.io.IOException;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SubmissionActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://docs.google.com/forms/d/e/";
    private Button mButton;
    private static final String TAG = "SubmissionActivity";
    private TextView mFirstName;
    private TextView mLastName;
    private TextView mEmail;
    private TextView mGithub;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFirstName = findViewById(R.id.first_name);
        mLastName = findViewById(R.id.last_name);
        mEmail = findViewById(R.id.email);
        mGithub = findViewById(R.id.github);


        mButton = findViewById(R.id.submit_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!mFirstName.getText().toString().isEmpty() && !mLastName.getText().toString().isEmpty()
                && !mEmail.getText().toString().isEmpty() && !mGithub.getText().toString().isEmpty()) {
                    submit(mFirstName.getText().toString(),mLastName.getText().toString(),mEmail.getText().toString(),mGithub.getText().toString());
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please fill out the fields",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void submit(final String firstName, final String lastName, final String email, final String github){
        Log.d(TAG,"submit: submitting to google form");
        VolleyLog.DEBUG = true;

        AlertDialog.Builder builder = new AlertDialog.Builder(SubmissionActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView  = LayoutInflater.from(SubmissionActivity.this).inflate(R.layout.confirm_submission_layout,viewGroup,false);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        TextView yesButton = dialogView.findViewById(R.id.yes_button);
        ImageButton cancelButton = dialogView.findViewById(R.id.cancel);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HttpUrl httpUrl = HttpUrl.parse(BASE_URL);
//                URL url = Uri.parse(BASE_URL);
                Retrofit retrofit = new Retrofit.Builder().baseUrl(httpUrl)
//                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                SubmissionService submissionService = retrofit.create(SubmissionService.class);
                Call<Void> call = submissionService.submit(firstName, lastName, email, github);
                AsyncTask<Call<Void>,Void,Response<Void>> task = new AsyncTask<Call<Void>, Void, Response<Void>>() {
                    @Override
                    protected Response<Void> doInBackground(Call<Void>... calls) {
                        Call<Void> call = calls[0];
                        Response<Void> response = null;
                        try {
                            response = call.execute();
                        } catch (IOException e) {
                            Log.d(TAG,"catch block : error "+ e.getMessage());
                            e.printStackTrace();
                        }
                        return response;
                    }

                    @Override
                    protected void onPostExecute(Response<Void> voidResponse) {
                        super.onPostExecute(voidResponse);
                        Response<Void> response = voidResponse;
                    if(response.isSuccessful()){
                        Log.d(TAG,"onResponse : response "+response.toString());

                        AlertDialog.Builder builderDialog = new AlertDialog.Builder(SubmissionActivity.this);
                        ViewGroup viewgroup = findViewById(android.R.id.content);
                        View dialogview  = LayoutInflater.from(SubmissionActivity.this).inflate(R.layout.successful_submission_layout,viewgroup,false);
                        builderDialog.setView(dialogview);
                        final AlertDialog dialog = builderDialog.create();
                        alertDialog.hide();
                        dialog.show();
                    }
                    else {
                        Log.d(TAG, "onFailure : error " + response.errorBody()+", "+response.raw().toString());

                        AlertDialog.Builder builderDialog = new AlertDialog.Builder(SubmissionActivity.this);
                        ViewGroup viewgroup = findViewById(android.R.id.content);
                        View dialogview = LayoutInflater.from(SubmissionActivity.this).inflate(R.layout.unsuccessful_submission_layout, viewgroup, false);
                        builderDialog.setView(dialogview);
                        final AlertDialog dialog = builderDialog.create();
                        alertDialog.hide();
                        dialog.show();
                    }
                    }
                };
                task.execute(call);

                /*final RequestQueue requestQueue = Volley.newRequestQueue(SubmissionActivity.this);
                StringRequest request = new StringRequest(Request.Method.POST, BASE_URL+FORM_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        String track = " ";
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put(EMAIL_ENTRY, email);
                        params.put(FIRSTNAME_ENTRY, firstName);
                        params.put(LASTNAME_ENTRY, lastName);
                        params.put(TRACK_ENTRY,track);
                        params.put(GITHUB_ENTRY, github);
                        return params;
                    }
                };
//                request.setShouldCache(false);
//                request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
                requestQueue.add(request);*/

//                Toast.makeText(SubmissionActivity.this,"You clicked yes",Toast.LENGTH_LONG).show();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"You clicked cancel");
//                Toast.makeText(SubmissionActivity.this,"You clicked cancel",Toast.LENGTH_LONG).show();
                alertDialog.hide();
//                        finish();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}