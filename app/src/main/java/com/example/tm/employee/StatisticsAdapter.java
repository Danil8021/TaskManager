package com.example.tm.employee;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tm.R;
import com.example.tm.Repository;
import com.example.tm.task.Task;
import com.example.tm.ui.employee.add.AddEmployeeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class StatisticsAdapter extends ArrayAdapter<Employee> {

    private LayoutInflater inflater;
    private int layout;
    private List<Employee> employees;
    Employee Employee;
    Application application;

    Repository repository;
    public void setContext( Application application ){
        this.application = application;
    }

    public interface ReplaceFragmentStatisticsAdapter {
        void itemClick(View view);
    }
    ReplaceFragmentStatisticsAdapter replaceFragmentStatisticsAdapter;

    public void setReplaceFragmentStatisticsAdapter ( ReplaceFragmentStatisticsAdapter replaceFragmentStatisticsAdapter ){
        this.replaceFragmentStatisticsAdapter = replaceFragmentStatisticsAdapter;
    }

    public StatisticsAdapter ( Context context , int resource ,  List<Employee> employees ) {
        super ( context , resource , employees );
        this.inflater = LayoutInflater.from(context);
        this.layout = resource;
        this.employees = employees;
        repository = new Repository ( application );
    }

    public View getView( int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);
        view.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                Employee = getItem(position);
                AddEmployeeFragment.Add = false;
                AddEmployeeFragment.employee = Employee;
                replaceFragmentStatisticsAdapter.itemClick ( view );
            }
        } );
        TextView nameTV = (TextView) view.findViewById( R.id.name_text_view);
        TextView positionTV = (TextView) view.findViewById( R.id.position_text_view);
        TextView completedTV = (TextView) view.findViewById( R.id.completed_text_view);
        TextView notCompletedTV = (TextView) view.findViewById( R.id.not_completed_text_view);
        Employee = employees.get(position);
        repository.getTasksRef ()
                .orderByChild ( "idEmployee" )
                .equalTo ( Employee.id )
                .addValueEventListener ( new ValueEventListener ( ) {
                    @Override
                    public void onDataChange ( @NonNull DataSnapshot snapshot ) {
                        int completed = 0;
                        int notCompleted = 0;
                        for (DataSnapshot task : snapshot.getChildren ( )) {
                            Task newTask = task.getValue ( Task.class );
                            if (newTask != null){
                                if (newTask.done)
                                    completed++;
                                else
                                    notCompleted++;
                            }
                        }
                        completedTV.setText ( application.getString( R.string.completed) + completed );
                        notCompletedTV.setText ( application.getString( R.string.notCompleted) + notCompleted );
                    }
                    @Override
                    public void onCancelled ( @NonNull DatabaseError e ) {
                        Toast.makeText ( getContext () , e.getMessage () , Toast.LENGTH_SHORT ).show ( );
                    }
                });
        nameTV.setText( Employee.surname + " " + Employee.firstName + " " + Employee.patronymic);
        positionTV.setText( Employee.position);
        return view;
    }
}
