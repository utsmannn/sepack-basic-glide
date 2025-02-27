package com.utsman.sepack

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.utsman.sepack.domain.Result
import com.utsman.sepack.ext.loadUrlSvg
import com.utsman.sepack.ext.logi
import com.utsman.sepack.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    // call viewmodel instance by Koin
    private val viewModel: MainViewModel by viewModel()

    private val svgUrl = "https://dev.w3.org/SVG/tools/svgweb/samples/svg-files/ielock.svg"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // sample load svg with glide
        img_log.loadUrlSvg(svgUrl, "id-1")

        // fetch users
        viewModel.users()

        // observing user as result case
        viewModel.users.observe(this) {
            when (it) {
                is Result.Idle -> {
                    // idle
                    logi("idle..")
                }
                is Result.Loading -> {
                    // loading
                    logi("loading..")
                    txt_log.text = "loading..."
                }
                is Result.Error -> {
                    // error
                    logi("error..")
                    val throwable = it.th
                    txt_log.text = "error, ${throwable.message}"
                    throwable.printStackTrace()
                }
                is Result.Success -> {
                    // success
                    logi("success..")
                    val data = it.data
                    txt_log.text = "success, count user -> ${data.count()}"
                }
            }
        }
    }
}