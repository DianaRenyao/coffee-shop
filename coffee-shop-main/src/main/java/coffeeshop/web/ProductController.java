package coffeeshop.web;

import coffeeshop.ejb.CartManagerException;
import coffeeshop.ejb.ProductManager;
import coffeeshop.ejb.ProductManagerException;
import coffeeshop.entity.Category;
import coffeeshop.entity.Ingredient;
import coffeeshop.entity.Product;
import coffeeshop.facade.IngredientFacade;
import coffeeshop.web.util.MessageBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named
@SessionScoped
public class ProductController implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());

    @EJB
    private ProductManager productManager;

    @Inject
    private CartController cartController;

    @Inject
    private MessageBundle bundle;

    // For find ingredient by view returned id
    @EJB
    private IngredientFacade ingredientFacade;

    private String selectedCategory;
    private Product selectedProduct;
    private List<Ingredient> selectedIngredients;
    private short itemQuantity;

    @PostConstruct
    private void init() {
        selectedIngredients = new ArrayList<>();
    }

    public String getSelectedCategory() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        if (viewId.equals("/index.xhtml")) {
            this.selectedCategory = null;
        }
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
        // Important, clear product selections
        this.selectedIngredients.clear();
        this.itemQuantity = 1;
    }

    public List<Category> getCategories() {
        return productManager.getCategories();
    }

    private List<Product> getCategoryProducts(String categoryName) throws ProductManagerException {
        return productManager.getCategoryProducts(categoryName);
    }

    public List<Product> getSelectedCategoryProducts() throws ProductManagerException {
        return getCategoryProducts(selectedCategory);
    }

    public short getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(short itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public void increaseItemQuantity() {
        this.itemQuantity += 1;
    }

    public void decreaseItemQuantity() {
        this.itemQuantity -= 1;
    }

    public List<Ingredient> getSelectedIngredients() {
        return selectedIngredients;
    }

    public void setSelectedIngredients(List<Ingredient> selectedIngredients) {
        this.selectedIngredients = selectedIngredients;
    }

    public void ingredientChanged(ValueChangeEvent event) {
        String oldValue = (String) event.getOldValue();
        if (oldValue != null) {
            int id = Integer.parseInt(oldValue);
            Ingredient ingredient = ingredientFacade.find(id);
            selectedIngredients.remove(ingredient);
        }
        String newValue = (String) event.getNewValue();
        if (newValue != null) {
            int id = Integer.parseInt(newValue);
            Ingredient ingredient = ingredientFacade.find(id);
            selectedIngredients.add(ingredient);
        }
    }

    public BigDecimal getSuborderAmount() {
        BigDecimal cost = selectedProduct.getCost();
        LOG.log(Level.INFO, "Current cost: {0}", cost);
        for (Ingredient ingredient : selectedIngredients) {
            LOG.log(Level.INFO, "Current cost: {0}", cost);
            cost = cost.add(ingredient.getCost());
        }
        LOG.log(Level.INFO, "Current cost: {0}", cost);
        return cost;
    }

    public BigDecimal getIngredientsAmount() {
        BigDecimal cost = BigDecimal.ZERO;
        LOG.log(Level.INFO, "Current cost: {0}", cost);
        for (Ingredient ingredient : selectedIngredients) {
            LOG.log(Level.INFO, "Current cost: {0}", cost);
            cost = cost.add(ingredient.getCost());
        }
        LOG.log(Level.INFO, "Current cost: {0}", cost);
        return cost;
    }

    public void addToCart() throws CartManagerException {
        cartController.getCartManager().add(selectedProduct, selectedIngredients, itemQuantity);
        LOG.log(Level.INFO, "Add suborder to cart: {0} {1} {2}",
                new Object[]{selectedProduct, selectedIngredients, itemQuantity});
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                bundle.getString("Ui.Message.AddedToCart"),
                null
        ));
    }

}
