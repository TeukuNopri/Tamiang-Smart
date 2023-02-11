package com.milaparsia.rsia.models

data class AntrianPoli(
    val nomorantrean: String?,
    val namapoli: String?,
    val namadokter: String?,
    val sisaantrean: String?,
    val antreanpanggil: String?,
    val statusantrian: String?,
    val keterangan: String?
)

data class AntrianPoliResponse(
    val response: AntrianPoli?,
    val metaData: MetaData?
)
