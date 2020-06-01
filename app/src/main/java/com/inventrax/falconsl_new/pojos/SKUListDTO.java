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

    public SKUListDTO()
    {

    }

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
                    case  "CustomerName" :
                        if(entry.getValue()!=null) {
                            this.setCustomerName(entry.getValue().toString());
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


}

