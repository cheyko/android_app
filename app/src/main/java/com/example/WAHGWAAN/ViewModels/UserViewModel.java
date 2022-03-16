package com.example.WAHGWAAN.ViewModels;

import android.content.Context;
import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;

import com.example.WAHGWAAN.HelperClasses.PostAsync;
import com.example.WAHGWAAN.Models.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInput;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {

    public MutableLiveData<User> appUser;
    private SavedStateHandle savedStateHandle;

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
        String response = new String();
        /*try {
            URL url = new URL("https://wahgwaan.herokuapp.com/api/login");
            HttpsURLConnection urlConnection = new HttpsURLConnection(url) {
                @Override
                public String getCipherSuite() {
                    return null;
                }

                @Override
                public Certificate[] getLocalCertificates() {
                    return new Certificate[0];
                }

                @Override
                public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
                    return new Certificate[0];
                }

                @Override
                public void disconnect() {

                }

                @Override
                public boolean usingProxy() {
                    return true;
                }

                @Override
                public void connect() throws IOException {

                        this.setDoOutput(true);
                        this.setChunkedStreamingMode(0);
                        Authenticator.setDefault(new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(Username, Password.toCharArray());
                            }
                        });
                        TestVal[0] = this.getResponseCode();
                }
            };

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
       /* URL url = new URL("https://wahgwaan.herokuapp.com/api/login");
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
       try {
            Map<String,String> parameters = new HashMap<>();
            parameters.put("email", Username);
            parameters.put("password", Password);

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Charset", "UTF-8");

            con.setReadTimeout(10000);
            con.setConnectTimeout(15000);

            con.connect();
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
            out.flush();
            out.close();
            //TestVal = con.getResponseCode();
            //con.disconnect();

            InputStream in = new BufferedInputStream(con.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (ProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (con != null){
                con.disconnect();
            }
        }*/

        URL url = new URL("https://wahgwaan.herokuapp.com/api/login");
        java.net.HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            urlConnection.setRequestProperty("Accept", "application/json");

            String payload = String.format("{\"email\":\"%s\",\"password\":\"%s\"}",Username,Password);
            OutputStream stream = (OutputStream) urlConnection.getOutputStream();
            byte[] input = payload.getBytes("utf-8");
            stream.write(input, 0, input.length);

            InputStreamReader in = new InputStreamReader(urlConnection.getInputStream(),"utf-8");
            BufferedReader reader = new BufferedReader(in);
            StringBuilder result = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                result.append(line.trim());
            }
            response = result.toString();
        }catch (NetworkOnMainThreadException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        /*String payload = String.format("{\"email\":\"%s\",\"password\":\"%s\"}",Username,Password);
        String route = "/api/login";
        String method = "POST";

        AsyncTask<String, String, JSONObject> result = new PostAsync().execute(route,method,payload);
        //ObjectMapper objectMapper = new ObjectMapper();
        //User FoundUser = objectMapper.readValue(result.toString(), User.class);

         */
        User FoundUser = new User();
        FoundUser.loginUser(true);
        FoundUser.setUsername(response); //(result.toString());
        appUser.setValue(FoundUser);
        //appUser.getValue().setUsername(TestVal.toString());
        savedStateHandle.set("Username","TESTing");
        return appUser;
    }

    //temporary login working as a login for cached user.
    public MutableLiveData<User> loginUser(Boolean Value){ //String Username, String Password){
        //User FoundUser = new User();
        //FoundUser.loginUser(Value);
        //appUser.setValue(FoundUser);
        //savedStateHandle.set("Username","TESTing");
        return appUser;
    }
}
