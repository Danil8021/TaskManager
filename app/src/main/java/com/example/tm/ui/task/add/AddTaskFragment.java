package com.example.tm.ui.task.add;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.tm.R;
import com.example.tm.employee.EmployeeAdapter;
import com.example.tm.task.Task;
import com.example.tm.task.TaskAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
                if (heading.isEmpty () || description.isEmpty () || date.isEmpty ()){
                    Toast.makeText ( getContext () , R.string.not_all_fields_are_filled_in , Toast.LENGTH_SHORT ).show ( );
                    return;
                }
                DatabaseReference tasks = vm.getTasksRef ();
                String key = tasks.push ().getKey ();

                String idEmployee;
                if (vm.bAdd){
                    idEmployee = EmployeeAdapter.Employee.id;
                }
                else {
                    idEmployee = vm.getAuth ().getUid ();
                }
                if (Add){
                    Task task = new Task();
                    task.heading = heading;
                    task.description = description;
                    task.date = date;
                    task.done = false;
                    task.idEmployee = idEmployee;
                    task.id = key;

                    tasks.child ( key )
                            .setValue ( task )
                            .addOnSuccessListener ( new OnSuccessListener<Void> ( ) {
                                @Override
                                public void onSuccess ( Void unused ) {
                                    Navigation.findNavController ( view ).navigateUp ();
                                }
                            } )
                            .addOnFailureListener ( new OnFailureListener ( ) {
                                @Override
                                public void onFailure ( @NonNull Exception e ) {
                                    Toast.makeText ( getContext () , e.getMessage () , Toast.LENGTH_SHORT ).show ( );
                                }
                            } );;
                }
                else{
                        Map<String,Object> taskMap = new HashMap<String,Object> ();
                        taskMap.put("heading", heading);
                        taskMap.put("description",description);
                        taskMap.put("date",date);
                        tasks.child ( TaskAdapter.Task.id )
                                .updateChildren ( taskMap )
                                .addOnSuccessListener ( new OnSuccessListener<Void> ( ) {
                                    @Override
                                    public void onSuccess ( Void unused ) {
                                        Navigation.findNavController ( view ).navigateUp ();
                                    }
                                } )
                                .addOnFailureListener ( new OnFailureListener ( ) {
                                    @Override
                                    public void onFailure ( @NonNull Exception e ) {
                                        Toast.makeText ( getContext () , e.getMessage () , Toast.LENGTH_SHORT ).show ( );
                                    }
                                } );
                }
            }
        });
        return view;
    }
}