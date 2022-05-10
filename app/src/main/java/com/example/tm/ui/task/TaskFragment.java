package com.example.tm.ui.task;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.tm.MainActivity;
import com.example.tm.R;
import com.example.tm.task.Task;
import com.example.tm.task.TaskAdapter;
import com.example.tm.TaskRepository;
import com.example.tm.databinding.FragmentTaskBinding;
import com.example.tm.ui.task.add.AddTaskFragment;
import com.example.tm.ui.task.add.AddTaskViewModel;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskFragment extends Fragment implements TaskAdapter.ReplaceFragmentTaskAdapter {

    private FragmentTaskBinding binding;

    ListView taskListView;
    TaskAdapter adapter;
    Context context;

    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        binding = FragmentTaskBinding.inflate(inflater, container, false);

        TaskViewModel vm = new ViewModelProvider(this).get(TaskViewModel.class);

        View root = binding.getRoot();
        TextView messageTextView = root.findViewById( R.id.messageTextView);
        taskListView = root.findViewById(R.id.tasksListView);

        context = getActivity ();

        TaskAdapter.ReplaceFragmentTaskAdapter replaceFragmentTaskAdapter = this;
        TaskAdapter.checkEnable = true;
        vm.getTaskList ().observe ( ( LifecycleOwner ) context , new Observer<List<Task>> ( ) {
                    @Override
                    public void onChanged ( List<Task> tasks ) {
                        if(tasks.isEmpty ())
                            messageTextView.setVisibility ( View.VISIBLE );
                        else{
                            adapter = new TaskAdapter ( context, R.layout.item_task_list, tasks );
                            adapter.setReplaceFragmentTaskAdapter ( replaceFragmentTaskAdapter );
                            adapter.setContext ( vm.getApplication () );
                            taskListView.setAdapter ( adapter );
                        }
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