package com.example.tm.ui.authorization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.tm.MainActivity;
import com.example.tm.R;
import com.example.tm.ui.setting.SettingsViewModel;

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
        TextView loginTextView = findViewById ( R.id.login_edit_text );
        TextView passwordTextView = findViewById ( R.id.password_edit_text );
        Button enterButton = findViewById ( R.id.btn_enter );
        enterButton.setOnClickListener ( view -> {
            String login = loginTextView.getText ().toString ();
            String password = passwordTextView.getText ().toString ();
            if (vm.EmployeeVerification (  login, password )){
                loginTextView.setText ( "" );
                passwordTextView.setText ( "" );
                Intent intent = new Intent ( getApplicationContext (), MainActivity.class );
                intent.putExtra ( "idEmp", vm.getIdEmployee () );
                startActivity(intent);
            }
        } );
    }

    @Override
    public void onBackPressed () {
        finishAffinity();
    }
}