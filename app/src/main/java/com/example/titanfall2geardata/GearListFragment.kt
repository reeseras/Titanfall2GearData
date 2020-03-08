package com.example.titanfall2geardata

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "GearListFragment" /// for debugging

class GearListFragment : Fragment() {

    interface Callbacks {
        fun onGearSelected(gearId: UUID)
    }

    private var callbacks: Callbacks? = null

    private lateinit var gearRecyclerView: RecyclerView
    private var adapter: GearAdapter? = GearAdapter(emptyList())
    private val gearListViewModel: GearListViewModel by lazy {
        ViewModelProviders.of(this).get(GearListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_gear_list, container, false)

        gearRecyclerView = view.findViewById(R.id.gear_recycler_view) as RecyclerView
        gearRecyclerView.layoutManager = LinearLayoutManager(context)
        gearRecyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gearListViewModel.gearListLiveData.observe(
            viewLifecycleOwner,
            Observer { gear ->
                gear?.let {
                    Log.i(TAG, "Received Gear: ${gear.size}")
                    updateUI(gear)
                }
            }
        )
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun updateUI(gear: List<Gear>) {
        adapter = GearAdapter(gear)
        gearRecyclerView.adapter = adapter
    }

    // companion object here? pg. 178
    fun newInstance(): Fragment {
        return GearListFragment()
    }

    private inner class GearHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var gear: Gear

        private val nameTextView: TextView = itemView.findViewById(R.id.gear_name);
        private val typeTextView: TextView = itemView.findViewById(R.id.gear_type);

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(gear: Gear) {
            this.gear = gear
            nameTextView.text = this.gear.name
            typeTextView.text = this.gear.type
            // if crime is solved was here (crime solved)
            View.VISIBLE
        }

        override fun onClick(v: View) {
            callbacks?.onGearSelected(gear.id)
        }
    }



    private inner class GearAdapter(var gearAll: List<Gear>)
        : RecyclerView.Adapter<GearHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GearHolder {
            val view = layoutInflater.inflate(R.layout.list_item_gear, parent, false)
            return GearHolder(view)
        }

        override fun getItemCount() = gearAll.size

        override fun onBindViewHolder(holder: GearHolder, position: Int) {
            val aGear = gearAll[position]
            holder.bind(aGear)
        }
    }
}