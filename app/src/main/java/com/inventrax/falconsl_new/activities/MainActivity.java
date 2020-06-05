package com.inventrax.falconsl_new.activities;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.honeywell.aidc.BarcodeReader;
import com.inventrax.falconsl_new.R;
import com.inventrax.falconsl_new.application.AbstractApplication;
import com.inventrax.falconsl_new.fragments.AboutFragment;
import com.inventrax.falconsl_new.fragments.CycleCountDetailsFragment;
import com.inventrax.falconsl_new.fragments.CycleCountHeaderFragment;
import com.inventrax.falconsl_new.fragments.DeleteOBDPickedItemsFragment;
import com.inventrax.falconsl_new.fragments.DeleteVLPDPickedItemsFragment;
import com.inventrax.falconsl_new.fragments.DrawerFragment;
import com.inventrax.falconsl_new.fragments.GoodsInFragment;
import com.inventrax.falconsl_new.fragments.HomeFragment;
import com.inventrax.falconsl_new.fragments.InternalTransferFragment;
import com.inventrax.falconsl_new.fragments.LiveStockFragment;
import com.inventrax.falconsl_new.fragments.LoadGenerationFragment;
import com.inventrax.falconsl_new.fragments.LoadSheetFragment;
import com.inventrax.falconsl_new.fragments.LoadingFragment;
import com.inventrax.falconsl_new.fragments.NewLoadSheetFragment;
import com.inventrax.falconsl_new.fragments.OBDPickingDetailsFragment;
import com.inventrax.falconsl_new.fragments.OBDPickingHeaderFragment;
import com.inventrax.falconsl_new.fragments.PackingFragment;
import com.inventrax.falconsl_new.fragments.PalletTransfersFragment;
import com.inventrax.falconsl_new.fragments.PutawayFragment;
import com.inventrax.falconsl_new.fragments.SortingFragment;
import com.inventrax.falconsl_new.fragments.StockTransferPutAway;
import com.inventrax.falconsl_new.fragments.UnloadingFragment;
import com.inventrax.falconsl_new.fragments.VLPDPickingDetailsFragment;
import com.inventrax.falconsl_new.fragments.VLPDPickingHeaderFragment;
import com.inventrax.falconsl_new.interfaces.ScanKeyListener;
import com.inventrax.falconsl_new.logout.LogoutUtil;
import com.inventrax.falconsl_new.model.NavDrawerItem;
import com.inventrax.falconsl_new.util.AndroidUtils;
import com.inventrax.falconsl_new.util.DialogUtils;
import com.inventrax.falconsl_new.util.FragmentUtils;
import com.inventrax.falconsl_new.util.ProgressDialogUtils;
import com.inventrax.falconsl_new.util.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DrawerFragment.FragmentDrawerListener {

    private Toolbar mToolbar;
    private DrawerFragment drawerFragment;
    private FragmentUtils fragmentUtils;
    private CharSequence[] userRouteCharSequences;
    private List<String> userRouteStringList;
    private String selectedRouteCode;
    private FragmentActivity fragmentActivity;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private LogoutUtil logoutUtil;
    private static BarcodeReader barcodeReader;
    public String barcode = "";
    GoodsInFragment goodsInFragment;

    ScanKeyListener scanKeyListener = new ScanKeyListener() {
        @Override
        public void getScannedData(String message) {

        }
    };

    public void setScanKeyListener(ScanKeyListener scanKeyListener) {
        this.scanKeyListener = scanKeyListener;
    }

    public static BarcodeReader getBarcodeObject() {
        return barcodeReader;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("RestrictedApi")
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {

/*
        if (isKeyBoardOpen) {
*/

            if (e.getAction() == KeyEvent.ACTION_DOWN && e.getKeyCode() != KeyEvent.KEYCODE_ENTER) {
                // Log.i(TAG,"dispatchKeyEvent: "+e.toString());
                char pressedKey = (char) e.getUnicodeChar();
                //barcode += String.valueOf(pressedKey);
/*            if(Character.isLetter(pressedKey) || Character.isDigit(pressedKey)){
                barcode = new StringBuilder(barcode).append(pressedKey).toString();
            }*/
                if (Character.toString(pressedKey).matches("^[a-zA-Z0-9!@#$&( )|_\\-`.+,/\"]*$")) {
                    barcode = new StringBuilder(barcode).append(pressedKey).toString();
                }
            }

            if (e.getAction() == KeyEvent.ACTION_DOWN && e.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                // Toast.makeText(getApplicationContext(), barcode, Toast.LENGTH_LONG).show();
                if (barcode != null) {
                    // scanKeyListener.getScannedData(barcode);
                    // ((GoodsInFragment)goodsInFragment).myScannedData(MainActivity.this,barcode);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    // (if you're not using the support library)
                    // FragmentManager fragmentManager = getFragmentManager();

                        for (final Fragment fragment : fragmentManager.getFragments()) {

                            if (fragment != null && fragment.isVisible() && fragment instanceof CycleCountDetailsFragment) {
                                ((CycleCountDetailsFragment) fragment).myScannedData(MainActivity.this, barcode);
                            }
                            if (fragment != null && fragment.isVisible() && fragment instanceof DeleteOBDPickedItemsFragment) {
                                ((DeleteOBDPickedItemsFragment) fragment).myScannedData(MainActivity.this, barcode);
                            }
                            if (fragment != null && fragment.isVisible() && fragment instanceof DeleteVLPDPickedItemsFragment) {
                                ((DeleteVLPDPickedItemsFragment) fragment).myScannedData(MainActivity.this, barcode);
                            }
                            if (fragment != null && fragment.isVisible() && fragment instanceof GoodsInFragment) {
                                ((GoodsInFragment) fragment).myScannedData(MainActivity.this, barcode);
                            }
                            if (fragment != null && fragment.isVisible() && fragment instanceof InternalTransferFragment) {
                                ((InternalTransferFragment) fragment).myScannedData(MainActivity.this, barcode);
                            }
                            if (fragment != null && fragment.isVisible() && fragment instanceof LiveStockFragment) {
                                ((LiveStockFragment) fragment).myScannedData(MainActivity.this, barcode);
                            }
                            if (fragment != null && fragment.isVisible() && fragment instanceof LoadingFragment) {
                                ((LoadingFragment) fragment).myScannedData(MainActivity.this, barcode);
                            }
                            if (fragment != null && fragment.isVisible() && fragment instanceof OBDPickingDetailsFragment) {
                                ((OBDPickingDetailsFragment) fragment).myScannedData(MainActivity.this, barcode);
                            }
                            if (fragment != null && fragment.isVisible() && fragment instanceof PalletTransfersFragment) {
                                ((PalletTransfersFragment) fragment).myScannedData(MainActivity.this, barcode);
                            }
                            if (fragment != null && fragment.isVisible() && fragment instanceof PutawayFragment) {
                                ((PutawayFragment) fragment).myScannedData(MainActivity.this, barcode);
                            }
                            if (fragment != null && fragment.isVisible() && fragment instanceof SortingFragment) {
                                ((SortingFragment) fragment).myScannedData(MainActivity.this, barcode);
                            }
                            if (fragment != null && fragment.isVisible() && fragment instanceof StockTransferPutAway) {
                                ((StockTransferPutAway) fragment).myScannedData(MainActivity.this, barcode);
                            }
                            if (fragment != null && fragment.isVisible() && fragment instanceof VLPDPickingDetailsFragment) {
                                ((VLPDPickingDetailsFragment) fragment).myScannedData(MainActivity.this, barcode);
                            }
                            if (fragment != null && fragment.isVisible() && fragment instanceof LoadSheetFragment) {
                                ((LoadSheetFragment) fragment).myScannedData(MainActivity.this, barcode);
                            }
                            if (fragment != null && fragment.isVisible() && fragment instanceof PackingFragment) {
                                ((PackingFragment) fragment).myScannedData(MainActivity.this, barcode);
                            }
                            
                    }
                }

                barcode = "";
                return true;
            }
/*        }else{
            Toast.makeText(MainActivity.this, "Please close keyboard while scanning.....", Toast.LENGTH_SHORT).show();
            barcode = "";
        }*/

        return super.dispatchKeyEvent(e);
    }

    @Override
    public void onBackPressed() {
        barcode="";
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            setContentView(R.layout.activity_main);

            goodsInFragment = new GoodsInFragment();

            loadFormControls();


        } catch (Exception ex) {
            // Logger.Log(MainActivity.class.getName(), ex);
        }
    }

    public void loadFormControls() {

        try {

            setScanKeyListener(scanKeyListener);

            logoutUtil = new LogoutUtil();

            mToolbar = (Toolbar) findViewById(R.id.toolbar);

            fragmentUtils = new FragmentUtils();

            fragmentActivity = this;

            new ProgressDialogUtils(this);

            AbstractApplication.FRAGMENT_ACTIVITY = this;

            setSupportActionBar(mToolbar);

           /* if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setIcon(R.mipmap.ic_launcher);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }*/

            View logoView = AndroidUtils.getToolbarLogoIcon(mToolbar);

            if (logoView != null) logoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentUtils.replaceFragmentWithBackStack(fragmentActivity, R.id.container_body, new HomeFragment());
                }
            });

            drawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
            drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
            drawerFragment.setDrawerListener(this);


            sharedPreferencesUtils = new SharedPreferencesUtils("LoginActivity", getApplicationContext());

            userRouteStringList = new ArrayList<>();


            userRouteCharSequences = userRouteStringList.toArray(new CharSequence[userRouteStringList.size()]);
            logoutUtil.setActivity(this);
            logoutUtil.setFragmentActivity(fragmentActivity);
            logoutUtil.setSharedPreferencesUtils(sharedPreferencesUtils);
            // display the first navigation drawer view on app launch
            displayView(0, new NavDrawerItem(false, "Home"));






        } catch (Exception ex) {
            DialogUtils.showAlertDialog(this, "Error while loading form controls");
            return;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case R.id.action_logout: {
                logoutUtil.doLogout();
            }
            break;


            case R.id.action_about: {
                FragmentUtils.replaceFragmentWithBackStack(this, R.id.container_body, new AboutFragment());
            }
            break;

            case R.id.home: {
                FragmentUtils.replaceFragmentWithBackStack(this, R.id.container_body, new HomeFragment());
            }
            break;

            case R.id.rescan: {

            }
            break;

        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDrawerItemSelected(View view, int position, NavDrawerItem menuItem) {

        displayView(position, menuItem);
    }


    private void displayView(int position, NavDrawerItem menuItem) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (menuItem.getTitle()) {
            case "Home":
                fragment = new HomeFragment();
                title = "Home";
                break;

            case "Receiving":
                fragment = new UnloadingFragment();
                title = "Receiving";
                break;

            case "Putaway": {
                fragment = new PutawayFragment();
                title = "Putaway";
            }
            break;

            case "OBD Picking": {
                fragment = new OBDPickingHeaderFragment();
                title = "OBD Picking";
            }
            break;

            case "VLPD Picking": {
                fragment = new VLPDPickingHeaderFragment();
                title = "OBD Picking";
            }
            break;

            /*case "Transfer Order Pick": {
                fragment = new StockTransferOrderPick();
                title = "Transfer";
            }
            break;

            case "Transfer Order PutAway": {
                fragment = new StockTransferPutAway();
                title = "Transfer";
            }
            break;*/

            case "Cycle Count": {
                fragment = new CycleCountHeaderFragment();
                title = "Cycle Count";
            }
            break;

            case "Bin to Bin": {
                fragment = new InternalTransferFragment();
                title = "Transfers";
            }
            break;

            case "Pallet Transfers": {
                fragment = new PalletTransfersFragment();
                title = "Pallet Transfers";
            }
            break;

            case "Live Stock": {
                fragment = new LiveStockFragment();
                title = "Live Stock";
            }
            break;

            case "Packing": {
                fragment = new PackingFragment();
                title = "Packing";
            }
            break;

            case "Loading": {
                fragment = new NewLoadSheetFragment();
                title = "Loading";
            }
            break;

           case "Load Generation": {
                fragment = new LoadGenerationFragment();
                title = "Load Generation";
           }
           break;

            case "Delete OBD Picked Items": {
                fragment = new DeleteOBDPickedItemsFragment();
                title = "Delete OBD";
            }
            break;

            case "Delete VLPD Picked Items": {
                fragment = new DeleteVLPDPickedItemsFragment();
                title = "Delete OBD";
            }
            break;

            default:
                break;

        }

        if (fragment != null) {
            fragmentUtils.replaceFragmentWithBackStack(this, R.id.container_body, fragment);
            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

       /* try {

            EnterpriseDeviceManager enterpriseDeviceManager = (EnterpriseDeviceManager)getSystemService(EnterpriseDeviceManager.ENTERPRISE_POLICY_SERVICE);

            RestrictionPolicy restrictionPolicy = enterpriseDeviceManager.getRestrictionPolicy();

            restrictionPolicy.allowSettingsChanges(false);

        }catch (Exception ex){

        }*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void initiateBackgroundServices() { }

}
