package com.bh.rms.domain.aggregate.purchase.service;

import com.bh.rms.domain.aggregate.material.MaterialService;
import com.bh.rms.domain.aggregate.purchase.Purchase;
import com.bh.rms.domain.aggregate.purchase.PurchaseItem;
import com.bh.rms.domain.aggregate.purchase.PurchaseService;
import com.bh.rms.domain.aggregate.purchase.exception.PurchaseNotFoundException;
import com.bh.rms.domain.aggregate.purchase.PurchaseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @Mock
    PurchaseRepository purchaseRepository;

    @Mock
    MaterialService materialService;

    @InjectMocks
    PurchaseService purchaseService;

    @Test
    void create() {
        String expect = "purchase1";
        Purchase purchase = new Purchase();
        purchase.setPurchaseItems(List.of(new PurchaseItem()));
        when(purchaseRepository.create(any())).thenReturn(expect);
        when(materialService.existById(any())).thenReturn(true);

        String result = purchaseService.create(purchase);

        assertEquals(expect, result);
    }

    @Test
    void delete() {
        String purchaseId = "purchase1";

        purchaseService.delete(purchaseId);

        verify(purchaseRepository).delete(purchaseId);
    }

    @Test
    void deleteWithNotExistPurchase() {
        String purchaseId = "purchase1";
        doThrow(PurchaseNotFoundException.class)
                .when(purchaseRepository)
                .delete(purchaseId);

        assertThrows(PurchaseNotFoundException.class, () ->
                purchaseService.delete(purchaseId));

        verify(purchaseRepository).delete(purchaseId);
    }

    @Test
    void update() {
        Purchase purchase = new Purchase();
        purchase.setPurchaseItems(List.of(new PurchaseItem()));
        when(materialService.existById(any())).thenReturn(true);

        purchaseService.update(purchase);

        verify(purchaseRepository).update(purchase);
    }

}
