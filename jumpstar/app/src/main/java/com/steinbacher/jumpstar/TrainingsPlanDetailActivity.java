package com.steinbacher.jumpstar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.steinbacher.jumpstar.core.Equipment;
import com.steinbacher.jumpstar.core.TrainingsPlan;
import com.steinbacher.jumpstar.util.Factory;
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

import static com.steinbacher.jumpstar.Configuration.CURRENT_TRAININGSPLANS_ID_KEY;

//TODO if this trainingsplan includes other trainingsplans -> show them here
public class TrainingsPlanDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "TrainingsPlanDetailActi";
    public static final String TRAININGS_PLAN_ID = "trainings_plan_id";

    private TrainingsPlan mTrainingsPlan;

    private ActivityCheckout mCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainings_plan_detail);

        Billing billing = BillingSingleton.getInstance(getApplicationContext()).getBilling();
        mCheckout = Checkout.forActivity(this, billing);

        int trainingsPlanId = getIntent().getIntExtra(TRAININGS_PLAN_ID, -1);
        if(trainingsPlanId == -1) {
            Log.e(TAG, "onCreate: No trainingsplanId provided");
            finish();
        } else {
            mTrainingsPlan = Factory.createTraingsPlan(trainingsPlanId);
        }

        //title
        TextView txtName = findViewById(R.id.detail_trainings_plan_name);
        txtName.setText(mTrainingsPlan.getName());

        //image
        AppCompatImageView imgView = findViewById(R.id.image);
        final String imageName = "trainingsplan_" + mTrainingsPlan.getId();
        final int resourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());

        if(resourceId != 0) {
            Drawable d = getResources().getDrawable(resourceId);
            imgView.setImageDrawable(d);
        } else {
            Log.e(TAG, "image for trainingsplan: " + imageName + " not found");
            //TODO what should we do if the image is not found?
        }

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
        int hours = (int) (mTrainingsPlan.getEstimatedDurationSeconds() / 3600);
        int minutes = (int) (mTrainingsPlan.getEstimatedDurationSeconds() % 3600) / 60;
        durationTextView.setText(getString(R.string.detail_plan_estimated_time, String.format("%02d:%02d", hours, minutes)));

        //add button
        AppCompatButton btnAddTrainingsPlan = findViewById(R.id.detail_button_add_trainings_plan);
        final int[] currentPlans = Configuration.getIntArray(getApplicationContext(), CURRENT_TRAININGSPLANS_ID_KEY);
        boolean planIsCurrentTrainingsPlan = false;
        for (int i = 0; i < currentPlans.length; i++) {
            if(currentPlans[i] == mTrainingsPlan.getId()) {
                planIsCurrentTrainingsPlan = true;
                break;
            }
        }

        if(planIsCurrentTrainingsPlan) {
            btnAddTrainingsPlan.setVisibility(View.GONE);
        } else {
            btnAddTrainingsPlan.setOnClickListener(this);

            if(mTrainingsPlan.isPremium()) {
                btnAddTrainingsPlan.setText(R.string.detail_buy_trainingsplan);
            }
        }

        //billing
        mCheckout.start();
        mCheckout.createPurchaseFlow(new PurchaseListener());

        mInventory = mCheckout.makeInventory();
        mInventory.load(Inventory.Request.create()
                .loadAllPurchases()
                .loadSkus(ProductTypes.IN_APP, "trainingsplan_5"), new InventoryCallback());
    }

    @Override
    public void onClick(View v) {
        if(mTrainingsPlan.isPremium()) {
            mCheckout.whenReady(new Checkout.EmptyListener() {
                @Override
                public void onReady(BillingRequests requests) {
                    requests.purchase(ProductTypes.IN_APP, "trainingsplan_5", null, mCheckout.getPurchaseFlow());
                }
            });
        } else {
            final int[] currentPlans = Configuration.getIntArray(getApplicationContext(), CURRENT_TRAININGSPLANS_ID_KEY);
            int[] newCurrentPlans = new int[currentPlans.length + 1];
            newCurrentPlans[0] = mTrainingsPlan.getId();
            for (int i = 0; i < currentPlans.length; i++) {
                newCurrentPlans[i + 1] = currentPlans[i];
            }
            Configuration.set(getApplicationContext(), CURRENT_TRAININGSPLANS_ID_KEY, newCurrentPlans);

            finish();
        }
    }

    private Inventory mInventory;
    private class PurchaseListener extends EmptyRequestListener<Purchase> {
        @Override
        public void onSuccess(Purchase purchase) {
            // here you can process the loaded purchase
        }

        @Override
        public void onError(int response, Exception e) {
            // handle errors here
        }
    }

    private class InventoryCallback implements Inventory.Callback {
        @Override
        public void onLoaded(Inventory.Products products) {
            // your code here
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCheckout.stop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCheckout.onActivityResult(requestCode, resultCode, data);
    }
}
