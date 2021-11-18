package com.Ainwik.varshamakeovers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

   Button buttonSignUp;

    String url="http://www.varshamakeovers.com//signup.php";
    RequestQueue requestQueue;


    EditText e1,e2,e3,e4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        buttonSignUp=findViewById(R.id.SignUpbutton);
        e1=findViewById(R.id.PersonName);
        e2=findViewById(R.id.PersonEmail);
        e3=findViewById(R.id.PersonPhone);
        e4=findViewById(R.id.PersonPassword);
        requestQueue= Volley.newRequestQueue(this);


        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                progressDialog.setMessage("Please Wait !!");
                progressDialog.show();

                String name = e1.getText().toString();
                String email = e2.getText().toString();
                String phone = e3.getText().toString();
                String password = e4.getText().toString();
                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty())
                {
                    if(name.isEmpty())
                    {
                        e1.setError("Please enter name");
                    }
                    if(email.isEmpty())
                    {
                        e2.setError("Please enter email");
                    }
                    if(phone.isEmpty())
                    {
                        e3.setError("Please enter phone");
                    }
                    if(password.isEmpty())
                    {

                        e4.setError("Please enter password");
                    }


                 /*   if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty())
                {
                    if(name.isEmpty())
                    {
                        e1.setError("Please enter name");
                    }
                    if(email.isEmpty())
                    {
                        e2.setError("Please enter email");
                    }
                    if(phone.isEmpty())
                    {
                        e1.setError("Please enter phone");
                    }
                    if(password.isEmpty())
                    {
                        e1.setError("Please enter password");
                    }
*/

                }
                else {

                   /* e1.setError(null);
                    e2.setError(null);
                    e3.setError(null);
                    e4.setError(null);
*/
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Signup Successfully", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(SignUp.this, Login.class);
                            startActivity(i);
                            
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();


                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> data = new HashMap<String, String>();
                            data.put("name", e1.getText().toString());
                            data.put("email", e2.getText().toString());
                            data.put("phone", e3.getText().toString());
                            data.put("password", e4.getText().toString());
                            return data;
                        }
                    };

                    requestQueue.add(request);

                }


            }
        });




        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
       // getSupportActionBar().hide();


    }
}