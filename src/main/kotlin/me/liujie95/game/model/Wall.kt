package me.liujie95.game.model

import me.liujie95.game.Config
import me.liujie95.game.business.Attackable
import me.liujie95.game.business.Blockable
import me.liujie95.game.business.Destoryable
import me.liujie95.game.business.Sufferable
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter

class Wall(override val x: Int, override val y: Int) :Blockable,Sufferable,Destoryable{

    override var blood: Int = 3

    override var width:Int = Config.block
    override var height:Int = Config.block
    //显示行为
    override fun draw(){
        Painter.drawImage("img/wall.gif",x,y)
    }

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        blood -= attackable.attackPower
        Composer.play("audio/hit.wav")
        return arrayOf(Blast(x,y))
    }

    override fun isDestoryed(): Boolean {
        return blood<=0
    }
}