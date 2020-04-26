package me.liujie95.game.ext

import me.liujie95.game.model.View

//这是View的扩展方法，当View不允许再被修改时，可以用这种方式给View进行扩展，实现开闭原则

fun View.checkCollision(view: View):Boolean{
    return checkCollision(x,y,width,height,view.x,view.y,view.width,view.height)
}