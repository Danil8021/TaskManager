package com.example.tm.ui.task.add;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tm.R;
import com.example.tm.task.Task;
import com.example.tm.task.TaskAdapter;

import java.text.DateFormat;
import java.util.Date;

public class AddTaskFragment extends Fragment {

    private AddTaskViewModel vm;

    public static boolean Add;

    @Override
    public View onCreateView ( LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState ) {
        View view = inflater.inflate ( R.layout.fragment_add_task , container , false );

        vm = new ViewModelProvider ( this ).get ( AddTaskViewModel.class );

        EditText headingET = view.findViewById(R.id.heading_edit_text);
        EditText descriptionET = view.findViewById(R.id.description_edit_text);
        EditText dateET = view.findViewById(R.id.date_edit_text);
        Date date = new Date();
        dateET.setText( DateFormat.getDateInstance(DateFormat.SHORT).format(date));
        if ( !Add ) {
            Task task = TaskAdapter.Task;
            headingET.setText(task.heading);
            descriptionET.setText(task.description);
            dateET.setText(task.date);
        }
        Button addBtn = view.findViewById(R.id.addTaskBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String heading = headingET.getText().toString().trim();
                String description = descriptionET.getText().toString().trim();
                String date = dateET.getText().toString();
                if (Add){
                    if (vm.addTask ( heading, description, date )){
                        Navigation.findNavController ( view ).navigateUp ();
                    }
                    else
                        Toast.makeText ( getContext () , R.string.not_all_fields_are_filled_in , Toast.LENGTH_SHORT ).show ( );
                }
                else{
                    if (vm.updateTask ( TaskAdapter.Task, heading, description, date )){
                        Navigation.findNavController ( view ).navigateUp ();
                    }
                    else
                        Toast.makeText ( getContext ( ) , R.string.not_all_fields_are_filled_in , Toast.LENGTH_SHORT ).show ( );
                }
            }
        });
        return view;
    }
}