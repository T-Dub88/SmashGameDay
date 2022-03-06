package com.example.smashgameday.statistics

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

data class GameStats(
    val playerName: String,
    private val _killCount: MutableLiveData<Int> = MutableLiveData(0),
    val killCount: LiveData<Int> = _killCount,
    private val _damageGiven: MutableLiveData<Int> = MutableLiveData(0),
    val damageGiven: LiveData<Int> = _damageGiven,
    private val _selfDeaths: MutableLiveData<Int> = MutableLiveData(0),
    val selfDeaths: LiveData<Int> = _selfDeaths,
    private val _place: MutableLiveData<Int> = MutableLiveData(0),
    val place: LiveData<Int> = _place,
    private val _finalScore: MutableLiveData<Int> = MutableLiveData(0),
    val finalScore: LiveData<Int> = _finalScore,
    private val _wins: MutableLiveData<Int>  =MutableLiveData(0),
    val wins: LiveData<Int> = _wins,
    var roundKillCount: Int = 0,
    var roundDamageGiven: Int = 0,
    var roundSelfDeaths: Int = 0,
    var roundPlace: Int = 1
) {
    fun finalizeStats() {
        _killCount.value = _killCount.value?.plus(roundKillCount)
        roundKillCount = 0

        _damageGiven.value = _damageGiven.value?.plus(roundDamageGiven)
        roundDamageGiven = 0

        _selfDeaths.value = _selfDeaths.value?.plus(roundSelfDeaths)
        roundSelfDeaths = 0

        _place.value = _place.value?.minus(roundPlace - 1)
        Log.i("place", roundPlace.toString())
        if (roundPlace == 1) {
            _wins.value = _wins.value?.plus(1)
        }
        roundPlace = 1

        _finalScore.value = ((_killCount.value?.minus(_selfDeaths.value!!))?.plus(_place.value!!))?.
            plus((_damageGiven.value?.div(100)!!))
    }
}