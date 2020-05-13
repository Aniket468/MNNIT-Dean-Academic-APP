package com.example.aniketkumar.mnnit_portal;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class ImageDialog extends Dialog implements View.OnClickListener {
    Context ctx;
  //  Button yes,no;
    ImageView iv;
    Bitmap bitmap;
    public ImageDialog(@NonNull Context context, Bitmap bitmap) {
        super(context);
        this.ctx=context;
        this.bitmap=bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.imagedialog_);
        iv=findViewById(R.id.iv);
        iv.setImageBitmap(bitmap);

    }


    @Override
    public void onClick(View view) {

    }
}
