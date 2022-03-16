package com.example.WAHGWAAN.Components;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.WAHGWAAN.Models.User;
import com.example.WAHGWAAN.R;
import com.example.WAHGWAAN.ViewModels.UserViewModel;

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

public class TimelineFragment extends Fragment {

    private UserViewModel userViewModel;
    private Boolean Test;
    private View theView;

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
            Toast.makeText(getContext(), "View created : " , Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), "A. " + Test, Toast.LENGTH_SHORT).show();

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
                Toast.makeText(getContext(), "C. ", Toast.LENGTH_SHORT).show();
                userViewModel.loginUser(true);
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

            Toast.makeText(getContext(), "End " +getContext().getFilesDir(), Toast.LENGTH_LONG).show();
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
}
