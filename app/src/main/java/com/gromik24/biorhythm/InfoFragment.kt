package com.gromik24.biorhythm

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_info, container, false)

        val biorhythmsInfoHeaders = resources.getStringArray(R.array.biorhythms_info_headers)
        val biorhythmsInfoTexts = resources.getStringArray(R.array.biorhythms_info_texts)

        val infoList = mutableListOf<Info>()

        for (i in 0..4) {
            infoList += Info(biorhythmsInfoHeaders[i], biorhythmsInfoTexts[i])
        }

        val infoRecyclerView: RecyclerView = view.findViewById(R.id.info_recycler_view)
        infoRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        infoRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        infoRecyclerView.adapter = InfoAdapter(infoList)

        return view
    }


}