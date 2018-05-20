package com.github.backraw.sourcepython.models


import java.io.Serializable


class Forum(val title: String, val href: String,
            var subForums: ArrayList<SubForum> = ArrayList())
    : Serializable