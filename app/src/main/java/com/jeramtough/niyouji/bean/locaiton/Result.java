package com.jeramtough.niyouji.bean.locaiton;

/**
 * Auto-generated: 2018-02-18 13:11:7
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Result {

    private Location location;
    private String address;
    private FormattedAddresses formattedAddresses;
    private AddressComponent addressComponent;
    private AdInfo adInfo;
    private AddressReference addressReference;
    public void setLocation(Location location) {
         this.location = location;
     }
     public Location getLocation() {
         return location;
     }

    public void setAddress(String address) {
         this.address = address;
     }
     public String getAddress() {
         return address;
     }

    public void setFormattedAddresses(FormattedAddresses formattedAddresses) {
         this.formattedAddresses = formattedAddresses;
     }
     public FormattedAddresses getFormattedAddresses() {
         return formattedAddresses;
     }

    public void setAddressComponent(AddressComponent addressComponent) {
         this.addressComponent = addressComponent;
     }
     public AddressComponent getAddressComponent() {
         return addressComponent;
     }

    public void setAdInfo(AdInfo adInfo) {
         this.adInfo = adInfo;
     }
     public AdInfo getAdInfo() {
         return adInfo;
     }

    public void setAddressReference(AddressReference addressReference) {
         this.addressReference = addressReference;
     }
     public AddressReference getAddressReference() {
         return addressReference;
     }

}