package Controller;

import javafx.scene.control.TextArea;
import main.R;

/**
 * Created by 42586 on 2016/12/23.
 */
public class TipsController {
    private final static TextArea textArea = R.txt_Tips;

    /*PC闪烁时的文本提示*/
    public static void FlashPC() {
        String str = "[取指阶段]" + "程序计数器PC装入要执行的指令的地址" + String.valueOf(R.Value_PC) +
                ",PC的内容被放到指令地址总线上 , 对指定进行译码, 并启动读命令";
        textArea.setText(str);
    }

    public static void PcToOrdeCache() {
        textArea.setText("从" + String.valueOf(R.Value_PC) + "号地址读出指令" + R.Txt_opCode[R.Value_PC - R.PC_DEFAULT].getText()
                + ",通过指令总线IBUS装入指定寄存器IR");
    }

    public static void PCplus() {
        textArea.setText("程序计数器内容加一变成" + R.Value_PC + ",为下一条指令做好准备");
    }

    public static void Decoder() {
        textArea.setText("指令寄存系中的操作码被译码 CPU识别出是指令" + R.Txt_IR_opCode.getText() + "\n至此取指周期即告结束");
    }
    /*执行周期开始*/
    public static void ExcuteStart(String Tag) {
        switch (Tag) {
            case "ADD":
            case "MOV":
            case "STO":
                textArea.setText("[执行周期] 操作控制器OC送出控制命令到通用寄存器");
                break;
            case "JMP":
            case "LAD":
                textArea.setText("[执行周期] OC发出操作控制命令\n" + "打开指令寄存器IR的输出三态门");
                break;
        }
    }
    /*MOV指令选择R0R1的文本提示*/
    public static void Select_R0R1(){
        textArea.setText("选择R1做源寄存器,选R0作为目标寄存器");
    }
    public static void Select_R1R2(){
        textArea.setText("选择R1做源寄存器,选R2作为目标寄存器");
    }
    public static void Select_R3(){
        textArea.setText("选择R3等于"+R.Txt_R[3].getText()+"作为数据存储器的地址,打开通用寄存器输出三态门," +
                "将地址放到DBUS上,打入地址计存器AR，并进行地址译码");
    }
    public static void OcSelectALU(){
        textArea.setText("OC送出控制信号到ALU,指定ALU做传送操作");
    }
    public static void AluToDR(){
        textArea.setText("打开ALU输出三态门,将ALU输出送到数据总线DBUS上,\n" +
                "将DBUS上的数据打入数据缓冲寄存器DR");
    }
    public static void MOV_End(){
        textArea.setText("将DR中的数据打入目标寄存器R0\n" + "R0的内容变为"+R.Txt_R[0].getText()+",至此MOV指令执行完毕");
    }

    public static void IrToAR(){
        textArea.setText("将指令中的直接地址码:" +R.Txt_moveBox01.getText()+ " 放到数据总线线DBUS上,装入地址寄存器AR");
    }
    public static void ArToDataCache(){
        textArea.setText("将数存"+R.Txt_moveBox01.getText()+"号单元中的数"+
                R.Txt_DataCache[Integer.valueOf(R.Txt_moveBox01.getText())].getText() +"读出到DBUS上,并装入缓冲寄存器DR");
    }
    public static void DataCacheToDR(){
        textArea.setText("将DR中的数" + R.Txt_DR.getText()+ "装入通用寄存器R1,原来R1中的数被冲掉\n" +
                "至此LAD指令执行结束");
    }

    public static void ADD_RegisterToAlu(){
        textArea.setText("ALU做R1和R2的加法运算,打开ALU输出三态门");
    }
    public static void ADD_AluToDR(){
        textArea.setText("将运算结果送到DBUS上,打入缓冲寄存器DR");
    }

    public static void ADD_AluToPSW(){
        textArea.setText("ALU产生的进位信号,保存在状态字寄存器PSW中");
    }

    public static void ADD_DrToCommeR(){
        textArea.setText("将DR装入R2,R2中原来的内容被冲掉,至此add指令执行周期结束");
    }

    public static void STO_OC(){
        textArea.setText("操作控制器发出操作命令到通用寄存器,选择R2等于"+R.Txt_R[2].getText()+"作为数存的写入数据");
    }
    public static void STO_End(){
        textArea.setText("将数据"+R.Txt_R[2].getText()+"写入数存"+R.Txt_R[3].getText()+"号单元\n" +
                "它原先的数据被冲掉,至此,STO指令执行周期结束");
    }

    public static void JMP_IrToPC(){
        textArea.setText("将IR中的地址码101发送到DBUS上,将DBUS的地址码101,打入到程序计数器PC中,PC中原先内容被更换");
    }
    public static void JMP_End(String PrePC){
        textArea.setText("于是下一条指令不是从"+PrePC+"号单元取出,而是转移到101号单元取出,至此JMP指令执行周期结束");
    }
}
