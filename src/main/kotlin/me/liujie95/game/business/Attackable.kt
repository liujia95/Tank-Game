package me.liujie95.game.business

import me.liujie95.game.model.View

/**
 * 攻击的能力
 */
interface Attackable:View {

    //攻击者
    val owner:View

    val attackPower:Int

    fun isCollision(sufferable: Sufferable):Boolean

    fun notifyAttack(sufferable: Sufferable)
}