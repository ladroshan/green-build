/*
 * Copyright 2018 Keval Patel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kevalpatel2106.ci.greenbuild.ciSelector

import android.app.Activity
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.CiServer
import kotlinx.android.synthetic.main.row_ci_selector_list.view.*

/**
 * Created by Keval on 21/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class CiSelectorViewHolder private constructor(itemView: View,
                                                        private val activity: Activity)
    : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun create(activity: Activity, parent: ViewGroup): CiSelectorViewHolder {
            return CiSelectorViewHolder(
                    activity = activity,
                    itemView = LayoutInflater.from(parent.context)
                            .inflate(R.layout.row_ci_selector_list, parent, false)
            )
        }
    }

    fun bind(ciServer: CiServer) {
        itemView.ci_server_name.text = ciServer.name
        itemView.ci_server_description.text = ciServer.description

        with(ciServer.domain) {
            itemView.ci_server_domain_tv.visibility = if (this == null) View.GONE else View.VISIBLE
            itemView.ci_server_domain_tv.text = this
        }

        itemView.ci_server_logo.setImageResource(ciServer.icon)

        itemView.setOnClickListener {
            val pairs = arrayListOf<Pair<View, String>>(
                    Pair.create(itemView.ci_server_name, ViewCompat.getTransitionName(itemView.ci_server_name)),
                    Pair.create(itemView.ci_server_logo, ViewCompat.getTransitionName(itemView.ci_server_logo))
            ).toTypedArray()

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity,
                    *pairs
            )

            ciServer.onClick.invoke(options)
        }
    }

}
