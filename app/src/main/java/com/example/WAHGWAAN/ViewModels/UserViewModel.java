package com.example.WAHGWAAN.ViewModels;

import com.example.WAHGWAAN.HelperClasses.ApiInterface;
import com.example.WAHGWAAN.HelperClasses.SignInCred;
import com.example.WAHGWAAN.Models.User;

import java.io.IOException;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserViewModel extends ViewModel {

    public MutableLiveData<User> appUser;
    private SavedStateHandle savedStateHandle;
    private String responseVal = new String();

    public UserViewModel(SavedStateHandle savedStateHandle) {
        this.savedStateHandle = savedStateHandle;
    }

    public MutableLiveData<User> getUser(){
        if(appUser == null){
            appUser = new User();
            loadUser();
        }
        return appUser;
    }

    private void loadUser(){
        //Retrieve User from File system
        User testUser = new User();
        testUser.loginUser(false);
        appUser.setValue(testUser);
    }

    public MutableLiveData<User> loginUser(String Username, String Password) throws IOException { //String Username, String Password){
        final Integer[] TestVal = {new Integer(0)};

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wahgwaan.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<User> call = apiInterface.signIn(new SignInCred(Username, Password));

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()){
                    try {
                        responseVal = Integer.toString(response.code());
                        User noAuth = new User();
                        noAuth.loginUser(false);
                        appUser = noAuth;
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }else {
                    try {
                        User loggedUser = response.body();
                        loggedUser.loginUser(true);
                        appUser.setValue(loggedUser);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                responseVal = "A. " + t.getMessage();
            }
        });

        return appUser;
    }

}

