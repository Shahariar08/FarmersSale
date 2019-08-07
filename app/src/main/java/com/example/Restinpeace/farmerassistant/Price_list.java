package com.example.Restinpeace.farmerassistant;

public class Price_list {
    private String state;
    private String district;
    private String market;
    private String commodity;
    private String variety;
    private String min_price;
    private String max_price;

    public Price_list(String state, String district, String market, String commodity, String variety, String min_price, String max_price) {
        this.state = state;
        this.district = district;
        this.market = market;
        this.commodity = commodity;
        this.variety = variety;
        this.min_price = min_price;
        this.max_price = max_price;
        
    }

    public String getState() {
        return state;
    }

    public String getDistrict() {
        return district;
    }

    public String getMarket() {
        return market;
    }

    public String getCommodity() {
        return commodity;
    }

    public String getVariety() {
        return variety;
    }

    public String getMin_price() {
        return min_price;
    }

    public String getMax_price() {
        return max_price;
    }

}
