package indi.ss.pipes.taobao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * <LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:paddingBottom="6dp"
    android:paddingLeft="4dp"
    android:paddingRight="4dp"
    android:paddingTop="6dp">

    <ImageView
        android:id="@+id/icon"
        android:layout_width="80dp"
        android:layout_height="80dp" />

    <RelativeLayout
        android:layout_marginLeft="8dp"
        android:layout_width="match_parent"
        android:layout_height="80dp">

        <TextView
            android:id="@+id/title"
            android:maxLines="2"
            android:textSize="15dp"
            android:textColor="#000000"
            tools:text="This is title\nand title"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

        <TextView
            android:id="@+id/price"
            android:layout_below="@+id/title"
            android:layout_above="@+id/origin"
            android:maxLines="1"
            android:textSize="14dp"
            android:textColor="#ff5e14"
            tools:text="12.32"
            android:gravity="center|left"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />

        <TextView
            android:id="@+id/origin"
            android:layout_alignParentBottom="true"
            android:maxLines="1"
            android:textSize="12dp"
            android:textColor="#6e6e6e"
            tools:text="12.32"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

    </RelativeLayout>

</LinearLayout>
 */

public class TaobaoViewGenerator {

    private float scale = 1f;

    private Context context;
    ImageView icon;
    TextView title, origin, price;

    public TaobaoViewGenerator(Context context) {
        this.context = context;
        scale = context.getResources().getDisplayMetrics().density;
    }

    @SuppressLint("ResourceType")
    public View get(){
        CardView cardView = new CardView(context, null);
        int left = dip2px(4);
        int top = dip2px(6);
        cardView.setPadding(left, top, left, top);

        LinearLayout root = new LinearLayout(context, null);
        cardView.addView(root);

        icon = new ImageView(context, null);
        LinearLayout.LayoutParams iconLp = new LinearLayout.LayoutParams(
                dip2px(80), dip2px(80));
        icon.setLayoutParams(iconLp);
        root.addView(icon);

        RelativeLayout detailRlt = new RelativeLayout(context, null);
        LinearLayout.LayoutParams detailLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dip2px(80));
        detailLp.leftMargin = dip2px(8);
        detailRlt.setLayoutParams(detailLp);
        root.addView(detailRlt);

        title = new TextView(context, null);
        title.setId(1);
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        title.setMaxLines(2);
        title.setTextColor(Color.BLACK);

        detailRlt.addView(title);

        origin = new TextView(context, null);
        origin.setId(2);
        origin.setTextColor(Color.parseColor("#6e6e6e"));
        origin.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        origin.setMaxLines(1);
        RelativeLayout.LayoutParams originLp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        originLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        origin.setLayoutParams(originLp);
        detailRlt.addView(origin);

        price = new TextView(context, null);
        price.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        price.setTextColor(Color.parseColor("#ff5e14"));
        price.setMaxLines(1);
        RelativeLayout.LayoutParams priceLp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        originLp.addRule(RelativeLayout.ABOVE, 2);
        originLp.addRule(RelativeLayout.BELOW, 1);
        price.setLayoutParams(priceLp);
        detailRlt.addView(price);

        return cardView;
    }

    private int dip2px(float dpValue) {
        return (int) (dpValue * scale + 0.5f);
    }

}