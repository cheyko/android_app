package com.example.WAHGWAAN.HelperClasses;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.WAHGWAAN.MainActivity;

import org.json.JSONObject;

public class PostAsync extends AsyncTask<String, String, JSONObject> {

    JSONParser jsonParser = new JSONParser();
    @Override
    protected JSONObject doInBackground(String... args) {
        try {
            JSONObject jsonObject = jsonParser.makeHttpRequest(args[0], args[1], args[2]);

            if (jsonObject != null){
                return jsonObject;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        /*if (jsonObject != null){
            Toast.makeText(MainActivity.this, jsonObject.toString(), Toast.LENGTH_LONG).show();
        }*/
    }
}
