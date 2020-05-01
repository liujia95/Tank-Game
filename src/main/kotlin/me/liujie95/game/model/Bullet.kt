package me.liujie95.game.model

import me.liujie95.game.Config
import me.liujie95.game.business.Attackable
import me.liujie95.game.business.AutoMovable
import me.liujie95.game.business.Destoryable
import me.liujie95.game.business.Sufferable
import me.liujie95.game.enums.Direction
import me.liujie95.game.ext.checkCollision
import org.itheima.kotlin.game.core.Painter

class Bullet(override val owner: View,val direction:Direction,
             create:(width:Int,height:Int)-> Pair<Int,Int>)
    :AutoMovable,Destoryable,Attackable,Sufferable{

    override var width: Int =  Config.block
    override var height: Int = Config.block

    override val attackPower: Int = 1

    override val currentDirection: Direction = direction
    override val speed: Int = 8
    override var blood: Int = 1

    override var x: Int = 0
    override var y: Int = 0

    private var isDestoryed = false

    private var imagePath = when(direction){
        Direction.UP ->  "img/bullet_u.gif"
        Direction.LEFT -> "img/bullet_l.gif"
        Direction.RIGHT -> "img/bullet_r.gif"
        Direction.DOWN -> "img/bullet_d.gif"
    }

    init {
        val size = Painter.size(imagePath)
        width = size[0]
        height = size[1]
        val invoke = create.invoke(width,height)
        x = invoke.first
        y = invoke.second
    }

    override fun draw() {
        Painter.drawImage(imagePath, x, y)
    }

    override fun autoMove() {
        when(currentDirection){
            Direction.UP->this.y -= speed
            Direction.DOWN->this.y += speed
            Direction.LEFT->this.x -= speed
            Direction.RIGHT->this.x += speed
        }
    }

    override fun isDestoryed(): Boolean {
        if(isDestoryed){
            return true
        }
        return  x< -width ||
                x > Config.gameWidth ||
                y < -height ||
                y > Config.gameHeight
    }


    override fun isCollision(sufferable: Sufferable): Boolean {
        return checkCollision(sufferable)
    }

    override fun notifyAttack(sufferable: Sufferable) {
        isDestoryed = true
    }

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        return arrayOf(Blast(x,y))
    }
}