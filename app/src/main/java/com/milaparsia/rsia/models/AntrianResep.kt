package com.milaparsia.rsia.models

data class AntrianResep (
    val no_resep: String?,
    val status_resep: String?,
)

data class AntrianResepResponse (
    val response: AntrianResep?,
    val metaData: MetaData?
)