package com.github.backraw.sourcepython


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

import com.github.backraw.sourcepython.models.Forum


class ForumFragment
    : Fragment() {

    private lateinit var forum: Forum
    private lateinit var listView : ListView
    private lateinit var listViewAdapter: ForumFragmentAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            forum = it.getSerializable("forum") as Forum
        }

        Log.d("FRAG", "Initialized fragment for ${forum.title}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view : View = inflater.inflate(R.layout.fragment_forum, container, false)

        listView = view.findViewById(R.id.list_subforums)
        listViewAdapter = ForumFragmentAdapter(activity!!.baseContext, listView.id, forum)
        listViewAdapter.refreshData()

        listView.adapter = listViewAdapter
        return view
    }


    fun adapter(): ForumFragmentAdapter {
        return listViewAdapter
    }


    companion object {

        @JvmStatic
        fun newInstance(forum: Forum) =
                ForumFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable("forum", forum)
                    }
                }
    }
}
