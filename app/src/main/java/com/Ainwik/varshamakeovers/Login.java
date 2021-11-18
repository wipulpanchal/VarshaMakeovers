package com.Ainwik.varshamakeovers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    Button btn;
    TextView textView;
    boolean isEmailValid, isPasswordValid;

    String url="http://www.varshamakeovers.com/login.php";

    RequestQueue requestQueue;

    EditText e1,e2;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ProgressDialog pd;

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn=findViewById(R.id.SignInLogin);
        textView=findViewById(R.id.SignUpText);
        requestQueue= Volley.newRequestQueue(this);
        e1=findViewById(R.id.editTextTextPersonEmail);
        e2=findViewById(R.id.editTextTextPersonPassword);

        preferences=getSharedPreferences("login_credential", Context.MODE_PRIVATE);
        editor=preferences.edit();

        if (preferences.getString("user",null)!=null && preferences.getString("pass",null)!=null)
        {
            Intent i = new Intent(Login.this,Home.class);
            startActivity(i);
            finish();
        }



        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
       // getSupportActionBar().hide();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Login.this, SignUp.class);
                startActivity(i);
            }
        });







        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                pd=new ProgressDialog(Login.this);
                pd.setMessage("Please Wait!");
                pd.show();

                String uname=e1.getText().toString();
                String pass=e2.getText().toString();

                if(uname.isEmpty() || pass.isEmpty())
                {

                    if (uname.isEmpty())
                    {
                        e1.setError("Please Enter Username");
                    }

                    if(pass.isEmpty())
                    {
                        e2.setError("Please Enter Password");
                    }

                }
                else {


                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //  Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                int data = jsonObject.getInt("status");

                                if (data == 200) {

                                    String user = jsonObject.getString("user");
                                    String pass = jsonObject.getString("pass");

                                    editor.putString("user", user);
                                    editor.putString("pass", pass);
                                    editor.commit();

                                    // Toast.makeText(getApplicationContext(), user+"\n"+pass, Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(Login.this, Home.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                }


                                //  Toast.makeText(getApplicationContext(), ""+data, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                            }


                       /* Intent i = new Intent(Login.this,Home.class);
                        startActivity(i);
*/
pd.dismiss();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                             pd.dismiss();
                        }
                    }) {


                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {


                            Map<String, String> map = new HashMap<String, String>();
                            map.put("email", e1.getText().toString());
                            map.put("password", e2.getText().toString());


                            return map;
                        }
                    };
                    requestQueue.add(request);

                }
            }
        });


    }


}