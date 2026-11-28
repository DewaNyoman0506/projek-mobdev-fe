package com.leksin.app.data.mapper

import com.leksin.app.data.remote.dto.response.ChildResponse
import com.leksin.app.domain.model.Child

fun ChildResponse.toDomain(): Child {
    return Child(
        id = id,
        nickname = nickname,
    )
}
