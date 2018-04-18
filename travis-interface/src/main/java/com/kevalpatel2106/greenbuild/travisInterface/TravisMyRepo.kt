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

package com.kevalpatel2106.greenbuild.travisInterface

import com.google.gson.annotations.SerializedName

internal data class TravisMyRepo(

        @field:SerializedName("repositories")
        val repositories: List<TravisRepo>,

        @field:SerializedName("@pagination")
        val pagination: Pagination
) {

    internal data class Pagination(

            @field:SerializedName("is_last")
            val isLast: Boolean,

            @field:SerializedName("offset")
            val offset: Int,

            @field:SerializedName("limit")
            val limit: Int,

            @field:SerializedName("count")
            val count: Int? = null,

            @field:SerializedName("is_first")
            val isFirst: Boolean
    )
}