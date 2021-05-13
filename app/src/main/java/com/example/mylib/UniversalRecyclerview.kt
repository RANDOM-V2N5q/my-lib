package com.example.mylib

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylib.databinding.FragmentUniversalRecyclerviewBinding

class UniversalRecyclerview(var path: String) : Fragment() {

    private var _binding: FragmentUniversalRecyclerviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var myAdapter: ItemAdapter
    private lateinit var myLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {

        override fun onMove(recyclerView: RecyclerView,
                            viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder): Boolean {

            val adapter = recyclerView.adapter as ItemAdapter
            val from = viewHolder.adapterPosition
            val to = target.adapterPosition

            adapter.notifyItemMoved(from, to)

            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        }

    })

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUniversalRecyclerviewBinding.inflate(inflater, container, false)

        val list = arrayListOf(
                ItemData("adfhdfhsdfh", "dsfhsdfh", 3.0, 1),
                ItemData("adfhdfhsdfh", "dsfhsdfh", 4.0, 2),
                ItemData("adfhdfhsdfh", "dsfhsdfh", 5.0, 3)
        )

        myAdapter = ItemAdapter(list, itemTouchHelper)
        myLayoutManager = LinearLayoutManager(context)

        binding.floatingActionButton.setOnClickListener {
            //TODO: Show bottom sheet
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView.apply {
            this.adapter = myAdapter
            this.layoutManager = myLayoutManager
        }

        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}