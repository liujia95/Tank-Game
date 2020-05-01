package me.liujie95.game.model

import me.liujie95.game.Config
import me.liujie95.game.business.Attackable
import me.liujie95.game.business.Blockable
import me.liujie95.game.business.Destoryable
import me.liujie95.game.business.Sufferable
import org.itheima.kotlin.game.core.Painter

/**
 * 大本营
 */
class Camp(override var x: Int, override var y: Int) :View,Blockable,Sufferable,Destoryable {

    override var blood: Int = 12

    override var width: Int = Config.block*2
    override var height: Int = Config.block+32

    override fun draw() {
        if(blood <= 3){
            width = Config.block
            height = Config.block
            x = (Config.gameWidth-Config.block)/2
            y = Config.gameHeight-Config.block
            Painter.drawImage("img/camp.gif",x,y)
        }else if(blood<=6){
            Painter.drawImage("img/wall_small.gif",x,y)
            Painter.drawImage("img/wall_small.gif",x+32,y)
            Painter.drawImage("img/wall_small.gif",x+64,y)
            Painter.drawImage("img/wall_small.gif",x+96,y)

            Painter.drawImage("img/wall_small.gif",x,y+32)
            Painter.drawImage("img/wall_small.gif",x,y+64)

            Painter.drawImage("img/wall_small.gif",x+96,y+32)
            Painter.drawImage("img/wall_small.gif",x+96,y+64)

            Painter.drawImage("img/camp.gif",x+32,y+32)
        }else{
            Painter.drawImage("img/steel_small.gif",x,y)
            Painter.drawImage("img/steel_small.gif",x+32,y)
            Painter.drawImage("img/steel_small.gif",x+64,y)
            Painter.drawImage("img/steel_small.gif",x+96,y)

            Painter.drawImage("img/steel_small.gif",x,y+32)
            Painter.drawImage("img/steel_small.gif",x,y+64)

            Painter.drawImage("img/steel_small.gif",x+96,y+32)
            Painter.drawImage("img/steel_small.gif",x+96,y+64)

            Painter.drawImage("img/camp.gif",x+32,y+32)
        }
    }

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        blood -= attackable.attackPower
        if(blood==3||blood == 6){
            val x = x-32
            val y = y-32
            return arrayOf(Blast(x,y),
                    Blast(x+32,y),
                    Blast(x+64,y),
                    Blast(x+96,y),
                    Blast(x+128,y),
                    Blast(x,y+32),
                    Blast(x,y+64),
                    Blast(x,y+96),
                    Blast(x+128,y+32),
                    Blast(x+128,y+64),
                    Blast(x+128,y+96)
                )
        }
        return null
    }

    override fun isDestoryed(): Boolean = blood<=0

    override fun showDestory(): Array<View>? {
        return arrayOf(
                Blast(x-32,y-32),
                Blast(x,y-32),
                Blast(x+32,y-32),

                Blast(x-32,y),
                Blast(x,y),
                Blast(x+32,y),

                Blast(x-32,y+32),
                Blast(x,y+32),
                Blast(x+32,y+32)
        )
    }
}