package com.mygdx.roguelikeproject.entities.items;

import com.mygdx.roguelikeproject.entities.Player;
import com.mygdx.roguelikeproject.utils.Timer;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ItemManagerIntegrationTest {

    @Test
    public void testItemCollectedAndRemovedAfterCollision() throws Exception {
        Player player = mock(Player.class);
        ItemManager itemManager = new ItemManager(player);

        HealthBoost boost = new HealthBoost(player.getX(), player.getY());

        // 🔥 Récupérer la liste privée "items" proprement
        Field itemsField = ItemManager.class.getDeclaredField("items");
        itemsField.setAccessible(true);

        @SuppressWarnings("unchecked")
        List<ItemBase> items = (List<ItemBase>) itemsField.get(itemManager);
        items.add(boost);

        // ✅ Avant collision, l'item est vivant
        assertTrue(boost.getHitbox() != null);

        itemManager.checkPlayerCollision(player);

        // ✅ Après collision, il doit être supprimé de la liste
        assertEquals(0, items.size());
    }
}
