package me.liujie95.game.model

import me.liujie95.game.Config
import me.liujie95.game.business.Destoryable
import org.itheima.kotlin.game.core.Painter
import java.util.*

class Blast(override val x: Int, override val y: Int) :Destoryable{

    override val width: Int = Config.block
    override val height: Int = Config.block

    private var index = 0

    var imagePaths = arrayListOf<String>()

    init {
        (1..32).forEach {
            imagePaths.add("img/blast_${it}.png")
        }
    }

    override fun draw() {
        val i = index % imagePaths.size
        Painter.drawImage(imagePaths[i],x,y)
        index++
    }

    override fun isDestoryed(): Boolean {
        return index>=imagePaths.size®
    }

}