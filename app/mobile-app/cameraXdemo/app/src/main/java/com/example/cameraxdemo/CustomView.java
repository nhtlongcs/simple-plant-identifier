package com.example.cameraxdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class CustomView extends View {

    private ArrayList<Rect> mRect;
    private ArrayList<ArrayList<Rect>> mRectAll;
    private ArrayList<ArrayList<String>> mClassAll;
    private Paint mPaintRect;
    private Paint mPaintStroke;
    private Paint mPaintRectActive;
    private Paint mPaintStrokeActive;
    private int mImageIndex;
    private int mRectIndex;
    private int imageLeft;
    private int imageTop;
    private int canvasHeight = 1375;
    private int canvasWidth = 1080;
    ArrayList<String> mImagePAll;
    private AddCardManager addCardManager;
    private CardView addCardView;
    private boolean isReady = false;

    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        mRect = new ArrayList<Rect>();

        mPaintRect = new Paint();
        mPaintRect.setColor(Color.GREEN);
        mPaintRect.setAlpha(50);

        mPaintStroke = new Paint();
        mPaintStroke.setColor(Color.RED);
        mPaintStroke.setStyle(Paint.Style.STROKE);
        mPaintStroke.setStrokeWidth(2);

        mPaintRectActive = new Paint();
        mPaintRectActive.setColor(Color.BLUE);
        mPaintRectActive.setAlpha(50);

        mPaintStrokeActive = new Paint();
        mPaintStrokeActive.setColor(Color.GREEN);
        mPaintStrokeActive.setStyle(Paint.Style.STROKE);
        mPaintStrokeActive.setStrokeWidth(2);

        mRectIndex = -1;

        imageLeft = 0;
        imageTop = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawBitmap(mImage, imageLeft, 0, null);
        for (int i = 0; i < mRect.size(); i++) {
            if (i == mRectIndex) {
                canvas.drawRect(mRect.get(i), mPaintStrokeActive);
                canvas.drawRect(mRect.get(i), mPaintRectActive);
            } else {
                canvas.drawRect(mRect.get(i), mPaintStroke);
                canvas.drawRect(mRect.get(i), mPaintRect);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                float x = event.getX();
                float y = event.getY();

                for (int i = 0; i < mRect.size(); i++) {
                    if (x < mRect.get(i).right && x > mRect.get(i).left && y > mRect.get(i).top && y < mRect.get(i).bottom) {
                        if (mRectIndex == i) {
                            mRectIndex = -1;
                            addCardView.setVisibility(View.GONE);
                            addCardManager.getAddButton().setVisibility(View.GONE);
                            addCardManager.getAddCompleteButton().setVisibility(View.GONE);
                        } else {
                            mRectIndex = i;
                            Bitmap image = BitmapFactory.decodeFile(mImagePAll.get(mImageIndex));
                            int l = mRectAll.get(mImageIndex).get(mRectIndex).left;
                            int t = mRectAll.get(mImageIndex).get(mRectIndex).top;
                            int w = mRectAll.get(mImageIndex).get(mRectIndex).right - mRectAll.get(mImageIndex).get(mRectIndex).left;
                            int h = mRectAll.get(mImageIndex).get(mRectIndex).bottom - mRectAll.get(mImageIndex).get(mRectIndex).top;
                            Bitmap sub_image = Bitmap.createBitmap(image, l, t, w, h);
                            Plant plant = LabelManager.getPlantByClass(mClassAll.get(mImageIndex).get(mRectIndex));
                            addCardView.setVisibility(View.VISIBLE);
                            addCardManager.getAddButton().setVisibility(View.VISIBLE);
                            addCardManager.setInfo(plant, sub_image);
                        }
                    }
                }
            }
            postInvalidate();
            return true;
        }
        return value;
    }

    private void resizeRect(int iHeight, int iWidth) {
        int reqWidth = 0, reqHeight = 0;
        float alpha;
        if ((float) canvasHeight / canvasWidth > (float) iHeight / iWidth) {
            reqWidth = canvasWidth;
            reqHeight = (int) canvasWidth * iHeight / iWidth;
            alpha = (float) canvasWidth / iWidth;
        } else {
            reqHeight = canvasHeight;
            reqWidth = (int) canvasHeight * iWidth / iHeight;
            alpha = (float) canvasHeight / iHeight;
        }

        imageLeft = (int) (canvasWidth - reqWidth) / 2;
        imageTop = (int) (canvasHeight - reqHeight) / 2;

        zoomRectByAlpha(alpha);
    }

    private void zoomRectByAlpha(float alpha) {
        for (int i = 0; i < mRect.size(); i++) {
            mRect.get(i).bottom = (int) (mRect.get(i).bottom * alpha) + imageTop;
            mRect.get(i).right = (int) (mRect.get(i).right * alpha) + imageLeft;
            mRect.get(i).left = (int) (mRect.get(i).left * alpha) + imageLeft;
            mRect.get(i).top = (int) (mRect.get(i).top * alpha) + imageTop;
        }
    }

    private ArrayList<Rect> cloneRect(ArrayList<Rect> origin) {
        ArrayList<Rect> newRects = new ArrayList<Rect>();
        Rect rect;
        for (int i = 0; i < origin.size(); i++) {
            rect = new Rect(origin.get(i));
            newRects.add(rect);
        }
        return newRects;
    }

    public void setInits(ArrayList<String> paths, ArrayList<ArrayList<Rect>> bounds, ArrayList<ArrayList<String>> classes, AddCardManager addCardd, CardView addCardVieww) {
        mImagePAll = paths;
        mRectAll = bounds;
        addCardManager = addCardd;
        addCardView = addCardVieww;
        mClassAll = classes;
    }

    public void drawBoundingBox(int index, int imageHeight, int imageWidth) {
        mImageIndex = index;
        mRectIndex = -1;
        if(isReady == true){
            mRect = cloneRect(mRectAll.get(index));
            resizeRect(imageHeight, imageWidth);
            postInvalidate();
        }
    }

    public void setAsyncBounds(ArrayList<ArrayList<Rect>> bounds, ArrayList<ArrayList<String>> classes, int imageHeigth, int imageWidth, int ind){
        mRectAll = bounds;
        mClassAll = classes;
        if(ind == mImageIndex){
            isReady = true;
            drawBoundingBox(mImageIndex, imageHeigth, imageWidth);
        }
    }

    public void setReady(){
        isReady = true;
    }
}
