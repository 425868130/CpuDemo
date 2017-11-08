package Models.AnimationEventHandle;

import Controller.TipsController;
import Models.Animations.FlashAnimation;
import javafx.animation.Animation;
import javafx.event.EventHandler;
import main.R;

/**
 * Created by 42586 on 2016/12/18.
 * 本类为动画完成处理事件,主要用于处理每段动画结束后界面文本的变化
 */
public class Handler_onFinished {

    /**
     * 定义PC到OrderCache路径动画完成事件
     */
    public static EventHandler PcToOrdeCache() {
           /*闪烁对应的指令Cache*/
        EventHandler handler = e -> {
             /*设置文本提示*/
            Animation animation = FlashAnimation.OrderCache();
            animation.setOnFinished(event -> {
                TipsController.PcToOrdeCache();
            });
            animation.play();
        };
        return handler;
    }

    /**
     * 定义指令Cache到IR的路径动画完成事件:
     * 将指令读取到IR上,PC值加1
     */
    public static EventHandler OrderCacheToIR() {
        EventHandler handler = e -> {
            String op = R.Txt_moveBox01.getText().substring(0, 3);
            String addr = R.Txt_moveBox01.getText().substring(3);
            R.Txt_IR_opCode.setText(op);
            R.Txt_IR_addrCode.setText(addr);
        };
        return handler;
    }

    /*ALU到DR的动画完成事件
    * 将移动图形上的值赋给DR
    * */
    public static void AluToDR() {
        R.Txt_DR.setText(R.Txt_moveBox01.getText());
    }

    /*数据Cache到DR的路径动画完成事件*/
    public static EventHandler DataCacheToDR() {
        EventHandler handler = e -> {
            R.Txt_DR.setText(R.Txt_moveBox01.getText());
        };
        return handler;
    }

    /*IR到到AR动画完成,将移动图形的值赋值给AR
    * */
    public static EventHandler IrToAR() {
        EventHandler handler = e -> {
            R.Txt_AR.setText(R.Txt_moveBox01.getText());
        };
        return handler;
    }

    /*ALU到PSW动画完成事件*/
    public static EventHandler AluToPsw() {
        EventHandler handler = e -> {
            R.Txt_PSW.setText(R.Txt_moveBox02.getText());
            TipsController.ADD_DrToCommeR();
            FlashAnimation.PSW().play();
        };
        return handler;
    }

    public static EventHandler RegisterToAR() {
        EventHandler handler = e -> {
            R.Txt_AR.setText(R.Txt_moveBox01.getText());
        };
        return handler;
    }

    public static EventHandler IrToPC() {
        EventHandler handler = event -> {
            /*PC的值变为IR的地址码*/
            R.Value_PC = Integer.valueOf(R.Txt_IR_addrCode.getText().trim());
            R.Txt_PC.setText(R.Txt_IR_addrCode.getText());
        };
        return handler;
    }
}
