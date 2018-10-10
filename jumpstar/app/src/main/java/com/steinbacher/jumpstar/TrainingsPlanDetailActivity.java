package com.steinbacher.jumpstar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.steinbacher.jumpstar.core.Equipment;
import com.steinbacher.jumpstar.core.TrainingsPlan;
import com.steinbacher.jumpstar.db.PlanReader;
import com.steinbacher.jumpstar.util.DrawableLoader;
import com.steinbacher.jumpstar.util.Factory;
import com.steinbacher.jumpstar.util.PaidProducts;
import com.steinbacher.jumpstar.view.EquipmentView;
import com.steinbacher.jumpstar.view.ExercisesView;

import org.solovyev.android.checkout.ActivityCheckout;
import org.solovyev.android.checkout.Billing;
import org.solovyev.android.checkout.BillingRequests;
import org.solovyev.android.checkout.Checkout;
import org.solovyev.android.checkout.EmptyRequestListener;
import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.ProductTypes;
import org.solovyev.android.checkout.Purchase;
import org.solovyev.android.checkout.Sku;

import static com.steinbacher.jumpstar.Configuration.CURRENT_TRAININGSPLANS_ID_KEY;

//TODO if this trainingsplan includes other trainingsplans -> show them here
public class TrainingsPlanDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "TrainingsPlanDetailActi";

    public static final String TRAININGS_PLAN_ID = "trainings_plan_id";
    public static final String TRAININGS_PLAN_IS_OWN_PLAN = "trainings_plan_is_own_plan";

    private TrainingsPlan mTrainingsPlan;

    private ActivityCheckout mCheckout;
    private boolean planPaided = false;
    private String mSku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainings_plan_detail);

        int trainingsPlanId = getIntent().getIntExtra(TRAININGS_PLAN_ID, -1);
        boolean isOwnPlan = getIntent().getBooleanExtra(TRAININGS_PLAN_IS_OWN_PLAN, false);

        if(isOwnPlan) {
            PlanReader planReader = new PlanReader(getApplicationContext());
            Cursor cursor = planReader.getById(trainingsPlanId);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                mTrainingsPlan = Factory.createTraingsPlan(cursor);
            } else {
                Log.e(TAG, "onCreate: own plan not found");
            }
        } else {
            if (trainingsPlanId == -1) {
                Log.e(TAG, "onCreate: No trainingsplanId provided");
                finish();
            } else {
                mTrainingsPlan = Factory.createTraingsPlan(trainingsPlanId);
            }
        }

        //title
        TextView txtName = findViewById(R.id.detail_trainings_plan_name);
        txtName.setText(mTrainingsPlan.getName());

        //image
        AppCompatImageView imgView = findViewById(R.id.image);
        DrawableLoader.loadPlanImage(getApplicationContext(), mTrainingsPlan, imgView);

        //Description
        TextView txtDescription = findViewById(R.id.detail_trainings_plan_description);
        txtDescription.setText(mTrainingsPlan.getDescription());


        //Equipment
        EquipmentView equipmentViewHolder= findViewById(R.id.equipment_view);
        List<Equipment> equipmentList = mTrainingsPlan.getNeededEquipment();
        equipmentViewHolder.setEquipment(equipmentList);

        //exercises
        ExercisesView exercisesView = findViewById(R.id.detail_trainings_plan_exercises);
        exercisesView.setTrainingsplan(mTrainingsPlan);

        //duration
        TextView durationTextView = findViewById(R.id.detail_plan_estimated_time);
        if(mTrainingsPlan.getEstimatedDurationSeconds() < 0) {
            LinearLayoutCompat general = findViewById(R.id.section_general);
            general.setVisibility(View.GONE);
        } else {
            int hours = (int) (mTrainingsPlan.getEstimatedDurationSeconds() / 3600);
            int minutes = (int) (mTrainingsPlan.getEstimatedDurationSeconds() % 3600) / 60;
            durationTextView.setText(getString(R.string.detail_plan_estimated_time, String.format("%02d:%02d", hours, minutes)));
        }

        //add button
        AppCompatButton btnAddTrainingsPlan = findViewById(R.id.detail_button_add_trainings_plan);
        final int[] currentPlans = Configuration.getIntArray(getApplicationContext(), CURRENT_TRAININGSPLANS_ID_KEY);
        final int planId = mTrainingsPlan.isOwnPlan() ? -mTrainingsPlan.getId() : mTrainingsPlan.getId();
        boolean planIsCurrentTrainingsPlan = false;
        for (int i = 0; i < currentPlans.length; i++) {
            if(currentPlans[i] == planId) {
                planIsCurrentTrainingsPlan = true;
                break;
            }
        }

        if(planIsCurrentTrainingsPlan) {
            btnAddTrainingsPlan.setVisibility(View.GONE);
        } else {
            btnAddTrainingsPlan.setOnClickListener(this);

            if(mTrainingsPlan.isPremium()) {
                LinearLayoutCompat buyLine = findViewById(R.id.buy_plan_line);
                btnAddTrainingsPlan.setVisibility(View.GONE);
                buyLine.setVisibility(View.VISIBLE);

                AppCompatButton buyPlanButton = findViewById(R.id.detail_button_buy_plan);
                buyPlanButton.setText(getString(R.string.detail_buy_trainingsplan_with_price, ""));
                buyPlanButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCheckout.whenReady(new Checkout.EmptyListener() {
                            @Override
                            public void onReady(BillingRequests requests) {
                                requests.purchase(ProductTypes.IN_APP, mSku, null, mCheckout.getPurchaseFlow());
                            }
                        });
                    }
                });

                AppCompatButton buyPremiumButton = findViewById(R.id.detail_button_buy_premium);
                buyPremiumButton.setText(getString(R.string.detail_buy_premium_with_price, ""));
                buyPremiumButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCheckout.whenReady(new Checkout.EmptyListener() {
                            @Override
                            public void onReady(BillingRequests requests) {
                                requests.purchase(ProductTypes.IN_APP, PaidProducts.PREMIUM, null, mCheckout.getPurchaseFlow());
                            }
                        });
                    }
                });
            }
        }

        if(mTrainingsPlan.isPremium()) {
            //billing
            Billing billing = BillingSingleton.getInstance(getApplicationContext()).getBilling();
            mCheckout = Checkout.forActivity(this, billing);
            mCheckout.start();
            mCheckout.createPurchaseFlow(new PurchaseListener());

            mInventory = mCheckout.makeInventory();

            mSku = "trainingsplan_" + mTrainingsPlan.getId();
            List<String> skus = new ArrayList<>();
            skus.add(mSku);
            skus.add(PaidProducts.PREMIUM);
            mInventory.load(Inventory.Request.create()
                    .loadAllPurchases()
                    .loadSkus(ProductTypes.IN_APP, skus), new InventoryCallback());
        }
    }

    @Override
    public void onClick(View v) {
        final int[] currentPlans = Configuration.getIntArray(getApplicationContext(), CURRENT_TRAININGSPLANS_ID_KEY);
        int[] newCurrentPlans = new int[currentPlans.length + 1];

        if(mTrainingsPlan.isOwnPlan()) {
            newCurrentPlans[0] = -mTrainingsPlan.getId();
        } else {
            newCurrentPlans[0] = mTrainingsPlan.getId();
        }

        for (int i = 0; i < currentPlans.length; i++) {
            newCurrentPlans[i + 1] = currentPlans[i];
        }
        Configuration.set(getApplicationContext(), CURRENT_TRAININGSPLANS_ID_KEY, newCurrentPlans);

        finish();
    }

    private Inventory mInventory;
    private class PurchaseListener extends EmptyRequestListener<Purchase> {
        @Override
        public void onSuccess(Purchase purchase) {
            planPaided = true;

            AppCompatButton btnAddTrainingsPlan = findViewById(R.id.detail_button_add_trainings_plan);
            btnAddTrainingsPlan.setVisibility(View.VISIBLE);

            LinearLayoutCompat buyLine = findViewById(R.id.buy_plan_line);
            buyLine.setVisibility(View.GONE);
        }

        @Override
        public void onError(int response, Exception e) {
            Log.e(TAG, "onError: Purchase failed");
        }
    }

    private class InventoryCallback implements Inventory.Callback {
        @Override
        public void onLoaded(Inventory.Products products) {
            AppCompatButton btnAddTrainingsPlan = findViewById(R.id.detail_button_add_trainings_plan);
            btnAddTrainingsPlan.setVisibility(View.VISIBLE);

            LinearLayoutCompat buyLine = findViewById(R.id.buy_plan_line);
            buyLine.setVisibility(View.GONE);

            if(PaidProducts.ownsProduct(products, mSku) && PaidProducts.ownsProduct(products, PaidProducts.PREMIUM)) {
                planPaided = true;
            } else {
                String planPrice = PaidProducts.getPice(products, mSku);
                AppCompatButton buyPlanButton = findViewById(R.id.detail_button_buy_plan);
                buyPlanButton.setText(getString(R.string.detail_buy_trainingsplan_with_price, planPrice));

                String premiumPrice = PaidProducts.getPice(products, PaidProducts.PREMIUM);
                AppCompatButton buypremiumButton = findViewById(R.id.detail_button_buy_premium);
                buypremiumButton.setText(getString(R.string.detail_buy_premium_with_price, premiumPrice));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTrainingsPlan.isPremium()) {
            mCheckout.stop();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mTrainingsPlan.isPremium()) {
            mCheckout.onActivityResult(requestCode, resultCode, data);
        }
    }
}
