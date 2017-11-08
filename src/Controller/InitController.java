package Controller;

import Models.Animations.AnimationMOV;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import main.R;

import static Controller.AnimationController.AnimationIinit;

/**
 * Created by 42586 on 2016/12/12.
 * 本类为控制器的初始化类,用于对所有控制器进行初始化
 * 复位按钮通过本类直接完成所有功能
 */
public class InitController {
    static int count;

    /*初始化全部界面元素*/
    public static void InitAll() {
        OnRecovery();
        /*增加文本监听器*/
        TxtChangeListener.SetAllTextListener();
    }

    /*用于复位按钮调用,初始化文本及界面元素透明度*/
    public static void OnRecovery() {
        initParameter();
        initAnimationArea();
        InitFloatShape();
        AnimationIinit();
    }

    /*参数输入框初始化及指令列表初始化*/
    private static void initParameter() {
        R.input_R0.setText("00");
        R.input_R1.setText("10");
        R.input_R2.setText("20");
        R.input_R3.setText("3");
        R.input_Address.setText("");
        R.input_Data.setText("");
        ObservableList<String> items = FXCollections.observableArrayList(
                "MOV     R0   R1", "LAD      R1   3", "ADD     R1   R2", "STO      R2  (R3)", "JMP     101");
        R.list_Code.setItems(items);
        R.list_Code.getSelectionModel().clearSelection();
        if (R.list_SelectCode.getItems() != null)
            R.list_SelectCode.setItems(null);
    }

    public static void initAnimationArea() {
        /*文本初始化*/
        R.txt_Tips.setText("教程文本");
        R.Txt_R[0].setText("00");
        R.Txt_R[1].setText("10");
        R.Txt_R[2].setText("20");
        R.Txt_R[3].setText("3");
        R.Txt_ALU.setText("ALU");
        R.Txt_AR.setText("数据地址寄存器");
        R.Txt_PSW.setText("状态字寄存器");
        R.Txt_PC.setText("程序计数器");
        R.Txt_IR_addrCode.setText("地址码");
        R.Txt_IR_opCode.setText("OP码");
        R.Txt_DR.setText("数据缓冲寄存器");
        /*文本初始化*/
        for (count = 0; count < 6; count++) {
            R.Txt_DataCache[count].setText("10");
            R.Txt_opCode[count].setText("");
            R.Txt_addrCode[count].setText("");
        }
        R.Value_PC = R.PC_DEFAULT;
    }

    /*初始化浮动面板*/
    public static void InitFloatShape() {
        //隐藏浮动图形
        R.VBox_MoveBoxOC.setOpacity(0);
        R.Pane_MoveBox01.setOpacity(0);
        R.Pane_MoveBox02.setOpacity(0);
        R.Txt_moveBox01.setText("");
        R.Txt_moveBox02.setText("CF");
    }
    /*图形背景色初始化，防止因闪烁动画的颜色修改导致图形背景色变化*/
    public static void InitShape(){
        R.Shape_ALU.setFill(R.DEFAULT_COLOR);
        R.Shape_AR.setFill(R.DEFAULT_COLOR);
        R.Shape_Decoder.setFill(R.DEFAULT_COLOR);
        R.Shape_DR.setFill(R.DEFAULT_COLOR);
        R.Shape_PSW.setFill(R.DEFAULT_COLOR);
        R.Shape_IR.setFill(R.DEFAULT_COLOR);
        R.Shape_OC.setFill(R.DEFAULT_COLOR);
        R.Shape_PC.setFill(R.DEFAULT_COLOR);
        for (int i = 0; i < 6; i++) {
            if (i<4)
                R.Shape_R[i].setFill(R.DEFAULT_COLOR);
            R.Shape_OrderCache[i].setFill(R.DEFAULT_COLOR);
            R.Shape_DataCache[i].setFill(R.DEFAULT_COLOR);
        }
        R.Txt_IR_addrCode.setFill(Color.WHITE);
        R.Txt_IR_opCode.setFill(Color.WHITE);
    }
}
