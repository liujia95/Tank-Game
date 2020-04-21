package me.liujie95.game.model

import me.liujie95.game.Config
import org.itheima.kotlin.game.core.Painter

class Grass(override val x: Int, override val y: Int):View {

    override var width:Int = Config.block
    override var height:Int = Config.block

    override fun draw(){
        Painter.drawImage("img/obstacle/grass.png",x,y)
    }
}