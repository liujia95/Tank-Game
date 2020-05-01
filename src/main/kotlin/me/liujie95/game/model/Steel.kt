package me.liujie95.game.model

import me.liujie95.game.Config
import me.liujie95.game.business.Attackable
import me.liujie95.game.business.Blockable
import me.liujie95.game.business.Sufferable
import org.itheima.kotlin.game.core.Painter

class Steel(override val x: Int, override val y: Int) :Blockable,Sufferable{
    override var blood: Int = 1

    override var width:Int = Config.block
    override var height:Int = Config.block
    //显示行为
    override fun draw(){
        Painter.drawImage("img/steel.gif",x,y)
    }

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        return null
    }
}