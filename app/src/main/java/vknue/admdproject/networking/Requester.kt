package vknue.admdproject.networking

@Suppress("DEPRECATION")
class Requester {
    companion object {
        @JvmStatic
        fun makeGetRequest(urlString: String): String {
            val requestTask = HttpRequestTask()
            val response = requestTask.execute(urlString).get()
            return response
        }
    }


}