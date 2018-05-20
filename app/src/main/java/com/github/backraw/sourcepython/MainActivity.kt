package com.github.backraw.sourcepython


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import org.jetbrains.anko.doAsync

import com.github.backraw.sourcepython.models.Forum
import com.github.backraw.sourcepython.parser.ForumParser
import com.github.backraw.sourcepython.parser.ForumParserListener


class MainActivity
    : AppCompatActivity()
    , ForumParserListener {

    private lateinit var parser: ForumParser

    private lateinit var forumsPager: ViewPager
    private val forumsPagerAdapter: ForumPagerAdapter = ForumPagerAdapter(supportFragmentManager)


    // TODO: Implement mode caching

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forumsPager = findViewById(R.id.forumsPager)
        forumsPager.adapter = forumsPagerAdapter

        parser = ForumParser(this)
    }

    override fun onStart() {
        super.onStart()

        fetchData()
    }

    private fun fetchData() {
        doAsync {
            val responseString: String = HttpClient.get("$URL_PREFIX/index.php").body()!!.string()
            parser.parseForums(responseString)
        }
    }

    override fun onParsingFinished(forums: ArrayList<Forum>) {
        forumsPagerAdapter.refreshFragments()

        runOnUiThread {
            forumsPagerAdapter.notifyDataSetChanged()
        }
    }
}
