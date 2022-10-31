package com.codingproject.apidata_parsing;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    // Instantiate the RequestQueue.
    RequestQueue queue;
    String url = "https://www,google.com";
    String apiUrl = "https://jsonplaceholder.typicode.com/todos";
    String objApiUrl = "https://jsonplaceholder.typicode.com/todos/1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        queue = Volley.newRequestQueue(this);
        TextView textView = findViewById(R.id.textView001);

        queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
//        queue.add(jsonGetArrayRequest());
        MySingleton.getInstance(this).addToRequestQueue(jsonGetArrayRequest());
        // OR WE CAN ALSO WRITE    "queue.add(jsonGetArrayRequest());"


//        // MAKING A STANDARD JSON OBJECT REQUEST
//        jsonGetObjectRequest(textView);
//
//        // MAKING A STANDARD JSON ARRAY REQUEST
//        JsonArrayRequest jsonRequest = jsonGetArrayRequest();
//        queue.add(jsonRequest);
//
        // STRING REQUEST
////        StringRequest request = getStringRequest();
//        // Add the request to the RequestQueue.
////        queue.add(request);
    }

    private void jsonGetObjectRequest(TextView textView) {
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, objApiUrl, null,
                response -> {
                    try {
                        textView.setText(response.getString("title"));
                        Log.d("OBJECT", " RESPONSE: " + response.getString("title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("Obj ERROR", "OBJ ERROR: " + error.toString()));

        queue.add(jsonObjRequest);
    }

    private JsonArrayRequest jsonGetArrayRequest() {
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Log.d("JSON", "OnCreate: " + jsonObject.getString("title"));
                        Log.d("JSON", "ON-INT: " + jsonObject.getInt("userId"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "ERROR: " + error.toString());
            }
        });
        return jsonRequest;
//        queue.add(jsonRequest);
    }

    @NonNull
    private StringRequest getStringRequest() {
        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // DISPLAY THE CONTENTS OF OUR URL
                Log.d("Main", "ONCreate: " + response.substring(0, 600));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Main", "ONError: " + error.toString());
            }
        });
        return request;
    }
}