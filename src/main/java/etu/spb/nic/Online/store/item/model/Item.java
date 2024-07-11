package etu.spb.nic.Online.store.item.model;

import etu.spb.nic.Online.store.item.model.status.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    @Transient
    private String itemStatus;
    @Column(name = "total_count")
    private Long totalCount;
}
