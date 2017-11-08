package Models.Animations;

import Controller.TipsController;
import Controller.TxtChangeListener;
import Models.WarningStage;
import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;
import main.R;

/**
 * Created by 42586 on 2016/12/13.
 */
public class AnimationSTO {
    private static SequentialTransition animationList;

    public static Animation CreateAnimation() {
        /*分别获取取指周期动画和执行周期动画*/
        animationList = new SequentialTransition(FetchCycle.getAnimation(), GetExecutionCycle());
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
        /*设置延时*/
        animations[i - 1].statusProperty().addListener(observable -> {
            if (animations[0].getStatus() == Animation.Status.RUNNING)
                TipsController.ExcuteStart("STO");
        });
        animations[i - 1].setDelay(Duration.seconds(5));
        /*通用寄存器R3闪烁*/
        animations[i++] = FlashAnimation.CommonR(3);
        animations[i - 1].statusProperty().addListener(observable -> {
            if (animations[1].getStatus() == Animation.Status.RUNNING) {
                TipsController.Select_R3();
            }
        });
        animations[i - 1].setOnFinished(e -> {
            /*地址译码，读出R3所指向的地址*/
            R.Txt_moveBox01.setText(R.Txt_R[3].getText());
        });
        /*通用寄存器到AR*/
        animations[i++] = PathAnimation.RegisterToAR(R.Pane_MoveBox01, 8);
        animations[i++] = PathAnimation.ArToDataCache(R.Pane_MoveBox01, 2);
        animations[i - 1].setDelay(Duration.seconds(1));
        animations[i - 1].setOnFinished(e -> {
            /*此次要判断地址值是否大于数据Cache的最大地址值*/
            int position = Integer.valueOf(R.Txt_moveBox01.getText());
            /*闪烁数据Cache*/
            FlashAnimation.DataCache(position).play();
        });
        animations[i++] = PathAnimation.OCAnimation(4);
        animations[i - 1].setDelay(Duration.seconds(3));
        /*OC发出操作命令时的提示文字*/
        animations[i - 1].statusProperty().addListener(observable -> {
            TipsController.STO_OC();
        });
        animations[i++] = FlashAnimation.CommonR(2);
        animations[i - 1].setOnFinished(e -> {
            /*从R2中取值*/
            R.Txt_moveBox01.setText(R.Txt_R[2].getText());
            TipsController.STO_End();
        });
        animations[i++] = PathAnimation.RegisterToDataCache(R.Pane_MoveBox01, 6);
        animations[i - 1].setOnFinished(event -> {
            int position = Integer.valueOf(R.Txt_R[3].getText());
            /*将移动图形上的值赋给指定数据Cache*/
            R.Txt_DataCache[position].setText(R.Txt_moveBox01.getText());
        });
        /*闪烁数据Cache*/
        animations[i++] = FlashAnimation.DataCache(Integer.valueOf(R.Txt_R[3].getText()));
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
