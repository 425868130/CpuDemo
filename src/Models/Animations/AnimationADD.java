package Models.Animations;

import Controller.TipsController;
import Models.AnimationEventHandle.Handler_onFinished;
import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;
import main.R;

/**
 * Created by 42586 on 2016/12/13.
 */
public class AnimationADD {
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
        Animation[] animations = new Animation[10];
        int i = 0;
        /*执行周期动画*/
        /*OC发出操作命令*/
        animations[i++] = PathAnimation.OCAnimation(4);
        animations[i - 1].statusProperty().addListener(observable -> {
            if (animations[0].getStatus() == Animation.Status.RUNNING)
                TipsController.ExcuteStart("ADD");
        });
        /*设置延时*/
        animations[i - 1].setDelay(Duration.seconds(5));
        /*通用寄存器R1,R2闪烁*/
        animations[i++] = FlashAnimation.CommonR(1);
        animations[i - 1].statusProperty().addListener(observable -> {
            if (animations[1].getStatus() == Animation.Status.RUNNING) {
                TipsController.Select_R1R2();
            }
        });
        animations[i++] = FlashAnimation.CommonR(2);
        animations[i - 1].setOnFinished(e -> {
            /*将两个操作数加载到移动图形*/
            R.Txt_moveBox01.setText(R.Txt_R[1].getText() + " | " + R.Txt_R[2].getText());
        });
        animations[i++] = PathAnimation.RegisterToAlu(R.Pane_MoveBox01, 2);
        animations[i - 1].setOnFinished(event -> {
            TipsController.ADD_RegisterToAlu();
        });
        animations[i++] = FlashAnimation.ALU();
        animations[i - 1].setOnFinished(e -> {
            /*将俩操作数相加后赋值给ALU*/
            int r1 = Integer.valueOf(R.Txt_R[1].getText());
            int r2 = Integer.valueOf(R.Txt_R[2].getText());
            String sum = String.valueOf(r1 + r2);
            R.Txt_ALU.setText(sum);
            R.Txt_moveBox01.setText(sum);
            TipsController.ADD_AluToDR();
        });
        animations[i++] = PathAnimation.AluToDR(R.Pane_MoveBox01, 12);
        animations[i - 1].setOnFinished(event -> {
            /*完成事件:将ALU的值加载到DR*/
            Handler_onFinished.AluToDR();
            /*提前为接下来的动画设置文本提示*/
            TipsController.ADD_AluToPSW();
        });
        /*DR闪烁*/
        animations[i++] = FlashAnimation.DR();
        /*ALU发送CF到PSW*/
        animations[i++] = PathAnimation.AluToPsw(R.Pane_MoveBox02, 8);
        animations[i++] = PathAnimation.DrToRegister(R.Pane_MoveBox01, 2);
        animations[i - 1].setOnFinished(e -> {
            R.Txt_R[2].setText(R.Txt_moveBox01.getText());
        });
        animations[i++] = FlashAnimation.CommonR(2);
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
