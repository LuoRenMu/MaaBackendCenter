package plus.maa.backend.repository.entity.gamedata

data class ArkCharacter(
    val name: String,
    val profession: String,
    val rarity: Int,
) {
    var id: String? = null
}
