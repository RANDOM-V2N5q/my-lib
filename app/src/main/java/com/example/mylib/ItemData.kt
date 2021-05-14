package com.example.mylib

import java.util.*

data class ItemData(
    var title: String,
    var subtitle: String,
    var rate: Double,
    var position: Int)
{
    constructor(data: Map<*,*>) : this(
        data["title"] as String,
        data["subtitle"] as String,
        (data["rate"] as Number).toDouble(),
        (data["position"] as Number).toInt()
    )
}
