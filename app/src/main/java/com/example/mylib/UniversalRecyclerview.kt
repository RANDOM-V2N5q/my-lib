package com.example.mylib

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylib.databinding.FragmentUniversalRecyclerviewBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.*
import kotlin.collections.ArrayList


class UniversalRecyclerview(var path: String) : Fragment() {

    private var _binding: FragmentUniversalRecyclerviewBinding? = null
    private val binding get() = _binding!!

    private val list = ArrayList<ItemData>()
    private val id = ArrayList<String>()
    private val database = Firebase.database.getReference(path)

    private lateinit var myAdapter: ItemAdapter
    private lateinit var myLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {

            val adapter = recyclerView.adapter as ItemAdapter
            val from = viewHolder.adapterPosition
            val to = target.adapterPosition
            Collections.swap(list, from, to)
            Collections.swap(id, from, to)

//            val childUpdates = mapOf<String, Any>(
//                "$path/${id[from]}/position" to to,
//                "$path/${id[to]}/position" to from
//            )
//
//            Firebase.database.reference.updateChildren(childUpdates)

            adapter.notifyItemMoved(from, to)

            return true
        }

        override fun isLongPressDragEnabled(): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            if(direction == ItemTouchHelper.LEFT) {
                database.child(id[position]).removeValue()
            }
            else if(direction == ItemTouchHelper.RIGHT) {
                val position = viewHolder.adapterPosition
                database.child(id[position]).removeValue()
                if(path.endsWith("/done")){
                    Firebase.database.getReference(path.replace("/done", "/undone")).push().setValue(list[position])
                }
                else if(path.endsWith("/undone")) {
                    Firebase.database.getReference(path.replace("/undone", "/done")).push().setValue(list[position])
                }
            }
            list.removeAt(position)
            id.removeAt(position)
            myAdapter.notifyDataSetChanged()
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
                .addSwipeLeftActionIcon(R.drawable.ic__delete)
                .addSwipeLeftLabel(getString(R.string.delete))
                .setSwipeLeftActionIconTint(ContextCompat.getColor(requireContext(), R.color.white))
                .setSwipeLeftLabelColor(ContextCompat.getColor(requireContext(), R.color.white))
                .addSwipeRightBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
                .addSwipeRightActionIcon(R.drawable.ic__swap)
                .addSwipeRightLabel(getString(R.string.swap))
                .setSwipeRightActionIconTint(ContextCompat.getColor(requireContext(), R.color.white))
                .setSwipeRightLabelColor(ContextCompat.getColor(requireContext(), R.color.white))
                .create()
                .decorate()

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUniversalRecyclerviewBinding.inflate(inflater, container, false)

        myAdapter = ItemAdapter(list, itemTouchHelper)
        myLayoutManager = LinearLayoutManager(context)

        binding.floatingActionButton.setOnClickListener {
            val bottomSheet = BottomSheetInput(path, list.size + 1)
            bottomSheet.show(requireActivity().supportFragmentManager, "")
        }

        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                id.add(dataSnapshot.key!!)
                if(dataSnapshot.value is Map<*, *>) {
                        list.add(ItemData(dataSnapshot.value as Map<*, *>))
                }
                myAdapter.notifyDataSetChanged()
            }
//
            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
//
//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so displayed the changed comment.
//                val newComment = dataSnapshot.getValue<Comment>()
//                val commentKey = dataSnapshot.key
//
//                // ...
            }
//
            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
//
//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so remove it.
//                val commentKey = dataSnapshot.key
//
//                // ...
            }
//
            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
//
//                // A comment has changed position, use the key to determine if we are
//                // displaying this comment and if so move it.
//                val movedComment = dataSnapshot.getValue<Comment>()
//                val commentKey = dataSnapshot.key
//
//                // ...
            }
//
            override fun onCancelled(databaseError: DatabaseError) {
//                Log.w(TAG, "postComments:onCancelled", databaseError.toException())
//                Toast.makeText(context, "Failed to load comments.",
//                    Toast.LENGTH_SHORT).show()
            }
        }
//        val query = database.orderByChild("position")
//        query.addChildEventListener(childEventListener)
        database.addChildEventListener(childEventListener)

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