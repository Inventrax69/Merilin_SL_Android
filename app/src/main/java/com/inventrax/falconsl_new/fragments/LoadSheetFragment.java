package com.inventrax.falconsl_new.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cipherlab.barcode.GeneralString;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.ScannerUnavailableException;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.honeywell.aidc.UnsupportedPropertyException;
import com.inventrax.falconsl_new.R;
import com.inventrax.falconsl_new.activities.MainActivity;
import com.inventrax.falconsl_new.common.Common;
import com.inventrax.falconsl_new.common.constants.EndpointConstants;
import com.inventrax.falconsl_new.common.constants.ErrorMessages;
import com.inventrax.falconsl_new.interfaces.ApiInterface;
import com.inventrax.falconsl_new.pojos.InventoryDTO;
import com.inventrax.falconsl_new.pojos.LoadDTO;
import com.inventrax.falconsl_new.pojos.OutbountDTO;
import com.inventrax.falconsl_new.pojos.WMSCoreMessage;
import com.inventrax.falconsl_new.pojos.WMSExceptionMessage;
import com.inventrax.falconsl_new.searchableSpinner.SearchableSpinner;
import com.inventrax.falconsl_new.services.RestService;
import com.inventrax.falconsl_new.util.DialogUtils;
import com.inventrax.falconsl_new.util.ExceptionLoggerUtils;
import com.inventrax.falconsl_new.util.FragmentUtils;
import com.inventrax.falconsl_new.util.ProgressDialogUtils;
import com.inventrax.falconsl_new.util.ScanValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ch Anil on 27/03/2020.
 */

public class LoadSheetFragment extends Fragment implements View.OnClickListener,BarcodeReader.TriggerListener, BarcodeReader.BarcodeListener, AdapterView.OnItemSelectedListener {

    private static final String classCode = "API_FRAG_009";
    private View rootView;
    Button btnGo, btnCloseOne, btnLoadingComplete, btnCloseTwo,btnCloseThree,btnCreateNew,btnCreate;
    RelativeLayout rlLoadingOne,rlLoadingTwo,rlLoadListThree;
    TextView lblLoadSheetNo,lblScannedSku;
    CardView cvScanSku;
    ImageView ivScanSku;
    SearchableSpinner spinnerSelectLoadList;
    EditText etQty,lblDrName,lblDrNo,lblVehicleNo,lblVehicleType;
    TextInputLayout txtInputLayoutQty;
    String userId =null;
    String scanner = null;
    String getScanner = null;
    private IntentFilter filter;
    private Gson gson;
    //For Honey well barcode
    private static BarcodeReader barcodeReader;
    private AidcManager manager;
    private Common common;
    private WMSCoreMessage core;
    List<LoadDTO> lstloaddata= null;
    String loadSheetNo=null,loadNoCustomerCode=null;
    private String Materialcode = null;
    private boolean IsUserConfirmedRedo=false;

    private ExceptionLoggerUtils exceptionLoggerUtils;
    private ErrorMessages errorMessages;

    // Cipher Barcode Scanner
    private final BroadcastReceiver myDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            scanner = intent.getStringExtra(GeneralString.BcReaderData);  // Scanned Barcode info
            ProcessScannedinfo(scanner.trim().toString());
        }
    };

    public void myScannedData(Context context, String barcode){
        try {
            ProcessScannedinfo(barcode.trim());
        }catch (Exception e){
            //  Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public LoadSheetFragment(){ }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView  = inflater.inflate(R.layout.fragment_load_sheet,container,false);
        barcodeReader = MainActivity.getBarcodeObject();
        loadFormControls();

        return rootView;
    }

    private void loadFormControls() {

        rlLoadingOne = (RelativeLayout) rootView.findViewById(R.id.rlLoadingOne);
        rlLoadingTwo = (RelativeLayout) rootView.findViewById(R.id.rlLoadingTwo);
        rlLoadListThree = (RelativeLayout) rootView.findViewById(R.id.rlLoadListThree);
        lstloaddata = new ArrayList<LoadDTO>();
        spinnerSelectLoadList = (SearchableSpinner) rootView.findViewById(R.id.spinnerSelectLoadList);
        spinnerSelectLoadList.setOnItemSelectedListener(this);
        txtInputLayoutQty = (TextInputLayout) rootView.findViewById(R.id.txtInputLayoutQty);

        etQty = (EditText) rootView.findViewById(R.id.etQty);
        lblDrName = (EditText) rootView.findViewById(R.id.lblDrName);
        lblDrNo = (EditText) rootView.findViewById(R.id.lblDrNo);
        lblVehicleNo = (EditText) rootView.findViewById(R.id.lblVehicleNo);
        lblVehicleType = (EditText) rootView.findViewById(R.id.lblVehicleType);

        //DrawableCompat.setTint(isDockScanned.getDrawable(), ContextCompat.getColor(getContext(), R.color.green));
        btnGo = (Button) rootView.findViewById(R.id.btnGo);
        btnCloseOne = (Button) rootView.findViewById(R.id.btnCloseOne);
        btnCloseTwo = (Button)rootView.findViewById(R.id.btnCloseTwo);
        btnCloseThree = (Button)rootView.findViewById(R.id.btnCloseThree);
        btnCreateNew = (Button)rootView.findViewById(R.id.btnCreateNew);
        btnCreate = (Button)rootView.findViewById(R.id.btnCreate);
        btnLoadingComplete = (Button)rootView.findViewById(R.id.btnLoadingComplete );

        lblLoadSheetNo = (TextView) rootView.findViewById(R.id.lblLoadSheetNo);
        lblScannedSku = (TextView) rootView.findViewById(R.id.lblScannedSku);

        cvScanSku = (CardView) rootView.findViewById(R.id.cvScanSku);
        ivScanSku = (ImageView) rootView.findViewById(R.id.ivScanSku);

        exceptionLoggerUtils = new ExceptionLoggerUtils();
        errorMessages = new ErrorMessages();

        // For Cipher Barcode reader
        Intent RTintent = new Intent("sw.reader.decode.require");
        RTintent.putExtra("Enable", true);
        getActivity().sendBroadcast(RTintent);
        this.filter = new IntentFilter();
        this.filter.addAction("sw.reader.decode.complete");
        getActivity().registerReceiver(this.myDataReceiver, this.filter);

        gson = new GsonBuilder().create();

        btnGo.setOnClickListener(this);
        btnCloseOne.setOnClickListener(this);
        btnCloseTwo.setOnClickListener(this);
        btnCloseThree.setOnClickListener(this);
        btnCreateNew.setOnClickListener(this);
        btnCreate.setOnClickListener(this);

        common = new Common();
        core= new WMSCoreMessage();

      //  GetLoadSheetNo();

        //For Honeywell
        AidcManager.create(getActivity(), new AidcManager.CreatedCallback() {

            @Override
            public void onCreated(AidcManager aidcManager) {

                manager = aidcManager;
                barcodeReader = manager.createBarcodeReader();
                try {
                    barcodeReader.claim();
                    HoneyWellBarcodeListeners();

                } catch (ScannerUnavailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGo:
                // GetLoaddetails();
                rlLoadingOne.setVisibility(View.GONE);
                rlLoadingTwo.setVisibility(View.GONE);
                rlLoadListThree.setVisibility(View.VISIBLE);
                break;
            case R.id.btnCreateNew:
                rlLoadingOne.setVisibility(View.GONE);
                rlLoadingTwo.setVisibility(View.VISIBLE);
                rlLoadListThree.setVisibility(View.GONE);

                // TODO Check pending OBD List;



                break;
            case R.id.btnCreate:

                if(lblDrName.getText().toString().isEmpty()){
                    common.showUserDefinedAlertType("Enter Driver Name", getActivity(), getContext(), "Warning");
                    return;
                }
                if(lblDrNo.getText().toString().isEmpty()){
                    common.showUserDefinedAlertType("Enter Driver number", getActivity(), getContext(), "Warning");
                    return;
                }
                if(lblVehicleNo.getText().toString().isEmpty()){
                    common.showUserDefinedAlertType("Enter Vehicle number", getActivity(), getContext(), "Warning");
                    return;
                }
                if(lblVehicleType.getText().toString().isEmpty()){
                    common.showUserDefinedAlertType("Enter Vechile Type", getActivity(), getContext(), "Warning");
                    return;
                }

                rlLoadingOne.setVisibility(View.GONE);
                rlLoadingTwo.setVisibility(View.GONE);
                rlLoadListThree.setVisibility(View.VISIBLE);

                break;
            case R.id.btnCloseOne:
                FragmentUtils.replaceFragmentWithBackStack(getActivity(), R.id.container_body, new HomeFragment());
                break;
            case R.id.btnCloseTwo:
                FragmentUtils.replaceFragmentWithBackStack(getActivity(), R.id.container_body, new HomeFragment());
                break;
            case R.id.btnCloseThree:
                FragmentUtils.replaceFragmentWithBackStack(getActivity(), R.id.container_body, new HomeFragment());
                break;
            case R.id.btnRevert:
                break;
            case R.id.btnLoadingComplete:
                break;
        }
    }



    // honeywell
    @Override
    public void onBarcodeEvent(final BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // update UI to reflect the data
                getScanner = barcodeReadEvent.getBarcodeData();
                ProcessScannedinfo(getScanner);

            }

        });
    }
    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

    }
    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {

    }
    //Honeywell Barcode reader Properties
    public void HoneyWellBarcodeListeners() {

        barcodeReader.addTriggerListener(this);

        if (barcodeReader != null) {
            // set the trigger mode to client control
            barcodeReader.addBarcodeListener(this);
            try {
                barcodeReader.setProperty(BarcodeReader.PROPERTY_TRIGGER_CONTROL_MODE,
                        BarcodeReader.TRIGGER_CONTROL_MODE_AUTO_CONTROL);
            } catch (UnsupportedPropertyException e) {
                // Toast.makeText(this, "Failed to apply properties", Toast.LENGTH_SHORT).show();
            }

            Map<String, Object> properties = new HashMap<String, Object>();
            // Set Symbologies On/Off
            properties.put(BarcodeReader.PROPERTY_CODE_128_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_GS1_128_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_QR_CODE_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_CODE_39_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_DATAMATRIX_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_UPC_A_ENABLE, true);
            properties.put(BarcodeReader.PROPERTY_EAN_13_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_AZTEC_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_CODABAR_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_INTERLEAVED_25_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_PDF_417_ENABLED, false);
            // Set Max Code 39 barcode length
            properties.put(BarcodeReader.PROPERTY_CODE_39_MAXIMUM_LENGTH, 10);
            // Turn on center decoding
            properties.put(BarcodeReader.PROPERTY_CENTER_DECODE, true);
            // Enable bad read response
            properties.put(BarcodeReader.PROPERTY_NOTIFICATION_BAD_READ_ENABLED, true);
            // Apply the settings
            barcodeReader.setProperties(properties);
        }

    }


    //Assigning scanned value to the respective fields
    public void ProcessScannedinfo(String scannedData) {
        if (scannedData != null) {
            if (ScanValidator.IsItemScanned(scannedData)) {
                cvScanSku.setCardBackgroundColor(getResources().getColor(R.color.skuColor));
                ivScanSku.setImageResource(R.drawable.fullscreen_img);
                lblScannedSku.setText(scannedData.split("[-]",2)[0]);
                Materialcode = scannedData;
                // ConfirmLoading();
            }
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        loadSheetNo = spinnerSelectLoadList.getSelectedItem().toString().split("[-]", 2)[0].trim();
        loadNoCustomerCode=spinnerSelectLoadList.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


/*    public void GetLoaddetails()
    {
        rlLoadingOne.setVisibility(View.GONE);
        rlLoadListTwo.setVisibility(View.VISIBLE);
        lblLoadSheetNo.setText(loadNoCustomerCode);
        for(LoadDTO oLoditem:lstloaddata)
        {
            if(oLoditem.getLoadSheetNo().equals(loadSheetNo))
            {
*//*                lblVehicleNo.setText(oLoditem.getVehicleNumber());
                lblDockNo.setText(oLoditem.getDockNumber());
                lblBoxQty.setText(oLoditem.getLoadedQuantity() +"/"+ oLoditem.getLoadSheetQuantity());
                lblVolume.setText(String.valueOf(oLoditem.getVolume()));
                lblWeight.setText(String.valueOf(oLoditem.getWeight()));*//*
            }
        }
    }*/

/*    public  void GetLoadSheetNo()
    {

        try {
            WMSCoreMessage message = new WMSCoreMessage();
            message = common.SetAuthentication(EndpointConstants.Outbound,getContext());
            OutbountDTO outbountDTO = new OutbountDTO();
            outbountDTO.setUserId(userId);
            message.setEntityObject(outbountDTO);


            Call<String> call = null;
            ApiInterface apiService =
                    RestService.getClient().create(ApiInterface.class);

            try {
                //Checking for Internet Connectivity
                // if (NetworkUtils.isInternetAvailable()) {
                // Calling the Interface method
                ProgressDialogUtils.showProgressDialog("Please Wait");
                call = apiService.GetLoadSheetNo(message);
                // } else {
                // DialogUtils.showAlertDialog(getActivity(), "Please enable internet");
                // return;

                // }

            } catch (Exception ex) {
                try {
                    exceptionLoggerUtils.createExceptionLog(ex.toString(),classCode,"001_01",getActivity());
                    logException();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProgressDialogUtils.closeProgressDialog();
                DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0002);

            }
            try {
                //Getting response from the method
                call.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        try {

                            core = gson.fromJson(response.body().toString(), WMSCoreMessage.class);

                            List<LinkedTreeMap<?, ?>> _lLoadSheetNo = new ArrayList<LinkedTreeMap<?, ?>>();
                            _lLoadSheetNo = (List<LinkedTreeMap<?, ?>>) core.getEntityObject();

                            List<OutbountDTO> lstDto = new ArrayList<OutbountDTO>();
                            List<String> lstLoadSheetNo = new ArrayList<>();


                            for (int i = 0; i < _lLoadSheetNo.size(); i++) {
                                OutbountDTO dto = new OutbountDTO(_lLoadSheetNo.get(i).entrySet());
                                lstDto.add(dto);
                            }

                            for (int i = 0; i < lstDto.size(); i++) {
                                for (int j = 0; j < lstDto.get(i).getLoadList().size(); j++) {
                                    lstLoadSheetNo.add(lstDto.get(i).getLoadList().get(j).getLoadSheetNo());
                                    lstloaddata = lstDto.get(i).getLoadList();
                                }
                            }

                            ProgressDialogUtils.closeProgressDialog();
                            ArrayAdapter arrayAdapterLoadSheet = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, lstLoadSheetNo);
                            spinnerSelectLoadList.setAdapter(arrayAdapterLoadSheet);




                        } catch (Exception ex) {
                            try {
                                exceptionLoggerUtils.createExceptionLog(ex.toString(),classCode,"001_02",getActivity());
                                logException();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ProgressDialogUtils.closeProgressDialog();
                        }


                    }

                    // response object fails
                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {

                        ProgressDialogUtils.closeProgressDialog();
                        DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0001);
                    }
                });
            } catch (Exception ex) {
                try {
                    exceptionLoggerUtils.createExceptionLog(ex.toString(),classCode,"001_03",getActivity());
                    logException();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProgressDialogUtils.closeProgressDialog();
                DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0001);
            }
        } catch (Exception ex) {
            try {
                exceptionLoggerUtils.createExceptionLog(ex.toString(),classCode,"001_04",getActivity());
                logException();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ProgressDialogUtils.closeProgressDialog();
            DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0003);
        }
    }*/




    // sending exception to the database
    public void logException() {
        try {

            String textFromFile = exceptionLoggerUtils.readFromFile(getActivity());

            WMSCoreMessage message = new WMSCoreMessage();
            message = common.SetAuthentication(EndpointConstants.Exception, getActivity());
            WMSExceptionMessage wmsExceptionMessage = new WMSExceptionMessage();
            wmsExceptionMessage.setWMSMessage(textFromFile);
            message.setEntityObject(wmsExceptionMessage);

            Call<String> call = null;
            ApiInterface apiService =
                    RestService.getClient().create(ApiInterface.class);

            try {
                //Checking for Internet Connectivity
                // if (NetworkUtils.isInternetAvailable()) {
                // Calling the Interface method
                call = apiService.LogException(message);
                // } else {
                // DialogUtils.showAlertDialog(getActivity(), "Please enable internet");
                // return;
                // }

            } catch (Exception ex) {
                try {
                    exceptionLoggerUtils.createExceptionLog(ex.toString(),classCode,"002_01",getActivity());
                    logException();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProgressDialogUtils.closeProgressDialog();
                DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0002);
            }
            try {
                //Getting response from the method
                call.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        try {

                            core = gson.fromJson(response.body().toString(), WMSCoreMessage.class);

                            // if any Exception throws
                            if ((core.getType().toString().equals("Exception"))) {
                                List<LinkedTreeMap<?, ?>> _lExceptions = new ArrayList<LinkedTreeMap<?, ?>>();
                                _lExceptions = (List<LinkedTreeMap<?, ?>>) core.getEntityObject();

                                WMSExceptionMessage owmsExceptionMessage = null;
                                for (int i = 0; i < _lExceptions.size(); i++) {
                                    owmsExceptionMessage = new WMSExceptionMessage(_lExceptions.get(i).entrySet());
                                    ProgressDialogUtils.closeProgressDialog();
                                    common.showAlertType(owmsExceptionMessage, getActivity(),getContext());
                                    return;
                                }
                            } else {
                                LinkedTreeMap<String, String> _lResultvalue = new LinkedTreeMap<String, String>();
                                _lResultvalue = (LinkedTreeMap<String, String>) core.getEntityObject();
                                for (Map.Entry<String, String> entry : _lResultvalue.entrySet()) {
                                    if (entry.getKey().equals("Result")) {
                                        String Result = entry.getValue();
                                        if (Result.equals("0")) {
                                            ProgressDialogUtils.closeProgressDialog();
                                            return;
                                        } else {
                                            ProgressDialogUtils.closeProgressDialog();
                                            exceptionLoggerUtils.deleteFile(getActivity());
                                            return;
                                        }
                                    }
                                }
                            }
                        } catch (Exception ex) {

                            try {
                                exceptionLoggerUtils.createExceptionLog(ex.toString(),classCode,"002_02",getActivity());
                                logException();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            ProgressDialogUtils.closeProgressDialog();
                            //Log.d("Message", core.getEntityObject().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        //Toast.makeText(LoginActivity.this, throwable.toString(), Toast.LENGTH_LONG).show();
                        ProgressDialogUtils.closeProgressDialog();
                        DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0001);
                    }
                });
            } catch (Exception ex) {
                try {
                    exceptionLoggerUtils.createExceptionLog(ex.toString(),classCode,"002_03",getActivity());
                    logException();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProgressDialogUtils.closeProgressDialog();
                DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0001);
            }
        } catch (Exception ex) {
            try {
                exceptionLoggerUtils.createExceptionLog(ex.toString(),classCode,"002_04",getActivity());
                logException();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ProgressDialogUtils.closeProgressDialog();
            DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0003);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (barcodeReader != null) {
            // release the scanner claim so we don't get any scanner
            try {
                barcodeReader.claim();
            } catch (ScannerUnavailableException e) {
                e.printStackTrace();
            }
            barcodeReader.release();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (barcodeReader != null) {
            try {
                barcodeReader.claim();
            } catch (ScannerUnavailableException e) {
                e.printStackTrace();
                // Toast.makeText(this, "Scanner unavailable", Toast.LENGTH_SHORT).show();
            }
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_activity_loading));
    }


    @Override
    public void onDestroyView() {

        // Honeywell onDestroyView
        if (barcodeReader != null) {
            // unregister barcode event listener honeywell
            barcodeReader.removeBarcodeListener((BarcodeReader.BarcodeListener) this);

            // unregister trigger state change listener
            barcodeReader.removeTriggerListener((BarcodeReader.TriggerListener) this);
        }

        // Cipher onDestroyView
        Intent RTintent = new Intent("sw.reader.decode.require");
        RTintent.putExtra("Enable", false);
        getActivity().sendBroadcast(RTintent);
        getActivity().unregisterReceiver(this.myDataReceiver);
        super.onDestroyView();

    }

}