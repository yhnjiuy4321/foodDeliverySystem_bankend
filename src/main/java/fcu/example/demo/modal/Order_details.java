package fcu.example.demo.modal;

public class Order_details {
    private String f_o_id;
    private String f_r_id;
    private String f_m_id;

    public Order_details(String f_o_id, String f_r_id, String f_m_id) {
        this.f_o_id = f_o_id;
        this.f_r_id = f_r_id;
        this.f_m_id = f_m_id;
    }

    public Order_details() {

    }

    public String getF_o_id() {
        return f_o_id;
    }

    public void setF_o_id(String f_o_id) {
        this.f_o_id = f_o_id;
    }

    public String getF_r_id() {
        return f_r_id;
    }

    public void setF_r_id(String f_r_id) {
        this.f_r_id = f_r_id;
    }

    public String getF_m_id() {
        return f_m_id;
    }

    public void setF_m_id(String f_m_id) {
        this.f_m_id = f_m_id;
    }
}
