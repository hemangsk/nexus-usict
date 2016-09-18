package xyz.hemangkumar.rnfapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import xyz.hemangkumar.rnfapp.fragments.PostWorkshop;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    GoogleApiClient googleApiClient;
    GoogleSignInOptions googleSignInOptions;
    int RC_SIGN_IN = 3;
    SignInButton signin ;
  //  Button signout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signin = (SignInButton) findViewById(R.id.sign_in_button);
      //  signout = (Button) findViewById(R.id.sign_out_button);

        signin.setOnClickListener(this);
       // signout.setOnClickListener(this);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    Toast.makeText(LoginActivity.this, "Signed in!", Toast.LENGTH_SHORT).show();
                    
                    finish();


                }
                else{
                    //Toast.makeText(LoginActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();

                }
            }
        };

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(LoginActivity.this, "Google Play Services Error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null)
        auth.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

          if(requestCode == RC_SIGN_IN){

              GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

              if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();

                final ProgressDialog progressDialog= new ProgressDialog(this);

                AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                  progressDialog.show();
                  progressDialog.setMessage("Authenticating");

                auth.signInWithCredential(authCredential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Handler h= new Handler();
                                    h.postDelayed(new Runnable(){
                                        public void run() {

                                            progressDialog.hide();
                                        }}, 500);

                                    Snackbar.make(getView(), "Please check your internet connection", Snackbar.LENGTH_LONG ).show();


                                }




                            }
                        });


            }
        }
    }

    private View getView() {
        return (View)findViewById(R.id.login_activity_root);
    }

    public void signIn(){
       // Toast.makeText(LoginActivity.this, "Calling Intent", Toast.LENGTH_SHORT).show();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signOut(){
        auth.signOut();

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
         //       Toast.makeText(LoginActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_in_button) {
            signIn();
        }
    }

    @Override
    public void onBackPressed() {
       // Toast.makeText(LoginActivity.this, "Back", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("STATUS", "false");
        setResult(3, intent);
        finish();
    }


}
