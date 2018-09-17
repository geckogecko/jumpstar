package com.steinbacher.jumpstar;

import android.content.Context;

import org.solovyev.android.checkout.Billing;

/**
 * Created by stge on 17.09.18.
 */

class BillingSingleton {
    private static BillingSingleton ourInstance = null;

    static BillingSingleton getInstance(Context context) {
        if(ourInstance == null) {
            ourInstance =  new BillingSingleton(context);
        }
        return ourInstance;
    }

    private Billing mBilling;
    private BillingSingleton(Context context) {
        mBilling = new Billing(context, new Billing.DefaultConfiguration() {
            @Override
            public String getPublicKey() {
                return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA01SS71VfH91qM4yputaoBBiV/8QevoDTvCLK1BBIli1+lGMYAIG/RaFBLL02foCoGKWkdaHhGF84ODfjCesaBbbJV0h7OtUZA92FNXKrNG+lmAHkHcmbdd4rXuZ7ASeH+dwbNtdvBTY65kkIAr4Tgi2sdxlVVpNg1SoIW8E/Cq0RAGeUTEmQBEQVP85xkb5Z3XnGrIUANnVNnZem+wete4RyMQWGofvk1216OemEhUSG+xCrkjBp5Or0JGH2PFTVkIuJ17t5igMH3VukKxUs6l64qgEyhe1uePiVBSdT+CqoRToa594N8RONkXq9PrThjde/wz6Csuzmu6GQBomUDQIDAQAB";
            }
        });
    }

    public Billing getBilling() {
        return mBilling;
    }
}
