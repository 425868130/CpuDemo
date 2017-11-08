package Models.AnimationEventHandle;

import Controller.TipsController;
import javafx.animation.Animation;
import javafx.event.EventHandler;
import javafx.util.Duration;
import main.R;

/**
 * Created by 42586 on 2016/12/19.
 */
public class Handler_FlashEvent {

    /**
     * PC闪烁完成后的处理事件
     * 获取PC当前的值,同时将改值设置到运动图形MoveBox上
     */
    public static EventHandler PC() {
        EventHandler handler = e -> {
            R.Txt_PC.setText(String.valueOf(R.Value_PC));
            System.out.println("PC:" + R.Value_PC);
            R.Txt_moveBox01.setText(String.valueOf(R.Value_PC));
        };
        return handler;
    }

    /**
     * IR闪烁完成后的处理事件
     * 将移动图形上的指令读取到IR
     */
    public static EventHandler IR(Animation animation) {
        EventHandler handler = e -> {
            animation.setDelay(Duration.seconds(2));
            R.Value_PC ++;
            R.Txt_PC.setText(String.valueOf(R.Value_PC));
            TipsController.PCplus();
        };
        return handler;
    }

}
