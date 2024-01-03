package vknue.admdproject.networking

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HttpRequestTask : AsyncTask<String, Void, String>() {
    override fun doInBackground(vararg params: String?): String {
        val urlString = params[0]

        return try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val response = StringBuilder()
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }

            reader.close()
            connection.disconnect()

            response.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            "Error occurred: ${e.message}"
        }
    }
}