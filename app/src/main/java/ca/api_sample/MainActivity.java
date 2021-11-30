package ca.api_sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.action_fetch_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNetworkResponse("https://api-sample-2021.herokuapp.com/current_time");
            }
        });
    }

    private void getNetworkResponse(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        setViews(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setViewsWithError(error);
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                return params;
            }
        }
        ;

        Volley.newRequestQueue(this).add(jsonObjectRequest);

    }

    private void setViews(JSONObject response){
        try {
            ((TextView)findViewById(R.id.textView)).setText(response.getString("date_time_str"));
        } catch (JSONException e) {
            e.printStackTrace();
            ((TextView)findViewById(R.id.textView)).setText("Hmmm.. something went wrong. Try again?");
        }
    }

    private void setViewsWithError(VolleyError error){
        error.printStackTrace();
        ((TextView)findViewById(R.id.textView)).setText("Hmmm.. something went wrong. Try again?");
    }
}