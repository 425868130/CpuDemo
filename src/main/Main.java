package main;

import Controller.InitController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Created by 42586 on 2016/12/12.
 * 本类为程序主窗体类,程序执行入口
 * 关联的吧布局文件:main.fxml
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("/View/main.fxml"));
        /*建立索引*/
        R.setParent(root);
        /*初始化控制器*/
        InitController.InitAll();
        // FlashAnimation.CreateAnimation(R.Shape_ALU,500,6).play();
        primaryStage.setTitle("CPU指令执行演示  V2.0");
        primaryStage.setScene(new Scene(root, 1430, 768));
        /*固定窗口大小*/
        primaryStage.setResizable(false);
        /*设置图标*/
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/resource/images/icon.png")));
        primaryStage.requestFocus();
        primaryStage.show();
        primaryStage.requestFocus();
        /*当提示界面显示时，一直获取焦点，防止用户忽略提示界面继续操作*/
        primaryStage.focusedProperty().addListener(observable -> {
            if (R.warningStage.isShowing() && primaryStage.isFocused()) {
                R.warningStage.requestFocus();
            }
        });
        /*当主窗口关闭时，提示窗口同时关闭*/
        primaryStage.setOnCloseRequest(event -> {
            R.warningStage.close();
            primaryStage.close();
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
