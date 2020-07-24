package com.example.sangstagram_firebase_server.navigation

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sangstagram_firebase_server.R
import com.example.sangstagram_firebase_server.navigation.model.AlarmDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_alarm.view.*
import kotlinx.android.synthetic.main.item_commnet.view.*

class AlarmFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_alarm, container, false)
        view.alarmfragment_recyclerview.adapter = AlarmRecyclerviewAdapter()
        view.alarmfragment_recyclerview.layoutManager = LinearLayoutManager(activity)

        return view
    }
    inner class AlarmRecyclerviewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        var alarmDTDList : ArrayList<AlarmDTO> = arrayListOf()

        init {
            val uid = FirebaseAuth.getInstance().currentUser?.uid

            FirebaseFirestore.getInstance().collection("alarms").whereEqualTo("destinationUid",uid).addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                alarmDTDList.clear()
                if(querySnapshot == null) return@addSnapshotListener

                for(snapshot in querySnapshot.documents) {
                    alarmDTDList.add(snapshot.toObject(AlarmDTO::class.java)!!)
                }
                notifyDataSetChanged()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item_commnet,parent,false)
            return CustomViewHolder(view)
        }
        inner class CustomViewHolder(view : View) : RecyclerView.ViewHolder(view)

        override fun getItemCount(): Int {
            return alarmDTDList.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val layoutParams = holder.itemView.layoutParams
            layoutParams.height = 80
            holder.itemView.requestLayout()

            var view = holder.itemView

            FirebaseFirestore.getInstance().collection("profileImages").document(alarmDTDList[position].uid!!).get().addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    val url = task.result!!["image"]
                    Glide.with(view.context).load(url).apply(RequestOptions().circleCrop()).into(view.commentviewitem_imageview_profile)
                }
            }



            when(alarmDTDList[position].kind) {
                0 -> {
                    val str_0 = alarmDTDList[position].userId + getString(R.string.alarm_favorite)
                    view.commentviewitem_textview_profile.text = str_0
                }
                1 -> {
                    val str_0 = alarmDTDList[position].userId + " : " + alarmDTDList[position].message
                    view.commentviewitem_textview_profile.text = str_0
                }
                2 -> {
                    val str_0 = alarmDTDList[position].userId + " " + getString(R.string.alarm_follow)
                    view.commentviewitem_textview_profile.text = str_0
                }
            }
            view.commentviewitem_textview_comment.visibility = View.INVISIBLE
        }

    }
}