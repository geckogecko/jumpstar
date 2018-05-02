package georg.steinbacher.community_jump_trainer.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import georg.steinbacher.community_jump_trainer.R;

/**
 * Created by Georg Steinbacher on 02.05.18.
 */

public class VerticalProgressInputView extends CardView {
    private View mRootView;
    private Context mContext;

    public VerticalProgressInputView(Context context) {
        super(context);
        init(context);
    }

    public VerticalProgressInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mRootView = inflate(context, R.layout.view_vertical_progress_input, this);
    }
}
