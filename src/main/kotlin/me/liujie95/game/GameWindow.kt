package me.liujie95.game

import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import me.liujie95.game.business.*
import me.liujie95.game.enums.Direction
import me.liujie95.game.model.*
import org.itheima.kotlin.game.core.Window
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.concurrent.CopyOnWriteArrayList

class GameWindow:Window(title = Config.gameName,
                        icon = Config.gameIcon,
                        width = Config.gameWidth,
                        height = Config.gameHeight) {

    /**
     * 不安全的集合：子线程做增删会报错
     */
//    private val views = arrayListOf<View>()
    /**
     * 线程安全的集合
     */
    private val views = CopyOnWriteArrayList<View>()
    private lateinit var tank:Tank

    private var gameOver :Boolean = false

    private var enemyTotalSize = 2

    //敌方坦克在地图上最可可显示几个
    private var enemyActiveSize = 1

    //地方出生点
    private var enemyBornLocation = arrayListOf<Pair<Int,Int>>()

    private var bornIndex:Int = 0

    override fun onCreate() {
//        val file = File(javaClass.getResource("/map/1.map").path)

        //在压缩包里要用这种引用地址的方式
        val resourceAsStream = javaClass.getResourceAsStream("/map/1.map")
        val read = BufferedReader(InputStreamReader(resourceAsStream, "utf-8"))
        val lines = read.readLines()
        var lineCount = 0
        lines.forEach{ line->
            var columnCount = 0
            line.toCharArray().forEach { column->
                when(column){
                    '砖'-> views.add(Wall(columnCount*Config.block,lineCount*Config.block))
                    '草'-> views.add(Grass(columnCount*Config.block,lineCount*Config.block))
                    '铁'-> views.add(Steel(columnCount*Config.block,lineCount*Config.block))
                    '水'-> views.add(Water(columnCount*Config.block,lineCount*Config.block))
                    '敌'->enemyBornLocation.add(Pair(columnCount*Config.block,lineCount*Config.block))
                }
                columnCount++
            }
            lineCount++
        }

        tank = Tank(Config.block * 10, Config.block * 12)
        views.add(tank)

        views.add(Camp(Config.gameWidth/2-64,Config.gameHeight-96))
    }

    override fun onDisplay() {
        views.forEach{
            it.draw()
        }
    }

    override fun onKeyPressed(event: KeyEvent) {
        if(!gameOver){
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
                KeyCode.SPACE->{
                    val bullet = tank.shot()
                    views.add(bullet)
                }
            }
        }
    }

    override fun onRefresh() {
        views.filter { it is Destoryable }.forEach {
            if((it as Destoryable).isDestoryed()){
                views.remove(it)

                if(it is Enemy){
                    enemyTotalSize--
                }

                val destory = it.showDestory()
                destory?.let {
                    views.addAll(destory)
                }
            }
        }

        if(gameOver) return
        //检测碰撞
        views.filter { it is Movable }.forEach { move->
            move as Movable
            var badDirection :Direction? = null
            var badBlock:Blockable? = null
            views.filter { (it is Blockable) and (move!=it) }.forEach blockTag@{ block->
                block as Blockable

                val direction = move.willCollision(block)
                direction?.let {
                    badDirection = direction
                    badBlock = block
                    return@blockTag
                }
            }
            move.notifyDirection(badDirection,badBlock)
        }

        views.filter { it is AutoMovable }.forEach {
            (it as AutoMovable).autoMove()
        }

        views.filter { it is Attackable }.forEach { attack->
            attack as Attackable
            //攻击方的源不能是发射方
            //攻击方不能是挨打者
            views.filter{(it is Sufferable) and (attack.owner!= it) and (attack != it)}.forEach sufferTag@ {suffer->
                suffer as Sufferable
                if(attack.isCollision(suffer)){
                    attack.notifyAttack(suffer)

                    val sufferView = suffer.notifySuffer(attack)
                    sufferView?.let {
                        views.addAll(sufferView)
                    }
                    return@sufferTag
                }
            }
        }

        views.filter { it is AutoShot }.forEach {
            it as AutoShot
            val shot = it.autoShot()
            shot?.let {
                views.add(shot)
            }
        }

        //检测敌方坦克出生
        if((enemyTotalSize>0) and (views.filter { it is Enemy }.size<enemyActiveSize)){
            val index = bornIndex%enemyBornLocation.size
            val pair = enemyBornLocation[index]
            views.add(Enemy(pair.first,pair.second))
            bornIndex++
        }

        if((views.filter { it is Camp }.isEmpty()) or (enemyTotalSize<=0)){
            gameOver = true
        }
    }



}