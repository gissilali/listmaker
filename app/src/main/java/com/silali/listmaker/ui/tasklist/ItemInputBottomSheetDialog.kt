package com.silali.listmaker.ui.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialogFragment
import com.silali.listmaker.R


class ItemInputBottomSheetDialog(private val clickListener: BottomSheetDialogClickListener) : RoundedBottomSheetDialogFragment() {

    interface BottomSheetDialogClickListener {
        fun submitForm(taskName: View, dialog: ItemInputBottomSheetDialog)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL,
            R.style.DialogStyle
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.row_add_item, container, false)
        val saveButton = view.findViewById<Button>(R.id.save_btn)
        saveButton.setOnClickListener {
            clickListener.submitForm(view, this)
        }

        dialog?.let {
            it.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }

        return view
    }
}