package com.example.tm.ui.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tm.MainActivity;
import com.example.tm.R;
import com.example.tm.databinding.FragmentSettingBinding;

import java.util.Collections;

public class SettingsFragment extends Fragment {

    private FragmentSettingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel vm = new ViewModelProvider(this).get( SettingsViewModel.class);

        binding = FragmentSettingBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        SwitchCompat themeSwitch = root.findViewById ( R.id.theme_switch );
        Spinner languageSpinner = root.findViewById ( R.id.language_spinner );

        if (vm.getTheme ().equals ( vm.DARK_THEME ))
            themeSwitch.setChecked ( true );
        else
            themeSwitch.setChecked ( false );

        themeSwitch.setOnCheckedChangeListener ( new CompoundButton.OnCheckedChangeListener ( ) {
            @Override
            public void onCheckedChanged ( CompoundButton compoundButton , boolean b ) {
                if (b)
                    vm.setTheme ( vm.DARK_THEME );
                else
                    vm.setTheme ( vm.WHITE_THEME );
                getActivity ().recreate ();
            }
        } );

        if (vm.getLanguage ( ).equals ( "en" ))
            languageSpinner.setSelection ( 0 );
        if (vm.getLanguage ( ).equals ( "ru" ))
            languageSpinner.setSelection ( 1 );

        languageSpinner.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener ( ) {
            @Override
            public void onItemSelected ( AdapterView<?> adapterView , View view , int i , long l ) {
                if ( vm.getSettingsPreferences ( ).getString ( vm.LANGUAGE , vm.EN_LANGUAGE ).equals ( "en" ) && languageSpinner.getSelectedItemPosition () == 0 ||vm.getSettingsPreferences ( ).getString ( vm.LANGUAGE , vm.EN_LANGUAGE ).equals ( "ru" ) && languageSpinner.getSelectedItemPosition () == 1  ){
                    return;
                }
                if (i == 0)
                    vm.setLanguage ( vm.EN_LANGUAGE );
                else
                    vm.setLanguage ( vm.RU_LANGUAGE );
                getActivity ().recreate ();
            }

            @Override
            public void onNothingSelected ( AdapterView<?> adapterView ) {

            }
        } );
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}