package com.imhanjie.v2ex.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.imhanjie.v2ex.R

class StudentAdapter : PagedListAdapter<Student, StudentViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder(parent)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bindTo(getItem(position)!!)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Student>() {
            override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean =
                oldItem == newItem
        }
    }

}


class StudentViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.item_student, parent, false
    )
) {

    private val idTv = itemView.findViewById<TextView>(R.id.tv_id)
    private val nameTv = itemView.findViewById<TextView>(R.id.tv_name)

    fun bindTo(student: Student) {
        idTv.text = student.id.toString()
        nameTv.text = student.name
    }


}