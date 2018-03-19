package com.magicsoft.mylibrary;

import android.content.Context;
import android.view.View;

/**
 * FileName: pwChoiceHeader2
 *
 * @author : Lss kiwilss
 *         e-mail : kiwilss@163.com
 *         time   : 2018/3/19
 *         desc   : ${DESCRIPTION}
 *         Description: ${DESCRIPTION}
 */

public class pwChoiceHeader2 extends BottomPushPopupWindow<pwChoiceHeader2.ChoiceHeader> {


    public pwChoiceHeader2(Context context, ChoiceHeader choiceHeader) {
        super(context, choiceHeader);
    }

    @Override
    protected View generateCustomView(ChoiceHeader choiceHeader) {


        return null;
    }

    public interface ChoiceHeader{

    }
}
