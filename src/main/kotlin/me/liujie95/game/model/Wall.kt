package me.liujie95.game.model

import me.liujie95.game.Config
import me.liujie95.game.business.Blockable
import org.itheima.kotlin.game.core.Painter

class Wall(override val x: Int, override val y: Int) :Blockable{

    override var width:Int = Config.block
    override var height:Int = Config.block
    //显示行为
    override fun draw(){
        Painter.drawImage("img/walls.gif",x,y)
    }
}