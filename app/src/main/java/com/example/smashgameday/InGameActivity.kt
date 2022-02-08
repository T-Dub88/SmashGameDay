package com.example.smashgameday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.smashgameday.adapter.ItemAdapter
import com.example.smashgameday.databinding.ActivityInGameBinding
import com.example.smashgameday.statistics.GameStats

class InGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the list of players from MainActivity
        val playerStats = intent.getParcelableArrayListExtra<GameStats>("PLAYER_STATS")
        var roundCount = 1
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        binding.nextRoundButton.text = getString(R.string.round, roundCount)

        binding.nextRoundButton.setOnClickListener {
            Log.i("New", playerStats.toString())
            roundCount += 1
            binding.nextRoundButton.text = getString(R.string.round, roundCount)

            if (playerStats != null) {
                saveStats(playerStats)
            }

            recyclerView.adapter = playerStats?.let { it1 -> ItemAdapter(it1) }
        }


        Log.i("New", playerStats.toString())

        recyclerView.adapter = playerStats?.let { ItemAdapter(it) }

    }

    private fun saveStats(stats:ArrayList<GameStats>) {
        for (item in stats) {
            item.killCount += item.roundKillCount
            item.roundKillCount = 0
            Log.i("Kill", item.killCount.toString())

            item.selfDeaths += item.roundSelfDeaths
            item.roundSelfDeaths = 0

            item.place -= item.roundPlace - 1
            item.roundPlace = 1

            item.damageGiven += item.roundDamageGiven
            item.roundDamageGiven = 0

            item.finalScore = item.killCount - item.selfDeaths + item.place +
                    (item.damageGiven / 100)


        }

    }


}
