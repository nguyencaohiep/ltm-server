package model;

import java.util.List;

public class Effect {
    private int code;
    private String name;
    private String des;
    private List<Medicine> medicines;

    public Effect(int code, String name, String des, List<Medicine> medicines) {
        this.code = code;
        this.name = name;
        this.des = des;
        this.medicines = medicines;
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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }
}
