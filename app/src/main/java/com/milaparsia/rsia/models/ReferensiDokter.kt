package com.milaparsia.rsia.models

import com.google.gson.annotations.SerializedName

data class ReferensiDokter(
    @field:SerializedName("kd_dokter") val kd_dokter: String?= null,
    @field:SerializedName("nm_dokter") val nm_dokter: String?= null,
)

data class ReferensiDokterResponse(
    @field:SerializedName("response") val response: List<ReferensiDokter>? = null,
    val metaData : MetaData?
)
