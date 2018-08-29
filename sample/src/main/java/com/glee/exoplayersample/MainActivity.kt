package com.glee.exoplayersample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    val musicPlayer = MusicPlayer(applicationContext)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        musicPlayer.addMusicSource(object : MusicSource {
            override fun getUri() = "https://doc-0o-4g-docs.googleusercontent.com/docs/securesc/njaiessvppk87jorbnt1r36ere9io902/q2r9uv2jb53sl8l903ti5joeemvh7u2e/1535508000000/11964013474993480047/10499873144395046252/12_EoMD0PJXC1ul1TBYPnF2nLHLJsUoFw?e=download&nonce=ru9qisqec36i4&user=10499873144395046252&hash=s1j5h98pghlu0qhcquh6ttpg2ul0tr7c"
        })

        musicPlayer.play(0)
    }
}
