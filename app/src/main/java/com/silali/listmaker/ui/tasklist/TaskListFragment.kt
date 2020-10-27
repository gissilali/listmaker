package com.silali.listmaker.ui.tasklist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.silali.listmaker.R
import com.silali.listmaker.data.TaskListDatabase
import com.silali.listmaker.data.model.TaskList
import com.silali.listmaker.data.repository.TaskListRepository
import com.silali.listmaker.data.viewmodel.TaskListViewModel
import com.silali.listmaker.data.viewmodel.TaskListViewModelFactory
import com.silali.listmaker.databinding.FragmentTaskListBinding
import kotlinx.android.synthetic.main.fragment_task_list.*


class TaskListFragment : Fragment(), TaskListAdapter.TodoListClickListener,
    ItemInputBottomSheetDialog.BottomSheetDialogClickListener {
    private lateinit var currentView: View
    private var binding: FragmentTaskListBinding? = null
    private lateinit var taskListViewModel : TaskListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao = TaskListDatabase.getInstance(requireContext())!!.taskListDao
        val repository = TaskListRepository(dao)
        val factory = TaskListViewModelFactory(repository)

        taskListViewModel = ViewModelProvider(this, factory).get(TaskListViewModel::class.java)
        currentView = view
        fab.setOnClickListener { view ->
            showCreateListDialog(view)
        }
        initRecyclerView()
    }

    private fun showCreateListDialog(view: View) {
        parentFragmentManager.let {
            val bottomSheetDialog = ItemInputBottomSheetDialog(this)
            bottomSheetDialog.show(it, "TAG")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initRecyclerView() {
        binding!!.todoListRecyclerView!!.layoutManager = LinearLayoutManager(context)
        displayTaskLists()
    }
    private fun displayTaskLists() {
        taskListViewModel.taskLists.observe(viewLifecycleOwner, Observer {
            binding?.todoListRecyclerView?.adapter = TaskListAdapter(it, this)
            Log.i("TASKS", it.toString())
        })
    }

    companion object;

    override fun itemClicked(list: TaskList) {
        showTaskListItems(list)
    }

    private fun showTaskListItems(list: TaskList) {
        val action = TaskListFragmentDirections.actionTaskListFragmentToDetailFragment(list.id.toString())
        findNavController().navigate(action)
    }

    override fun submitForm(view: View, dialog: ItemInputBottomSheetDialog) {
        val titleInput = view.findViewById<EditText>(R.id.list_title_input)
        val descriptionInput = view.findViewById<EditText>(R.id.list_description_input)
        val taskTitle = titleInput.text.toString()
        if (taskTitle.isBlank()) {
            Snackbar.make(currentView, "Need that list name.", Snackbar.LENGTH_SHORT).show()
            dialog.dismiss()
        } else {
            val list = TaskList(0, taskTitle, descriptionInput.text.toString(), ArrayList(), null, null)
            taskListViewModel.add(list)
            dialog.dismiss()
        }
    }
}