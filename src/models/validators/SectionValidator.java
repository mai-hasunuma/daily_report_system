package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.Section;


public class SectionValidator {
    public static List<String> validates(Section s, Boolean name_duplicate_check) {
        List<String> errors = new ArrayList<String>();

        String name_error = _validateName(s.getName());
        if(!name_error.equals("")) {
            errors.add(name_error);
        }

        return errors;
    }


    // 社員名の必須入力チェック
    private static String _validateName(String name) {
        if(name == null || name.equals("")) {
            return "氏名を入力してください";
        }
        return "";
    }
}
