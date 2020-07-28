package com.silali.listmaker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class DetailFragment : Fragment() {
    lateinit var taskList: TaskList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            taskList = it.getParcelable(ARG_LIST)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    companion object {
        private const val ARG_LIST = "list"
        fun newInstance(list: TaskList): DetailFragment {
            val bundle : Bundle = Bundle()
            bundle.putParcelable(ARG_LIST, list)
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}