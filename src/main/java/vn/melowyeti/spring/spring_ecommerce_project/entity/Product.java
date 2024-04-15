package vn.melowyeti.spring.spring_ecommerce_project.entity;

import jakarta.persistence.*;

import java.sql.Blob;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "product_name")
    private String product_name;

    @Column(name = "product_price")
    private double product_price;

    @Column(name = "product_detail")
    private String product_detail;

//    @Lob
//    @Column(name = "product_img")
//    private Blob product_img;
    @Column(name = "product_img")
    private String product_img;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {
            CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinColumn(name="category_id")
    private Category category;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = {
//            CascadeType.MERGE,
//            CascadeType.DETACH, CascadeType.REFRESH
//    })
//    @JoinTable(
//            name="order_product",
//            joinColumns = @JoinColumn(name="product_id"),
//            inverseJoinColumns = @JoinColumn(name = "order_id")
//    )
//    private List<Order> orders;

    @OneToMany(mappedBy = "product_id")
    List<OrderItem> orderItems;

    public Product() {
    }

    public Product(String product_name, double product_price, String product_detail, Category category) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_detail = product_detail;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public String getProduct_detail() {
        return product_detail;
    }

    public void setProduct_detail(String product_detail) {
        this.product_detail = product_detail;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

//    public List<Order> getOrders() {
//        return orders;
//    }

//    public void setOrders(List<Order> orders) {
//        if (this.orders == null) {
//            this.orders = orders;
//        } else {
//            orders.forEach(order -> {
//                this.orders.add(order);
//            });
//        }
//    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", product_name='" + product_name + '\'' +
                ", product_price=" + product_price +
                ", product_detail='" + product_detail + '\'' +
                ", category=" + category +
                '}';
    }
}
