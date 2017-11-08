package Controller;

import Models.AnimationEventHandle.Handler_onFinished;
import Models.AnimationEventHandle.LoadCode;
import Models.Animations.*;
import Models.AudioPlayer;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import main.R;

/**
 * Created by 42586 on 2016/12/11.
 * 本类为演示面板的按钮控制器
 * 关联的布局:controlView.fxml
 * 主要用于控制演示动画的播放,暂停循环以及程序复位
 */
public class AnimationController {
    @FXML
    Button button_pause, button_loop, button_sound, button_single, button_continuity;
    private static Animation animation;
    private static boolean isContinue = false, isLooped = false;
    private static MediaPlayer mediaPlayer;
    /*指令Cache每格之间的间距，即指针箭头每次移动的距离*/
    private final static int Spacing = 30;
    private final static double layoutY = 93;
    /*标识当前是否静音*/
    private static boolean hasSound = true;

    /*单条执行按钮*/
    @FXML
    public void onSingleClick(ActionEvent event) {
        if (!HasCode()) return;
        isContinue = false;
        button_single.setTextFill(Color.RED);
        button_continuity.setTextFill(Color.BLACK);
        /*有指令则播放指令*/
        AnimationPlay();
    }

    /*连续执行按钮*/
    @FXML
    public void onContinuityClick(ActionEvent event) {
        /*如果没有指令则直接返回*/
        if (!HasCode()) {
            button_continuity.setTextFill(Color.BLACK);
            return;
        }
        isContinue = true;
        button_single.setTextFill(Color.BLACK);
        button_continuity.setTextFill(Color.RED);
        AnimationPlay();
    }

    /*循环执行按钮*/
    @FXML
    public void onLoopClick(ActionEvent event) {
        if (animation == null) {
            R.warningStage.setContents("没有指令！", "没有正在执行的指令，无法循环执行", "请先执行一条指令");
            return;
        }
        /*初始为不循环，改为循环且将按钮文本颜色改为红*/
        if (isLooped == false) {
            isLooped = true;
            button_loop.setTextFill(Color.RED);
        } else {
            isLooped = false;
            button_loop.setTextFill(Color.BLACK);
        }
    }

    /*暂停执行按钮*/
    @FXML
    public void onPauseClick(ActionEvent event) {
        /*如果没有动画在播放则弹出提示*/
        if (animation == null) {
            R.warningStage.setContents("没有指令！", "没有正在执行的指令，无法暂停", "请先执行一条指令");
            return;
        }
        if (button_pause.getText().equals("暂停演示")) {
            animation.pause();
            mediaPlayer.pause();
            button_pause.setText("继续演示");
            return;
        }
        if (button_pause.getText().equals("继续演示")) {
            animation.play();
            mediaPlayer.play();
            button_pause.setText("暂停演示");
            return;
        }
    }

    /*复位按钮*/
    @FXML
    public void onRecoveryClick(ActionEvent event) {
        InitController.OnRecovery();
        InitController.InitShape();
        ButtonInit();
    }

    /*静音按钮*/
    @FXML
    public void onSoundClick(ActionEvent event) {
        /*允许指令执行之前将程序静音*/
        if (hasSound) {
            hasSound = false;
            button_sound.setStyle("-fx-background-image: url('/resource/images/silence.PNG');-fx-background-size: stretch");
            if (mediaPlayer != null) mediaPlayer.setVolume(0);
        } else {
            hasSound = true;
            button_sound.setStyle("-fx-background-image: url('/resource/images/sound.jpg');-fx-background-size: stretch");
            if (mediaPlayer != null) mediaPlayer.setVolume(1);
        }
    }

    private void AnimationPlay() {
        /*如果PC的值大于数据Cache的最大地址值则重置PC值*/
        if (R.Value_PC > 106) {
            R.Value_PC = R.PC_DEFAULT;
            /*清除已执行完成的指令，并加载新的指令*/
            LoadCode.CleanCode();
        }
        /*加载指令*/
        LoadCode.Load();

        /*如果当前动画对象为空则创建动画,否则直接播放动画*/
        if (animation == null) {
            /*获取当前PC值所对应的指令*/
            System.out.println("指令位置：" + String.valueOf(R.Value_PC - R.PC_DEFAULT));
            String OpCode = R.Txt_opCode[R.Value_PC - R.PC_DEFAULT].getText().trim();
            switch (OpCode) {
                case "MOV":
                    animation = AnimationMOV.CreateAnimation();
                    mediaPlayer = AudioPlayer.getAudio_MOV();
                    break;
                case "LAD":
                    animation = AnimationLAD.CreateAnimation();
                    mediaPlayer = AudioPlayer.getAudio_LAD();
                    break;
                case "ADD":
                    animation = AnimationADD.CreateAnimation();
                    mediaPlayer = AudioPlayer.getAudio_ADD();
                    break;
                case "STO":
                    int r3 = Integer.valueOf(R.Txt_R[3].getText());
                    /*STO指令R3值过大则暂停演示要求用户修改R3的值*/
                    if (r3 > 5) {
                        R.warningStage.setContents("R3地址越界", "R3值大于数据Cache的最大地址值！"
                                , "修改R3的值并重试");
                        return;
                    } else {

                        animation = AnimationSTO.CreateAnimation();
                        mediaPlayer = AudioPlayer.getAudio_STO();
                    }
                    break;
                case "JMP":
                    animation = AnimationJMP.CreateAnimation();
                    mediaPlayer = AudioPlayer.getAudio_JMP();
                    break;
                    /*没有对应的指令则直接结束*/
                default:
                    return;
            }
             /*动画完成事件*/
            animation.setOnFinished(e -> {
                animation = null;
            });

        /*音频播放前判断静音状态*/
            if (!hasSound)
                mediaPlayer.setVolume(0);
        /*音频播放完成销毁对象*/
        /*若音频播放完成才表示一个完整动画执行完成*/
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer = null;
            /*若为循环播放状态则将PC减一，重复执行*/
                if (isLooped) {
                    R.Value_PC--;
                    isLooped = false;
                    animation = null;
                    AnimationPlay();
                    onLoopClick(null);
                }
                 /*指令执行完成后判断是否为连续执行状态*/
                else if (isContinue) {
                    if (animation != null)
                        animation = null;
                /*若连续执行则回调连续执行方法*/
                    onContinuityClick(null);
                }
                /*不是连续执行则恢复按钮演示*/
                else {
                    button_single.setTextFill(Color.BLACK);
                }
            });
        /*动画开始前显示指针箭头，指针箭头的位置依据PC的值动态变化*/
            R.arrow.setOpacity(1);
            R.arrow.setLayoutY(layoutY + Spacing * (R.Value_PC - R.PC_DEFAULT));
        }
        animation.play();
        mediaPlayer.play();
    }
    /*初始化按钮状态*/
    private void ButtonInit() {
        button_continuity.setTextFill(Color.BLACK);
        button_loop.setTextFill(Color.BLACK);
        button_single.setTextFill(Color.BLACK);
        button_pause.setText("暂停演示");
        button_sound.setStyle("-fx-background-image: url('/resource/images/sound.jpg');-fx-background-size: stretch");
    }

    public static void AnimationIinit() {
        if (animation != null)
            animation.stop();
        if (mediaPlayer != null)
            mediaPlayer.stop();
        mediaPlayer = null;
        animation = null;
        isContinue = false;
        isLooped = false;
        hasSound = true;
        R.arrow.setOpacity(0);
    }


    /**
     * 判断是否有下一条指令
     */
    public static boolean HasCode() {
        /*判断是否还有指令*/
        /*1.若已选指令列表的Item对象为空则判定指令没有指令
        * 2.若已选指令的Item对像内容为空且指令Cache的第一项为空则判定为没有指令
        * */
        if (R.list_SelectCode.getItems() == null || R.list_SelectCode.getItems().isEmpty())
        {
            if (R.Txt_opCode[0].getText().length() == 0)
            {
                R.warningStage.setContents("没有指令", "没有选择指令，请先添加指令", "从指令列表选取指令！");
                return false;
            }
            if (R.Txt_opCode[R.Value_PC - R.PC_DEFAULT].getText().length() == 0) {
                R.warningStage.setContents("指令执行完成", "所有指令均已执行完成", "请添加指令以继续执行！");
                return false;
            }
        }
        return true;
    }

    /*获取当前是否有动画对象*/
    public static boolean hasAnimation() {
        if (animation == null) return false;
        return true;
    }
}
