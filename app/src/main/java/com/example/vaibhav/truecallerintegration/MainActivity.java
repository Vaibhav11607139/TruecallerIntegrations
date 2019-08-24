package com.example.vaibhav.truecallerintegration;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;
import com.truecaller.android.sdk.TrueSDK;
import com.truecaller.android.sdk.TrueSdkScope;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ITrueCallback{
    TextView textView;
    TrueSdkScope trueScope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textview);
         trueScope = new TrueSdkScope.Builder(this,this)
                .consentMode(TrueSdkScope.CONSENT_MODE_POPUP)
                .consentTitleOption( TrueSdkScope.SDK_CONSENT_TITLE_VERIFY )
                .footerType( TrueSdkScope.FOOTER_TYPE_SKIP )
                .build();

        TrueSDK.init(trueScope);
        if(TrueSDK.getInstance().isUsable())
        {

            Locale locale = new Locale("en");
            TrueSDK.getInstance().setLocale(locale);
            TrueSDK.getInstance().getUserProfile(this);



        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TrueSDK.getInstance().onActivityResultObtained( this,resultCode, data);
    }


    @Override
    public void onSuccessProfileShared(@NonNull TrueProfile trueProfile) {
        textView.setText(trueProfile.firstName);
    }

    @Override
    public void onFailureProfileShared(@NonNull TrueError trueError) {
        Toast.makeText(this, trueError.getErrorType()+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVerificationRequired() {

    }

}
