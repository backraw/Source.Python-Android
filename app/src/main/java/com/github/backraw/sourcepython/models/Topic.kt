package com.github.backraw.sourcepython.models


import java.io.Serializable


class Topic(val title: String, val autor: String, val href: String,
            var posts: ArrayList<Post> = ArrayList())
    : Serializable