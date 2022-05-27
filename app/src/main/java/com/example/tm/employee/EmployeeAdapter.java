package com.example.tm.employee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.tm.R;
import com.example.tm.MainActivity;
import com.example.tm.ui.employee.add.AddEmployeeFragment;
import com.example.tm.ui.task.add.AddTaskViewModel;

import java.util.List;

public class EmployeeAdapter extends ArrayAdapter<Employee> {
    private final LayoutInflater inflater;
    private final int layout;
    private final List<Employee> employees;
    public static Employee Employee;

    public interface ReplaceFragmentEmployeeAdapter{
        void btnAddClick(View view);
        void itemClick(View view);
    }
    ReplaceFragmentEmployeeAdapter replaceFragmentEmployeeAdapter;

    public EmployeeAdapter ( @NonNull Context context, int resource, List<Employee> employees) {
        super(context, resource, employees);
        this.inflater = LayoutInflater.from(context);
        this.layout = resource;
        this.employees = employees;
    }

    public void setReplaceFragmentEmployeeAdapter(ReplaceFragmentEmployeeAdapter replaceFragmentEmployeeAdapter){
        this.replaceFragmentEmployeeAdapter = replaceFragmentEmployeeAdapter;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);
        view.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                Employee = getItem(position);
                AddEmployeeFragment.Add = false;
                replaceFragmentEmployeeAdapter.itemClick ( view );
            }
        } );
        TextView nameTV = (TextView) view.findViewById( R.id.name_text_view);
        TextView positionTV = (TextView) view.findViewById( R.id.position_text_view);
        Button addBtn = (Button) view.findViewById( R.id.add_task_button);
        if (MainActivity.AuthorizationEmployee.isDirector){
            addBtn.setVisibility ( View.VISIBLE );
        }
        Employee = employees.get(position);
        nameTV.setText( Employee.surname + " " + Employee.firstName + " " + Employee.patronymic);
        positionTV.setText( Employee.position);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Employee = getItem(position);
                AddTaskViewModel.bAdd = true;
                replaceFragmentEmployeeAdapter.btnAddClick ( view );
            }
        });
        return view;
    }
}
