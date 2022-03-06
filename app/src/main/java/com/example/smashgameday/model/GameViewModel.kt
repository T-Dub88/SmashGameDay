package com.example.smashgameday.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smashgameday.statistics.GameStats

class GameViewModel : ViewModel() {
    private lateinit var playerToRemove: GameStats

    // List for holding the instances of player data.
    var playerList = mutableListOf<GameStats>()

    // List of names to display.
    private val _playerNamesDisplay = MutableLiveData("")
    val playerNamesDisplay: LiveData<String> = _playerNamesDisplay

    // Variable for tracking round number.
    private val _roundNumber = MutableLiveData(1)
    val roundNumber: LiveData<Int> = _roundNumber

    // Creates player data using the entered name.
    fun createPlayerInstance(name: String) {
        playerList.add(GameStats(name))
    }

    // Method for creating a string to display the player names.
    fun displayPlayerNames() {
        _playerNamesDisplay.value = ""
        for (player in playerList) {
            _playerNamesDisplay.value += "${player.playerName}\n"
        }
    }

    // Method for removing a player instance from the player list.
    fun deletePlayerInstance(name: String) {
        // Determine if the name is the name of one of the players.
        for (player in playerList) {
            if (player.playerName == name) {
                playerToRemove = player
                playerList.remove(playerToRemove)
                // After removing a player, reconstruct the playerNamesDisplay.
                displayPlayerNames()
                return
            }
        }
    }

    // Method for increasing the round count.
    fun increaseRoundNumber() {
        _roundNumber.value = _roundNumber.value?.plus(1)
    }

    // Method that calls each player instances method for finalizing round stats.
    fun saveStats() {
        for (player in playerList) {
            player.finalizeStats()
        }
    }
}