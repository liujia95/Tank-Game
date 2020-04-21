package me.liujie95.game.model

/**
 * 显示的视图，定义显示规范
 */
interface View {
    val x:Int
    val y:Int

    val width:Int
    val height:Int

    fun draw()
}