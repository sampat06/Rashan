package com.rashan.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.rashan.R;
import com.rashan.databinding.ActivityLoginBinding;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;
import com.truecaller.android.sdk.TruecallerSDK;
import com.truecaller.android.sdk.TruecallerSdkScope;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setTitle("");

        binding.trueCallerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TruecallerSdkScope trueScope = new TruecallerSdkScope.Builder(LoginActivity.this, sdkCallback)
                        .consentMode(TruecallerSdkScope.CONSENT_MODE_POPUP )
                        .consentTitleOption( TruecallerSdkScope.SDK_CONSENT_TITLE_VERIFY )
                        .footerType( TruecallerSdkScope.FOOTER_TYPE_SKIP )
                        .sdkOptions( TruecallerSdkScope.SDK_OPTION_WITH_OTP )
                        .build();

                TruecallerSDK.init(trueScope);

                TruecallerSDK.getInstance();

                TruecallerSDK.getInstance().isUsable();

                TruecallerSDK.getInstance().getUserProfile(LoginActivity.this);

                Locale locale = new Locale("en");
                TruecallerSDK.getInstance().setLocale(locale);

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TruecallerSDK.getInstance().onActivityResultObtained(this, resultCode, data);

    }

    private final ITrueCallback sdkCallback = new ITrueCallback() {

        @Override
        public void onSuccessProfileShared(@NonNull final TrueProfile trueProfile) {

            Log.d( "re==", "Verified without OTP! (Truecaller User): " + trueProfile.firstName+"/"+trueProfile.phoneNumber );
        }

        @Override
        public void onFailureProfileShared(@NonNull final TrueError trueError) {

            Log.d( "re==", "onFailureProfileShared: " + trueError.getErrorType() );

        }

        @Override
        public void onVerificationRequired() {
        }

    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
