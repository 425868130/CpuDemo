package Controller;

import Models.Animations.FlashAnimation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.shape.Shape;
import main.R;

/**
 * Created by 42586 on 2016/12/18.
 * 本类为文本改变监听器,当文本改变时自动闪烁对应编号的矩形框给出提示,提升用户体验
 * 主要是为了在循环中同一完成监听器的添加减少代码量
 * 构造方法的count的值为要闪烁的寄存器的编号
 */
public class TxtChangeListener implements ChangeListener {
    private static final int MILLIS = 500;
    /*count表示要闪烁的图形编号,target表示监听的对象,0表示通用寄存器,1表示数据Cache,-1为其他图形
    * shap为需要处理的图形
    * */
    private int count, target;
    private Shape shape;

    public TxtChangeListener(int n, int tag) {
        count = n;
        target = tag;
    }

    public TxtChangeListener(Shape sp) {
        shape = sp;
        target = -1;
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if (target == 0 && count < 4)
            FlashAnimation.CreateAnimation(R.Shape_R[count], MILLIS, 4).play();
        else if (target == 1 && count < 6)
            FlashAnimation.CreateAnimation(R.Shape_DataCache[count], MILLIS, 4).play();
        else if (target == -1) {
            FlashAnimation.CreateAnimation(shape, MILLIS, 4).play();
        }
    }

    public static void SetAllTextListener() {
        for (int i = 0; i < 6; i++) {
            /*通用寄存器文本添加监听事件,当文本值改变时闪烁对应的矩形框给出提示*/
            if (i < 4)
                R.Txt_R[i].textProperty().addListener(new TxtChangeListener(i, 0));
            /*指令Cache添加监听*/
            R.Txt_DataCache[i].textProperty().addListener(new TxtChangeListener(i, 1));
        }
        R.Txt_ALU.textProperty().addListener(new TxtChangeListener(R.Shape_ALU));
        R.Txt_PC.textProperty().addListener(new TxtChangeListener(R.Shape_PC));
        R.Txt_AR.textProperty().addListener(new TxtChangeListener(R.Shape_AR));
        R.Txt_IR_opCode.textProperty().addListener(new TxtChangeListener(R.Txt_IR_opCode));
        R.Txt_IR_addrCode.textProperty().addListener(new TxtChangeListener(R.Txt_IR_addrCode));
    }
}
