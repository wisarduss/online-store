package etu.spb.nic.online.store.item.model;

import etu.spb.nic.online.store.category.model.Category;
import etu.spb.nic.online.store.order.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "photo_url")
    private String photoURL;
    @Column(name = "price")
    private Long price;
    private String itemStatus;
    @Column(name = "total_count")
    private Long totalCount;
    @ManyToMany
    @JoinTable(
            name = "item_categories",
            joinColumns = {@JoinColumn(name = "item_id") },
            inverseJoinColumns = {@JoinColumn(name = "cat_id") }
    )
    private Set<Category> categories = new HashSet<>();
}
