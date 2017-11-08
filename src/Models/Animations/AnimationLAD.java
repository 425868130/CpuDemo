package Models.Animations;

import Controller.TipsController;
import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;
import main.R;

/**
 * Created by 42586 on 2016/12/13.
 */
public class AnimationLAD {
    private static SequentialTransition animationList;

    public static Animation CreateAnimation() {
        /*分别获取取指周期动画和执行周期动画*/
        animationList = new SequentialTransition(FetchCycle.getAnimation(), GetExecutionCycle());
        /*设置MOV指令执行完成后的事件*/
       /* animationList.setOnFinished(e -> {
            animationList = null;
        });*/
        return animationList;
    }

    /*获取LAD指令的执行周期动画*/
    private static Animation GetExecutionCycle() {
        SequentialTransition sequentialTransition;
        Animation[] animations = new Animation[8];
        int i = 0;
        /*执行周期动画*/
        /*OC发出操作命令*/
        animations[i++] = PathAnimation.OCAnimation(4);
        animations[i - 1].statusProperty().addListener(observable -> {
            if (animations[0].getStatus() == Animation.Status.RUNNING)
                TipsController.ExcuteStart("LAD");
        });
        /*设置延时*/
        animations[i - 1].setDelay(Duration.seconds(5));
        /*IR地址码闪烁*/
        animations[i++] = FlashAnimation.CreateAnimation(R.Txt_IR_addrCode, 500, 4);
        animations[i - 1].setOnFinished(e -> {
            /*将直接地址码赋给移动图形*/
            R.Txt_moveBox01.setText("3");
            TipsController.IrToAR();
        });
        /*IR到AR*/
        animations[i++] = PathAnimation.IrToAR(R.Pane_MoveBox01, 8);
        /*AR到数据Cache*/
        animations[i++] = PathAnimation.ArToDataCache(R.Pane_MoveBox01, 3);
        animations[i - 1].setOnFinished(event -> {
            TipsController.ArToDataCache();
        });
        /*闪烁对应的数据Cache*/
        animations[i++] = FlashAnimation.DataCache(3);
        animations[i - 1].setOnFinished(e -> {
            /*将数据Cache对应AR传进来的直接地址的数据Cache项文本添加到移动图形*/
            R.Txt_moveBox01.setText(R.Txt_DataCache[3].getText());
        });
        animations[i++] = PathAnimation.DataCacheToDR(R.Pane_MoveBox01, 7);
        animations[i - 1].setOnFinished(event -> {
            TipsController.DataCacheToDR();
        });
        /*DR到通用寄存器*/
        animations[i++] = PathAnimation.DrToRegister(R.Pane_MoveBox01, 2);
        animations[i - 1].setOnFinished(e -> {
            /*把移动图形的内容复制给R1*/
            R.Txt_R[1].setText(R.Txt_moveBox01.getText());
        });
        animations[i++] = FlashAnimation.CommonR(1);
        animations[i - 1].setDelay(Duration.seconds(1.5));

        sequentialTransition = new SequentialTransition(animations);
        return sequentialTransition;
    }

    public static void play() {
        animationList.play();
    }

    public static void pause() {
        animationList.pause();
    }

    public static void stop() {
        animationList.stop();
    }

    public static Animation.Status getStatus() {
        return animationList.getStatus();
    }
}
