package georg.steinbacher.community_jump_trainer.view;

import android.support.v7.widget.AppCompatEditText;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import georg.steinbacher.community_jump_trainer.R;
import georg.steinbacher.community_jump_trainer.db.VerticalHeightWriter;


/**
 * Created by Georg Steinbacher on 02.05.18.
 */

public class VerticalProgressInputView extends CardView implements TextView.OnEditorActionListener {
    private View mRootView;
    private Context mContext;
    private AppCompatEditText mTextInput;

    private IInputDoneListener mListener;

    public interface IInputDoneListener {
        void onInputDone();
    }

    public VerticalProgressInputView(Context context) {
        super(context);
        init(context);
    }

    public VerticalProgressInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setListener(IInputDoneListener listener) {
        mListener = listener;
    }

    public void removeListener() {
        mListener = null;
    }

    private void init(Context context) {
        mContext = context;
        mRootView = inflate(context, R.layout.view_vertical_progress_input, this);
        mTextInput = findViewById(R.id.text_input);
        mTextInput.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_DONE) {
            VerticalHeightWriter writer = new VerticalHeightWriter(mContext);
            writer.add(System.currentTimeMillis(), Double.valueOf(v.getText().toString()));

            v.setText("");

            if(mListener != null) {
                mListener.onInputDone();
            }
        }
        return false;
    }
}
