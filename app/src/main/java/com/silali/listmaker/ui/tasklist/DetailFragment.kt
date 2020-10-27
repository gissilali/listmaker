package com.silali.listmaker.ui.tasklist

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.silali.listmaker.R
import com.silali.listmaker.data.TaskListDatabase
import com.silali.listmaker.data.model.TaskList
import com.silali.listmaker.data.repository.TaskListRepository
import com.silali.listmaker.data.viewmodel.TaskListViewModel
import com.silali.listmaker.data.viewmodel.TaskListViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class DetailFragment : Fragment() {
    private lateinit var todoListRecyclerView : RecyclerView
    private lateinit var taskListViewModel : TaskListViewModel
    private lateinit var repository: TaskListRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = TaskListDatabase.getInstance(requireContext())!!.taskListDao
        repository = TaskListRepository(dao)

        val factory = TaskListViewModelFactory(repository)

        taskListViewModel = ViewModelProvider(this, factory).get(TaskListViewModel::class.java)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {bundle ->
            val args = DetailFragmentArgs.fromBundle(bundle)
            taskListViewModel.getList(args.listId.toInt())
        }

        taskListViewModel.currentTaskList.observe(viewLifecycleOwner, Observer {taskList ->
            activity?.let {
                it.toolbar.title =  taskList?.title

            }
        })



    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    companion object {
    }

    private fun showAddTaskDialog(view: View) {
        val dialogTitle = "New Task"
        val positiveButtonLabel = getString(R.string.positive_button_label)
        activity?.let {
            val todoDialog = AlertDialog.Builder(it)
            val todoTextInput = EditText(it)
            todoTextInput.inputType = InputType.TYPE_CLASS_TEXT
            todoDialog.setTitle(dialogTitle)
            todoDialog.setView(todoTextInput)
            todoDialog.setPositiveButton(positiveButtonLabel) {
                    dialog, _ ->
                val adapter = todoListRecyclerView.adapter  as TaskAdapter
                if (todoTextInput.text.toString().isBlank()) {
                    dialog.dismiss()
                    Snackbar.make(view, "Need that list name.", Snackbar.LENGTH_SHORT).show()
                } else {
//                    taskList.tasks.add(todoTextInput.text.toString())
//                    listDataManager.saveList(taskList)
                    dialog.dismiss()
                }
            }

            todoDialog.create().show()
        }

    }
}