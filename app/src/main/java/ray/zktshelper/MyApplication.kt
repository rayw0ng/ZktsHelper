package ray.zktshelper

import android.app.Application

class MyApplication : Application() {
    lateinit var index: MutableMap<Char, String>

    override fun onCreate() {
        super.onCreate()
        index = HashMap()
        load("index.txt", index)
        loadAlt(index)
        load("sc.txt", index)
    }

    fun load(file: String, map: MutableMap<Char, String>) {
        assets.open(file).bufferedReader().forEachLine {
            val regex = "[-0-9]+".toRegex()
            val m = regex.find(it)
            val v = m?.value ?: ""
            val keys = it.substring(m?.range?.last ?: 0)
            for (k in keys)
                if (!map.containsKey(k))
                    map.put(k, v)
        }

    }

    fun loadAlt(map: MutableMap<Char, String>) {
        var tmp = HashMap<Char, String>()
        assets.open("alt.txt").bufferedReader().forEachLine {
            val k = it.first();
            for (v in it.substring(1))
                tmp.put(v, map[k]!!)
        }
        map.putAll(tmp)
    }
}

