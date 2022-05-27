package com.example.tm.ui.employee;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.tm.MainActivity;
import com.example.tm.R;
import com.example.tm.databinding.FragmentEmployeeBinding;
import com.example.tm.employee.Employee;
import com.example.tm.employee.EmployeeAdapter;
import com.example.tm.ui.employee.add.AddEmployeeFragment;
import com.example.tm.ui.task.add.AddTaskFragment;
import com.example.tm.ui.task.add.AddTaskViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployeeFragment extends Fragment implements EmployeeAdapter.ReplaceFragmentEmployeeAdapter {

    private FragmentEmployeeBinding binding;

    public EmployeeAdapter.ReplaceFragmentEmployeeAdapter replaceFragmentEmployeeAdapter;

    ArrayList<Employee> employeeArrayList;
    EmployeeAdapter adapter;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentEmployeeBinding.inflate(inflater,container,false);

        EmployeeViewModel vm = new ViewModelProvider(this).get(EmployeeViewModel.class);

        View root = binding.getRoot();

        ListView employeeListView = root.findViewById ( R.id.employeesListView );
        TextView messageTextView = root.findViewById ( R.id.messageTextView );
        Button addEmployeeBtn = root.findViewById ( R.id.addEmployeeBtn );
        if (MainActivity.AuthorizationEmployee.isDirector){
            addEmployeeBtn.setVisibility ( View.VISIBLE );
        }
        addEmployeeBtn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                AddEmployeeFragment.Add = true;
                Navigation.findNavController(view).navigate(R.id.action_nav_employee_to_addEmployeeFragment);
            }
        } );

        replaceFragmentEmployeeAdapter = this;
        context = getActivity ();
        vm.getEmployeesRef ()
                .orderByChild ( "directorId" )
                .equalTo ( MainActivity.AuthorizationEmployee.directorId )
                .addValueEventListener ( new ValueEventListener ( ) {
                    @Override
                    public void onDataChange ( @NonNull DataSnapshot snapshot ) {
                        employeeArrayList = new ArrayList<> (  );
                        for (DataSnapshot emp: snapshot.getChildren()) {
                            Employee newEmployee = emp.getValue ( Employee.class );
                            if (newEmployee != null)
                                if (!newEmployee.id.equals ( MainActivity.AuthorizationEmployee.id ))
                                    employeeArrayList.add ( newEmployee );
                        }
                        if (employeeArrayList.size () > 0) {
                            List<Employee> e = employeeArrayList;
                            adapter = new EmployeeAdapter ( context , R.layout.item_employee_list , e );
                            adapter.setReplaceFragmentEmployeeAdapter (replaceFragmentEmployeeAdapter);
                            employeeListView.setAdapter ( adapter );
                        }
                        else
                            messageTextView.setVisibility ( View.VISIBLE );
                    }

                    @Override
                    public void onCancelled ( @NonNull DatabaseError e ) {
                        Toast.makeText ( getContext () , e.getMessage () , Toast.LENGTH_SHORT ).show ( );
                    }
                } );
        return root;
    }

    @Override
    public void btnAddClick (View view) {
        AddTaskFragment.Add = true;
        AddTaskViewModel.bAdd = true;
        Navigation.findNavController(view).navigate(R.id.action_nav_employee_to_addTaskFragment);
    }

    @Override
    public void itemClick ( View view ) {
        AddEmployeeFragment.Add = false;
        Navigation.findNavController(view).navigate(R.id.action_nav_employee_to_addEmployeeFragment);
    }
}