package top.zhangyz.baijiaxing

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

object BaiJiaXing {
    var baiJiaXingMap = mutableMapOf<String, String>()
    private var preference = 0

    @Synchronized
    fun init(context: Context) {
        preference++
        if (baiJiaXingMap.isEmpty())
            try {
                BufferedReader(InputStreamReader(context.assets.open("baijiaxing.map"))).use { reader ->
                    while (reader.readLine()?.apply {
                            if (this[0] != '#') {
                                this.split(" ").let { map ->
                                    if (map.size == 2) {
                                        baiJiaXingMap[map[0]] = map[1]
                                    }
                                }
                            }
                        } != null) {
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
    }

    @Synchronized
    fun release() {
        preference--
        if (preference == 0)
            baiJiaXingMap.clear()
    }

    fun getFirstLetter(name: String?): Char {
        name?.let { n ->
            if (n.isNotEmpty()) {
                var pyString: String? = null
                if (n.length > 2) {
                    pyString = baiJiaXingMap[n.substring(0, 2)]
                }
                if (pyString == null) {
                    pyString = baiJiaXingMap[n.substring(0, 1)]
                    if (pyString == null) {
                        n[0].let {
                            if (it in 'a'..'z' || it in 'A'..'Z') {
                                return it.toUpperCase()
                            }
                        }
                    }
                }
                pyString?.let {
                    return it[0]
                }
            }
        }
        return '#'
    }
}