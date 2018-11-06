package com.steinbacher.jumpstar.util;

import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.ProductTypes;
import org.solovyev.android.checkout.Sku;

/**
 * Created by stge on 09.10.18.
 */

public class PaidProducts {
    public final static String PREMIUM = "premium";
    public final static String PLAN_5 = "trainingsplan_5";

    public static boolean ownsProduct(Inventory.Products products, String sku) {
        return products.get(ProductTypes.IN_APP).isPurchased(sku);
    }

    public static String getPice(Inventory.Products products, String sku) {
        Inventory.Product product = products.get(ProductTypes.IN_APP);

        String price = "";
        if(product.supported) {
            Sku loadedSku = product.getSku(sku);
            if(loadedSku != null)
                price = loadedSku.price;
        }

        return price;
    }
}
