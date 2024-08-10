package ru.gb.timesheet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class) // Внедриться в момент создания теста
class TaxCalculatorTest {

    @Test
    void testGetPriceWithTax() {
        TaxResolver mock = Mockito.mock(TaxResolver.class);
        // when(mock.getCurrentTax()).thenReturn(0.2);
        // Равноправно
        doReturn(0.2).when(mock).getCurrentTax();
        
        TaxCalculator taxCalculator = new TaxCalculator(mock);
        Assertions.assertEquals(120.0, taxCalculator.getPriceWithTax(100.0),0.000009);

        verify(mock).getCurrentTax(); // убедиться, что метод был вызван
    }

}