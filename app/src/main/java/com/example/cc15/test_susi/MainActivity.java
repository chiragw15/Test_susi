package com.example.cc15.test_susi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView answer;
    EditText question;
    Button send;
    RequestQueue queue;
    private String TAG = "chirag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        answer = (TextView) findViewById(R.id.answer);
        question = (EditText) findViewById(R.id.question);
        send = (Button) findViewById(R.id.button);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = question.getText().toString();
                sendForResponse(query);
            }
        });
    }

    private void sendForResponse(String query) {
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest("http://api.asksusi.com/susi/chat.json?timezoneOffset=-330&q="+query, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        printQuery(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonObjectReq);
    }

    private void printQuery(JSONObject response) {
        try {
            String susiAnswer = response.getJSONArray("answers").getJSONObject(0).getJSONArray("actions").getJSONObject(0).getString("expression");
            answer.setText(susiAnswer);
        } catch (Exception e) {
            answer.setText("Error");
        }
    }
}
