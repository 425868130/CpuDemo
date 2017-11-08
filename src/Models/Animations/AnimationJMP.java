package Models.Animations;

import Controller.TipsController;
import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;
import main.R;

/**
 * Created by 42586 on 2016/12/13.
 */
public class AnimationJMP {
    private static SequentialTransition animationList;
    /*JMP之前的PC值*/
    private static String tempPC;
    public static Animation CreateAnimation() {
        /*分别获取取指周期动画和执行周期动画*/
        animationList = new SequentialTransition(FetchCycle.getAnimation(), GetExecutionCycle());
        /*设置MOV指令执行完成后的事件*/
      /*  animationList.setOnFinished(e -> {
            animationList = null;
        });*/
        return animationList;
    }

    /*获取LAD指令的执行周期动画*/
    private static Animation GetExecutionCycle() {
        SequentialTransition sequentialTransition;
        Animation[] animations = new Animation[4];
        int i = 0;
        /*执行周期动画*/
        /*OC发出操作命令*/
        animations[i++] = PathAnimation.OCAnimation(4);
        animations[i - 1].statusProperty().addListener(observable -> {
            if (animations[0].getStatus() == Animation.Status.RUNNING)
                TipsController.ExcuteStart("JMP");
            tempPC = R.Txt_PC.getText();
        });
        /*设置延时*/
        animations[i - 1].setDelay(Duration.seconds(5));
        /*IR地址码闪烁*/
        animations[i++] = FlashAnimation.CreateAnimation(R.Txt_IR_addrCode, 500, 4);
        animations[i - 1].setOnFinished(e -> {
            /*将直接地址码赋给移动图形*/
            R.Txt_moveBox01.setText(R.Txt_IR_addrCode.getText());
            TipsController.JMP_IrToPC();
        });
        /*IR到PC*/
        animations[i++] = PathAnimation.IrToPC(R.Pane_MoveBox01, 8);
       /*闪烁指令Cache*/
        animations[i++] = FlashAnimation.CreateAnimation(R.Shape_OrderCache[0], 500, 6);
        animations[i - 1].statusProperty().addListener(observable -> {
            if (animations[3].getStatus() == Animation.Status.RUNNING)
                TipsController.JMP_End(tempPC);
        });
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
