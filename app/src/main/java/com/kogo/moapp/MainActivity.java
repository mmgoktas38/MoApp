package com.kogo.moapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kogo.moapp.databinding.ActivityMainBinding;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private ProgressDialog progressDialog;
    private int progressCountt = 0;
    private ActivityMainBinding activityMainBinding;
    private Dialog dialog;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        dialog=new Dialog(MainActivity.this);

        activityMainBinding.editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!activityMainBinding.editTextPassword.getText().toString().equals("")){
                    changeLoginButtonColor();
                }
            }
        });

        activityMainBinding.editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!activityMainBinding.editTextEmail.getText().toString().equals("")){
                    changeLoginButtonColor();
                }
            }
        });

        activityMainBinding.buttonGoMenuActivity.setOnClickListener(view -> {

            if (activityMainBinding.editTextEmail.getText().toString().equals("") || activityMainBinding.editTextPassword.getText().toString().equals("")){
                Toast.makeText(MainActivity.this,"Fill in the blanks", Toast.LENGTH_SHORT).show();
            }
            else {

                if (isValidEmail(activityMainBinding.editTextEmail.getText().toString())) {
                    if (isValidPassword(activityMainBinding.editTextPassword.getText().toString())) {

                        progressDialog = new ProgressDialog(MainActivity.this);
                        progressDialog.setMessage("Giriş Yapılıyor, lütfen bekleyiniz..");
                        progressCountt = progressDialog.getProgress();
                        System.out.println("get P: " + progressCountt);
                        progressDialog.show();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while(progressCountt<3){
                                    progressCountt+=1;
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.setProgress(progressCountt);
                                            System.out.println(": " + progressCountt);
                                            if (progressCountt==3){
                                                if (isNetworkAvailable(MainActivity.this)){
                                                    if (progressDialog != null && progressDialog.isShowing()) {
                                                        progressDialog.dismiss();
                                                    }
                                                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                                                    startActivity(intent);
                                                }
                                                else {
                                                    Toast.makeText(MainActivity.this, "Check internet connection", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    });
                                    try {
                                        Thread.sleep(1000);
                                    }catch (InterruptedException e){
                                        e.printStackTrace();
                                    }
                                }

                            }
                        }).start();

                    }
                    else {
                        // Toast.makeText(MainActivity.this,"Check the password", Toast.LENGTH_SHORT).show();
                        activityMainBinding.textViewValidPassword.setVisibility(View.VISIBLE);
                        editTextPasswordTextChange();
                    }
                }
                else {
                    if (isValidPassword(activityMainBinding.editTextPassword.getText().toString())){
                    }else {
                        activityMainBinding.textViewValidPassword.setVisibility(View.VISIBLE);
                        editTextPasswordTextChange();
                    }

                    activityMainBinding.textViewValidEmail.setVisibility(View.VISIBLE);
                    editTextEmailTextChange();
                }
            }

        });

        activityMainBinding.imageViewInfoPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.setContentView(R.layout.password_valid_info);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button buttonOkPasswordInfo=dialog.findViewById(R.id.buttonOkPasswordInfo);

                buttonOkPasswordInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        activityMainBinding.imageViewShowOrHidePassword.setOnClickListener(view -> {
            if (activityMainBinding.editTextPassword.getInputType() == 144){    // 144 mean is that if we can see the password now
                activityMainBinding.editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);  // close the password
                activityMainBinding.imageViewShowOrHidePassword.setImageResource(R.drawable.visibility_off);
            }
            else {
                activityMainBinding.editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);      // show the password
                activityMainBinding.imageViewShowOrHidePassword.setImageResource(R.drawable.visible);
            }
            activityMainBinding.editTextPassword.setSelection(activityMainBinding.editTextPassword.length());   // set cursor position end of the password
        });


    }

    public void editTextEmailTextChange(){
        activityMainBinding.editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isValidEmail(editable.toString()) == true){
                    activityMainBinding.textViewValidEmail.setText("Valid email");
                }
                else {
                    activityMainBinding.textViewValidEmail.setText("Please enter a valid email address");
                }
            }
        });
    }

    public void editTextPasswordTextChange(){
        activityMainBinding.editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isValidPassword(editable.toString()) == true){
                    activityMainBinding.textViewValidPassword.setText("Valid password");
                }
                else {
                    activityMainBinding.textViewValidPassword.setText("Please enter a valid password");
                }
            }
        });
    }

    public void changeLoginButtonColor(){
        if (activityMainBinding.editTextEmail.getText().toString().equals("") || activityMainBinding.editTextPassword.getText().toString().equals("")){
            activityMainBinding.buttonGoMenuActivity.setBackgroundResource(R.drawable.login_button_background);
            activityMainBinding.buttonGoMenuActivity.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue_false)));
        }
        else {
            activityMainBinding.buttonGoMenuActivity.setBackgroundResource(R.drawable.login_button_background_true);
            activityMainBinding.buttonGoMenuActivity.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
        }
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    public static boolean isValidEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean isValidPassword(String password){

        String passwordRegex =  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        Pattern pat = Pattern.compile(passwordRegex);
        if (password == null)
            return false;
        return pat.matcher(password).matches();

    }


}