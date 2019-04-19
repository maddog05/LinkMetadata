package com.maddog05.linkmetadata

import android.os.AsyncTask
import org.jsoup.Jsoup

class LinkMetadata(private val source: String) {

    fun execute(listener: OnMetadataResultListener) {
        if (source.trim().isNotEmpty() && (source.startsWith("http://") || source.startsWith("https://"))) {
            val properties = listOf(
                "meta[property=og:title]",
                "meta[property=og:description]",
                "meta[property=og:url]",
                "meta[property=og:image]"
            )
            LinkMetadataAsyncTask(properties, listener).execute(source)
        } else {
            listener.onError("Invalid url")
        }

    }

    private class LinkMetadataAsyncTask(
        val properties: List<String>,
        val listener: OnMetadataResultListener
    ) :
        AsyncTask<String, Void, Result>() {
        var errorMessage = ""
        override fun doInBackground(vararg params: String): Result {
            val url = params[0]
            val response = Result()
            try {
                val doc = Jsoup.connect(url).get()
                if (doc != null) {
                    val elementValues = mutableListOf<String>()
                    for (item in properties) {
                        val elementProperty = doc.select(item)
                        elementValues.add(
                            if (elementProperty.isNotEmpty()) elementProperty[0].attr(
                                "content"
                            ) else ""
                        )
                    }
                    response.title = elementValues[0]
                    response.description = elementValues[1]
                    response.url = elementValues[2]
                    response.image = elementValues[3]
                } else {
                    errorMessage = "Error when url was loading"

                }
            } catch (e: java.lang.Exception) {
                errorMessage = e.toString()
            }
            return response
        }

        override fun onPostExecute(result: Result) {
            if (errorMessage.isEmpty())
                listener.onSuccess(result)
            else
                listener.onError(errorMessage)
        }
    }

    interface OnMetadataResultListener {
        fun onError(text: String)
        fun onSuccess(result: Result)
    }

    class Result {
        var title = ""
        var description = ""
        var url = ""
        var image = ""
    }
}