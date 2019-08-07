package com.example.Restinpeace.farmerassistant;

public class Product_detail {

    private String name;
    private String state;
    private String district;
    private String pincode;
    private String commodity;
    private String price;
    private String weight;
    private boolean sold;
    private String phone;
    private String mImageUrl;
    private String time;
    private String wprice;
    private String wname;
    private String wphone;

    public Product_detail() {
    }

    public String getPhone() {
        return phone;
    }

    public Product_detail(String name, String state, String district, String pincode, String commodity,
                          String price, String weight, boolean sold, String phone,
                          String mImageUrl , String time,String wprice ,String wname ,String wphone) {
        this.name = name;
        this.state = state;
        this.district = district;
        this.pincode = pincode;
        this.commodity = commodity;
        this.price = price;
        this.weight = weight;
        this.sold=sold;
        this.phone=phone;
        this.mImageUrl = mImageUrl;
        this.time = time;
        this.wprice = wprice;
        this.wname = wname;
        this.wphone = wphone;
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

    public String getTime(){
        return time;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getwname(){return wname;}

    public String getwprice(){return wprice;}

    public String getwphone(){return wphone;}


    public boolean isSold() {
        return sold;
    }
}
