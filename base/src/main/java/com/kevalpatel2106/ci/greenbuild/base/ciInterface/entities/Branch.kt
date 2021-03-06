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
import com.google.gson.annotations.SerializedName

/**
 * Created by Kevalpatel2106 on 24-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
data class Branch(
        @field:SerializedName("name")
        val name: String,

        val isDefault: Boolean = false
) : Parcelable {

    var isSelected = false

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readByte() != 0.toByte()) {
        isSelected = parcel.readByte() != 0.toByte()
    }

    override fun equals(other: Any?): Boolean {
        return other != null && other is Branch && other.name == name && other.isDefault == isDefault
    }

    override fun hashCode(): Int {
        return name.hashCode() * 10 + isDefault.hashCode()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeByte(if (isDefault) 1 else 0)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Branch> {
        override fun createFromParcel(parcel: Parcel): Branch {
            return Branch(parcel)
        }

        override fun newArray(size: Int): Array<Branch?> {
            return arrayOfNulls(size)
        }
    }
}
