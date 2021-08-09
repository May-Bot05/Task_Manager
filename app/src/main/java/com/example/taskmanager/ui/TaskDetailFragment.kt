package com.example.taskmanager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.*
import com.example.taskmanager.data.PriorityLevel
import com.example.taskmanager.data.Task
import com.example.taskmanager.data.TaskStatus
import kotlinx.android.synthetic.main.fragment_task_detail.*

class TaskDetailFragment : Fragment() {

    private lateinit var viewModel: TaskDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(TaskDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val priorities = mutableListOf<String>()
        PriorityLevel.values().forEach { priorities.add(it.name)}
        val arrayAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, priorities)
        task_priority_task_detail.adapter = arrayAdapter

        task_priority_task_detail?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateTaskPriorityView(position)
            }
        }
        val id = TaskDetailFragmentArgs.fromBundle(requireArguments()).id
        viewModel.setTaskId(id)

        viewModel.task.observe(viewLifecycleOwner, Observer {
            it?.let{ setData(it) }
        })

        save_task.setOnClickListener {
            saveTask()
        }

        delete_task.setOnClickListener {
            deleteTask()
        }
    }

    private fun setData(task: Task){
        updateTaskPriorityView(task.taskPriority)
        task_title_task_detail.setText(task.title)
        task_detail_task_detail.setText(task.detail)
        if(task.status == TaskStatus.Open.ordinal){
            status_open.isChecked = true
        } else{
            status_closed.isChecked = true
        }
        task_priority_task_detail.setSelection(task.taskPriority)

    }

    private fun saveTask(){
        val title = task_title_task_detail.text.toString()
        val detail = task_detail_task_detail.text.toString()
        val priority = task_priority_task_detail.selectedItemPosition

        val selectedStatusButton =  status_group.findViewById<RadioButton>(status_group.checkedRadioButtonId)
        var status = TaskStatus.Open.ordinal
        if(selectedStatusButton.text == TaskStatus.Closed.name){
            status = TaskStatus.Closed.ordinal
        }
        val task = Task(viewModel.taskId.value!!, title, detail, priority, status)
        viewModel.saveTask(task)

        requireActivity().onBackPressed()
    }

    private fun deleteTask(){
        viewModel.deleteTask()

        requireActivity().onBackPressed()
    }

    private fun updateTaskPriorityView(priority: Int){
        when(priority){
            PriorityLevel.High.ordinal ->{
                task_priority_view.setBackgroundColor(
                    ContextCompat.getColor(requireActivity(),
                        R.color.colorPriorityHigh
                    ))
            }
            PriorityLevel.Medium.ordinal ->{
                task_priority_view.setBackgroundColor(
                    ContextCompat.getColor(requireActivity(),
                        R.color.colorPriorityMedium
                    ))
            }
            else ->  task_priority_view.setBackgroundColor(
                ContextCompat.getColor(requireActivity(),
                    R.color.colorPriorityLow
                ))
        }
    }
}

