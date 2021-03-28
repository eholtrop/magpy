package com.dpal.domain.details

import com.dpal.data.tag.Tag
import com.dpal.data.tag.TagRepository
import io.reactivex.rxjava3.core.Observable

class SaveGameTagUseCase(
    val tagRepository: TagRepository
) {
    operator fun invoke(
        tagId: String,
        gameId: String
    ): Observable<Unit> {
        return tagRepository.saveForResource(
            tagId = tagId,
            resourceId = gameId
        ).map { }
    }
}