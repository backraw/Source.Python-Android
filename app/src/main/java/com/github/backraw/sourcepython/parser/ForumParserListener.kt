package com.github.backraw.sourcepython.parser


import com.github.backraw.sourcepython.models.Forum


interface ForumParserListener {
    fun onParsingFinished(forums: ArrayList<Forum>)
}
