package Models.AnimationEventHandle;

import javafx.event.Event;
import javafx.event.EventHandler;
import main.R;

/**
 * Created by 42586 on 2016/12/18.
 * 本类为一条指令执行完成后的事件处理器
 * 当一条指令被完整执行后,需要将指令Cache整体上移一位,
 * 若已选指令列表还有指令则从中装载一条新指令到指令Cache结尾
 */
public class Handler_LoadCode implements EventHandler{
    public Handler_LoadCode(){

    }
    @Override
    public void handle(Event event) {
        for (int i = 0; i <5; i++) {
            /*指令Cache整体上移一位*/
            R.Txt_opCode[i].setText(R.Txt_opCode[i+1].getText());
            R.Txt_addrCode[i].setText(R.Txt_addrCode[i+1].getText());
            R.Txt_opCode[i+1].setText("");
            R.Txt_addrCode[i+1].setText("");
        }
        /*尝试加载指令到指令Cache*/
        LoadCode.Load();
    }
}
