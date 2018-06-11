package com.gint.app.bisis.editor.edit.validation;

public class ValidatorManager {
  public static void registerValidator(Validator v) {
    validator = v;
  }

  public static Validator getValidator() {
    return validator;
  }

  private static Validator validator;
}
