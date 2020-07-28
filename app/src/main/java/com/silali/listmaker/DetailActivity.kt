package com.silali.listmaker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    lateinit var list: TaskList

    lateinit var tasksRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        list = intent.getParcelableExtra<TaskList>(MainActivity.INTENT_LIST_KEY) as TaskList
        title = list.name

        tasksRecyclerView = findViewById(R.id.tasks_recycler_view)
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        tasksRecyclerView.adapter = TaskAdapter(list)

        task_fab.setOnClickListener {
            view ->
                showAddTaskDialog(view)
        }
    }

    override fun onBackPressed() {
        val bundle = Bundle()
        bundle.putParcelable(MainActivity.INTENT_LIST_KEY, list)
        val intent = Intent()
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }

    private fun showAddTaskDialog(view: View) {
        val dialogTitle = "New Task"
        val positiveButtonLabel = getString(R.string.positive_button_label)
        val todoDialog = AlertDialog.Builder(this)
        val todoTextInput = EditText(this)
        todoTextInput.inputType = InputType.TYPE_CLASS_TEXT
        todoDialog.setTitle(dialogTitle)
        todoDialog.setView(todoTextInput)
        todoDialog.setPositiveButton(positiveButtonLabel) {
                dialog, _ ->
            val adapter = tasksRecyclerView.adapter  as TaskAdapter
            if (todoTextInput.text.toString().isBlank()) {
                dialog.dismiss()
                Snackbar.make(view, "Need that list name.", Snackbar.LENGTH_SHORT).show()
            } else {
                list.tasks.add(todoTextInput.text.toString())

                dialog.dismiss()
            }
        }

        todoDialog.create().show()
    }
}