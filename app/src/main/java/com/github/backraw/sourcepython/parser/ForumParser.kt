package com.github.backraw.sourcepython.parser


import android.util.Log
import com.github.backraw.sourcepython.HttpClient
import com.github.backraw.sourcepython.models.Forum
import com.github.backraw.sourcepython.models.Forums
import com.github.backraw.sourcepython.models.SubForum
import com.github.backraw.sourcepython.models.Topic
import okhttp3.Response
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements


// TODO: Make this class a state machine => look for good state library
class ForumParser(private var listener: ForumParserListener?) {

    private var parsingIndex: Int = 0


    fun parseForums(html: String) {
        doAsync {
            val body: Element = Jsoup.parse(html).body()

            val topicLists: Elements = body.select("ul.topiclist")

            for (topicList in topicLists) {
                val firstLink: Element = topicList.selectFirst("a")

                if (firstLink.attr("class").equals("forumtitle")) {
                    continue
                }

                Forums.add(Forum(
                        firstLink.text(),
                        HttpClient.prependUrl(firstLink.attr("href"))
                ))
            }

            parseForum()
        }
    }

    private fun parseForum(index: Int = 0) {
        if (index == Forums.size) {
            Log.d("FP", "Finished parsing ${Forums.size} forums!")
            listener?.onParsingFinished(Forums)
        } else {
            val forum: Forum = Forums[index]
            Log.d("FP", "Parsing forum ${forum.title}...")

            parsingIndex = index
            parseForumResponse(HttpClient.get(forum.href))
        }
    }

    private fun parseForumResponse(response: Response) {
        val forum = Forums[parsingIndex]

        val body: Element = Jsoup.parse(response.body()?.string()).body()
        val subForums: Elements = body.select("ul[class=topiclist forums]")

        for (subForum in subForums) {
            val links: Elements = subForum.select("a.forumtitle")

            for (link in links) {
                forum.subForums.add(SubForum(
                        link.text(),
                        HttpClient.prependUrl(link.attr("href")))
                )
            }
        }

        Log.d("SUBF", "Parsed ${forum.subForums.size} sub-forums for ${forum.title}")
        parseSubForum(forum)
    }

    private fun parseSubForum(forum: Forum, index: Int = 0) {
        if (index == forum.subForums.size) {
            Log.d("FP", "Finished parsing ${forum.subForums.size} sub-forums!")
            parseForum(parsingIndex + 1) // TODO parse topics & posts first!!
        } else {
            val subForum = forum.subForums[index]
            Log.d("FP", "Parsing sub-forum ${subForum.title}...")

            parseSubForumResponse(HttpClient.get(subForum.href), forum, subForum)
        }
    }

    private fun parseSubForumResponse(response: Response, forum: Forum, subForum: SubForum) {
        val body: Element = Jsoup.parse(response.body()?.string()).body()

        val topics: Elements = body.select("ul[class=topiclist topics]")

        for (topic in topics) {
            val links = topic.select("a")

            val firstLink = links[0]
            val lastLink = links[links.size - 1]

            subForum.topics.add(Topic(
                    firstLink.text(), lastLink.text(),
                    HttpClient.prependUrl(firstLink.attr("href"))
            ))
        }

        parseSubForum(forum, forum.subForums.indexOf(subForum) + 1)
    }
}
