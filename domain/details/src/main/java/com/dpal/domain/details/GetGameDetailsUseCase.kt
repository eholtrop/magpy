package com.dpal.domain.details

import com.dpal.data.tag.Tag
import com.dpal.data.tag.TagRepository
import com.dpal.games.data.GameRepository
import com.example.dates.formatForUi
import io.reactivex.rxjava3.core.Observable

class GetGameDetailsUseCase(
    private val gameRepository: GameRepository,
    private val tagRepository: TagRepository
) {
    operator fun invoke(id: String): Observable<GameDomain> {
        return Observable.combineLatest(
            gameRepository.details(id),
            tagRepository.getAll(),
            tagRepository.getForResource(id)
        ) { details, allTags, gameTags ->
            GameDomain(
                title = details.name,
                subtitle = details.releaseDate.formatForUi(),
                boxArt = details.boxart,
                description = details.description,
                gameTags = gameTags.map { it.toTagDomain() },
                allTags = allTags.map { it.toTagDomain() }
            )
        }
    }
}

internal fun Tag.toTagDomain() = TagDomain(
    id = this.id,
    title = this.title
)

data class GameDomain(
    val title: String,
    val subtitle: String,
    val boxArt: String,
    val description: String,
    val gameTags: List<TagDomain>,
    val allTags: List<TagDomain>
)

data class TagDomain(
    val id: String,
    val title: String
)
