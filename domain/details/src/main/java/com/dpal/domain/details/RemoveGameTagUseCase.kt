package com.dpal.domain.details

import com.dpal.data.tag.TagRepository
import io.reactivex.rxjava3.core.Observable

class RemoveGameTagUseCase(
    val tagRepository: TagRepository
) {
    operator fun invoke(
        tagId: String,
        gameId: String
    ): Observable<Unit> {
        return tagRepository.removeForResource(
            tagId = tagId,
            resourceId = gameId
        ).map { }
    }
}
