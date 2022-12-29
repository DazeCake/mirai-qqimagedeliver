package moe.dazecake.utils

import java.net.URLDecoder


object ParamsGetter {
    fun getParameter(url: String): Map<String, Any> {
        var url = url
        val map: MutableMap<String, Any> = HashMap()
        try {
            val charset = "utf-8"
            url = URLDecoder.decode(url, charset)
            if (url.indexOf('?') != -1) {
                val contents = url.substring(url.indexOf('?') + 1)
                val keyValues = contents.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (i in keyValues.indices) {
                    val key = keyValues[i].substring(0, keyValues[i].indexOf("="))
                    val value = keyValues[i].substring(keyValues[i].indexOf("=") + 1)
                    map[key] = value
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return map
    }
}