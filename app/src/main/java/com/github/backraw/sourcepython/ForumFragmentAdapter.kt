package com.github.backraw.sourcepython


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import com.github.backraw.sourcepython.models.Forum
import com.github.backraw.sourcepython.models.Forums
import com.github.backraw.sourcepython.models.SubForum


class ForumFragmentAdapter(context: Context, resource: Int, private val forum: Forum)
    : ArrayAdapter<SubForum>(context, resource) {


    private var layoutInflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    fun refreshData() {
        clear()
        addAll(forum.subForums)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = if (convertView == null)
            layoutInflater.inflate(
                R.layout.fragment_forum_item, parent, false
            ) else
                convertView

        val subForum: SubForum = getItem(position)

        val textTitle: TextView = view.findViewById(R.id.text_forum_title)
        textTitle.text = subForum.title

        return view
    }

}
