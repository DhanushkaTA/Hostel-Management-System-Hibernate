package lk.ijse.hms_hibernet.util;

import animatefx.animation.Shake;
import animatefx.animation.ZoomIn;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class AnimeUtil {
    public static void setAnime(AnimeTypes animeType,Object place){
        switch (animeType){
            case SHAKE:
                new Shake((Node) place).play();
                break;
            case ZOOMIN:
                new ZoomIn((Node) place).play();
                break;
        }
    }

    public static void addCss(AnimeTypes animeTypes, TextField txt) {
        switch (animeTypes){
            case CORRECT:
                txt.getStylesheets().add("lk/ijse/hms_hibernet/assets/css/CorrectText.css");
                break;
            case WRONG:
                txt.getStylesheets().add("lk/ijse/hms_hibernet/assets/css/WrongText.css");
                break;
        }
    }

    public static void removeCss(AnimeTypes animeTypes, TextField txt) {
        switch (animeTypes){
            case CORRECT:
                txt.getStylesheets().remove("lk/ijse/hms_hibernet/assets/css/CorrectText.css");
                break;
            case WRONG:
                txt.getStylesheets().remove("lk/ijse/hms_hibernet/assets/css/WrongText.css");
                break;
        }
    }
}
