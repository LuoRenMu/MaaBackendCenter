package plus.maa.backend.service.model.parser

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import plus.maa.backend.repository.entity.ArkLevel
import plus.maa.backend.repository.entity.gamedata.ArkTilePos
import plus.maa.backend.service.ArkGameDataService
import plus.maa.backend.service.model.ArkLevelType

private val log = KotlinLogging.logger { }

/**
 * @author john180
 *
 *
 * Legion level will be tagged like this:<br></br>
 * LEGION -> POSITION -> StageCode == obt/legion/TOWER_ID/LEVEL_ID<br></br>
 * eg:<br></br>
 * 保全派驻 -> 阿卡胡拉丛林 -> LT-1 == obt/legion/lt06/level_lt06_01<br></br>
 */
@Component
class LegionParser(
    private val dataService: ArkGameDataService,
) : ArkLevelParser {
    override fun supportType(type: ArkLevelType): Boolean {
        return ArkLevelType.LEGION == type
    }

    override fun parseLevel(level: ArkLevel, tilePos: ArkTilePos): ArkLevel? {
        level.catOne = ArkLevelType.LEGION.display

        val stage = dataService.findStage(level.levelId!!, tilePos.code!!, tilePos.stageId!!)
        if (stage == null) {
            log.error { "[PARSER]保全派驻关卡未找到stage信息: ${level.levelId}" }
            return null
        }

        val catTwo: String = dataService.findTower(stage.zoneId)?.name ?: ""

        level.catTwo = catTwo
        level.catThree = tilePos.code
        return level
    }
}
