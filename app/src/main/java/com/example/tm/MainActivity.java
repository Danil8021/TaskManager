package com.example.tm;


import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tm.employee.Employee;
import com.example.tm.ui.setting.SettingsViewModel;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tm.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    EmployeeRepository employeeRepository;

    public static Employee AuthorizationEmployee;

    private void setLocale(String lang) {
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

        String land = settingsPreferences.getString ( SettingsViewModel.LANGUAGE, SettingsViewModel.EN_LANGUAGE );
        setLocale ( land );

        if (settingsPreferences.getString ( SettingsViewModel.THEME, SettingsViewModel.WHITE_THEME ).equals ( SettingsViewModel.DARK_THEME ))
            setTheme ( R.style.Theme_TMDark );
        else
            setTheme ( R.style.Theme_TM );

        super.onCreate ( savedInstanceState );

        Bundle arguments = getIntent().getExtras();
        int idAuthorizationEmployee = arguments.getInt ( "idEmp" );

        employeeRepository = new EmployeeRepository ( getApplication () );
        AuthorizationEmployee = employeeRepository.getEmployeeById ( idAuthorizationEmployee );

        binding = ActivityMainBinding.inflate ( getLayoutInflater ( ) );
        setContentView ( binding.getRoot ( ) );

        setSupportActionBar ( binding.appBarMain.toolbar );

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder (
                R.id.nav_employee , R.id.nav_task , R.id.nav_setting, R.id.authorizationActivity )
                .setOpenableLayout ( drawer )
                .build ( );
        NavController navController = Navigation.findNavController ( this , R.id.nav_host_fragment_content_main );
        NavigationUI.setupActionBarWithNavController ( this , navController , mAppBarConfiguration );
        NavigationUI.setupWithNavController ( navigationView , navController );

        View nav_header = navigationView.getHeaderView ( 0 );

        TextView tvName = nav_header.findViewById ( R.id.NavHeadingNameTextView );
        TextView tvPosition = nav_header.findViewById ( R.id.NavHeadingPositionTextView );
        String name = AuthorizationEmployee.surname + ' ' + AuthorizationEmployee.firstName + ' ' + AuthorizationEmployee.patronymic;
        tvName.setText ( name );
        tvPosition.setText ( AuthorizationEmployee.position );

    }

    @Override
    public boolean onSupportNavigateUp () {
        NavController navController = Navigation.findNavController ( this , R.id.nav_host_fragment_content_main );
        return NavigationUI.navigateUp ( navController , mAppBarConfiguration )
                || super.onSupportNavigateUp ( );
    }
}