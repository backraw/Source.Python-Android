package com.github.backraw.sourcepython.models


import java.io.Serializable


class Post(val author: String, val timestamp: String, val href: String, val text: String)
    : Serializable