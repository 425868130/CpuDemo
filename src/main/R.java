package main;

import Models.WarningStage;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 * Created by 42586 on 2016/12/12.
 * 本类为布局元素对象索引,通过ID查找对应节点
 */
public class R {
    private static Parent ViewRoot;
    public static TextArea txt_Tips;
    public static Rectangle Rectangle_MoveBox01, Rectangle_MoveBox02;
    public static Text Txt_moveBox01, Txt_moveBox02;
    public static TextField input_R0, input_R1, input_R2, input_R3, input_Address, input_Data;
    public static FlowPane parameterPane;
    public static Path path_pcToOrdeCache, path_orderCacheToIR, path_IrToDecoder, path_OC, path_AluToDR,
            path_drToRegister, path_RegisterToAlu, path_AluToPsw, path_RegisterToDataCache, path_RegisterToAR,
            path_ArToDataCache, path_DataCacheToDR, path_IrToPC, path_IrToAR;
    public static Text Txt_AR, Txt_PSW, Txt_DR, Txt_ALU, Txt_IR_opCode, Txt_IR_addrCode, Txt_PC;
    public static Text[] Txt_R, Txt_opCode, Txt_DataCache, Txt_addrCode;
    public static VBox VBox_DataCache, VBox_OrderCache, VBox_MoveBoxOC;
    public static ListView list_Code, list_SelectCode;
    public static WarningStage warningStage;
    public static Shape Shape_ALU, Shape_DR, Shape_PSW, Shape_AR, Shape_OC, Shape_PC,
            Shape_Decoder, Shape_IR;
    public static Shape[] Shape_R, Shape_DataCache, Shape_OrderCache;
    public static Pane Pane_MoveBox01, Pane_MoveBox02;
    //PC的初始默认值
    public static final int PC_DEFAULT = 101;
    /*执行过程的动态PC值*/
    public static int Value_PC = PC_DEFAULT;
    /*图形的默认填充色*/
    public static final Paint DEFAULT_COLOR = Color.DODGERBLUE;
    /*指针箭头*/
    public static Path arrow;
    public static void setParent(Parent root) {
        /*获取布局根节点*/
        ViewRoot = root;
        /*通过id查找对应VIEW*/
        /*-----------------------------------FlowPane------------------------------*/
        parameterPane = (FlowPane) ViewRoot.lookup("#parameterPane");
        /*--------------------------------------TextField----------------------------------*/
        input_R0 = (TextField) ViewRoot.lookup("#input_R0");
        input_R1 = (TextField) ViewRoot.lookup("#input_R1");
        input_R2 = (TextField) ViewRoot.lookup("#input_R2");
        input_R3 = (TextField) ViewRoot.lookup("#input_R3");
        input_Address = (TextField) ViewRoot.lookup("#input_Address");
        input_Data = (TextField) ViewRoot.lookup("#input_Data");
        /*-----------------------TextArea-------------------------------*/
        txt_Tips = (TextArea) ViewRoot.lookup("#txt_Tips");
        /*-------------------------------------Path------------------------------*/
        path_pcToOrdeCache = (Path) ViewRoot.lookup("#path_pcToOrdeCache");
        path_orderCacheToIR = (Path) ViewRoot.lookup("#path_orderCacheToIR ");
        path_IrToDecoder = (Path) ViewRoot.lookup("#path_IrToDecoder");
        path_OC = (Path) ViewRoot.lookup("#path_OC");
        path_AluToDR = (Path) ViewRoot.lookup("#path_AluToDR");
        path_drToRegister = (Path) ViewRoot.lookup("#path_drToRegister");
        path_RegisterToAlu = (Path) ViewRoot.lookup("#path_RegisterToAlu");
        path_AluToPsw = (Path) ViewRoot.lookup("#path_AluToPsw");
        path_RegisterToDataCache = (Path) ViewRoot.lookup("#path_RegisterToDataCache");
        path_RegisterToAR = (Path) ViewRoot.lookup("#path_RegisterToAR");
        path_ArToDataCache = (Path) ViewRoot.lookup("#path_ArToDataCache");
        path_DataCacheToDR = (Path) ViewRoot.lookup("#path_DataCacheToDR");
        path_IrToPC = (Path) ViewRoot.lookup("#path_IrToPC");
        path_IrToAR = (Path) ViewRoot.lookup("#path_IrToAR");
        /*-------------------------Text-------------------------------*/
        Txt_AR = (Text) ViewRoot.lookup("#Txt_DataAddrCache");
        Txt_PSW = (Text) ViewRoot.lookup("#Txt_StatusRegister");
        Txt_DR = (Text) ViewRoot.lookup("#Txt_BufferRegister");
        Txt_ALU = (Text) ViewRoot.lookup("#Txt_ALU");

        Txt_R = new Text[4];
        Txt_R[0] = (Text) ViewRoot.lookup("#Txt_R0");
        Txt_R[1] = (Text) ViewRoot.lookup("#Txt_R1");
        Txt_R[2] = (Text) ViewRoot.lookup("#Txt_R2");
        Txt_R[3] = (Text) ViewRoot.lookup("#Txt_R3");

        Txt_DataCache = new Text[6];
        Txt_DataCache[0] = (Text) ViewRoot.lookup("#Txt_DataCache01");
        Txt_DataCache[1] = (Text) ViewRoot.lookup("#Txt_DataCache02");
        Txt_DataCache[2] = (Text) ViewRoot.lookup("#Txt_DataCache03");
        Txt_DataCache[3] = (Text) ViewRoot.lookup("#Txt_DataCache04");
        Txt_DataCache[4] = (Text) ViewRoot.lookup("#Txt_DataCache05");
        Txt_DataCache[5] = (Text) ViewRoot.lookup("#Txt_DataCache06");

        Txt_addrCode = new Text[6];
        Txt_addrCode[0] = (Text) ViewRoot.lookup("#Txt_addrCode01");
        Txt_addrCode[1] = (Text) ViewRoot.lookup("#Txt_addrCode02");
        Txt_addrCode[2] = (Text) ViewRoot.lookup("#Txt_addrCode03");
        Txt_addrCode[3] = (Text) ViewRoot.lookup("#Txt_addrCode04");
        Txt_addrCode[4] = (Text) ViewRoot.lookup("#Txt_addrCode05");
        Txt_addrCode[5] = (Text) ViewRoot.lookup("#Txt_addrCode06");

        Txt_opCode = new Text[6];
        Txt_opCode[0] = (Text) ViewRoot.lookup("#Txt_opCode01");
        Txt_opCode[1] = (Text) ViewRoot.lookup("#Txt_opCode02");
        Txt_opCode[2] = (Text) ViewRoot.lookup("#Txt_opCode03");
        Txt_opCode[3] = (Text) ViewRoot.lookup("#Txt_opCode04");
        Txt_opCode[4] = (Text) ViewRoot.lookup("#Txt_opCode05");
        Txt_opCode[5] = (Text) ViewRoot.lookup("#Txt_opCode06");

        Txt_IR_opCode = (Text) ViewRoot.lookup("#Txt_IR_opCode");
        Txt_IR_addrCode = (Text) ViewRoot.lookup("#Txt_IR_addrCode");
        Txt_PC = (Text) ViewRoot.lookup("#Txt_PC");
        /*---------------------------ListView----------------------------------*/
        list_Code = (ListView) ViewRoot.lookup("#list_Code");
        list_SelectCode = (ListView) ViewRoot.lookup("#list_SelectCode");
        /*------------------------------WarningStage---------------------------*/
        warningStage = new WarningStage();
        /*--------------------------------Shape-----------------------------------*/
        Shape_ALU = (Shape) ViewRoot.lookup("#Shape_ALU");
        Shape_AR = (Shape) ViewRoot.lookup("#Shape_AR");
        Shape_DR = (Shape) ViewRoot.lookup("#Shape_DR");
        Shape_PSW = (Shape) ViewRoot.lookup("#Shape_PSW");
        Shape_OC = (Shape) ViewRoot.lookup("#Shape_OC");
        Shape_PC = (Shape) ViewRoot.lookup("#Shape_PC");
        Shape_Decoder = (Shape) ViewRoot.lookup("#Shape_Decoder");
        Shape_IR = (Shape) ViewRoot.lookup("#Shape_IR");

        Shape_R = new Shape[4];
        Shape_R[0] = (Shape) ViewRoot.lookup("#Shape_R0");
        Shape_R[1] = (Shape) ViewRoot.lookup("#Shape_R1");
        Shape_R[2] = (Shape) ViewRoot.lookup("#Shape_R2");
        Shape_R[3] = (Shape) ViewRoot.lookup("#Shape_R3");

        Shape_DataCache = new Shape[6];
        Shape_DataCache[0] = (Shape) ViewRoot.lookup("#Shape_DataCache02");
        Shape_DataCache[1] = (Shape) ViewRoot.lookup("#Shape_DataCache03");
        Shape_DataCache[2] = (Shape) ViewRoot.lookup("#Shape_DataCache04");
        Shape_DataCache[3] = (Shape) ViewRoot.lookup("#Shape_DataCache05");
        Shape_DataCache[4] = (Shape) ViewRoot.lookup("#Shape_DataCache06");
        Shape_DataCache[5] = (Shape) ViewRoot.lookup("#Shape_DataCache07");

        Shape_OrderCache = new Shape[6];
        Shape_OrderCache[0] = (Shape) ViewRoot.lookup("#Shape_OrderCache02");
        Shape_OrderCache[1] = (Shape) ViewRoot.lookup("#Shape_OrderCache03");
        Shape_OrderCache[2] = (Shape) ViewRoot.lookup("#Shape_OrderCache04");
        Shape_OrderCache[3] = (Shape) ViewRoot.lookup("#Shape_OrderCache05");
        Shape_OrderCache[4] = (Shape) ViewRoot.lookup("#Shape_OrderCache06");
        Shape_OrderCache[5] = (Shape) ViewRoot.lookup("#Shape_OrderCache07");
        /*--------------------------------Vbox----------------------------------------*/
        VBox_DataCache = (VBox) ViewRoot.lookup("#Shape_DataCache");
        VBox_OrderCache = (VBox) ViewRoot.lookup("#Shape_OrderCache");
        /*-------------------------------------上层运动图形----------------------------------*/
        Pane_MoveBox01 = (Pane) ViewRoot.lookup("#Pane_MoveBox01");
        Pane_MoveBox02 = (Pane) ViewRoot.lookup("#Pane_MoveBox02");
        VBox_MoveBoxOC = (VBox) ViewRoot.lookup("#Pane_MoveBoxOC");
        /*----------------------------图形上的Text------------------------------------------*/
        Txt_moveBox01 = (Text) ViewRoot.lookup("#Txt_moveBox01");
        Txt_moveBox02 = (Text) ViewRoot.lookup("#Txt_moveBox02");
        /*-----------------------------------浮动元素对象Rectangle----------------------------*/
        Rectangle_MoveBox01 = (Rectangle) ViewRoot.lookup("#Rectangle_MoveBox01");
        Rectangle_MoveBox02 = (Rectangle) ViewRoot.lookup("#Rectangle_MoveBox02");
        /*-------------------------------------指针箭头----------------------------------------*/
        arrow = (Path) ViewRoot.lookup("#arrow");
    }
}