package Models.Animations;

import Controller.TipsController;
import Models.AnimationEventHandle.Handler_onFinished;
import javafx.animation.*;
import javafx.util.Duration;
import main.R;

/**
 * Created by 42586 on 2016/12/13.
 */
public class AnimationMOV {
    private static SequentialTransition animationList;

    public static Animation CreateAnimation() {
        /*分别获取取指周期动画和执行周期动画*/
        animationList = new SequentialTransition(FetchCycle.getAnimation(), GetExecutionCycle());
        /*设置MOV指令执行完成后的事件*/
        /*animationList.setOnFinished(e -> {
            animationList = null;
        });*/
        return animationList;
    }

    /*获取MOV指令的执行周期动画*/
    private static Animation GetExecutionCycle() {
        SequentialTransition sequentialTransition;
        Animation[] animations = new Animation[9];
        /*执行周期动画*/
        /*OC发出操作命令,动画完成后闪烁地址码对应的通用寄存器*/
        animations[0] = PathAnimation.OCAnimation(4);
        animations[0].statusProperty().addListener(observable -> {
            //if (animations[0].getStatus()== Animation.Status.RUNNING)
            TipsController.ExcuteStart("MOV");
        });
        animations[0].setDelay(Duration.seconds(5));
        /*通用寄存器闪烁*/
        animations[1] = FlashAnimation.CommonR(1);
        animations[1].statusProperty().addListener(observable -> {
            if (animations[1].getStatus() == Animation.Status.RUNNING) {
                TipsController.Select_R0R1();
            }
        });
        /*将通用寄存器的值送到ALU*/
        animations[1].setOnFinished(e -> {
            R.Txt_moveBox01.setText(R.Txt_R[1].getText());
        });
        animations[2] = PathAnimation.RegisterToAlu(R.Pane_MoveBox01, 1);
        /*通用寄存器到ALU完成时将移动图形上的值赋给ALU*/
        animations[2].setOnFinished(event -> {
            R.Txt_ALU.setText(R.Txt_moveBox01.getText());
        });
        animations[3] = FlashAnimation.CommonR(0);
        /*闪烁ALU*/
        animations[4] = FlashAnimation.ALU();
        animations[4].statusProperty().addListener(observable -> {
            if (animations[4].getStatus() == Animation.Status.RUNNING)
                TipsController.OcSelectALU();
        });
        /*ALU送到DR内*/
        animations[5] = PathAnimation.AluToDR(R.Pane_MoveBox01, 6);
        animations[5].setOnFinished(event -> {
            Handler_onFinished.AluToDR();
            TipsController.MOV_End();
        });
        animations[5].statusProperty().addListener(observable -> {
            TipsController.AluToDR();
        });
        /*DR闪烁*/
        animations[6] = FlashAnimation.DR();
        /*DR到通用寄存器*/
        animations[7] = PathAnimation.DrToRegister(R.Pane_MoveBox01, 2);
        /*动画完成后把DR的值赋给通用寄存器*/
        animations[7].setOnFinished(e -> {
            R.Txt_R[0].setText(R.Txt_DR.getText());
        });
        animations[8]= FlashAnimation.CommonR(0);
        animations[8].setDelay(Duration.seconds(1.5));
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
