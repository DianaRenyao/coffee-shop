package coffeeshop.web;

import coffeeshop.ejb.StoreManager;
import coffeeshop.entity.Store;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class StoreController {

    private static final Logger LOG = Logger.getLogger(StoreController.class.getName());

    @EJB
    private StoreManager storeManager;

    public List<Store> getStores() {
        List<Store> selectedStoreList = new ArrayList<>();
        for(Store store : storeManager.getStores()){
            if (store.getIsAvailable() == (short)1){
                selectedStoreList.add(store);
            }
        }
        LOG.log(Level.INFO, "Get all store: {0}", storeManager.getStores());
        return selectedStoreList;
    }

}
