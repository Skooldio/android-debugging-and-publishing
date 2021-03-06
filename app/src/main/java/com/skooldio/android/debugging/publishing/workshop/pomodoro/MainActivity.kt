package com.skooldio.android.debugging.publishing.workshop.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skooldio.android.debugging.publishing.workshop.pomodoro.data.LocalStorage
import com.skooldio.android.debugging.publishing.workshop.pomodoro.config.PomodoroConfig
import com.skooldio.android.debugging.publishing.workshop.pomodoro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private const val WORK_DURATION_DEFAULT = PomodoroConfig.WORK_DURATION_DEFAULT
        private const val WORK_DURATION_MIN = PomodoroConfig.WORK_DURATION_MIN
        private const val WORK_DURATION_MAX = PomodoroConfig.WORK_DURATION_MAX

        private const val SHORT_BREAK_DURATION_DEFAULT = PomodoroConfig.SHORT_BREAK_DURATION_DEFAULT
        private const val SHORT_BREAK_DURATION_MIN = PomodoroConfig.SHORT_BREAK_DURATION_MIN
        private const val SHORT_BREAK_DURATION_MAX = PomodoroConfig.SHORT_BREAK_DURATION_MAX

        private const val LONG_BREAK_DURATION_DEFAULT = PomodoroConfig.LONG_BREAK_DURATION_DEFAULT
        private const val LONG_BREAK_DURATION_MIN = PomodoroConfig.LONG_BREAK_DURATION_MIN
        private const val LONG_BREAK_DURATION_MAX = PomodoroConfig.LONG_BREAK_DURATION_MAX
    }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var workDuration = WORK_DURATION_DEFAULT
    private var shortBreakDuration = SHORT_BREAK_DURATION_DEFAULT
    private var longBreakDuration = LONG_BREAK_DURATION_DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
    }

    override fun onStart() {
        super.onStart()
        restorePomodoroConfigValue()
        updateWorkDuration()
        updateShortBreakDuration()
        updateLongBreakDuration()
    }

    override fun onStop() {
        super.onStop()
        savePomodoroConfigValue()
    }

    private fun setupView() {
        binding.buttonAddWorkDuration.setOnClickListener {
            workDuration += 5
            workDuration = workDuration.coerceIn(WORK_DURATION_MIN, WORK_DURATION_MAX)
            updateWorkDuration()
        }
        binding.buttonReduceWorkDuration.setOnClickListener {
            workDuration -= 5
            workDuration = workDuration.coerceIn(WORK_DURATION_MIN, WORK_DURATION_MAX)
            updateWorkDuration()
        }

        binding.buttonAddShortBreakDuration.setOnClickListener {
            shortBreakDuration += 5
            shortBreakDuration = shortBreakDuration.coerceIn(SHORT_BREAK_DURATION_MIN, SHORT_BREAK_DURATION_MAX)
            updateShortBreakDuration()
        }
        binding.buttonReduceShortBreakDuration.setOnClickListener {
            shortBreakDuration -= 5
            shortBreakDuration = shortBreakDuration.coerceIn(SHORT_BREAK_DURATION_MIN, SHORT_BREAK_DURATION_MAX)
            updateShortBreakDuration()
        }

        binding.buttonAddLongBreakDuration.setOnClickListener {
            longBreakDuration += 5
            longBreakDuration = longBreakDuration.coerceIn(LONG_BREAK_DURATION_MIN, LONG_BREAK_DURATION_MAX)
            updateLongBreakDuration()
        }
        binding.buttonReduceLongBreakDuration.setOnClickListener {
            longBreakDuration -= 5
            longBreakDuration = longBreakDuration.coerceIn(LONG_BREAK_DURATION_MIN, LONG_BREAK_DURATION_MAX)
            updateLongBreakDuration()
        }

        binding.buttonReady.setOnClickListener {
            val config = Config(
                workDuration = workDuration,
                shortBreakDuration = shortBreakDuration,
                longBreakDuration = longBreakDuration
            )
            val intent = TimerActivity.newIntent(
                context = this,
                config = config
            )
            startActivity(intent)
        }
    }

    private fun updateWorkDuration() {
        binding.textViewWorkDuration.text = getString(R.string.duration_value, workDuration)
    }

    private fun updateShortBreakDuration() {
        binding.textViewShortDuration.text = getString(R.string.duration_value, shortBreakDuration)
    }

    private fun updateLongBreakDuration() {
        binding.textViewLongDuration.text = getString(R.string.duration_value, longBreakDuration)
    }

    private fun savePomodoroConfigValue() {
        LocalStorage.saveConfig(
            context = this,
            workDuration = workDuration,
            shortBreakDuration = shortBreakDuration,
            longBreakDuration = longBreakDuration
        )
    }

    private fun restorePomodoroConfigValue() {
        val (
            workDuration,
            shortBreakDuration,
            longBreakDuration
        ) = LocalStorage.getConfig(this)
        this.workDuration = workDuration
        this.shortBreakDuration = shortBreakDuration
        this.longBreakDuration = longBreakDuration
    }
}
