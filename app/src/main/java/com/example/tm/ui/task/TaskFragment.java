package com.example.tm.ui.task;

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
import com.example.tm.databinding.FragmentTaskBinding;
import com.example.tm.task.Task;
import com.example.tm.task.TaskAdapter;
import com.example.tm.ui.task.add.AddTaskFragment;
import com.example.tm.ui.task.add.AddTaskViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskFragment extends Fragment implements TaskAdapter.ReplaceFragmentTaskAdapter {

    private FragmentTaskBinding binding;

    ListView taskListView;
    TaskAdapter adapter;
    ArrayList<Task> taskArrayList;
    Context context;
    TaskViewModel vm;

    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        binding = FragmentTaskBinding.inflate(inflater, container, false);

        vm = new ViewModelProvider(this).get(TaskViewModel.class);

        View root = binding.getRoot();
        TextView messageTextView = root.findViewById( R.id.messageTextView);
        taskListView = root.findViewById(R.id.tasksListView);

        context = getActivity ();

        TaskAdapter.ReplaceFragmentTaskAdapter replaceFragmentTaskAdapter = this;
        TaskAdapter.checkEnable = true;

        vm.getTasksRef ()
                .orderByChild ( "idEmployee" )
                .equalTo ( vm.getAuth ().getCurrentUser ().getUid () )
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
                        else
                            messageTextView.setVisibility ( View.VISIBLE );
                    }

                    @Override
                    public void onCancelled ( @NonNull DatabaseError e ) {
                        Toast.makeText ( getContext () , e.getMessage () , Toast.LENGTH_SHORT ).show ( );
                    }
                } );

        Button add = root.findViewById ( R.id.addTaskButton );
        if (MainActivity.AuthorizationEmployee.isDirector){
            add.setVisibility ( View.VISIBLE );
        }
        add.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                AddTaskFragment.Add = true;
                AddTaskViewModel.bAdd = false;
                Navigation.findNavController(view).navigate(R.id.action_nav_task_to_addTaskFragment);
            }
        } );

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick ( View view ) {
        AddTaskFragment.Add = false;
        AddTaskViewModel.bAdd = false;
        Navigation.findNavController(view).navigate(R.id.action_nav_task_to_addTaskFragment);
    }
}