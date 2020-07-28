package com.silali.listmaker

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class TaskListFragment : Fragment(), TaskListAdapter.TodoListClickListener {
    var listener : OnFragmentInteractionListener? = null

    private lateinit var todoListRecyclerView : RecyclerView
    private lateinit var listDataManager : ListDataManager

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
        todoListRecyclerView.layoutManager = LinearLayoutManager(activity)
        todoListRecyclerView.adapter = TaskListAdapter(listDataManager.readList(),this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
            listDataManager = ListDataManager(context)
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

    interface OnFragmentInteractionListener {
        fun onItemClicked(list: TaskList)
    }

    override fun itemClicked(list: TaskList) {
        listener?.onItemClicked(list)
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
}