package com.example.tm.ui.employee.add;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.tm.MainActivity;
import com.example.tm.R;
import com.example.tm.employee.Employee;
import com.example.tm.task.Task;
import com.example.tm.task.TaskAdapter;
import com.example.tm.ui.task.add.AddTaskFragment;
import com.example.tm.ui.task.add.AddTaskViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddEmployeeFragment extends Fragment implements TaskAdapter.ReplaceFragmentTaskAdapter {

    private AddEmployeeViewModel vm;

    public static boolean Add;
    public static Employee employee;
    TaskAdapter adapter;
    ArrayList<Task> taskArrayList;
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
            loginET.setEnabled ( false );
            passwordET.setEnabled ( false );
//            if (bEmployeeAdapter)
//                employee = EmployeeAdapter.Employee;
//            else
//                employee = StatisticsAdapter.Employee;
            surnameET.setText ( employee.surname );
            firstNameET.setText ( employee.firstName );
            patronymicET.setText ( employee.patronymic );
            positionET.setText ( employee.position );
            loginET.setText ( employee.login );
            passwordET.setText ( employee.password );

            context = getActivity ();
            replaceFragmentTaskAdapter = this;
            TaskAdapter.checkEnable = false;
            vm.getTasksRef ()
                    .orderByChild ( "idEmployee" )
                    .equalTo ( employee.id )
                    .addValueEventListener ( new ValueEventListener ( ) {
                        @Override
                        public void onDataChange ( @NonNull DataSnapshot snapshot ) {
                            taskArrayList = new ArrayList<> (  );
                            for (DataSnapshot task: snapshot.getChildren()) {
                                Task newTask = task.getValue ( Task.class );
                                if (newTask != null)
                                    taskArrayList.add ( newTask );
                            }
                            if (taskArrayList.size () > 0) {
                                List<Task> t = taskArrayList;
                                Collections.sort ( t , new Comparator<Task> ( ) {
                                    @Override
                                    public int compare ( Task o1 , Task o2 ) {
                                        int c = Boolean.compare(o1.done,o2.done);
                                        if (c == 0){
                                            c = o1.getDate ().compareTo ( o2.getDate () );
                                        }
                                        return c;
                                    }
                                } );
                                adapter = new TaskAdapter ( context , R.layout.item_task_list , t, vm.getAuth ().getCurrentUser ().getUid () );
                                adapter.setReplaceFragmentTaskAdapter (replaceFragmentTaskAdapter);
                                taskListView.setAdapter ( adapter );
                            }
                        }

                        @Override
                        public void onCancelled ( @NonNull DatabaseError e ) {
                            Toast.makeText ( getContext () , e.getMessage () , Toast.LENGTH_SHORT ).show ( );
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

                String surname = surnameET.getText ().toString ().trim ();
                String firstName = firstNameET.getText ().toString ().trim ();
                String patronymic = patronymicET.getText ().toString ().trim ();
                String position = positionET.getText ().toString ().trim ();
                String login = loginET.getText ().toString ().trim ();
                String password = passwordET.getText ().toString ().trim ();

                if (surname.isEmpty () || firstName.isEmpty () || patronymic.isEmpty () || position.isEmpty () || login.isEmpty () || password.isEmpty ()){
                    Toast.makeText(getContext (), R.string.not_all_fields_are_filled_in,Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Add){
                    Employee newEmployee = new Employee (  );
                    newEmployee.login = login;
                    newEmployee.password = password;
                    newEmployee.surname = surname;
                    newEmployee.firstName = firstName;
                    newEmployee.patronymic = patronymic;
                    newEmployee.isDirector = false;
                    newEmployee.position = position;
                    vm.getAuth ().createUserWithEmailAndPassword ( login, password )
                            .addOnSuccessListener ( new OnSuccessListener<AuthResult> ( ) {
                                @Override
                                public void onSuccess ( AuthResult authResult ) {
                                    newEmployee.directorId = MainActivity.AuthorizationEmployee.id;
                                    newEmployee.id = vm.getAuth ().getUid ();
                                    vm.getEmployeesRef ()
                                            .child ( newEmployee.id )
                                            .setValue ( newEmployee )
                                            .addOnSuccessListener ( new OnSuccessListener<Void> ( ) {
                                                @Override
                                                public void onSuccess ( Void unused ) {
                                                    Toast.makeText ( getContext ( ) , getString( R.string.employee_added) , Toast.LENGTH_SHORT ).show ( );
                                                    Navigation.findNavController ( view ).navigateUp ();
                                                }
                                            } )
                                            .addOnFailureListener ( new OnFailureListener ( ) {
                                                @Override
                                                public void onFailure ( @NonNull Exception e ) {
                                                    Toast.makeText ( getContext ( ) , e.getMessage () , Toast.LENGTH_SHORT ).show ( );
                                                }
                                            } );
                                }
                            } )
                            .addOnFailureListener ( new OnFailureListener ( ) {
                                @Override
                                public void onFailure ( @NonNull Exception e ) {
                                    Toast.makeText ( getContext ( ) , e.getMessage () , Toast.LENGTH_SHORT ).show ( );
                                }
                            } );
                    vm.getAuth ().signInWithEmailAndPassword ( MainActivity.AuthorizationEmployee.login, MainActivity.AuthorizationEmployee.password );
                }
                else {
                    Map<String,Object> taskMap = new HashMap<String,Object> ();
                    taskMap.put("login", login);
                    taskMap.put("password",password);
                    taskMap.put("surname",surname);
                    taskMap.put("firstName",firstName);
                    taskMap.put("patronymic",patronymic);
                    taskMap.put("position",position);

                    vm.getEmployeesRef ()
                            .child ( employee.id )
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
                    vm.getAuth ().signInWithEmailAndPassword ( MainActivity.AuthorizationEmployee.login, MainActivity.AuthorizationEmployee.password );
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