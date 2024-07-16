package etu.spb.nic.Online.store.item;

import etu.spb.nic.Online.store.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {


    @Query("SELECT i FROM Item i JOIN i.categories c WHERE c.id = 2")
    List<Item> findByCatIdIphone();

    @Query("SELECT i FROM Item i JOIN i.categories c WHERE c.id = 3")
    List<Item> findByCatIdSamsung();

    @Query("SELECT i FROM Item i JOIN i.categories c WHERE c.id = 1")
    List<Item> findByCatIdPhones();

    @Query("SELECT i FROM Item i JOIN i.categories c WHERE c.id = 9")
    List<Item> findByCatIdAppleWatch();

    @Query("SELECT i FROM Item i JOIN i.categories c WHERE c.id = 4")
    List<Item> findByCatIdWatch();

    @Query("SELECT i FROM Item i JOIN i.categories c WHERE c.id = 10")
    List<Item> findByCatIdXiaomiWatch();

    @Query("SELECT i FROM Item i JOIN i.categories c WHERE c.id = 5")
    List<Item> findByCatIdAudio();

    @Query("SELECT i FROM Item i JOIN i.categories c WHERE c.id = 11")
    List<Item> findByCatIdAppleAirpods();

    @Query("SELECT i FROM Item i JOIN i.categories c WHERE c.id = 12")
    List<Item> findByCatIdSmartSpeakers();

    @Query("SELECT i FROM Item i JOIN i.categories c WHERE c.id = 7")
    List<Item> findByCatIdHeadphones();

    @Query("SELECT i FROM Item i JOIN i.categories c WHERE c.id = 13")
    List<Item> findByCatIdSamsungHeadphones();

    @Query("SELECT i FROM Item i JOIN i.categories c WHERE c.id = 14")
    List<Item> findByCatIdAppleCase();

    @Query("SELECT i FROM Item i JOIN i.categories c WHERE c.id = 6")
    List<Item> findByCatIdAccessories();
}
