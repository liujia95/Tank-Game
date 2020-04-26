package me.liujie95.game.business

import me.liujie95.game.model.View

/**
 * 攻击的能力
 */
interface Attackable:View {

    val attackPower:Int

    fun isCollision(sufferable: Sufferable):Boolean

    fun notifyAttack(sufferable: Sufferable)
}