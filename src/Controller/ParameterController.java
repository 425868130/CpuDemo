package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import main.R;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by 42586 on 2016/12/11.
 * 本类为参数填写面板的控制类,
 * 关联的布局文件:parameterPane.fxml
 * 主要用于对输入值进行校验和边界检查,
 * 以及指令选择列表的空控制
 */
public class ParameterController implements Initializable {
    private String parameter[] = new String[6];
    private final static String ErrorEmpty = "参数不能为空!";
    private final static String ErrorInvalid = "非法输入!";
    private final static String ErrorOverstep = "参数超过范围!!";
    /*最多能添加20条指令*/
    private final static int MAXCOUNT = 20;
    @FXML
    private TextField input_R0, input_R1, input_R2, input_R3, input_Address, input_Data;
    @FXML
    FlowPane parameterPane;
    @FXML
    ListView list_Code, list_SelectCode;

    /*清除按钮事件:清空所有输入的文本*/
    public void onClearClick(ActionEvent event) {
        input_R0.setText("");
        input_R1.setText("");
        input_R2.setText("");
        input_R3.setText("");
        input_Address.setText("");
        input_Data.setText("");
    }

    /*确认按钮事件*/
    public void onSubmitClick(ActionEvent event) {
        if (AnimationController.hasAnimation()){
            R.warningStage.setContents("拒绝修改","指令执行期间禁止修改数据！","请在指令执行前或指令执行完成后修改参数");
            return;
        }
        int tempParm[] = new int[6];
        /*获取输入值*/
        parameter[0] = input_R0.getText();
        parameter[1] = input_R1.getText();
        parameter[2] = input_R2.getText();
        parameter[3] = input_R3.getText();
        parameter[4] = input_Address.getText();
        parameter[5] = input_Data.getText();
        /*判断输入是否为空*/
        for (int i = 0; i < 4; i++) {
            if (parameter[i].length() == 0) {
                R.warningStage.setContents(ErrorEmpty, "R" + Integer.toString(i) + "不能为空！", "通用寄存器参数（R0~R3）的取值范围为-999~999");
                return;
            }
            /*参数不为空则开始转换为整数*/
            try {
                tempParm[i] = Integer.valueOf(parameter[i]);
            } catch (Exception e) {
                R.warningStage.setContents(ErrorInvalid, "R" + Integer.toString(i) + "输入数据不合法！", "请检查是否输入空格或非数值字符");
                return;
            }
            if (tempParm[i] < -999 || tempParm[i] > 999) {
                R.warningStage.setContents(ErrorOverstep, "R" + Integer.toString(i) + "超出范围!", "通用寄存器参数（R0~R3）的取值范围为-999~999");
                return;
            }
            /*参数检验通过则赋值*/
            R.Txt_R[i].setText(parameter[i]);
        }
        /*数据和地址只有其中一个有输入*/
        if (parameter[4].length() == 0 && parameter[5].length() != 0) {
            R.warningStage.setContents(ErrorEmpty, "请输入数据Cache的地址值!", null);
            return;
        } else if (parameter[4].length() != 0 && parameter[5].length() == 0) {
            R.warningStage.setContents(ErrorEmpty, "请输入数据Cache的数据值!", null);
            return;
        }
        /*数据和地址输入都不为空*/
        if (parameter[4].length() != 0 && parameter[5].length() != 0) {
            try {
                tempParm[4] = Integer.valueOf(parameter[4]);
                tempParm[5] = Integer.valueOf(parameter[5]);
            } catch (Exception e) {
                R.warningStage.setContents(ErrorInvalid, "地址或数据输入不合法！", "请检查是否输入空格或非数值字符");
                return;
            }
            /*检查数据Cache的地址范围*/
            if (tempParm[4] < 0 || tempParm[4] > 5) {
                R.warningStage.setContents(ErrorOverstep, "请输入正确范围的地址值!", "数据Cache的地址范围为0~5");
                return;
            } else if (tempParm[5] < -999 || tempParm[5] > 999) {
                R.warningStage.setContents(ErrorOverstep, "数据值大小超出范围!", "数据值的取值范围为-999~999");
                return;
            } else {
                        R.Txt_DataCache[tempParm[4]].setText(parameter[5]);
                }
        }
    }

    /*清空按钮事件*/
    public void onCleanCodeClick(ActionEvent event) {
        R.list_SelectCode.setItems(null);
    }

    /*删除按钮事件*/
    public void onDeleteClick(ActionEvent event) {
        if (R.list_SelectCode.getItems() == null || R.list_SelectCode.getSelectionModel().getSelectedItems().isEmpty()) {
            R.warningStage.setContents("没有选中", "请先选择要删除的指令！", null);
            return;
        }
        ObservableList<String> items = list_SelectCode.getItems();
        items.remove(list_SelectCode.getSelectionModel().getSelectedIndex());
        list_SelectCode.setItems(items);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*给指令选择列表添加监听事件*/
        list_Code.getSelectionModel().selectedItemProperty().addListener(ov -> {
            ObservableList<String> items;
            /*若已选指令列表没有items对象则新建一个*/
            if (list_SelectCode.getItems() == null) {
                items = FXCollections.observableArrayList();
            } else {
                items = list_SelectCode.getItems();
            }
            if (items.size()>=MAXCOUNT){
                R.warningStage.setContents("列表已满", "指令列表已满无法添加！", "最多可选"+MAXCOUNT+"条指令");
                return;
            }
            /*把当前指令列表的选中项添加到已选指令列表的items对象中*/
            if(list_Code.getSelectionModel().getSelectedItem()!=null){
                items.add(list_Code.getSelectionModel().getSelectedItem().toString());
                list_SelectCode.setItems(items);
            }
        });
    }
}
