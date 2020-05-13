package com.example.aniketkumar.mnnit_portal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;


@SuppressLint("ValidFragment")
public class ViewFlipperHome extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ViewFlipper vf;
    Context ctx;
 //   EditText info;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    int[] resources = {
            R.drawable.home1,
            R.drawable.home2,
            R.drawable.home3,
            R.drawable.home4,
            R.drawable.home5,
            R.drawable.home6,
            R.drawable.home7
    };
    public ViewFlipper mViewFlipper;
    public GestureDetector mGestureDetector;

    @SuppressLint("ValidFragment")
    public ViewFlipperHome(Context applicationContext) {
        this.ctx=applicationContext;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_view_flipper_home, container, false);
        mViewFlipper = (ViewFlipper) v.findViewById(R.id.viewFlipper);
        for (int i = 0; i < resources.length; i++) {
            ImageView imageView = new ImageView(ctx);
            imageView.setImageResource(resources[i]);
            mViewFlipper.addView(imageView);
        }
        MyGestureDetector customGestureDetector = new MyGestureDetector();
        mGestureDetector = new GestureDetector(ctx, customGestureDetector);
        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(3000);
        mViewFlipper.setInAnimation(ctx, android.R.anim.fade_in);
        mViewFlipper.setOutAnimation(ctx, android.R.anim.fade_out);
        return v;

    }
    class MyGestureDetector extends SimpleOnGestureListener {

        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_MAX_OFF_PATH = 250;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            System.out.println(" in onFling() :: ");
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
               // mViewFlipper.setInAnimation(ctx,R.anim.left_to_right);
               // mViewFlipper.setOutAnimation(outToLeftAnimation());
                mViewFlipper.showNext();
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
              //  mViewFlipper.setInAnimation(ctx, R.anim.leftin);
              //  mViewFlipper.setOutAnimation(ctx,R.anim.right_to_left);
                mViewFlipper.showPrevious();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
