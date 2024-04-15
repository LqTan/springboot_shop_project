package vn.melowyeti.spring.spring_ecommerce_project.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phone_number;

    @Column(name = "total")
    private double total;

    @Column(name = "status")
    private String status;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "order_id")
    List<OrderItem> orderItems;

//    @ManyToMany(fetch = FetchType.LAZY,
//            mappedBy = "orders",
//            cascade = {
//            CascadeType.PERSIST,
//            CascadeType.MERGE,
//            CascadeType.DETACH, CascadeType.REFRESH
//    })
//    private List<Product> products;

    public Order() {
    }

    public Order(String fullname, String address, String phone_number, double total, String status, User user) {
        this.fullname = fullname;
        this.address = address;
        this.phone_number = phone_number;
        this.total = total;
        this.status = status;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
//    public List<Product> getProducts() {
//        return products;
//    }

//    public void setProducts(List<Product> products) {
//        if (this.products == null) {
//            this.products = products;
//        } else {
//            products.forEach(product -> {
//                this.products.add(product);
//            });
//        }
//    }


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", address='" + address + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", total=" + total +
                ", status='" + status + '\'' +
                ", user=" + user +
                '}';
    }
}
