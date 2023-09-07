package com.example.finalproject

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ItemMain2Binding

class XmlViewHolder(val binding: ItemMain2Binding): RecyclerView.ViewHolder(binding.root)
class XmlAdapter(val context: Context, val datas:MutableList<mylists>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun getItemCount(): Int {
        return datas?.size ?:0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return XmlViewHolder(ItemMain2Binding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as XmlViewHolder).binding
        val model = datas!![position]
        binding.name.text = model.uiryeongsingleparentType + " : " + model.uiryeongsingleparentTitle
        binding.tel.text = model.uiryeongsingleparentRegDt
        binding.addr.text = model.uiryeongsingleparentAddr
    }
}