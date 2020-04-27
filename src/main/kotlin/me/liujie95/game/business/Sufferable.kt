package me.liujie95.game.business

import me.liujie95.game.model.View

/**
 * 遭受攻击的能力
 */
interface Sufferable:View {

    var blood:Int

    fun notifySuffer(attackable: Attackable):Array<View>?

}