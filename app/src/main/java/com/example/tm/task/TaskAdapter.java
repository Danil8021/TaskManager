package com.example.tm.task;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.tm.MainActivity;
import com.example.tm.R;
import com.example.tm.TaskRepository;
import com.example.tm.ui.employee.add.AddEmployeeFragment;
import com.example.tm.ui.task.add.AddTaskViewModel;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    private final LayoutInflater inflater;
    private final int layout;
    private final List<Task> tasks;
    public static com.example.tm.task.Task Task;

    TaskRepository taskRepository;
    Application application;

    public static boolean checkEnable = true;

    public interface ReplaceFragmentTaskAdapter{
        void onClick(View view);
    }
    ReplaceFragmentTaskAdapter replaceFragmentTaskAdapter;

    public TaskAdapter ( @NonNull Context context, int resource, List<Task> tasks) {
        super(context, resource, tasks);
        this.inflater = LayoutInflater.from(context);
        this.layout = resource;
        this.tasks = tasks;
        this.taskRepository = new TaskRepository ( application );
    }

    public void setContext( Application application ){
        this.application = application;
    }
    public void setReplaceFragmentTaskAdapter( ReplaceFragmentTaskAdapter replaceFragmentTaskAdapter ) { this.replaceFragmentTaskAdapter = replaceFragmentTaskAdapter; }

    public View getView( int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate ( this.layout , parent , false );
        if (MainActivity.AuthorizationEmployee.isDirector)
        view.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                Task = getItem(position);
                AddTaskViewModel.bAdd = false;
                replaceFragmentTaskAdapter.onClick ( view );
            }
        } );
        TextView headingTextView = ( TextView ) view.findViewById ( R.id.heading_text );
        CheckBox doneCheckBox = ( CheckBox ) view.findViewById ( R.id.done_check );
        TextView descriptionTextView = ( TextView ) view.findViewById ( R.id.description_text );
        TextView dateTextView = ( TextView ) view.findViewById ( R.id.date_text );
        Task = tasks.get ( position );
        headingTextView.setText ( Task.heading );
        descriptionTextView.setText ( Task.description );
        doneCheckBox.setChecked ( Task.done );
        dateTextView.setText ( Task.date );
        doneCheckBox.setTag ( Task );
        if (!MainActivity.AuthorizationEmployee.isDirector && !checkEnable)
            doneCheckBox.setEnabled ( false );
        else
            doneCheckBox.setEnabled ( true );
        doneCheckBox.setOnCheckedChangeListener ( new CompoundButton.OnCheckedChangeListener ( ) {
            @Override
            public void onCheckedChanged ( CompoundButton compoundButton , boolean b ) {
                Task = ( Task ) compoundButton.getTag ( );
                Task.done = b;
                taskRepository.update ( Task );
            }
        } );
        return view;
    }
}
