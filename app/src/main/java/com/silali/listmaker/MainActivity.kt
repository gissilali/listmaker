package com.silali.listmaker

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() , TaskListFragment.OnFragmentInteractionListener {
    private var taskListFragment : TaskListFragment = TaskListFragment.newInstance()

    companion object {
        const val INTENT_LIST_KEY = "taskList"
        const val LIST_DETAIL_REQUEST_CODE = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            showCreateListDialog(view)
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.task_list_fragment, taskListFragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LIST_DETAIL_REQUEST_CODE) {
            data?.let {
                val list = data.getParcelableExtra<TaskList>(INTENT_LIST_KEY)!!
                taskListFragment.saveList(list)
            }
        }
    }

    private fun showCreateListDialog(view: View) {
        val dialogTitle = getString(R.string.todo_dialog_title)
        val positiveButtonLabel = getString(R.string.positive_button_label)
        val todoDialog = AlertDialog.Builder(this)
        val todoTextInput = EditText(this)
        todoTextInput.inputType = InputType.TYPE_CLASS_TEXT
        todoDialog.setTitle(dialogTitle)
        todoDialog.setView(todoTextInput)
        todoDialog.setPositiveButton(positiveButtonLabel) {
            dialog, _ ->
                if (todoTextInput.text.toString().isBlank()) {
                    dialog.dismiss()
                    Snackbar.make(view, "Need that list name.", Snackbar.LENGTH_SHORT).show()
                } else {
                    val list = TaskList(todoTextInput.text.toString())
                    taskListFragment.addList(list)
                    dialog.dismiss()
                    showTaskListItems(list);
                }
        }

        todoDialog.create().show()
    }

    private fun showTaskListItems(list: TaskList) {
        val taskListItem = Intent(this, DetailActivity::class.java)
        taskListItem.putExtra(INTENT_LIST_KEY, list)
        startActivityForResult(taskListItem, LIST_DETAIL_REQUEST_CODE)
    }

    override fun onItemClicked(list: TaskList) {
        showTaskListItems(list)
    }
}