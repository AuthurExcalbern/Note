package com.example.state.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.state.R

class StateChangeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.state_change_page, container, false)
        val radioGroup = view.findViewById<RadioGroup>(R.id.my_radio_group)
        val inButton = view.findViewById<Button>(R.id.in_state)
        val outButton = view.findViewById<Button>(R.id.out_state)

        inButton.setOnClickListener {
            for (i in 0 until radioGroup.childCount) {
                val radioButton = radioGroup.getChildAt(i) as RadioButton
                if (radioButton.isChecked) {
                    Toast.makeText(context, "进入了：${radioButton.text}", Toast.LENGTH_SHORT).show()

                    val num = com.example.state.service.MyUserStateChangeService.mListenerList.beginBroadcast()
                    for(i in 0 until num) {
                        val listener = com.example.state.service.MyUserStateChangeService.mListenerList.getBroadcastItem(i)
                        listener.mCallBack(
                            com.example.state.bean.UserState(
                                radioButton.text.toString(),
                                "in"
                            )
                        )
                    }
                    com.example.state.service.MyUserStateChangeService.mListenerList.finishBroadcast()

                    break
                }
            }
        }
        outButton.setOnClickListener {
            for (i in 0 until radioGroup.childCount) {
                val radioButton = radioGroup.getChildAt(i) as RadioButton
                if (radioButton.isChecked) {
                    Toast.makeText(context, "出来了：${radioButton.text}", Toast.LENGTH_SHORT).show()

                    val num = com.example.state.service.MyUserStateChangeService.mListenerList.beginBroadcast()
                    for(i in 0 until num) {
                        val listener = com.example.state.service.MyUserStateChangeService.mListenerList.getBroadcastItem(i)
                        listener.mCallBack(
                            com.example.state.bean.UserState(
                                radioButton.text.toString(),
                                "out"
                            )
                        )
                    }
                    com.example.state.service.MyUserStateChangeService.mListenerList.finishBroadcast()

                    break
                }
            }
        }

        return view
    }
}