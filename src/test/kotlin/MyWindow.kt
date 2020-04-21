import javafx.application.Application
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.paint.Color
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter
import org.itheima.kotlin.game.core.Window

class MyWindow:Window() {
    override fun onCreate() {
        println("窗体创建")
    }

    override fun onDisplay() {
        //窗体渲染时候的回调，不停的执行
        Painter.drawImage("img/tank/tankU.gif",0,0)
        Painter.drawColor(Color.WHITE,0,0,100,100)
    }

    override fun onKeyPressed(event: KeyEvent) {
        when(event.code){
            KeyCode.ENTER-> println("点击了enter按钮")
            KeyCode.L->Composer.play("audio/blast.wav")
        }
    }

    override fun onRefresh() {
        //刷新，做业务逻辑，耗时操作
    }

}

fun main(args:Array<String>) {
    Application.launch(MyWindow::class.java)
}