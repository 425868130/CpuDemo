package Models;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Created by 42586 on 2016/12/14.
 */
public class AudioPlayer {
    /*创建音频索引地址*/
    private final static String Audio_ADD = AudioPlayer.class.getResource("/resource/audio/Audio_ADD.mp3").toString();
    private final static String Audio_JMP = AudioPlayer.class.getResource("/resource/audio/Audio_JMP.mp3").toString();
    private final static String Audio_LAD = AudioPlayer.class.getResource("/resource/audio/Audio_LAD.mp3").toString();
    private final static String Audio_MOV = AudioPlayer.class.getResource("/resource/audio/Audio_MOV.mp3").toString();
    private final static String Audio_STO = AudioPlayer.class.getResource("/resource/audio/Audio_STO.mp3").toString();


    /*各个音频对象的获取方法*/
    public static MediaPlayer getAudio_ADD() {
        return getPlayer(Audio_ADD);
    }

    public static MediaPlayer getAudio_JMP() {
        return getPlayer(Audio_JMP);
    }

    public static MediaPlayer getAudio_LAD() {
        return getPlayer(Audio_LAD);
    }

    public static MediaPlayer getAudio_MOV() {
        return getPlayer(Audio_MOV);
    }

    public static MediaPlayer getAudio_STO() {
        return getPlayer(Audio_STO);
    }

    private static MediaPlayer getPlayer(String url) {
        /*创建音乐播放者*/
        Media media = new Media(url);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        return mediaPlayer;
    }
}
