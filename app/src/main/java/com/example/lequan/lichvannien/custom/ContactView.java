package com.example.lequan.lichvannien.custom;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.rey.material.drawable.BlankDrawable;
import com.rey.material.util.ThemeUtil;
import com.rey.material.util.ViewUtil;
import com.rey.material.widget.ImageButton;
import com.rey.material.widget.RippleManager;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;
import com.example.lequan.lichvannien.R;

public class ContactView extends FrameLayout implements Target {
    private TextView mAddressView;
    private AvatarDrawable mAvatarDrawable;
    private int mAvatarSize;
    private ImageButton mButton;
    private int mButtonSize;
    private int mMinHeight;
    private TextView mNameView;
    private RippleManager mRippleManager = new RippleManager();
    private int mSpacing;

    private class AvatarDrawable extends Drawable {
        private Bitmap mBitmap;
        private BitmapShader mBitmapShader;
        private Matrix mMatrix;
        private Paint mPaint = new Paint(1);

        public AvatarDrawable() {
            this.mPaint.setStyle(Style.FILL);
            this.mMatrix = new Matrix();
        }

        public void setImage(Bitmap bm) {
            if (this.mBitmap != bm) {
                this.mBitmap = bm;
                if (this.mBitmap != null) {
                    this.mBitmapShader = new BitmapShader(this.mBitmap, TileMode.CLAMP, TileMode.CLAMP);
                    updateMatrix();
                }
                invalidateSelf();
            }
        }

        private void updateMatrix() {
            if (this.mBitmap != null) {
                Rect bounds = getBounds();
                if (bounds.width() != 0 && bounds.height() != 0) {
                    this.mMatrix.reset();
                    float scale = ((float) bounds.height()) / ((float) Math.min(this.mBitmap.getWidth(), this.mBitmap.getHeight()));
                    this.mMatrix.setScale(scale, scale, 0.0f, 0.0f);
                    this.mMatrix.postTranslate(bounds.exactCenterX() - ((((float) this.mBitmap.getWidth()) * scale) / 2.0f), bounds.exactCenterY() - ((((float) this.mBitmap.getHeight()) * scale) / 2.0f));
                    this.mBitmapShader.setLocalMatrix(this.mMatrix);
                }
            }
        }

        protected void onBoundsChange(Rect bounds) {
            updateMatrix();
        }

        public void draw(Canvas canvas) {
            if (this.mBitmap != null) {
                Rect bounds = getBounds();
                float x = bounds.exactCenterX();
                float y = bounds.exactCenterY();
                float radius = ((float) bounds.height()) / 2.0f;
                this.mPaint.setShader(this.mBitmapShader);
                canvas.drawCircle(x, y, radius, this.mPaint);
            }
        }

        public void setAlpha(int alpha) {
            this.mPaint.setAlpha(alpha);
        }

        public void setColorFilter(ColorFilter cf) {
            this.mPaint.setColorFilter(cf);
        }

        public int getOpacity() {
            return -3;
        }
    }

    public ContactView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public ContactView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public ContactView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public ContactView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        setWillNotDraw(false);
        this.mRippleManager.onCreate(this, context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ActionBar, defStyleAttr, defStyleRes);
        this.mAvatarSize = a.getDimensionPixelSize(4, ThemeUtil.dpToPx(context, 40));
        this.mSpacing = a.getDimensionPixelOffset(5, ThemeUtil.dpToPx(context, 8));
        this.mMinHeight = a.getDimensionPixelOffset(0, 0);
        int avatarSrc = a.getResourceId(3, 0);
        this.mNameView = new TextView(context);
        this.mNameView.setGravity(GravityCompat.START);
        this.mNameView.setSingleLine(true);
        this.mNameView.setEllipsize(TruncateAt.END);
        int nameTextSize = a.getDimensionPixelSize(8, 0);
        ColorStateList nameTextColor = a.getColorStateList(9);
        int nameTextAppearance = a.getResourceId(7, 0);
        if (nameTextAppearance > 0) {
            this.mNameView.setTextAppearance(context, nameTextAppearance);
        }
        if (nameTextSize > 0) {
            this.mNameView.setTextSize(0, (float) nameTextSize);
        }
        if (nameTextColor != null) {
            this.mNameView.setTextColor(nameTextColor);
        }
        setNameText(a.getString(6));
        this.mAddressView = new TextView(context);
        this.mAddressView.setGravity(GravityCompat.START);
        this.mAddressView.setSingleLine(true);
        this.mAddressView.setEllipsize(TruncateAt.END);
        int addressTextSize = a.getDimensionPixelSize(12, 0);
        ColorStateList addressTextColor = a.getColorStateList(13);
        int addressTextAppearance = a.getResourceId(11, 0);
        if (addressTextAppearance > 0) {
            this.mAddressView.setTextAppearance(context, addressTextAppearance);
        }
        if (addressTextSize > 0) {
            this.mAddressView.setTextSize(0, (float) addressTextSize);
        }
        if (addressTextColor != null) {
            this.mAddressView.setTextColor(addressTextColor);
        }
        setAddressText(a.getString(10));
        this.mButtonSize = a.getDimensionPixelOffset(2, 0);
        if (this.mButtonSize > 0) {
            this.mButton = new ImageButton(context);
            int resId = a.getResourceId(1, 0);
            if (resId != 0) {
                this.mButton.setImageResource(resId);
            }
            ViewUtil.setBackground(this.mButton, BlankDrawable.getInstance());
            this.mButton.setScaleType(ScaleType.CENTER_INSIDE);
            this.mButton.setFocusableInTouchMode(false);
            this.mButton.setFocusable(false);
            this.mButton.setClickable(false);
        }
        a.recycle();
        addView(this.mNameView);
        addView(this.mAddressView);
        if (this.mButton != null) {
            addView(this.mButton);
        }
        this.mAvatarDrawable = new AvatarDrawable();
        if (avatarSrc != 0) {
            setAvatarResource(avatarSrc);
        }
    }

    public void setOnClickListener(OnClickListener l) {
        if (l == this.mRippleManager) {
            super.setOnClickListener(l);
            return;
        }
        this.mRippleManager.setOnClickListener(l);
        setOnClickListener(this.mRippleManager);
    }

    public boolean onTouchEvent(@NonNull MotionEvent event) {
        return this.mRippleManager.onTouchEvent(this, event) || super.onTouchEvent(event);
    }

    public void setAvatarBitmap(Bitmap bm) {
        this.mAvatarDrawable.setImage(bm);
        invalidate();
    }

    public void setAvatarResource(int id) {
        if (id != 0) {
            setAvatarBitmap(BitmapFactory.decodeResource(getContext().getResources(), id));
        }
    }

    public void setAvatarDrawable(Drawable drawable) {
        if (drawable != null) {
            if (drawable instanceof BitmapDrawable) {
                setAvatarBitmap(((BitmapDrawable) drawable).getBitmap());
                return;
            }
            Bitmap bm = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(bm);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            setAvatarBitmap(bm);
        }
    }

    public void setNameText(CharSequence name) {
        this.mNameView.setText(name);
        this.mNameView.setVisibility(TextUtils.isEmpty(name) ? 8 : 0);
    }

    public void setAddressText(CharSequence address) {
        this.mAddressView.setText(address);
        this.mAddressView.setVisibility(TextUtils.isEmpty(address) ? 8 : 0);
    }

    public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
        setAvatarBitmap(bitmap);
    }

    public void onBitmapFailed(Drawable errorDrawable) {
        setAvatarDrawable(errorDrawable);
    }

    public void onPrepareLoad(Drawable placeHolderDrawable) {
        setAvatarDrawable(placeHolderDrawable);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int nonTextWidth = (this.mAvatarSize + (this.mSpacing * 3)) + this.mButtonSize;
        int ws = MeasureSpec.makeMeasureSpec(widthSize - nonTextWidth, widthMode == 0 ? widthMode : Integer.MIN_VALUE);
        int hs = MeasureSpec.makeMeasureSpec(0, 0);
        this.mNameView.measure(ws, hs);
        this.mAddressView.measure(ws, hs);
        if (this.mButton != null) {
            this.mButton.measure(MeasureSpec.makeMeasureSpec(this.mButtonSize, 1073741824), hs);
        }
        int width = widthMode == 1073741824 ? widthSize : Math.max(this.mNameView.getMeasuredWidth(), this.mAddressView.getMeasuredWidth()) + nonTextWidth;
        int height = Math.max(this.mAvatarSize + (this.mSpacing * 2), this.mNameView.getMeasuredHeight() + this.mAddressView.getMeasuredHeight());
        switch (heightMode) {
            case Integer.MIN_VALUE:
                height = Math.min(height, heightSize);
                break;
            case 1073741824:
                height = heightSize;
                break;
        }
        height = Math.max(this.mMinHeight, height);
        if (this.mButton != null) {
            this.mButton.measure(MeasureSpec.makeMeasureSpec(this.mButtonSize, 1073741824), MeasureSpec.makeMeasureSpec(height, 1073741824));
        }
        setMeasuredDimension(width, height);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childRight = right - left;
        int childBottom = bottom - top;
        int y = (childBottom - this.mAvatarSize) / 2;
        int childLeft = 0 + this.mSpacing;
        this.mAvatarDrawable.setBounds(childLeft, y, this.mAvatarSize + childLeft, this.mAvatarSize + y);
        childLeft += this.mAvatarSize + this.mSpacing;
        if (this.mButton != null) {
            this.mButton.layout(childRight - this.mButtonSize, 0, childRight, childBottom);
            childRight -= this.mButtonSize;
        }
        int childTop;
        if (this.mNameView.getVisibility() == 0) {
            if (this.mAddressView.getVisibility() == 0) {
                childTop = ((childBottom - this.mNameView.getMeasuredHeight()) - this.mAddressView.getMeasuredHeight()) / 2;
                this.mNameView.layout(childLeft, childTop, childRight - this.mSpacing, this.mNameView.getMeasuredHeight() + childTop);
                childTop += this.mNameView.getMeasuredHeight();
                this.mAddressView.layout(childLeft, childTop, childRight - this.mSpacing, this.mAddressView.getMeasuredHeight() + childTop);
                return;
            }
            childTop = (childBottom - this.mNameView.getMeasuredHeight()) / 2;
            this.mNameView.layout(childLeft, childTop, childRight - this.mSpacing, this.mNameView.getMeasuredHeight() + childTop);
        } else if (this.mAddressView.getVisibility() == 0) {
            childTop = (childBottom - this.mAddressView.getMeasuredHeight()) / 2;
            this.mAddressView.layout(childLeft, childTop, childRight - this.mSpacing, this.mAddressView.getMeasuredHeight() + childTop);
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.mAvatarDrawable.draw(canvas);
    }
}
