package com.silali.listmaker

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_task_list.*


class TaskListFragment : Fragment(), TaskListAdapter.TodoListClickListener, ItemInputBottomSheetDialog.BottomSheetDialogClickListener {
    private lateinit var todoListRecyclerView : RecyclerView
    private lateinit var listDataManager : ListDataManager
    private lateinit var currentView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todoListRecyclerView = view.findViewById(R.id.todo_list_recycler_view)
        activity?.let {
            todoListRecyclerView.layoutManager = LinearLayoutManager(it)
        }
        activity?.let {
            listDataManager = ViewModelProviders.of(this).get(ListDataManager::class.java)
        }

        todoListRecyclerView.adapter = TaskListAdapter(listDataManager.readList(),this)
        currentView = view
        fab.setOnClickListener { view ->
            showCreateListDialog(view)
        }

    }

    private fun showCreateListDialog(view: View) {
        parentFragmentManager?.let {
            val bottomSheetDialog = ItemInputBottomSheetDialog(this)
            bottomSheetDialog.show(it, "TAG")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TaskListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun itemClicked(list: TaskList) {
        showTaskListItems(list)
    }

    fun addList(list: TaskList) {
        listDataManager.saveList(list)
        val adapter = todoListRecyclerView.adapter  as TaskListAdapter
        adapter.addList(list)
    }

    fun saveList(list: TaskList) {
        listDataManager.saveList(list)
        updateList()
    }

    private fun updateList() {
        val lists = listDataManager.readList()
        todoListRecyclerView.adapter = TaskListAdapter(lists, this)
    }

    private fun showTaskListItems(list: TaskList) {
        val action = TaskListFragmentDirections.actionTaskListFragmentToDetailFragment(list.name)
        findNavController().navigate(action)
    }

    override fun submitForm(view: View, dialog: ItemInputBottomSheetDialog) {
        val textInput = view.findViewById<EditText>(R.id.text_input)
        val taskName = textInput.text.toString();
        if (taskName.isBlank()) {
            Snackbar.make(currentView, "Need that list name.", Snackbar.LENGTH_SHORT).show()
            dialog.dismiss()
        } else {
            val list = TaskList(taskName)
            addList(list)
            showTaskListItems(list);
            dialog.dismiss()
        }
    }
}