package Models;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;

//本类用于显示一个弹出提示窗口

public class WarningStage extends Stage {
    private VBox pane;
    private static Button OKbutton;
    private static Text TextTitle, TextWarning, TextTips;

    public WarningStage() {
        super();
        pane = new VBox();
        pane.setSpacing(30);
        TextTitle = new Text();
        TextWarning = new Text();
        TextTips = new Text();
        //增加确认按钮
        OKbutton = new Button("确认");
        OKbutton.setMinSize(100, 50);
        OKbutton.setCursor(Cursor.CLOSED_HAND);
        //绑定按钮处理器
        OKbutton.setOnAction(new ConfirmHandle(this));
        //设置面板内容
        TextWarning.setFill(Color.RED);
        TextTitle.setFill(Color.RED);
        TextWarning.setFont(Font.font(25));
        pane.getChildren().add(TextWarning);
        pane.getChildren().add(TextTips);
        pane.getChildren().add(OKbutton);
        pane.setAlignment(Pos.TOP_CENTER);
        //		窗体属性设置
        this.setResizable(false);
        this.setScene(new Scene(pane, 400, 200));
        this.getIcons().add(new Image(Main.class.getResourceAsStream("/resource/images/warning.png")));
        /*阻止stage对象在隐藏时被系统关闭,防止产生空指针异常*/
        this.setOnCloseRequest(e -> {
            this.hide();
            e.consume();
        });
    }

    /*设置提示内容*/
    public void setContents(String stagetitle, String warningText, String Tips) {
        TextTitle.setText(stagetitle);
        TextWarning.setText(warningText);
        TextTips.setText(Tips);
        this.setTitle(TextTitle.getText().toString());
        this.centerOnScreen();
        this.setAlwaysOnTop(true);
        this.requestFocus();
        this.show();
    }

    //关闭提示窗口
    class ConfirmHandle implements EventHandler<ActionEvent> {
        Stage closestag;

        public ConfirmHandle(Stage stg) {
            // TODO Auto-generated constructor stub
            closestag = stg;
        }

        @Override
        public void handle(ActionEvent event) {
            // TODO Auto-generated method stub
            closestag.hide();
        }
    }
}
