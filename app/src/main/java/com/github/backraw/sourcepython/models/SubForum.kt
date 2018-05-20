package com.github.backraw.sourcepython.models


import java.io.Serializable


class SubForum(val title: String, val href: String,
               var topics: ArrayList<Topic> = ArrayList())
    : Serializable