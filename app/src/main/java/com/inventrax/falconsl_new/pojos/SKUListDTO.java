package com.inventrax.falconsl_new.pojos;
import com.google.gson.annotations.SerializedName;

import java.util.Map;
import java.util.Set;

/**
 * Created by karthik.m on 07/20/2018.
 */

public class SKUListDTO
{

    @SerializedName("SKUName")
    private String SKUName;

    @SerializedName("PickedQty")
    private String PickedQty;

    @SerializedName("AssignQty")
    private String AssignQty;

    @SerializedName("MaterialMasterID")
    private String MaterialMasterID;

    @SerializedName("MCode")
    private String MCode;

    @SerializedName("SOQty")
    private String SOQty;

    @SerializedName("PackedQty")
    private String PackedQty;

    @SerializedName("OutboundID")
    private String OutboundID;

    @SerializedName("CustomerName")
    private String CustomerName;

    @SerializedName("BusinessType")
    private String BusinessType;

    @SerializedName("SODetailsID")
    private String SODetailsID;

    @SerializedName("ObdNumber")
    private String ObdNumber;

    @SerializedName("OBDNumber")
    private String OBDNumber;

    @SerializedName("CartonSerialNo")
    private String CartonSerialNo;

    @SerializedName("PSNID")
    private String PSNID;



    @SerializedName("PSNDetailsID")
    private String PSNDetailsID;

    public SKUListDTO()
    { }

    public SKUListDTO(Set<? extends Map.Entry<?, ?>> entries) {
            for (Map.Entry<?, ?> entry : entries) {

                switch (entry.getKey().toString()) {

                    case  "SKUName" :
                        if(entry.getValue()!=null) {
                            this.setSKUName(entry.getValue().toString());
                        }
                        break;

                    case  "PickedQty" :
                        if(entry.getValue()!=null) {
                            this.setPickedQty(entry.getValue().toString());
                        }
                        break;

                    case  "AssignQty" :
                        if(entry.getValue()!=null) {
                            this.setAssignQty(entry.getValue().toString());
                        }
                        break;
                   case  "MaterialMasterID" :
                        if(entry.getValue()!=null) {
                            this.setMaterialMasterID(entry.getValue().toString());
                        }
                        break;
                   case  "MCode" :
                        if(entry.getValue()!=null) {
                            this.setMCode(entry.getValue().toString());
                        }
                        break;
                    case  "SOQty" :
                        if(entry.getValue()!=null) {
                            this.setSOQty(entry.getValue().toString());
                        }
                        break;
                    case  "PackedQty" :
                        if(entry.getValue()!=null) {
                            this.setPackedQty(entry.getValue().toString());
                        }
                        break;
                   case  "OutboundID" :
                        if(entry.getValue()!=null) {
                            this.setOutboundID(entry.getValue().toString());
                        }
                        break;
                    case  "SODetailsID" :
                        if(entry.getValue()!=null) {
                            this.setSODetailsID(entry.getValue().toString());
                        }
                        break;
                   case  "CustomerName" :
                        if(entry.getValue()!=null) {
                            this.setCustomerName(entry.getValue().toString());
                        }
                        break;
                     case  "BusinessType" :
                        if(entry.getValue()!=null) {
                            this.setBusinessType(entry.getValue().toString());
                        }
                        break;
                     case  "ObdNumber" :
                        if(entry.getValue()!=null) {
                            this.setObdNumber(entry.getValue().toString());
                        }
                        break;
                     case  "OBDNumber" :
                        if(entry.getValue()!=null) {
                            this.setOBDNumber(entry.getValue().toString());
                        }
                     break;
                     case  "CartonSerialNo" :
                        if(entry.getValue()!=null) {
                            this.setCartonSerialNo(entry.getValue().toString());
                        }
                        break;
                     case  "PSNID" :
                        if(entry.getValue()!=null) {
                            this.setPSNID(entry.getValue().toString());
                        }
                        break;
                     case  "PSNDetailsID" :
                        if(entry.getValue()!=null) {
                            this.setPSNDetailsID(entry.getValue().toString());
                        }
                        break;
                }
            }
        }

    public String getSKUName() {
        return SKUName;
    }

    public void setSKUName(String SKUName) {
        this.SKUName = SKUName;
    }

    public String getPickedQty() {
        return PickedQty;
    }

    public void setPickedQty(String pickedQty) {
        PickedQty = pickedQty;
    }

    public String getAssignQty() {
        return AssignQty;
    }

    public void setAssignQty(String assignQty) {
        AssignQty = assignQty;
    }


    public String getMaterialMasterID() {
        return MaterialMasterID;
    }

    public void setMaterialMasterID(String materialMasterID) {
        MaterialMasterID = materialMasterID;
    }

    public String getMCode() {
        return MCode;
    }

    public void setMCode(String mcode) {
        MCode = mcode;
    }

    public String getSOQty() {
        return SOQty;
    }

    public void setSOQty(String SOQty) {
        this.SOQty = SOQty;
    }

    public String getPackedQty() {
        return PackedQty;
    }

    public void setPackedQty(String packedQty) {
        PackedQty = packedQty;
    }

    public String getOutboundID() {
        return OutboundID;
    }

    public void setOutboundID(String outboundID) {
        OutboundID = outboundID;
    }


    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getBusinessType() {
        return BusinessType;
    }

    public void setBusinessType(String businessType) {
        BusinessType = businessType;
    }

    public String getSODetailsID() {
        return SODetailsID;
    }

    public void setSODetailsID(String SODetailsID) {
        this.SODetailsID = SODetailsID;
    }


    public String getObdNumber() {
        return ObdNumber;
    }

    public void setObdNumber(String obdNumber) {
        ObdNumber = obdNumber;
    }

    public String getOBDNumber() {
        return OBDNumber;
    }

    public void setOBDNumber(String OBDNumber) {
        this.OBDNumber = OBDNumber;
    }

    public String getCartonSerialNo() {
        return CartonSerialNo;
    }

    public void setCartonSerialNo(String cartonSerialNo) {
        CartonSerialNo = cartonSerialNo;
    }

    public String getPSNID() {
        return PSNID;
    }

    public void setPSNID(String PSNID) {
        this.PSNID = PSNID;
    }

    public String getPSNDetailsID() {
        return PSNDetailsID;
    }

    public void setPSNDetailsID(String PSNDetailsID) {
        this.PSNDetailsID = PSNDetailsID;
    }


}

