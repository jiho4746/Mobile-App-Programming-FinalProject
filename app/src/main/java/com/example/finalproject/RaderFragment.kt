package com.example.finalproject

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalproject.databinding.FragmentRaderBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RaderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RaderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRaderBinding.inflate(layoutInflater)
        var radar_values : ArrayList<RadarEntry> = ArrayList()
        radar_values.add(RadarEntry(2.0f))
        radar_values.add(RadarEntry(7.0f))
        radar_values.add(RadarEntry(8.0f))
        radar_values.add(RadarEntry(1.0f))
        radar_values.add(RadarEntry(10.0f))
        val radar_dataset = RadarDataSet(radar_values, "DataSet2")
        radar_dataset.color = Color.MAGENTA
        val label = arrayOf("1월", "2월", "3월", "4월", "5월")
        val xAxis : XAxis = binding.radarChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(label)
        var radar_data = RadarData(radar_dataset)
        binding.radarChart.data = radar_data
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RaderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RaderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}