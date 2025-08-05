import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ComicVineResponse<T>(
    val error: String,
    val limit: Int,
    val offset: Int,
    @SerialName("number_of_page_results")
    val numberOfPageResults: Int,
    @SerialName("number_of_total_results")
    val numberOfTotalResults: Int,
    @SerialName("status_code")
    val statusCode: Int,
    val results: T,
    val version: String
)

@Serializable
data class ComicIssue(
    val id: Long,
    val name: String?,
    @SerialName("issue_number")
    val issueNumber: String?,
    @SerialName("store_date")
    val storeDate: String?,
    val description: String? = null,
    val image: Image?,
    val volume: VolumeInfo?
)

@Serializable
data class Image(
    @SerialName("icon_url")
    val iconUrl: String?,
    @SerialName("medium_url")
    val mediumUrl: String?,
    @SerialName("screen_url")
    val screenUrl: String?,
    @SerialName("small_url")
    val smallUrl: String?,
    @SerialName("super_url")
    val superUrl: String?,
    @SerialName("thumb_url")
    val thumbUrl: String?,
    @SerialName("tiny_url")
    val tinyUrl: String?,
    @SerialName("original_url")
    val originalUrl: String?
)

@Serializable
data class VolumeInfo(
    @SerialName("api_detail_url")
    val apiDetailUrl: String?,
    val id: Int,
    val name: String?,
    @SerialName("site_detail_url")
    val siteDetailUrl: String?
)
