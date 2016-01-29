package Views;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.mixalis.psagmenos.R;

/**
 * Created by h.lionakis on 29/1/2016.
 */
public class CustomImageView extends ImageView implements View.OnClickListener {

    private View.OnClickListener clickListener;

    public CustomImageView(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if (l == this) {
            super.setOnClickListener(l);
        } else {
            clickListener = l;
        }
    }

    @Override
    public void onClick(View v) {
        // start the Animation...
        // handle click event yourself and pass the event to supplied
        // listener also...
        Animation animFadein = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);

        v.startAnimation(animFadein);
        if (clickListener != null) {
            clickListener.onClick(this);
        }
    }

}
