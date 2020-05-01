package me.liujie95.game.business;

import me.liujie95.game.model.View

/**
 * 销毁的能力
 */
interface Destoryable: View {

    /**
     * 判断是否销毁了
     */
    fun isDestoryed():Boolean

    fun showDestory():Array<View>?{return null}

}
