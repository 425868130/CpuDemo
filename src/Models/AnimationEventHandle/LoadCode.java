package Models.AnimationEventHandle;

import main.R;

/**
 * Created by 42586 on 2016/12/18.
 * 本类为指令装载事件处理器
 * 用于将已选列表的指令加载到演示界面的指令Cache部分
 */
public class LoadCode {
    public static void Load() {
        /*定位到指令Cache第一项为空的位置*/
        int position;
        for (position = 0; position < 6; position++) {
            if (R.Txt_opCode[position].getText().length() == 0)
                break;
        }
        /*从指令Cache为空的位置往后依次装入已选指令*/
        for (int i = position; i < 6; i++) {
                /*如果已选指令列表为空则不加载指令*/
            if (R.list_SelectCode.getItems() == null || R.list_SelectCode.getItems().isEmpty()) return;
                /*否则移除已选指令列表的第一条数据并加载到指令Cache位*/
            String TmpeCode = R.list_SelectCode.getItems().remove(0).toString();
            R.Txt_opCode[i].setText(TmpeCode.substring(0, 3));
            /*除去两边空格*/
            R.Txt_addrCode[i].setText(TmpeCode.substring(3).trim());
        }
    }

    /*将指令Cache中的指令全部清空*/
    public static void CleanCode() {
        for (int i = 0; i < 6; i++) {
            R.Txt_opCode[i].setText("");
            R.Txt_addrCode[i].setText("");
        }
    }
}
