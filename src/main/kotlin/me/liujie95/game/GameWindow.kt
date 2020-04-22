package me.liujie95.game

import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import me.liujie95.game.business.Blockable
import me.liujie95.game.business.Movable
import me.liujie95.game.enums.Direction
import me.liujie95.game.model.*
import org.itheima.kotlin.game.core.Window
import java.io.File
import kotlin.math.tan

class GameWindow:Window(title = Config.gameName,
                        icon = Config.gameIcon,
                        width = Config.gameWidth,
                        height = Config.gameHeight) {

    private val views = arrayListOf<View>()
    private lateinit var tank:Tank

    override fun onCreate() {
        val file = File(javaClass.getResource("/map/1.map").path)
        val lines = file.readLines()
        var lineCount = 0
        lines.forEach{ line->
            var columnCount = 0
            line.toCharArray().forEach { column->
                when(column){
                    '砖'-> views.add(Wall(columnCount*Config.block,lineCount*Config.block))
                    '草'-> views.add(Grass(columnCount*Config.block,lineCount*Config.block))
                    '铁'-> views.add(Steel(columnCount*Config.block,lineCount*Config.block))
                    '水'-> views.add(Water(columnCount*Config.block,lineCount*Config.block))
                }
                columnCount++
            }
            lineCount++
        }

        tank = Tank(Config.block * 10, Config.block * 12)
        views.add(tank)
    }

    override fun onDisplay() {
        views.forEach{
            it.draw()
        }
    }

    override fun onKeyPressed(event: KeyEvent) {
        when(event.code){
            KeyCode.W->{
                tank.move(Direction.UP)
            }
            KeyCode.S->{
                tank.move(Direction.DOWN)
            }
            KeyCode.A->{
                tank.move(Direction.LEFT)
            }
            KeyCode.D->{
                tank.move(Direction.RIGHT)
            }
        }
    }

    override fun onRefresh() {
        //检测碰撞
        views.filter { it is Movable }.forEach { move->
            views.filter { it is Blockable }.forEach { block->
                move as Movable
                block as Blockable
                val direction = move.willCollision(block)
                move.notifyDirection(direction)
            }
        }
    }

}