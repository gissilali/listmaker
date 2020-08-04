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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.silali.listmaker.ListDataManager
import com.silali.listmaker.R
import com.silali.listmaker.TaskList
import com.silali.listmaker.data.TaskListDatabase
import com.silali.listmaker.data.repository.TaskListRepository
import com.silali.listmaker.data.viewmodel.TaskListViewModel
import com.silali.listmaker.data.viewmodel.TaskListViewModelFactory
import com.silali.listmaker.databinding.FragmentTaskListBinding
import kotlinx.android.synthetic.main.fragment_task_list.*


class TaskListFragment : Fragment(), TaskListAdapter.TodoListClickListener,
    ItemInputBottomSheetDialog.BottomSheetDialogClickListener {
    private lateinit var todoListRecyclerView: RecyclerView
    private lateinit var listDataManager: ListDataManager
    private lateinit var currentView: View
    private var binding: FragmentTaskListBinding? = null
    private lateinit var taskListViewModel : TaskListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
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
        displayTaskLists(view)
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

    private fun displayTaskLists(view: View) {
        taskListViewModel.taskLists.observe(viewLifecycleOwner, Observer {
//            todoListRecyclerView = view.findViewById(R.id.todo_list_recycler_view)
//            activity?.let {
//                todoListRecyclerView.layoutManager = LinearLayoutManager(it)
//            }
//
//            todoListRecyclerView.adapter =
//                TaskListAdapter(
//                    listDataManager.readList(),
//                    this
//                )
            Log.i("TASKS", it.toString())
        })
    }

    companion object;

    override fun itemClicked(list: TaskList) {
        showTaskListItems(list)
    }

    private fun showTaskListItems(list: TaskList) {
        val action = TaskListFragmentDirections.actionTaskListFragmentToDetailFragment(list.name)
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
            val list = com.silali.listmaker.data.model.TaskList(0, taskTitle, descriptionInput.text.toString(), ArrayList(), null, null)
            taskListViewModel.add(list)
            dialog.dismiss()
//        }
        }
    }
}