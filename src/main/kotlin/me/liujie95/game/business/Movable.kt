package me.liujie95.game.business;

import me.liujie95.game.Config
import me.liujie95.game.enums.Direction
import me.liujie95.game.model.View

/**
 * 移动的能力
 */
interface Movable : View {

    val speed:Int

    val currentDirection:Direction

    /**
     * 判断是否和阻塞物发生碰撞
     * @return 要碰撞的方向，如果为null，说明没有碰撞
     */
    fun willCollision(block:Blockable):Direction?{
        var x = this.x
        var y = this.y
        //将要碰撞时去做判断
        when(currentDirection){
            Direction.UP -> y-=speed
            Direction.DOWN -> y+=speed
            Direction.LEFT -> x-=speed
            Direction.RIGHT ->x+=speed
        }
        //检测地图边界
        if(x<0) return Direction.LEFT
        if(x> Config.gameWidth-width) return Direction.RIGHT
        if(y<0) return Direction.UP
        if(y> Config.gameHeight-height) return Direction.DOWN

        //检测碰撞，下一步是否碰撞
        val collision = checkCollision(block.x,block.y,block.width,block.height,x,y,width,height)
        return if(collision)currentDirection else null
    }

    /**
     * 通知碰撞发生的方向
     */
    fun notifyDirection(direction: Direction?,block: Blockable?)
}
