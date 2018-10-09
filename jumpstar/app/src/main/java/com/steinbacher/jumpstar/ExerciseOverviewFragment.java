package com.steinbacher.jumpstar;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.InputType;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.steinbacher.jumpstar.core.Exercise;
import com.steinbacher.jumpstar.db.PlanReader;
import com.steinbacher.jumpstar.db.PlanWriter;
import com.steinbacher.jumpstar.util.PaidProducts;
import com.steinbacher.jumpstar.view.ExerciseOverviewLine;
import com.steinbacher.jumpstar.view.NewPlanLineView;

import org.solovyev.android.checkout.ActivityCheckout;
import org.solovyev.android.checkout.Billing;
import org.solovyev.android.checkout.BillingRequests;
import org.solovyev.android.checkout.Checkout;
import org.solovyev.android.checkout.EmptyRequestListener;
import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.ProductTypes;
import org.solovyev.android.checkout.Purchase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stge on 24.09.18.
 */

public class ExerciseOverviewFragment extends Fragment implements ExerciseOverviewLine.IExerciseOverviewLineListener{
    private static final String TAG = "ExerciseOverviewFragmen";
    private View mView;
    private FloatingActionButton mCreateNewPlanButton;
    private ExercisePageAdapter mPageAdapter;
    private NewPlanLineView mNewPlanLineView;

    private ActivityCheckout mCheckout;
    private boolean mHasPremium = false;

    private List<Exercise> mClickedExercises = new ArrayList<>();

    private boolean mShowAddExerciseButton = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_exercise_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;

        mCreateNewPlanButton = view.findViewById(R.id.create_new_trainingsplan);
        mCreateNewPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlanReader customPlans = new PlanReader(getContext());
                Cursor cursor = customPlans.getAll();
                if(mHasPremium || cursor.getCount() == 0) {
                    createPlanDialog();
                } else {
                    createBuyPremiumDialog();
                }
            }
        });

        mPageAdapter = new ExercisePageAdapter(getFragmentManager());
        mPageAdapter.setExerciseOverviewLineListener(this);

        ViewPager viewPager = mView.findViewById(R.id.pager);
        viewPager.setAdapter(mPageAdapter);

        mNewPlanLineView = view.findViewById(R.id.save_exercise_button);

        //billing
        Billing billing = BillingSingleton.getInstance(getContext()).getBilling();
        mCheckout = Checkout.forActivity(getActivity(), billing);
        mCheckout.start();
        mCheckout.createPurchaseFlow(new PurchaseListener());

        mInventory = mCheckout.makeInventory();
        mInventory.load(Inventory.Request.create()
                .loadAllPurchases()
                .loadSkus(ProductTypes.IN_APP, PaidProducts.PREMIUM), new InventoryCallback());
    }

    @Override
    public void onAddExerciseClicked(Exercise clickedExercise) {
        mClickedExercises.add(clickedExercise);
    }

    @Override
    public void onExerciseUndoClicked(Exercise undoExercise) {
        if(mClickedExercises.get(mClickedExercises.size()-1).getName().equals(undoExercise.getName())) {
            mClickedExercises.remove(mClickedExercises.size()-1);
        }
    }

    private void createPlanDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.DialogTheme));
        builder.setTitle(getContext().getString(R.string.create_new_plan_dialog_title));

        final LinearLayoutCompat layoutCompat = new LinearLayoutCompat(getContext());
        builder.setView(layoutCompat);
        builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AppCompatEditText planName = layoutCompat.findViewById(R.id.edit_text);
                AppCompatEditText planDescription = layoutCompat.findViewById(R.id.edit_text_description);
                final String inputString = planName.getText().toString();
                if(inputString.isEmpty()) {
                    Toast.makeText(getContext(), getString(R.string.create_new_plan_dialog_missing_name), Toast.LENGTH_SHORT).show();
                } else {
                    String descriptionString = planDescription.getText().toString();
                    if(descriptionString.isEmpty()) {
                        descriptionString = getString(R.string.detail_description_default);
                    }
                    startCreateNewPlanMode(inputString, descriptionString);
                    createAddPlanHelpDialog();
                }
            }
        });
        builder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();

        View view = dialog.getLayoutInflater().inflate(R.layout.alertdialog_edittext_rich, layoutCompat);
        final AppCompatEditText plan_name = view.findViewById(R.id.edit_text);
        plan_name.setInputType(InputType.TYPE_CLASS_TEXT);
        plan_name.setHint(R.string.create_new_plan_name_hint);

        final AppCompatEditText plan_description = view.findViewById(R.id.edit_text_description);
        plan_description.setInputType(InputType.TYPE_CLASS_TEXT);
        plan_description.setHint(R.string.create_new_plan_description_hint);

        dialog.show();
    }

    private void createAddPlanHelpDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.DialogTheme));
        builder.setTitle(getContext().getString(R.string.create_new_plan_info_title));
        builder.setMessage(R.string.create_new_plan_info_description);
        builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void createBuyPremiumDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.DialogTheme));
        builder.setTitle(getContext().getString(R.string.buy_premium_dialog_title));
        builder.setMessage(R.string.buy_premium_dialog_text);
        builder.setPositiveButton(R.string.button_buy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCheckout.whenReady(new Checkout.EmptyListener() {
                    @Override
                    public void onReady(BillingRequests requests) {
                        requests.purchase(ProductTypes.IN_APP, PaidProducts.PREMIUM, null, mCheckout.getPurchaseFlow());
                    }
                });
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void startCreateNewPlanMode(final String planName, final String planDescription) {
        createPlanModeOn(true);
        mNewPlanLineView.setPlanName(planName);
        mNewPlanLineView.setOnSaveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlanWriter writer = new PlanWriter(getContext());

                String description = planDescription;
                if(description == null) {
                    description = "";
                }

                writer.add(planName, description, mClickedExercises);

                //reset the state
                createPlanModeOn(false);

                Toast.makeText(getContext(), getString(R.string.create_new_plan_plan_created), Toast.LENGTH_SHORT).show();
            }
        });
        mNewPlanLineView.setOnCancelButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickedExercises = new ArrayList<>();

                //reset the state
                createPlanModeOn(false);
            }
        });
    }

    private void createPlanModeOn(boolean on) {
        if(on) {
            mShowAddExerciseButton = true;
            mPageAdapter.notifyDataSetChanged();

            mCreateNewPlanButton.setVisibility(View.INVISIBLE);

            mNewPlanLineView.setVisibility(View.VISIBLE);
        } else {
            mShowAddExerciseButton = false;
            mPageAdapter.notifyDataSetChanged();

            mCreateNewPlanButton.setVisibility(View.VISIBLE);
            mNewPlanLineView.setVisibility(View.GONE);
        }
    }

    public class ExercisePageAdapter extends FragmentStatePagerAdapter implements ExerciseOverviewLine.IExerciseOverviewLineListener {
        private Fragment mCurrentFragment;

        private ExerciseOverviewLine.IExerciseOverviewLineListener mListener;

        public void setExerciseOverviewLineListener(ExerciseOverviewLine.IExerciseOverviewLineListener listener) {
            mListener = listener;
        }

        public ExercisePageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ExercisePageFragment page = new ExercisePageFragment();
            page.setExerciseOverviewLineListener(this);
            switch (position) {
                case 0:
                    page.init(Exercise.Category.WARMUP, mShowAddExerciseButton);
                    break;
                case 1:
                    page.init(Exercise.Category.STRENGTH, mShowAddExerciseButton);
                    break;
                case 2:
                    page.init(Exercise.Category.PLYOMETRIC, mShowAddExerciseButton);
                    break;
                case 3:
                    page.init(Exercise.Category.STRETCH, mShowAddExerciseButton);
                    break;
            }

            return page;
        }

        public Fragment getCurrentFragment() {
            return mCurrentFragment;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            if (getCurrentFragment() != object) {
                mCurrentFragment = ((Fragment) object);
            }
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.detail_exercises_warmup).replace(":", "");
                case 1:
                    return getString(R.string.detail_exercises_strength).replace(":", "");
                case 2:
                    return getString(R.string.detail_exercises_plyometric).replace(":", "");
                case 3:
                    return getString(R.string.detail_exercises_stretch).replace(":", "");
                default:
                    return "";
            }
        }

        @Override
        public int getItemPosition(Object object) {
            // POSITION_NONE makes it possible to reload the PagerAdapter
            return POSITION_NONE;
        }

        @Override
        public void onAddExerciseClicked(Exercise clickedExercise) {
            if (mListener != null) {
                mListener.onAddExerciseClicked(clickedExercise);
            } else {
                Log.d(TAG, "onAddExerciseClicked: no listener set");
            }
        }

        @Override
        public void onExerciseUndoClicked(Exercise undoExercise) {
            if(mListener != null) {
                mListener.onExerciseUndoClicked(undoExercise);
            } else {
                Log.d(TAG, "onExerciseUndoClicked: no listener set");
            }
        }
    }

    private Inventory mInventory;
    private class PurchaseListener extends EmptyRequestListener<Purchase> {
        @Override
        public void onSuccess(Purchase purchase) {
            mHasPremium = true;
            Toast.makeText(getContext(), getString(R.string.buy_premium_dialog_success), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(int response, Exception e) {
            Toast.makeText(getContext(), getString(R.string.buy_premium_dialog_fail), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "onError: Purchase failed");
        }
    }

    private class InventoryCallback implements Inventory.Callback {
        @Override
        public void onLoaded(Inventory.Products products) {
            if(PaidProducts.ownsProduct(products, PaidProducts.PREMIUM)) {
                mHasPremium = true;
            }
        }
    }
}
