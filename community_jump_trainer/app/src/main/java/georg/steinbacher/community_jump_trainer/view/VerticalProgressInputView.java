package georg.steinbacher.community_jump_trainer.view;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import georg.steinbacher.community_jump_trainer.Configuration;
import georg.steinbacher.community_jump_trainer.R;
import georg.steinbacher.community_jump_trainer.db.VerticalHeightWriter;


/**
 * Created by Georg Steinbacher on 02.05.18.
 */

public class VerticalProgressInputView extends CardView {
    private View mRootView;
    private Context mContext;
    private Button mButton;

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
        mButton = mRootView.findViewById(R.id.button_openInout_dialog);
        mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(mContext, R.style.AlertDialogStyle));
        builder.setTitle(mContext.getString(R.string.hint_vertical_reach));

        final EditText input = new EditText(mContext);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        if(Configuration.getString(mContext, Configuration.UNIT_LOCAL_KEY, Configuration.UnitLocal.METRIC.name())
                .equals(Configuration.UnitLocal.METRIC.name())) {
            input.setHint(mContext.getResources().getString(R.string.reach_height_input_hint,
                    mContext.getResources().getString(R.string.centimeters_short)));
        } else {
            input.setHint(mContext.getResources().getString(R.string.reach_height_input_hint,
                    mContext.getResources().getString(R.string.inches_short)));
        }
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final String inputString =  input.getText().toString();
                if(inputString.isEmpty()) {
                    dialog.cancel();
                } else {
                    VerticalHeightWriter writer = new VerticalHeightWriter(mContext);
                    if(Configuration.getString(mContext, Configuration.UNIT_LOCAL_KEY, Configuration.UnitLocal.METRIC.name())
                            .equals(Configuration.UnitLocal.METRIC.name())) {
                        writer.add(TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis()), Double.valueOf(inputString));
                    } else {
                        writer.add(TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis()), inchesToCm(Double.valueOf(inputString)));
                    }

                    if (mListener != null) {
                        mListener.onInputDone();
                    }
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    double inchesToCm(double inches) {
        return inches * 2.54;
    }
}
