package com.leksin.app.data.mapper

import com.leksin.app.data.remote.dto.response.ParentResponse
import com.leksin.app.domain.model.Parent

fun ParentResponse.toDomain(): Parent {
    return Parent(
        id = id,
        username = username,
        email = email,
    )
}
