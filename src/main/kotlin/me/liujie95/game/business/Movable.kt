package me.liujie95.game.business;

import me.liujie95.game.enums.Direction
import me.liujie95.game.model.View

/**
 * 移动的能力
 */
interface Movable : View {

    val speed:Int

    val currentDirection:Direction

    /**
     * 判断是否和阻塞物发生碰撞
     * @return 要碰撞的方向，如果为null，说明没有碰撞
     */
    fun willCollision(block:Blockable):Direction?

    /**
     * 通知碰撞发生的方向
     */
    fun notifyDirection(direction: Direction?)
}
