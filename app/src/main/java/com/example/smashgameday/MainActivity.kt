package com.example.smashgameday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.smashgameday.databinding.ActivityMainBinding
import com.example.smashgameday.statistics.GameStats

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Creates an array for the player list.
    private var playerStats: ArrayList<GameStats> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Listener for the player add button.
        binding.addPlayer.setOnClickListener {
            createPlayer()
        }

        // Listener for starting the game day and advancing to the next screen.
        binding.startGameDay.setOnClickListener {
            val intent = Intent(this, InGameActivity::class.java).apply {
                putParcelableArrayListExtra("PLAYER_STATS", playerStats)
            }

            // Starts the next activity and passes it the playerList.
            startActivity(intent)
        }
    }

    //Function for creating the list of players.
    private fun createPlayer() {
        //Takes the entered text and assigns to a variable.
        val playerName = binding.playerNameText.text.toString().trim()

        //If no text is entered the function returns without adding to the list.
        if (playerName.isBlank()) {
            return
        }

        //Clears text box after using input.
        binding.playerNameText.text?.clear()

        //Adds input to the list of players.
        playerStats.add(GameStats(playerName))

        //Display a list of all players.
        binding.playerList.text = ""
        for (player in playerStats) {
            binding.playerList.append("${player.playerName}\n")
        }
        Log.i("List", playerStats.toString())
        }

}