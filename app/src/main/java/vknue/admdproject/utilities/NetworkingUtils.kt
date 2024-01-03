package vknue.admdproject.utilities

class NetworkingUtils {

    companion object{
        @JvmStatic
        fun addParameterToUrl(baseUrl: String, paramName: String, paramValue: String): String {
            return if (baseUrl.contains("?")) {
                "$baseUrl&$paramName=$paramValue"
            } else {
                "$baseUrl?$paramName=$paramValue"
            }
        }
    }

}