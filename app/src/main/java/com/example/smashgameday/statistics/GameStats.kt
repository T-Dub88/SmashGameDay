package com.example.smashgameday.statistics

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameStats(
    val playerName: String,
    var killCount: Int = 0,
    var damageGiven: Int = 0,
    var selfDeaths: Int = 0,
    var place: Int = 0,
    var finalScore: Int = 0,
    var roundKillCount: Int = 0,
    var roundDamageGiven: Int = 0,
    var roundSelfDeaths: Int = 0,
    var roundPlace: Int = 1
) : Parcelable