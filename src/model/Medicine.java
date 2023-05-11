package model;

public class Medicine {
    private int code;
    private String name;
    private String price;
    private String type;
    private String effectCode;

    public Medicine(int code, String name, String price, String effectCode, String type) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.type = type;
        this.effectCode = effectCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEffectCode() {
        return effectCode;
    }

    public void setEffectCode(String effectCode) {
        this.effectCode = effectCode;
    }
    
}
