package etu.spb.nic.online.store.item.repository;

import etu.spb.nic.online.store.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i JOIN i.categories c WHERE c.id = :catId")
    List<Item> getItemForCatId(@Param("catId") Long catId);

}
