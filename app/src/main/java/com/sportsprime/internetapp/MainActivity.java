package com.sportsprime.internetapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;
    private TextView id;
    private TextView firstName;
    private TextView lastName;
    private TextView email;
    private Button btn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = findViewById(R.id.id);
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestApi();
            }
        });
    }

    private void requestApi() {
        String userId = editText.getText().toString().trim();
        if (userId == null) {


            return;
        }
        OkHttpClient client = new OkHttpClient();
        String apiKey = "6|Ite5W7mHyR72mUFLUQ8k4lEo7MZXNPZOucamJ5Ywba821c43";
        String url = "https://reqres.in/api/users/"+ userId;
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(()->textView.setText(e.toString()));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()){
                    runOnUiThread(()->textView.setText("Api request failed"));
                }else{
                    try {
                        String jsonData = response.body().string();
                        JSONObject jsonObject = new JSONObject(jsonData);
                        String result = jsonObject.getString("data");
                        User user = new Gson().fromJson(result, User.class);
                        runOnUiThread(()->id.setText(user.getId()));
                        runOnUiThread(()->firstName.setText(user.getFirst_name()));
                        runOnUiThread(()->lastName.setText(user.getLast_name()));
                        runOnUiThread(()->email.setText(user.getEmail()));
                    } catch (JSONException e) {
                        runOnUiThread(()->textView.setText(e.toString()));

                    }
                }
            }
        });
    }




}