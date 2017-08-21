package ray.zktshelper

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setTitle(R.string.full_name)

        editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                textView.text = ""
                button.visibility = View.GONE

                if (p0?.length == 0) return
                val app = applicationContext as MyApplication
                val index = app.index
                val result = getSharedPreferences("index", Activity.MODE_PRIVATE)
                        .getString(p0?.toString(), "n")
                if (result != "n")
                    textView.text = result
                else if (index.containsKey(p0?.first()))
                    textView.text = index[p0?.first()]
                else {
                    button.visibility = View.VISIBLE
                }

                if(editText.text.length >= 1)
                    editText.selectAll()
            }

        })

        button.setOnClickListener {
            val c = this@MainActivity.editText.text
            val edit = EditText(this)
            AlertDialog.Builder(this)
                    .setTitle(c.toString())
                    .setMessage("输入卷数和页码")
                    .setView(edit)
                    .setPositiveButton("添加", { _: DialogInterface, _: Int ->
                        getSharedPreferences("index", Activity.MODE_PRIVATE).edit()
                                .putString(c.toString(), edit.text.toString()).apply()
                    })
                    .setNegativeButton("取消", { _: DialogInterface, _: Int -> })
                    .show()
        }
    }

}


