package com.nhubnigeria.engineering.psirs.utils;


import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class DismissKeyboard {

        public static void dismissKeyboard(Activity activity) {
            InputMethodManager inputManager = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (null != activity.getCurrentFocus())
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getApplicationWindowToken(), 0);
        }

}
