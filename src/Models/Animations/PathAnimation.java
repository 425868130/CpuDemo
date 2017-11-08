package Models.Animations;

import Models.AnimationEventHandle.Handler_onFinished;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Node;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import main.R;

/**
 * Created by 42586 on 2016/12/14.
 * 本类为路径动画生成类,主要用于快速生成路径动画
 * path_IrToDecoder[],path_RegisterToDataCache[], path_RegisterToAR]][],
 * path_ArToDataCache[], path_DataCacheToDR[], path_IrToPC[], path_IrToAR;
 */
public class PathAnimation {
    /*动画执行前的延迟时间,单位:秒*/
    private static final Duration DELAY = Duration.seconds(1);

    /**
     * 按指定参数创建一段路径动画,动画执行前自动显示节点,动画完成后自动隐藏节点
     */
    public static PathTransition CreatePathAnimation(Node node, Path path, int seconds, int CycleCount) {
        PathTransition pathTransition = new PathTransition(Duration.seconds(seconds), path, node);
        pathTransition.setCycleCount(CycleCount);
        /*对动画的状态绑定值属性进行监听,如果动画开始执行则将运动图形透明显示,若动画停止则隐藏图形*/
        pathTransition.statusProperty().addListener(o -> {
            if (pathTransition.getStatus() == Animation.Status.RUNNING) {
                node.setOpacity(1);
            } else if (pathTransition.getStatus() == Animation.Status.STOPPED) {
                node.setOpacity(0);
            }
        });
        return pathTransition;
    }
    /**预定义全部路径动画*/
    /**
     * PC到指令Cache的路径动画
     */
    public static PathTransition PcToOrdeCache(Node node, int seconds) {
        PathTransition pathTransition = CreatePathAnimation(node, R.path_pcToOrdeCache, seconds, 1);
        pathTransition.setOnFinished(Handler_onFinished.PcToOrdeCache());
        /*设置动画启动延时*/
        pathTransition.setDelay(DELAY);
        return pathTransition;
    }

    /**
     * 指令Cache到IR
     */
    public static PathTransition OrderCacheToIR(Node node, int seconds) {
        PathTransition pathTransition = CreatePathAnimation(node, R.path_orderCacheToIR, seconds, 1);
        /*设置动画完成事件*/
        pathTransition.setOnFinished(Handler_onFinished.OrderCacheToIR());
        pathTransition.setDelay(DELAY);
        return pathTransition;
    }

    /**
     * OC送出操作指令的动画
     */
    public static Animation OCAnimation(int seconds) {
        /*OC闪烁*/
        Animation animation = FlashAnimation.OC();
        /*送出指令*/
        PathTransition pathTransition = CreatePathAnimation(R.VBox_MoveBoxOC, R.path_OC, seconds, 2);
        SequentialTransition sequentialTransition = new SequentialTransition(animation, pathTransition);
        return sequentialTransition;
    }

    /**
     * 通用寄存器到ALU
     */
    public static PathTransition RegisterToAlu(Node node, int seconds) {
        PathTransition pathTransition = CreatePathAnimation(node, R.path_RegisterToAlu, seconds, 1);
        return pathTransition;
    }

    /**
     * ALU到数据缓冲寄存器DR
     */
    public static PathTransition AluToDR(Node node, int seconds) {
        PathTransition pathTransition = CreatePathAnimation(node, R.path_AluToDR, seconds, 1);
        return pathTransition;
    }

    /**
     * Dr到通用寄存器
     */
    public static PathTransition DrToRegister(Node node, int seconds) {
        PathTransition pathTransition = CreatePathAnimation(node, R.path_drToRegister, seconds, 1);
        pathTransition.setDelay(Duration.seconds(2));
        return pathTransition;
    }

    /**
     * Alu到状态字寄存器PSW
     */
    public static PathTransition AluToPsw(Node node, int seconds) {
        PathTransition pathTransition = CreatePathAnimation(node, R.path_AluToPsw, seconds, 1);
        pathTransition.setOnFinished(Handler_onFinished.AluToPsw());
        pathTransition.setDelay(Duration.seconds(2));
        return pathTransition;
    }

    /**
     * Ir指令寄存器到译码器Decoder
     */
    public static PathTransition IrToDecoder(Node node, int second) {
        PathTransition pathTransition = CreatePathAnimation(node, R.path_IrToDecoder, second, 1);
        return pathTransition;
    }

    /**
     * 通用寄存器寄存器Register到数据Cache
     */
    public static PathTransition RegisterToDataCache(Node node, int second) {
        PathTransition pathTransition = CreatePathAnimation(node, R.path_RegisterToDataCache, second, 1);
        return pathTransition;
    }

    /**
     * 通用寄存器到AR
     */
    public static PathTransition RegisterToAR(Node node, int second) {
        PathTransition pathTransition = CreatePathAnimation(node, R.path_RegisterToAR, second, 1);
        pathTransition.setOnFinished(Handler_onFinished.RegisterToAR());
        return pathTransition;
    }

    /**
     * ARd到数据Cache
     */
    public static PathTransition ArToDataCache(Node node, int second) {
        PathTransition pathTransition = CreatePathAnimation(node, R.path_ArToDataCache, second, 1);
        return pathTransition;
    }

    /**
     * 数据Cache到DR
     */
    public static PathTransition DataCacheToDR(Node node, int second) {
        PathTransition pathTransition = CreatePathAnimation(node, R.path_DataCacheToDR, second, 1);
        pathTransition.setOnFinished(Handler_onFinished.DataCacheToDR());
        return pathTransition;
    }

    /**
     * Ir到PC
     */
    public static PathTransition IrToPC(Node node, int second) {
        PathTransition pathTransition = CreatePathAnimation(node, R.path_IrToPC, second, 1);
        pathTransition.setOnFinished(Handler_onFinished.IrToPC());
        return pathTransition;
    }

    /**
     * IR到AR
     */
    public static PathTransition IrToAR(Node node, int second) {
        PathTransition pathTransition = CreatePathAnimation(node, R.path_IrToAR, second, 1);
        pathTransition.setOnFinished(Handler_onFinished.IrToAR());
        return pathTransition;
    }

    /**
     * 该静态方法将所有路径动画添加到连续动画对象动画,
     * 主要用于对所有路径动画效果进行测试,
     * 完成测试后该方法不应再被调用
     */
    public static Animation PathTest(Node node) {
        int time = 3;
        return new SequentialTransition(PcToOrdeCache(node, time), OrderCacheToIR(node, time), OCAnimation(time),
                RegisterToAlu(node, time), RegisterToAlu(node, time), AluToDR(node, time), DrToRegister(node, time),
                AluToPsw(node, time), IrToDecoder(node, time), RegisterToDataCache(node, time), RegisterToAR(node, time),
                ArToDataCache(node, time), DataCacheToDR(node, time), IrToPC(node, time), IrToAR(node, time));
    }
}