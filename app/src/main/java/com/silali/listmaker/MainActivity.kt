package com.silali.listmaker

import android.os.Bundle
import android.text.InputType
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var todoListRecyclerView : RecyclerView
    private val todoList = arrayOf("Android Development", "House Work", "Errands")
    val listDataManager = ListDataManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        todoListRecyclerView = findViewById(R.id.todo_list_recycler_view)
        todoListRecyclerView.layoutManager = LinearLayoutManager(this)
        todoListRecyclerView.adapter = TodoListAdapter(listDataManager.readList())
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { _ ->
            showCreateListDialog()
        }
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

    fun showCreateListDialog() {
        val dialogTitle = getString(R.string.todo_dialog_title)
        val positiveButtonLabel = getString(R.string.positive_button_label)
        val todoDialog = AlertDialog.Builder(this)
        val todoTextInput = EditText(this)
        todoTextInput.inputType = InputType.TYPE_CLASS_TEXT
        todoDialog.setTitle(dialogTitle)
        todoDialog.setView(todoTextInput)
        todoDialog.setPositiveButton(positiveButtonLabel) {
            dialog, _ ->
                val adapter = todoListRecyclerView.adapter  as TodoListAdapter
            val list = TaskList(todoTextInput.text.toString())
                listDataManager.saveList(list)
                adapter.addList(list)
                dialog.dismiss()
        }

        todoDialog.create().show()
    }
}