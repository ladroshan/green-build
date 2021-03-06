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

package com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by Kevalpatel2106 on 18-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
data class Build(

        val id: Long,

        val number: String,

        var duration: Long,

        val state: BuildState,

        val previousState: BuildState?,

        val startedAt: Long = 0,

        val finishedAt: Long = 0,

        val triggerType: TriggerType,

        val author: Author,

        val branch: Branch,

        val commit: Commit
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readLong(),
            BuildState.valueOf(parcel.readString()),
            with(parcel.readString()) {
                return@with if (this == null) null else BuildState.valueOf(this)
            },
            parcel.readLong(),
            parcel.readLong(),
            TriggerType.valueOf(parcel.readString()),
            parcel.readParcelable(Author::class.java.classLoader),
            parcel.readParcelable(Branch::class.java.classLoader),
            parcel.readParcelable(Commit::class.java.classLoader))

    data class Author(
            val id: String,

            val username: String
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString())

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeString(username)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Author> {
            override fun createFromParcel(parcel: Parcel): Author {
                return Author(parcel)
            }

            override fun newArray(size: Int): Array<Author?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class Commit(
            val committedAt: String? = null,

            val message: String,

            val sha: String,

            val tagName: String?
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString())

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(committedAt)
            parcel.writeString(message)
            parcel.writeString(sha)
            parcel.writeString(tagName)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Commit> {
            override fun createFromParcel(parcel: Parcel): Commit {
                return Commit(parcel)
            }

            override fun newArray(size: Int): Array<Commit?> {
                return arrayOfNulls(size)
            }
        }
    }

    init {
        if (startedAt != 0L && state in setOf(BuildState.RUNNING, BuildState.BOOTING)) {
            duration = System.currentTimeMillis() - startedAt
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(number)
        parcel.writeLong(duration)
        parcel.writeString(state.name)
        parcel.writeString(previousState?.name)
        parcel.writeLong(startedAt)
        parcel.writeLong(finishedAt)
        parcel.writeString(triggerType.name)
        parcel.writeParcelable(author, flags)
        parcel.writeParcelable(branch, flags)
        parcel.writeParcelable(commit, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Build> {
        override fun createFromParcel(parcel: Parcel): Build {
            return Build(parcel)
        }

        override fun newArray(size: Int): Array<Build?> {
            return arrayOfNulls(size)
        }
    }
}
