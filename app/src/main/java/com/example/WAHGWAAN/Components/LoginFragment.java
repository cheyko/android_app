package com.example.WAHGWAAN.Components;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.WAHGWAAN.Models.User;
import com.example.WAHGWAAN.R;
import com.example.WAHGWAAN.ViewModels.UserViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

public class LoginFragment extends Fragment {

    private UserViewModel userViewModel;
    //private SavedStateHandle savedStateHandle;
    private View theView;
    //private Button usernameLabel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        theView = inflater.inflate(R.layout.fragment_login,container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        //usernameLabel = theView.getRootView().findViewById(R.id.usernameLabel);
        return theView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        Boolean Test = true;
        EditText username = view.findViewById(R.id.username);
        EditText password = view.findViewById(R.id.password);

        Button button = view.findViewById(R.id.loginBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(),"Logging In ..... ", Toast.LENGTH_LONG).show();
                String uname = username.getText().toString();
                String pword = password.getText().toString();
                if (uname != " " && pword != " " && !uname.matches("") && !pword.matches("")){
                    try {
                        userViewModel.loginUser(uname, pword).observe(getViewLifecycleOwner(), (Observer<User>) user -> {
                            if (user != null) {
                                if (user.LoginState() == true) {
                                    OutputStreamWriter outputStreamWriter = null;
                                    try {
                                        OutputStreamWriter osw = new OutputStreamWriter(getContext().openFileOutput("test.txt", Context.MODE_PRIVATE));
                                        osw.write(user.getUsername());
                                        osw.write("\n" + user.getId());
                                        osw.close();

                                        Toast.makeText(getContext(), "User Logged In successfully" + user.getUsername(), Toast.LENGTH_SHORT);
                                        outputStreamWriter = new OutputStreamWriter(getContext().openFileOutput("wg-cred-14.txt", Context.MODE_PRIVATE));
                                        outputStreamWriter.write(Test.toString());
                                        outputStreamWriter.close();
                                        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimelineFragment()).commit();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                    }
                                } else if (user.LoginState() == false) {
                                    Snackbar.make(view, "User not Logged In successfully; TRY AGAIN", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }else{
                    Snackbar.make(view, "Please enter a Username and Password.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
}
