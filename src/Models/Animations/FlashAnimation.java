package Models.Animations;

import Controller.TipsController;
import Models.AnimationEventHandle.Handler_FlashEvent;
import Models.AnimationEventHandle.Handler_onFinished;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import main.R;

/**
 * Created by 42586 on 2016/12/13.
 * 图形颜色闪烁动画
 * 用于获取对应图形的闪烁动画，并添加相应的闪烁动画的完成事件
 */
public class FlashAnimation {
    private static final int MILLIS = 500;
    private static final int COUNT = 4;

    /**
     * 闪烁动画,红色和原色循环切换
     */
    /*创建一个闪烁动画,参数分别为要闪烁的图形对象shape,闪烁时间间隔 TimeGap,闪烁次数Count*/
    public static Timeline CreateAnimation(Shape shape, int millis, int Count) {
        Paint bckcolor;
        bckcolor = shape.getFill();
        EventHandler<ActionEvent> handler = event -> {
            if (shape.getFill() == bckcolor) {
                shape.setFill(Color.RED);
            } else shape.setFill(bckcolor);
        };
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(millis), handler));
        animation.setCycleCount(Count);
        return animation;
    }

    /**
     * 闪烁PC
     * 加载PC的值
     * 将PC的值赋给移动图形
     */
    public static Animation PC() {
        Animation animation = CreateAnimation(R.Shape_PC, MILLIS * 2, COUNT / 2);
        animation.setOnFinished(Handler_FlashEvent.PC());
        return animation;
    }

    /**
     * 闪烁指令Cache的对应项
     * 并将指令加载到移动图形上
     */
    public static Animation OrderCache() {
        /*获取PC的值，通过PC的值判断闪烁位置*/
        int position = R.Value_PC - R.PC_DEFAULT;
        R.Txt_moveBox01.setText(R.Txt_opCode[position].getText().trim() + "  " + R.Txt_addrCode[position].getText().trim());
        Animation animation = CreateAnimation(R.Shape_OrderCache[position], MILLIS, COUNT);
        return animation;
    }

    /**
     * 闪烁IR
     */
    public static Animation IR() {
        Animation animation = CreateAnimation(R.Shape_IR, MILLIS, COUNT / 2);
        animation.setOnFinished(Handler_FlashEvent.IR(animation));
        animation.setDelay(Duration.seconds(2));
        return animation;
    }

    /**
     * 闪烁Decoder译码器
     */
    public static Animation Decoder() {
        Animation animation = CreateAnimation(R.Shape_Decoder, MILLIS, COUNT);
        animation.statusProperty().addListener(observable -> {
            /*在译码器闪烁前给出文本提示*/
            if (animation.getStatus()== Animation.Status.RUNNING){
                TipsController.Decoder();
            }
        });
        animation.setDelay(Duration.seconds(1));
        return animation;
    }

    /**
     * 闪烁OC
     */

    public static Animation OC() {
        Animation animation = CreateAnimation(R.Shape_OC, MILLIS, COUNT);
        return animation;
    }

    /**
     * 闪烁AR
     */

    public static Animation AR() {
        Animation animation = CreateAnimation(R.Shape_AR, MILLIS, COUNT);
        return animation;
    }

    /**
     * 闪烁数据Cache
     */
    public static Animation DataCache(int position) {
        Animation animation = CreateAnimation(R.Shape_DataCache[position], MILLIS, COUNT);
        return animation;
    }

    /**
     * 闪烁ALU
     */

    public static Animation ALU() {
        Animation animation = CreateAnimation(R.Shape_ALU, MILLIS, COUNT*2);
        return animation;
    }

    /**
     * 闪烁DR
     */

    public static Animation DR() {
        Animation animation = CreateAnimation(R.Shape_DR, MILLIS, COUNT);
        return animation;
    }

    /**
     * 闪烁通用寄存器
     */
    public static Animation CommonR(int position) {
        Animation animation = CreateAnimation(R.Shape_R[position], MILLIS, COUNT);
        return animation;
    }

    /**
     * 闪烁PSW
     */
    public static Animation PSW() {
        Animation animation = CreateAnimation(R.Shape_PSW, MILLIS, COUNT);
        return animation;
    }
}
