package com.example.WAHGWAAN.Components;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.WAHGWAAN.HelperClasses.ApiInterface;
import com.example.WAHGWAAN.Models.User;
import com.example.WAHGWAAN.R;
import com.example.WAHGWAAN.ViewModels.UserViewModel;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TimelineFragment extends Fragment {

    private UserViewModel userViewModel;
    private Boolean Test;
    private View theView;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://wahgwaan.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        theView = inflater.inflate(R.layout.fragment_timeline,container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        return theView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        try {
            super.onViewCreated(view, savedInstanceState);
            //Toast.makeText(getContext(), "View created : " , Toast.LENGTH_SHORT).show();

            File path = getContext().getFilesDir();
            File file = new File(path, "wg-cred-14.txt");
            int length = (int) file.length();

            byte[] bytes = new byte[length];
            FileInputStream in = new FileInputStream(file);
            try {
                in.read(bytes);
            } finally {
                in.close();
            }
            String contents = new String(bytes);
            Test = Boolean.parseBoolean(contents.toString());

            Toast.makeText(getContext(), "Test: " + Test, Toast.LENGTH_SHORT).show();

            if(Test == true){
                ConfigureUser();
            }else {
                Toast.makeText(getContext(), "D. ", Toast.LENGTH_SHORT).show();
                userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
            }
            userViewModel.getUser().observe(getViewLifecycleOwner(), (Observer<User>) user -> {
                 if (user == null) {
                     Toast.makeText(getContext(), "User-Login Required", Toast.LENGTH_SHORT).show();
                     getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
                 } else if(user != null && user.LoginState() == false) {
                     Toast.makeText(getContext(), "Try logging In Again", Toast.LENGTH_SHORT).show();
                     getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
                 } else {
                     //call function to retrieve prees.
                     //Toast.makeText(getContext(), "User: " + user.LoginState(), Toast.LENGTH_SHORT).show();
                     Welcome();
                 }
            });

            //Toast.makeText(getContext(), "End " +getContext().getFilesDir(), Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
            userViewModel.getUser().observe(getViewLifecycleOwner(), (Observer<User>) user -> {
                if (user == null) {
                    Toast.makeText(getContext(), "User-Login Required", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
                } else if(user != null && user.LoginState() == false) {
                    Toast.makeText(getContext(), "Try logging In Again", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
                } else {
                    //call function to retrieve prees.
                    //Toast.makeText(getContext(), "User: " + user.LoginState(), Toast.LENGTH_SHORT).show();
                    Welcome();
                }
            });
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void Welcome(){
        Toast.makeText(getContext(),"Welcome" , Toast.LENGTH_LONG).show();
    }

    public void ConfigureUser(){
        User restoredUser = new User();
        try {
            File newpath = getContext().getFilesDir();
            File userfile = new File(newpath, "test.txt");
            int newlength = (int) userfile.length();

            byte[] newbytes = new byte[newlength];
            FileInputStream new_in = new FileInputStream(userfile);
            try {
                new_in.read(newbytes);
            } finally {
                new_in.close();
            }
            String[] input = new String(newbytes).split("\n");
            NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
            View headerView = navigationView.getHeaderView(0);
            Button unameBtn = headerView.findViewById(R.id.usernameLabel);
            ImageView mirrorPic = headerView.findViewById(R.id.mirror_pic);
            unameBtn.setText(input[0]);

            Toast.makeText(getContext(), "Configure User: " + input[1], Toast.LENGTH_SHORT).show();
            restoredUser.setUsername(input[0]);
            restoredUser.setId(Integer.parseInt(input[1]));
            restoredUser.loginUser(true);
            //userViewModel.getUser().getValue().setUsername(input[0]);
            //userViewModel.getUser().getValue().setId(Integer.parseInt(input[1]));
            //userViewModel.loginUser(true);
            //userViewModel.appUser = restoredUser;
            Call<ResponseBody> call = apiInterface.getPic(restoredUser);
            Toast.makeText(getContext(), "call created", Toast.LENGTH_SHORT).show();

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        if (response.code() == 200) {
                            Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                            mirrorPic.setImageBitmap(bitmap);
                        }else{
                            Toast.makeText(getContext(), "No image available : " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "Unsuccessful, Response Code : " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
