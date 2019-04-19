package com.maddog05.linkmetadatademo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.maddog05.linkmetadata.LinkMetadata
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_action_delete.setOnClickListener {
            if (progress_get_metadata.visibility != View.VISIBLE)
                et_input_url.text?.clear()
        }
        btn_action_search.setOnClickListener {
            if (progress_get_metadata.visibility != View.VISIBLE) {
                progress_get_metadata.visibility = View.VISIBLE
                et_input_url.isEnabled = false
                tv_result_title.text = ""
                tv_result_description.text = ""
                tv_result_url.text = ""
                tv_result_image.text = ""
                LinkMetadata(et_input_url.text.toString())
                    .execute(object : LinkMetadata.OnMetadataResultListener {
                        override fun onError(text: String) {
                            progress_get_metadata.visibility = View.INVISIBLE
                            et_input_url.isEnabled = true
                            Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()
                        }

                        override fun onSuccess(result: LinkMetadata.Result) {
                            progress_get_metadata.visibility = View.INVISIBLE
                            et_input_url.isEnabled = true
                            tv_result_title.text = getString(
                                R.string.result_title,
                                if (result.title.isNotEmpty()) result.title else "<EMPTY>"
                            )
                            tv_result_description.text = getString(
                                R.string.result_description,
                                if (result.description.isNotEmpty()) result.description else "<EMPTY>"
                            )
                            tv_result_url.text = getString(
                                R.string.result_url,
                                if (result.url.isNotEmpty()) result.url else "<EMPTY>"
                            )
                            tv_result_image.text = getString(
                                R.string.result_image,
                                if (result.image.isNotEmpty()) result.image else "<EMPTY>"
                            )
                        }
                    })
            }
        }
    }
}
