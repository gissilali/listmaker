package com.silali.listmaker.ui.tasklist

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialogFragment
import com.silali.listmaker.R
import com.silali.listmaker.databinding.RowAddItemBinding


class ItemInputBottomSheetDialog(private val clickListener: BottomSheetDialogClickListener) : RoundedBottomSheetDialogFragment() {
    private var binding: RowAddItemBinding? = null

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
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.row_add_item, null, false
        )

        val saveButton = binding?.saveBtn

        binding?.taskListViewModel?.listTitle?.observe(this, Observer {
            saveButton?.isEnabled = it.isNotEmpty()
        })

        saveButton?.setOnClickListener {
            clickListener.submitForm(binding!!.root, this)
        }

        dialog?.let {
            it.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }

        return binding!!.root
    }
}