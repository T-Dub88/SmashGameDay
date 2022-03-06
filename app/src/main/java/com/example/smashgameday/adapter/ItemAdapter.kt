package com.example.smashgameday.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.smashgameday.R
import com.example.smashgameday.statistics.GameStats
import com.google.android.material.textfield.TextInputEditText


class ItemAdapter(
    private val data: ArrayList<GameStats>,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder> () {

//    private var clearEditText = false

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.player_name)
        val runningScore: TextView = view.findViewById(R.id.score)
        val killsText: TextInputEditText = view.findViewById(R.id.kills_text)
        val damageDealt: TextInputEditText = view.findViewById(R.id.damage_dealt_text)
        val place: TextInputEditText = view.findViewById(R.id.place_text)
        val sds: TextInputEditText = view.findViewById(R.id.sds_text)
        val killsStat: TextView = view.findViewById(R.id.kill_stat)
        val damageStat: TextView = view.findViewById(R.id.damage_dealt_stat)
        val sdStat: TextView = view.findViewById(R.id.sds_stat)
        val winsStat: TextView = view.findViewById(R.id.wins_stat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder((adapterLayout))
    }

    @SuppressLint("StringFormatMatches")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]
        val resources = holder.itemView.context.resources

        // Observers for different variables.
        item.killCount.observe(lifecycleOwner) {
            holder.killsStat.text = it.toString()
        }

        item.selfDeaths.observe(lifecycleOwner) {
            holder.sdStat.text = it.toString()
        }

        item.damageGiven.observe(lifecycleOwner) {
            holder.damageStat.text = it.toString()
        }

        item.finalScore.observe(lifecycleOwner) {
            holder.runningScore.text = resources.getString(R.string.total_score, it)
        }

        item.wins.observe(lifecycleOwner) {
            holder.winsStat.text = it.toString()
        }

//        if (clearEditText) {
//            holder.killsText.text?.clear()
//            holder.damageDealt.text?.clear()
//            holder.sds.text?.clear()
//            holder.place.text?.clear()
//        }

        holder.nameText.text = item.playerName
        holder.runningScore.text = resources.getString(R.string.total_score, item.finalScore.value)
        holder.killsStat.text = item.killCount.value.toString()
        holder.damageStat.text = item.damageGiven.value.toString()
        holder.sdStat.text = item.selfDeaths.value.toString()
        holder.winsStat.text = resources.getString(R.string.wins, item.wins.value)

        holder.killsText.doAfterTextChanged {
            try {
                item.roundKillCount = it.toString().toInt()
            } catch (e: NumberFormatException) {
                item.roundKillCount = 0
            }
        }

        holder.damageDealt.doAfterTextChanged {
            try {
                item.roundDamageGiven = it.toString().toInt()
            } catch (e: NumberFormatException) {
                item.roundDamageGiven = 0
            }
        }

        holder.sds.doAfterTextChanged {
            try {
                item.roundSelfDeaths = it.toString().toInt()
            } catch (e: NumberFormatException) {
                item.roundSelfDeaths = 0
            }
        }

        holder.place.doAfterTextChanged {
            try {
                item.roundPlace = it.toString().toInt()
            } catch (e: NumberFormatException) {
                item.roundPlace = 1
            }
        }
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun clearEntries() {
//        clearEditText = true
//        notifyDataSetChanged()
//    }

    override fun getItemCount(): Int = data.size

}