package com.puntogris.recap.feature_recap.data.data_source

import com.puntogris.recap.feature_recap.data.data_source.local.RecapEntity
import com.puntogris.recap.feature_recap.domain.model.Recap

fun RecapEntity.toDomain(): Recap {
    return Recap(
        id = id,
        title = title,
        rating = rating,
        season = season,
        episode = episode,
        type = type,
        language = language,
        author = author,
        body = body,
        status = status,
        category = category,
        image = image,
        deepLink = deepLink,
        created = created
    )
}


fun Recap.toEntity(): RecapEntity {
    return RecapEntity(
        id = id,
        title = title,
        rating = rating,
        season = season,
        episode = episode,
        type = type,
        language = language,
        author = author,
        body = body,
        status = status,
        category = category,
        image = image,
        deepLink = deepLink,
        created = created
    )
}