package com.example.tm.ui.authorization;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.tm.MainActivity;
import com.example.tm.R;
import com.example.tm.employee.Employee;
import com.example.tm.ui.setting.SettingsViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class AuthorizationActivity extends AppCompatActivity {

    private void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, null);
    }

    Context context;
    boolean reg = false;
    TextView messageTextView;
    EditText loginEditText, passwordEditText, surnameEditText, firstNameEditText, patronymicEditText, positionEditText;
    Button enterButton, regButton;
    ProgressBar progressBar;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        SharedPreferences settingsPreferences = getSharedPreferences ( SettingsViewModel.SETTINGS, MODE_PRIVATE);

        if (settingsPreferences.getString ( SettingsViewModel.THEME, SettingsViewModel.WHITE_THEME ).equals ( SettingsViewModel.DARK_THEME ))
            setTheme ( R.style.Theme_TMDark );
        else
            setTheme ( R.style.Theme_TM );

        String land = settingsPreferences.getString ( SettingsViewModel.LANGUAGE, SettingsViewModel.EN_LANGUAGE );
        changeLang ( land );

        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_authorization );

        AuthorizationViewModel vm = new ViewModelProvider ( this ).get ( AuthorizationViewModel.class );

        loginEditText = findViewById ( R.id.login_edit_text );
        passwordEditText = findViewById ( R.id.password_edit_text );
        surnameEditText = findViewById ( R.id.surname_edit_text );
        firstNameEditText = findViewById ( R.id.first_name_edit_text );
        patronymicEditText = findViewById ( R.id.patronymic_edit_text );
        positionEditText = findViewById ( R.id.position_edit_text );
        enterButton = findViewById ( R.id.btn_enter );
        regButton = findViewById ( R.id.btn_reg );
        progressBar = findViewById ( R.id.progress_bar );
        messageTextView = findViewById ( R.id.messageTextView );
        context = this;
        enterButton.setOnClickListener ( view -> {
            progressBar.setVisibility ( View.VISIBLE );
            String login = loginEditText.getText ( ).toString ( ).trim ();
            String password = passwordEditText.getText ( ).toString ( ).trim ();
            if (login.isEmpty () || password.isEmpty ()) {
                Toast.makeText ( getApplication ( ) , R.string.fields_are_not_filled , Toast.LENGTH_SHORT ).show ( );
                progressBar.setVisibility ( View.GONE);
                return;
            }
            if (reg){
                String surname, firstName, patronymic, position;
                surname = surnameEditText.getText ().toString ().trim ();
                firstName = firstNameEditText.getText ().toString ().trim ();
                patronymic = patronymicEditText.getText ().toString ().trim ();
                position = positionEditText.getText ().toString ().trim ();
                if(surname.isEmpty () || firstName.isEmpty () || patronymic.isEmpty () || position.isEmpty ()){
                    Toast.makeText ( getApplication ( ) , R.string.fields_are_not_filled , Toast.LENGTH_SHORT ).show ( );
                    progressBar.setVisibility ( View.GONE);
                    return;
                }
                vm.getAuth ().createUserWithEmailAndPassword ( login, password )
                        .addOnSuccessListener ( new OnSuccessListener<AuthResult> ( ) {
                            @Override
                            public void onSuccess ( AuthResult authResult ) {
                                Employee employee = new Employee ();
                                employee.login = login;
                                employee.password = password;
                                employee.surname = surname;
                                employee.firstName = firstName;
                                employee.patronymic = patronymic;
                                employee.isDirector = true;
                                employee.position = position;
                                employee.directorId = vm.getAuth ().getCurrentUser ().getUid();
                                employee.id = vm.getAuth ().getUid ();
                                vm.getEmployeesRef ().child ( employee.id ).setValue ( employee )
                                        .addOnSuccessListener ( new OnSuccessListener<Void> ( ) {
                                            @Override
                                            public void onSuccess ( Void unused ) {
                                                Toast.makeText ( getApplication ( ) , R.string.director_registered , Toast.LENGTH_SHORT ).show ( );
                                                UpdateIUReg ();
                                                progressBar.setVisibility ( View.GONE);
                                            }
                                        } )
                                        .addOnFailureListener ( new OnFailureListener ( ) {
                                            @Override
                                            public void onFailure ( @NonNull Exception e ) {
                                                Toast.makeText ( getApplication ( ) , e.getMessage () , Toast.LENGTH_SHORT ).show ( );
                                                progressBar.setVisibility ( View.GONE);
                                            }
                                        } );
                            }
                        } )
                        .addOnFailureListener ( new OnFailureListener ( ) {
                            @Override
                            public void onFailure ( @NonNull Exception e ) {
                                Toast.makeText ( getApplication ( ) , e.getMessage () , Toast.LENGTH_SHORT ).show ( );
                                progressBar.setVisibility ( View.GONE);
                            }
                        } );
            }
            else {
                vm.getAuth ().signInWithEmailAndPassword ( login, password )
                        .addOnSuccessListener ( new OnSuccessListener<AuthResult> ( ) {
                            @Override
                            public void onSuccess ( AuthResult authResult ) {
                                vm.getEmployeesRef ().child ( vm.getAuth ().getUid () ).addValueEventListener ( new ValueEventListener ( ) {
                                    @Override
                                    public void onDataChange ( @NonNull DataSnapshot snapshot ) {
                                        progressBar.setVisibility ( View.GONE);
                                        MainActivity.AuthorizationEmployee = ( snapshot.getValue ( Employee.class ) );
                                        passwordEditText.setText ( "" );
                                        passwordEditText.setText ( "" );
                                        Intent intent = new Intent ( getApplicationContext (), MainActivity.class );
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onCancelled ( @NonNull DatabaseError e ) {
                                        Toast.makeText ( getApplication ( ) , e.getMessage () , Toast.LENGTH_SHORT ).show ( );
                                        progressBar.setVisibility ( View.GONE);
                                    }
                                }) ;


                            }
                        } )
                        .addOnFailureListener ( new OnFailureListener ( ) {
                            @Override
                            public void onFailure ( @NonNull Exception e ) {
                                Toast.makeText ( getApplication ( ) , e.getMessage () , Toast.LENGTH_SHORT ).show ( );
                                progressBar.setVisibility ( View.GONE);
                            }
                        } );
            }
        });
        regButton.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                UpdateIUReg ();
            }
        } );
    }

    public void UpdateIUReg(){
        if (reg){
            reg = false;
            loginEditText.setText ( "" );
            passwordEditText.setText ( "" );
            surnameEditText.setText ( "" );
            firstNameEditText.setText ( "" );
            patronymicEditText.setText ( "" );
            surnameEditText.setVisibility ( View.GONE );
            firstNameEditText.setVisibility ( View.GONE );
            patronymicEditText.setVisibility ( View.GONE );
            positionEditText.setVisibility ( View.GONE );
            messageTextView.setVisibility ( View.GONE );
            enterButton.setText ( R.string.btn_enter );
            regButton.setText ( R.string.btn_reg );
        }
        else {
            reg = true;
            loginEditText.setText ( "" );
            passwordEditText.setText ( "" );
            surnameEditText.setText ( "" );
            firstNameEditText.setText ( "" );
            patronymicEditText.setText ( "" );
            surnameEditText.setVisibility ( View.VISIBLE );
            firstNameEditText.setVisibility ( View.VISIBLE );
            patronymicEditText.setVisibility ( View.VISIBLE );
            positionEditText.setVisibility ( View.VISIBLE );
            messageTextView.setVisibility ( View.VISIBLE );
            enterButton.setText ( R.string.btn_reg );
            regButton.setText ( R.string.btn_enter );
        }
    }

    @Override
    public void onBackPressed () {
        finishAffinity();
    }
}