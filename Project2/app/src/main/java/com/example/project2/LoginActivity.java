package com.example.project2;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

public class LoginActivity extends AppCompatActivity {

    private TextView info;
    private ImageView profile;
    private LoginButton login;
    private Button openApp;

    CallbackManager callbackManager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView animation = findViewById(R.id.login_animation);
        Drawable drawable = animation.getDrawable();

        AnimatedVectorDrawable loginAnimation = (AnimatedVectorDrawable) drawable;
        loginAnimation.start();

        info = findViewById(R.id.info);
        profile = findViewById(R.id.profile);
        login = findViewById(R.id.login_button);
        openApp = findViewById(R.id.open_app_button);

        if (isLoggedIn()) {
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            info.setText(accessToken.getUserId());
            String imageURL =
                    "https://graph.facebook.com/" + accessToken.getUserId() + "/picture" +
                            "?return_ssl_resources=1";
            Picasso.get().load(imageURL).into(profile);

            openApp.setText("앱 들어가기");
            openApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }

        else {
            openApp.setVisibility(View.GONE);
            callbackManager = CallbackManager.Factory.create();

            login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    info.setText("User Id: " + loginResult.getAccessToken().getUserId());
                    String imageURL =
                            "https://graph.facebook.com/" + loginResult.getAccessToken().getUserId() + "/picture?return_ssl_resources=1";
                    Picasso.get().load(imageURL).into(profile);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {

                }
            });
        }
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
