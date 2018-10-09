package com.steinbacher.jumpstar.util;

import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.ProductTypes;

/**
 * Created by stge on 09.10.18.
 */

public class PaidProducts {
    public final static String PREMIUM = "premium";
    public final static String PLAN_5 = "trainingsplan_5";

    public static boolean ownsProduct(Inventory.Products products, String sku) {
        return products.get(ProductTypes.IN_APP).isPurchased(sku);
    }
}
