package top.zhangyz.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import top.zhangyz.baijiaxing.BaiJiaXing
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.settings?.run {
            javaScriptEnabled = true
            setSupportZoom(true)
            builtInZoomControls = true
        }
        webView.loadUrl("file:///android_asset/baijiaxing.map")

        try {
            BufferedReader(InputStreamReader(assets.open("baijiaxing.map"))).use { reader ->
                while (reader.readLine()?.apply {
                        Log.i("ddddd", this)
                    } != null) {
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        BaiJiaXing.init(this)
        BaiJiaXing.baiJiaXingMap.forEach {
            Log.i("ddd", "${it.key}:${it.value}")
        }
        BaiJiaXing.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }
}
