package com.example.tm.ui.employee;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tm.MainActivity;
import com.example.tm.employee.Employee;
import com.example.tm.employee.EmployeeAdapter;
import com.example.tm.R;
import com.example.tm.databinding.FragmentEmployeeBinding;
import com.example.tm.ui.employee.add.AddEmployeeFragment;
import com.example.tm.ui.task.add.AddTaskFragment;
import com.example.tm.ui.task.add.AddTaskViewModel;

import java.util.List;

public class EmployeeFragment extends Fragment implements EmployeeAdapter.ReplaceFragmentEmployeeAdapter {

    private FragmentEmployeeBinding binding;

    public EmployeeAdapter.ReplaceFragmentEmployeeAdapter replaceFragmentEmployeeAdapter;

    EmployeeAdapter adapter;

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

        vm.getEmployeeListWithoutDirector ().observe ( getActivity ( ) , new Observer<List<Employee>> ( ) {
            @Override
            public void onChanged ( List<Employee> employees ) {
                if (employees.isEmpty ())
                    messageTextView.setVisibility ( View.VISIBLE );
                else {
                    adapter = new EmployeeAdapter ( root.getContext (), R.layout.item_employee_list,employees );
                    adapter.setReplaceFragmentEmployeeAdapter ( replaceFragmentEmployeeAdapter );
                    employeeListView.setAdapter ( adapter );
                }
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