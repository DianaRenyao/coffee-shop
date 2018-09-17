package coffeeshop.web.admin;

import coffeeshop.ejb.StoreManager;
import coffeeshop.web.util.MessageBundle;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "adminAddStoreController")
@SessionScoped
public class AdminAddStoreController implements Serializable {

    private static final Logger LOG = Logger.getLogger(AdminAddUserController.class.getName());

    @EJB
    private StoreManager storeManager;

    @Inject
    private MessageBundle bundle;
    
    private String country;
    private String province;
    private String city;
    private String district;
    private String detail;

    public void addStore() {
        storeManager.addStore(country, province, district, city, detail);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                bundle.getFormatted("Ui.Admin.Message.AddStoreSuccess")
        ));
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    

}
