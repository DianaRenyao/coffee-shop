package coffeeshop.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Ingredient.findAll", query = "SELECT i FROM Ingredient i"),
        @NamedQuery(name = "Ingredient.findById", query = "SELECT i FROM Ingredient i WHERE i.id = :id"),
        @NamedQuery(name = "Ingredient.findByName", query = "SELECT i FROM Ingredient i WHERE i.name = :name"),
        @NamedQuery(name = "Ingredient.findByDescription", query = "SELECT i FROM Ingredient i WHERE i.description = :description"),
        @NamedQuery(name = "Ingredient.findByCost", query = "SELECT i FROM Ingredient i WHERE i.cost = :cost")})
public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String name;
    @Size(max = 512)
    @Column(length = 512)
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal cost;
    @JoinTable(name = "ordered_product_has_ingredient", joinColumns = {
            @JoinColumn(name = "ingredient_id", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "ordered_product_id", referencedColumnName = "id", nullable = false)})
    @ManyToMany
    private List<Suborder> suborderList;
    @JoinColumn(name = "ingredient_category_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private IngredientCategory ingredientCategoryId;

    public Ingredient() {
    }

    public Ingredient(Integer id) {
        this.id = id;
    }

    public Ingredient(Integer id, String name, BigDecimal cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @XmlTransient
    public List<Suborder> getSuborderList() {
        return suborderList;
    }

    public void setSuborderList(List<Suborder> suborderList) {
        this.suborderList = suborderList;
    }

    public IngredientCategory getIngredientCategoryId() {
        return ingredientCategoryId;
    }

    public void setIngredientCategoryId(IngredientCategory ingredientCategoryId) {
        this.ingredientCategoryId = ingredientCategoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingredient)) {
            return false;
        }
        Ingredient other = (Ingredient) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "coffeeshop.entity.Ingredient[ id=" + id + " ]";
    }

}
