package georg.steinbacher.community_jump_trainer;

import android.content.Context;
import android.os.Build;

import com.stephentuso.welcome.FragmentWelcomePage;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;

import java.util.Locale;

public class SetupActivity extends WelcomeActivity {
    private static final String TAG = "SetupActivity";

    //https://github.com/stephentuso/welcome-android

    @Override
    protected WelcomeConfiguration configuration() {
        //set the correct unit system based on the locale
        final Locale locale = getCurrentLocale(getApplicationContext());
        Configuration.setUnitLocal(getApplicationContext(), locale);

        return new WelcomeConfiguration.Builder(this)
                .page(new TitlePage(R.drawable.ic_home_black_24dp,
                        "Welcome")
                .background(R.color.colorAccent))
                .page(new FragmentWelcomePage() {
                    @Override
                    protected android.support.v4.app.Fragment fragment() {
                        return new SetupReachHeightFragment();
                    }
                }.background(R.color.colorPrimary))
                .swipeToDismiss(true)
                .canSkip(true)
                .exitAnimation(android.R.anim.fade_out)
                .build();
    }

    private Locale getCurrentLocale(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return context.getResources().getConfiguration().getLocales().get(0);
        } else{
            //noinspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }
}
