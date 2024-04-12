package plus.maa.backend.repository.entity.gamedata

data class ArkCrisisV2Info(
    val seasonId: String,
    val name: String,
    // 时间戳，单位：秒
    val startTs: Long,
    val endTs: Long,
)
