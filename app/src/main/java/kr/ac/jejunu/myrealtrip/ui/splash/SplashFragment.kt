package kr.ac.jejunu.myrealtrip.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.library.BuildConfig
import androidx.navigation.fragment.findNavController
import kr.ac.jejunu.myrealtrip.R
import kr.ac.jejunu.myrealtrip.base.BaseFragment
import kr.ac.jejunu.myrealtrip.databinding.FragmentSplashBinding
import kr.ac.jejunu.myrealtrip.ui.splash.viewmodel.SplashViewModel
import org.koin.android.ext.android.inject
import java.util.*

class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {
    private val SPLACH_TIME: Long = 1300
    private val viewModel : SplashViewModel by inject()
    init{
        viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.versionText.text = "v${BuildConfig.VERSION_NAME}"
        Handler().postDelayed({
            val action = SplashFragmentDirections.actionSplashFragmentToNewsFragment()
            findNavController().navigate(action)
        }, SPLACH_TIME)
    }
}