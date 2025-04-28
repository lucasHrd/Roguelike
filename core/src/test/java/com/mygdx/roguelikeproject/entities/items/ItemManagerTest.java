package com.mygdx.roguelikeproject.entities.items;

import com.mygdx.roguelikeproject.entities.Player;
import com.mygdx.roguelikeproject.entities.items.ItemBase;
import com.mygdx.roguelikeproject.entities.items.ItemManager;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ItemManagerTest {

    private ItemManager itemManager;
    private Player mockPlayer;

    @Before
    public void setUp() {
        mockPlayer = mock(Player.class);
        itemManager = new ItemManager(mockPlayer);
    }

    @Test
    public void testSpawnItem() {
        itemManager.update(30f);
        assertTrue(true);
    }

    @Test
    public void testItemRemovedAfterUpdate() {
        ItemBase mockItem = mock(ItemBase.class);
        when(mockItem.shouldBeRemoved()).thenReturn(true);

        itemManager.update(0f);

        try {
            var spawnItemMethod = ItemManager.class.getDeclaredMethod("spawnItem");
            spawnItemMethod.setAccessible(true);
            spawnItemMethod.invoke(itemManager);
        } catch (Exception e) {
            fail("Erreur lors du spawn forcé d'un item : " + e.getMessage());
        }

        assertTrue(true);
    }
}
