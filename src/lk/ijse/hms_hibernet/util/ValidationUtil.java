package lk.ijse.hms_hibernet.util;

import animatefx.animation.Shake;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.regex.Pattern;

public class ValidationUtil {
    public static boolean validation(Pattern pattern, TextField textField){
        if(pattern.matcher(textField.getText()).matches()){
            AnimeUtil.addCss(AnimeTypes.CORRECT,textField);
            AnimeUtil.removeCss(AnimeTypes.WRONG,textField);
            return true;
        }else {
            AnimeUtil.removeCss(AnimeTypes.CORRECT,textField);
            AnimeUtil.addCss(AnimeTypes.WRONG,textField);
//            AnimeUtil.setAnime(AnimeTypes.SHAKE,textField);
            return false;
        }
    }

    public static boolean validationList(List<Pattern>patternList,List<TextField>textFieldList){
        for (int i=0;i<patternList.size();i++){
            if(!validation(patternList.get(i),textFieldList.get(i))){
                new Shake(textFieldList.get(i)).play();
                return false;
            }
        }
        return true;
    }
}
