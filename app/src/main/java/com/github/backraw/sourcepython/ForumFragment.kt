package com.github.backraw.sourcepython


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.backraw.sourcepython.models.Forum


class ForumFragment
    : Fragment() {

    private var forum: Forum? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            forum = it.getSerializable("forum") as Forum
        }

        Log.d("FRAG", "Initialized fragment for ${forum?.title}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Set ListView items here

        return inflater.inflate(R.layout.fragment_forum, container, false)
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
