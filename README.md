# ProjectFittness
Addition to project

    <LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:orientation="vertical"
    android:layout_height="match_parent" android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    android:paddingBottom="@dimen/activity_vertical_margin" tools:context=".MainActivity"
        android:weightSum="1">


        <Button
            style="?android:attr/buttonStyleSmall"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Back"
            android:id="@+id/button" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textAppearance="?android:attr/textAppearanceLarge"
            android:text="Food Group Tracker"
            android:id="@+id/textView"
            android:layout_gravity="center_horizontal" />

        <LinearLayout
        android:orientation="horizontal"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center">

        </LinearLayout>

        <LinearLayout
            android:orientation="vertical"
            android:layout_width="match_parent"
            android:layout_height="192dp"
            android:layout_weight="0.90" >

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textAppearance="?android:attr/textAppearanceMedium"
                android:text="Food Groups"
                android:id="@+id/textView2"/>

            <CheckBox
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Fruits 2 to 4 Servings"
                android:id="@+id/chkFruit"
                android:onClick="onClickFruit"
                android:checked="false" />
            <CheckBox
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Vegetables 3 to 5 Servings"
                android:id="@+id/chkVegetables"
                android:onClick="onClickVegetables"
                android:checked="false" />
            <CheckBox
                android:layout_width="86dp"
                android:layout_height="wrap_content"
                android:text="Grains 6 Servings"
                android:id="@+id/chkMeatsEggsNuts2to3Servings"
                android:onClick="onClickCafe"
                android:checked="false" />
            <CheckBox
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Meats, Eggs &amp; Nuts 2 to 3 Servings"
                android:id="@+id/chkDiner"
                android:onClick="onClickDiner"
                android:checked="false" />
            <CheckBox
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Dairy 2 to 3 Servings"
                android:id="@+id/chkDairy2to3Servings"
                android:onClick="onClickCafeteria"
                android:checked="false" />
            <CheckBox
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Fats USE SPARINGLY"
                android:id="@+id/chkFats"
                android:onClick="onClickBuffet"
                android:checked="false" />

        </LinearLayout>

    </LinearLayout>
