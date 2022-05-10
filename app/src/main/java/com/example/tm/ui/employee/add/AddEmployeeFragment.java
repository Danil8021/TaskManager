package com.example.tm.ui.employee.add;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tm.MainActivity;
import com.example.tm.employee.Employee;
import com.example.tm.employee.EmployeeAdapter;
import com.example.tm.R;
import com.example.tm.task.Task;
import com.example.tm.task.TaskAdapter;
import com.example.tm.ui.task.add.AddTaskFragment;
import com.example.tm.ui.task.add.AddTaskViewModel;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEmployeeFragment extends Fragment implements TaskAdapter.ReplaceFragmentTaskAdapter {

    private AddEmployeeViewModel vm;

    public static boolean Add;

    Employee employee;

    TaskAdapter taskAdapter;

    Context context;

    TaskAdapter.ReplaceFragmentTaskAdapter replaceFragmentTaskAdapter;

    @Override
    public View onCreateView ( LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState ) {
        View view = inflater.inflate ( R.layout.fragment_add_employee , container , false );

        vm = new ViewModelProvider ( this ).get ( AddEmployeeViewModel.class );

        EditText surnameET = (EditText) view.findViewById(R.id.surname_edit_text);
        EditText firstNameET = (EditText) view.findViewById(R.id.first_name_edit_text);
        EditText patronymicET = (EditText) view.findViewById(R.id.patronymic_edit_text);
        EditText positionET = (EditText) view.findViewById(R.id.position_edit_text);
        EditText loginET = (EditText) view.findViewById(R.id.login_edit_text);
        EditText passwordET = (EditText) view.findViewById(R.id.password_edit_text);
        ListView taskListView = (ListView ) view.findViewById ( R.id.taskListView );

        if (!Add) {
            employee = EmployeeAdapter.Employee;
            surnameET.setText ( employee.surname );
            firstNameET.setText ( employee.firstName );
            patronymicET.setText ( employee.patronymic );
            positionET.setText ( employee.position );
            loginET.setText ( employee.login );
            passwordET.setText ( employee.password );

            context = getActivity ();

            replaceFragmentTaskAdapter = this;
            vm.getTaskList ( ).observe ( ( LifecycleOwner ) context , new Observer<List<Task>> ( ) {
                        @Override
                        public void onChanged ( List<Task> tasks ) {
                            if (!tasks.isEmpty ( )) {
                                TaskAdapter.checkEnable = false;
                                taskAdapter = new TaskAdapter ( context   , R.layout.item_task_list , tasks );
                                taskAdapter.setReplaceFragmentTaskAdapter ( replaceFragmentTaskAdapter );
                                taskAdapter.setContext ( vm.getApplication ( ) );
                                taskListView.setAdapter ( taskAdapter );
                            }
                        }
                    } );
        }
        Button addBtn = (Button)view.findViewById(R.id.addEmployeeBtn);
        if (!MainActivity.AuthorizationEmployee.isDirector) {
            surnameET.setEnabled ( false );
            firstNameET.setEnabled ( false );
            patronymicET.setEnabled ( false );
            positionET.setEnabled ( false );
            loginET.setVisibility ( View.GONE );
            passwordET.setVisibility ( View.GONE );
            addBtn.setVisibility ( View.GONE );
        }
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Add){
                    if (vm.addEmployee ( surnameET.getText ().toString (), firstNameET.getText ().toString (), patronymicET.getText ().toString (), positionET.getText ().toString (), loginET.getText ().toString (), passwordET.getText ().toString ())){
                        Navigation.findNavController ( view ).navigateUp ();
                    }
                    else {
                        Toast.makeText(getContext (), R.string.not_all_fields_are_filled_in,Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if (vm.updateEmployee ( employee, surnameET.getText ( ).toString ( ) , firstNameET.getText ( ).toString ( ) , patronymicET.getText ( ).toString ( ) , positionET.getText ( ).toString ( ) , loginET.getText ( ).toString ( ) , passwordET.getText ( ).toString ( ) )) {
                        Navigation.findNavController ( view ).navigateUp ();
                    } else {
                        Toast.makeText ( getContext ( ) , R.string.not_all_fields_are_filled_in , Toast.LENGTH_SHORT ).show ( );
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onClick ( View view ) {
        AddTaskFragment.Add = false;
        AddTaskViewModel.bAdd = true;
        Navigation.findNavController ( view ).navigate (R.id.action_addEmployeeFragment_to_addTaskFragment);
    }
}