package com.example.aniketkumar.mnnit_portal;

import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by Aniket Kumar on 16-Sep-18.
 */

public interface Menu {

    default void onclickLogReg(View view)
    {

    }
    default void onclickReg(View view)
    {
        Context context;
        context=view.getContext();
        Intent intent = new Intent(context, Signup.class);
        context.startActivity(intent);
    }


}
