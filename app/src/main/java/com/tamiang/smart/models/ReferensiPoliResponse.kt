package com.tamiang.smart.models

import com.google.gson.annotations.SerializedName

data class ReferensiPoliResponse(
    @field:SerializedName("response") val response: List<ReferensiPoli>? = null,
    val metaData : MetaData?
)
