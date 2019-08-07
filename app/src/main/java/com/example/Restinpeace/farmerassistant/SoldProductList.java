package com.example.Restinpeace.farmerassistant;

public class SoldProductList {
    private String name;
    private String state;
    private String district;
    private String pincode;
    private String commodity;
    private String price;
    private String weight;
    private String phone;
    private boolean sold;
    private String key;
    private String mImageUrl;
    private String time;
    private String wprice;
    private String wname;
    private String wphone;
    public SoldProductList(String name, String state, String district, String pincode, String commodity,
                           String price, String weight,String phone, boolean sold,
                           String key,String mImageUrl,String time,String wprice,String wname,String wphone) {
        this.name = name;
        this.state = state;
        this.district = district;
        this.pincode = pincode;
        this.commodity = commodity;
        this.price = price;
        this.weight = weight;
        this.phone=phone;
        this.sold = sold;
        this.key = key;
        this.mImageUrl= mImageUrl;
        this.time = time;
        this.wprice = wprice;
        this.wphone = wphone;
        this.wname = wname;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getDistrict() {
        return district;
    }

    public String getPincode() {
        return pincode;
    }

    public String getCommodity() {
        return commodity;
    }

    public String getPrice() {
        return price;
    }

    public String getWeight() {
        return weight;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isSold() {
        return sold;
    }

    public String getKey() {
        return key;
    }

    public String getUrl() {
        return mImageUrl;
    }

    public String getTime(){
        return time;
    }

    public String getwname(){return wname;}
    public String getwprice(){return wprice;}
    public String getwphone(){return wphone;}
}
