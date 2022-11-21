package com.srbh.wander.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.srbh.wander.R
import com.srbh.wander.databinding.ItemNoteBinding
import com.srbh.wander.model.Note
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
            //TODO: Get note from list, GOTO update activity, set all fields with note data, let user update fields,update fields in note object, call update function from view model
            //viewModel.update(noteList[position])
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