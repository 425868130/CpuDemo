package Models.Animations;

import Controller.TipsController;
import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;
import main.R;

/**
 * Created by 42586 on 2016/12/18.
 * 本类为指令的取指周期动画,所有指令的取指周期的动画相同
 * 本类用于获取一个取指周期的动画实例
 */
public class FetchCycle {
    public static Animation getAnimation(){
        /*用于存放闪烁动画*/
        Animation[] animation=new Animation[7];
        SequentialTransition sequentialTransition;
        /*PC闪烁*/
        animation[0] = FlashAnimation.PC();
        animation[0].statusProperty().addListener(observable -> {
            TipsController.FlashPC();
        });
        //pc到地址cache的路径动画
        animation[1] = PathAnimation.PcToOrdeCache(R.Pane_MoveBox01, 4);
        /*路径动画:指令Cache到IR*/
        animation[2] = PathAnimation.OrderCacheToIR(R.Pane_MoveBox01, 10);
        /*IR闪烁*/
        animation[3] = FlashAnimation.IR();
        /*IR的操作码延迟3秒闪烁*/
        animation[4]=FlashAnimation.CreateAnimation(R.Txt_IR_opCode,500,6);
        animation[4].setDelay(Duration.seconds(3));
        /*译码器闪烁*/
        animation[5] = FlashAnimation.Decoder();
        /*OC闪烁*/
        animation[6] = FlashAnimation.OC();
        /*至此指令的取指周期动画完成*/
        sequentialTransition=new SequentialTransition(animation);
        /*延时1.5秒后执行*/
        sequentialTransition.setDelay(Duration.seconds(1.5));
        return sequentialTransition;
    }
}
