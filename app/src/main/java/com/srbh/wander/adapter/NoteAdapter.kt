package com.srbh.wander.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.srbh.wander.R
import com.srbh.wander.databinding.ItemNoteBinding
import com.srbh.wander.model.Note
import com.srbh.wander.view.AddActivity
import com.srbh.wander.viewmodel.NoteViewModel
import com.srbh.wander.viewmodel.NoteViewModelFactory

class NoteAdapter: RecyclerView.Adapter<NoteViewHolder>(){

    private val noteList = ArrayList<Note>()
    val viewModel: NoteViewModel = NoteViewModelFactory.getViewModel("NoteViewModel") as NoteViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemNoteBinding= DataBindingUtil.inflate(layoutInflater, R.layout.item_note,parent,false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(noteList[position])
        holder.binding.deleteButton.setOnClickListener {
            viewModel.delete(noteList[position])
        }
        holder.binding.editButton.setOnClickListener{
            val intent = Intent(holder.binding.root.context, AddActivity::class.java)
            intent.putExtra("noteId",noteList[position].id)
            intent.putExtra("noteSender",noteList[position].sender)
            intent.putExtra("noteTopic",noteList[position].topic)
            intent.putExtra("noteDescription",noteList[position].description)
            holder.binding.root.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun setList(list: List<Note>?) {
        noteList.clear()
        noteList.addAll(list!!)
    }

}

class NoteViewHolder(val binding: ItemNoteBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(note: Note){
        binding.itemTopic.text = note.topic
        binding.itemDescription.text = note.description
    }
}