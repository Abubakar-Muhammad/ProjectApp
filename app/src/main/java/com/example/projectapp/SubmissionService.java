package com.example.projectapp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SubmissionService {

    String FORM_URL = "1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse" ;
    String FIRSTNAME_ENTRY = "entry.1877115667";
    String LASTNAME_ENTRY = "entry.2006916086";
    String EMAIL_ENTRY = "entry.1824927963";
    String GITHUB_ENTRY = "entry.284483984";

    @POST(FORM_URL)
    @FormUrlEncoded
    public Call<Void> submit(@Field(FIRSTNAME_ENTRY) String firstName,
                             @Field(LASTNAME_ENTRY) String lastName,
                             @Field(EMAIL_ENTRY) String email,
                             @Field(GITHUB_ENTRY) String github);
}
