package com.kogo.moapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button buttonGoMenuActivity;
    private Handler handler = new Handler();
    private ProgressDialog progressDialog;
    private int progressCountt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGoMenuActivity = findViewById(R.id.buttonGoMenuActivity);

        buttonGoMenuActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Giriş Yapılıyor, lütfen bekleyiniz..");
                progressCountt = progressDialog.getProgress();
                System.out.println("get P: " + progressCountt);
                progressDialog.show();

                //  progressBar.setVisibility(View.VISIBLE);
                //  progressCount = progressBar.getProgress();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(progressCountt<3){
                            //        progressCount+=1;
                            progressCountt+=1;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.setProgress(progressCountt);
                                    //   progressBar.setProgress(progressCount);
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
                                            //   progressBar.setVisibility(View.INVISIBLE);
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
        });
    }


    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


}