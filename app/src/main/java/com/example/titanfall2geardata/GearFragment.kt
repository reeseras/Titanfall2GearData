package com.example.titanfall2geardata

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import java.util.*

private const val TAG = "GearFragment"
private const val ARG_GEAR_ID = "gear_id"

class GearFragment : Fragment() {

    private lateinit var gear: Gear
    private lateinit var nameField: EditText
    private lateinit var typeField: EditText // not working
    private lateinit var useCheckBox: CheckBox

    private val gearDetailViewModel: GearDetailViewModel by lazy {
        ViewModelProviders.of(this).get(GearDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gear = Gear()
        val gearId: UUID = arguments?.getSerializable(ARG_GEAR_ID) as UUID
        // load gear from database:
        gearDetailViewModel.loadGear(gearId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gear, container, false)

        nameField = view.findViewById(R.id.gear_name) as EditText
        typeField = view.findViewById(R.id.gear_type) as EditText
        useCheckBox = view.findViewById(R.id.gear_use) as CheckBox

        // dateButton.apply was here

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gearDetailViewModel.gearLiveData.observe(
            viewLifecycleOwner,
            Observer { gear ->
                gear?.let { // it: Gear
                    this.gear = gear
                    updateUI()
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()

        val nameWatcher = object : TextWatcher {

            override fun beforeTextChanged(
                sequence:CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                gear.name=sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {

            }
        }

        // I guess this isn't how it should be done?
        /*
        val typeWatcher = object : TextWatcher {

            override fun beforeTypeChanged(
                sequence:CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                gear.type=sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {

            }
        }
        */

        nameField.addTextChangedListener(nameWatcher)
        // typeField.addTextChangedListener(typeWatcher)

        useCheckBox.apply {
            setOnCheckedChangeListener { _, isChecked ->
                gear.use = isChecked
            }
        }
    }

    // saving changes
    override fun onStop() {
        super.onStop()
        gearDetailViewModel.saveGear(gear)
    }

    private fun updateUI() {
        nameField.setText(gear.name)
        typeField.setText(gear.type)
        useCheckBox.apply {
            isChecked = gear.use
            jumpDrawablesToCurrentState()
        }
    }

    companion object {

        fun newInstance(gearId: UUID): GearFragment {
            val args = Bundle().apply {
                putSerializable(ARG_GEAR_ID, gearId)
            }
            return GearFragment().apply {
                arguments = args
            }
        }
    }
}