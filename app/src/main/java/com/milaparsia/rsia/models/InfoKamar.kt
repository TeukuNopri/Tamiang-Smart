package com.milaparsia.rsia.models

import com.google.gson.annotations.SerializedName

data class InfoKamar(
    @field:SerializedName("nm_bangsal") val nm_bangsal: String?= null,
    @field:SerializedName("jumlah") val jumlah: String?= null,
    @field:SerializedName("isi") val isi: String?= null,
    @field:SerializedName("kosong") val kosong: String?= null,
)

data class InfoKamarResponse(
    @field:SerializedName("response") val response: List<InfoKamar>? = null,
    val metaData : MetaData?
)
