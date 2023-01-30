package hr.algebra.nasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.airbnb.lottie.LottieAnimationView
import hr.algebra.nasa.databinding.ActivitySplashScreenBinding
import hr.algebra.nasa.framework.*
import java.util.Objects
import javax.net.ssl.KeyStoreBuilderParameters

private const val DELAY = 3000L
const val DATA_IMPORTED = "hr.algebra.nasa.data_imported"

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimations()
        redirect()
    }

    private fun startAnimations() {
        val loading = findViewById<LottieAnimationView>(R.id.loading)
        val wait = findViewById<LottieAnimationView>(R.id.wait)

        Handler(Looper.getMainLooper()).postDelayed({
            loading.playAnimation()
        },2000)

        Handler(Looper.getMainLooper()).postDelayed({
            wait.playAnimation()
        },2000)
    }

    private fun redirect() {

        if (getBooleanPreference(DATA_IMPORTED)) {
            callDelayed(DELAY) { startActivity<HostActivity>() }

        } else {
            if (isOnline()) {
                NasaService.enqueue(this)
            } else {
                callDelayed(DELAY) { finish() }
            }
        }
    }

}