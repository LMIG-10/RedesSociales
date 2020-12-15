package com.example.redes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.facebook.appevents.AppEventsLogger;

import java.io.File;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView tweetText;
    ShareButton btnShare;
    ShareButton btnlink;
    Button btnText;
    CallbackManager callbackmanager;
    CallbackManager callmanager;
    ImageView imagen;
    private LoginButton login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //printKeyHash();
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        btnShare= (ShareButton) findViewById(R.id.btnShare);
        btnlink = (ShareButton) findViewById(R.id.fblink);
        login = findViewById(R.id.login_button);
        btnText = (Button) findViewById(R.id.tweettext);
        tweetText = (TextView) findViewById(R.id.texto);
        imagen = (ImageView)findViewById(R.id.laimagen);

        callbackmanager = CallbackManager.Factory.create();
        callmanager=CallbackManager.Factory.create();

        login.setPermissions(Arrays.asList("user_gender, user_friends"));
        login.registerCallback(callmanager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Log.d("Demo","Inicio de sesion exitoso");
                }

                @Override
                public void onCancel() {

                    Log.d("Demo","Inicio de sesion cancelado");
                }

                @Override
                public void onError(FacebookException error) {
                    Log.d("Demo","Error en inicio de sesion");
                }
            });

        //FACEBOOK IMAGEN
        BitmapDrawable draw = (BitmapDrawable) imagen.getDrawable();
        Bitmap image = draw.getBitmap();
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .build();
        SharePhotoContent contenido = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        btnShare.setShareContent(contenido);

        //FACEBOOK LINK
        ShareLinkContent contenidoenlace = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://www.google.com.mx"))
                .build();
        btnlink.setShareContent(contenidoenlace);


        //Twitter Texto
        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = tweetText.getText().toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, msg);
                intent.setType("text/plain");
                intent.setPackage("com.twitter.android");
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callmanager.onActivityResult(requestCode, resultCode, data);
    }

    private void printKeyHash() {
        try{
            PackageInfo info = getPackageManager().getPackageInfo("com.example.redes", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
























