package me.liujie95.game.business

import me.liujie95.game.enums.Direction
import me.liujie95.game.model.View


/**
 * 自动移动
 */
interface AutoMovable : View {

    val currentDirection:Direction

    val speed:Int

    fun autoMove()
}