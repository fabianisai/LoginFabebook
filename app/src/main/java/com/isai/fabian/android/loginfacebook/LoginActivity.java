package com.isai.fabian.android.loginfacebook;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.btnLogin)
    LoginButton btnLogin;
    @Bind(R.id.loginContainer)
    RelativeLayout loginContainer;

    private CallbackManager callbackManager;  //callback de face
    private ProfileTracker profileTracker;
    private AccessTokenTracker tokenTracker;

    /**
     * declarasmo el callback de face
     */
    private FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.e("oko", "success");
            AccessToken accessToken = loginResult.getAccessToken();
            getvaluesUser(accessToken);
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        callbackManager = CallbackManager.Factory.create();
        if (AccessToken.getCurrentAccessToken()!=null) {
            getvaluesUser(AccessToken.getCurrentAccessToken());
        }

        btnLogin.setReadPermissions(Arrays.asList("public_profile","email"));
        btnLogin.registerCallback(callbackManager,facebookCallback);


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);  //callback de face para admnistrar la session


    }


    private void getvaluesUser(AccessToken accessToken){
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,     //AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        try {
                            Log.e("oko", object.getString("id"));
                            Log.e("oko", object.getString("name"));
                            Log.e("oko", object.getString("email"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();

    }

    public void navigateToMainScreen() {
        Intent intent= new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        //startActivity(new Intent(this, Something.class));
    }
}
