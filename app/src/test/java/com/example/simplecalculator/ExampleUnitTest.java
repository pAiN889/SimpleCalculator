package com.example.simplecalculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests verifying all 15 required calculation logic scenarios.
 */
public class ExampleUnitTest {

    private CalculatorLogic calc;

    @Before
    public void setUp() {
        calc = new CalculatorLogic();
    }

    @Test
    public void test1_Addition() {
        calc.onDigit('2');
        calc.onOperator("+");
        calc.onDigit('3');
        calc.onEquals();
        assertEquals("5", calc.getCurrentInput());
    }

    @Test
    public void test2_Subtraction() {
        calc.onDigit('9');
        calc.onOperator("−");
        calc.onDigit('4');
        calc.onEquals();
        assertEquals("5", calc.getCurrentInput());
    }

    @Test
    public void test3_Multiplication() {
        calc.onDigit('6');
        calc.onOperator("×");
        calc.onDigit('7');
        calc.onEquals();
        assertEquals("42", calc.getCurrentInput());
    }

    @Test
    public void test4_Division() {
        calc.onDigit('2');
        calc.onDigit('0');
        calc.onOperator("÷");
        calc.onDigit('4');
        calc.onEquals();
        assertEquals("5", calc.getCurrentInput());
    }

    @Test
    public void test5_Decimals() {
        calc.onDigit('0');
        calc.onDecimal();
        calc.onDigit('5');
        calc.onOperator("+");
        calc.onDigit('0');
        calc.onDecimal();
        calc.onDigit('2');
        calc.onDigit('5');
        calc.onEquals();
        assertEquals("0.75", calc.getCurrentInput());
    }

    @Test
    public void test6_DivideByZero() {
        calc.onDigit('1');
        calc.onDigit('0');
        calc.onOperator("÷");
        calc.onDigit('0');
        calc.onEquals();
        assertEquals("Cannot divide by zero", calc.getCurrentInput());
        assertTrue(calc.isError());
    }

    @Test
    public void test7_Percent() {
        calc.onDigit('5');
        calc.onDigit('0');
        calc.onPercent();
        assertEquals("0.5", calc.getCurrentInput());
    }

    @Test
    public void test8_ToggleSign() {
        calc.onDigit('5');
        calc.onToggleSign();
        assertEquals("-5", calc.getCurrentInput());
        calc.onToggleSign();
        assertEquals("5", calc.getCurrentInput());
    }

    @Test
    public void test9_Delete() {
        calc.onDigit('1');
        calc.onDigit('2');
        calc.onDigit('3');
        calc.onDelete();
        assertEquals("12", calc.getCurrentInput());
    }

    @Test
    public void test10_Clear() {
        calc.onDigit('1');
        calc.onDigit('2');
        calc.onOperator("+");
        calc.clear();
        assertEquals("0", calc.getCurrentInput());
        assertEquals("", calc.getExpression());
    }

    @Test
    public void test11_SubsequentCalculation() {
        // 15 + 5 = 20
        calc.onDigit('1');
        calc.onDigit('5');
        calc.onOperator("+");
        calc.onDigit('5');
        calc.onEquals();
        assertEquals("20", calc.getCurrentInput());

        // + 2 = 22
        calc.onOperator("+");
        calc.onDigit('2');
        calc.onEquals();
        assertEquals("22", calc.getCurrentInput());
    }

    @Test
    public void test12_MultipleDecimals() {
        calc.onDigit('1');
        calc.onDecimal();
        calc.onDigit('2');
        calc.onDecimal();
        calc.onDigit('3');
        assertEquals("1.23", calc.getCurrentInput());
    }

    @Test
    public void test13_RepeatedOperatorPresses() {
        calc.onDigit('1');
        calc.onDigit('2');
        calc.onOperator("+");
        calc.onOperator("×");
        calc.onDigit('2');
        calc.onEquals();
        assertEquals("24", calc.getCurrentInput());
    }

    @Test
    public void test14_EqualsWithoutExpression() {
        calc.onDigit('1');
        calc.onDigit('2');
        calc.onEquals();
        assertEquals("12", calc.getCurrentInput());
    }

    @Test
    public void test15_RepeatedEqualsPresses() {
        // 15 + 5 = 20
        calc.onDigit('1');
        calc.onDigit('5');
        calc.onOperator("+");
        calc.onDigit('5');
        calc.onEquals();
        assertEquals("20", calc.getCurrentInput());

        // Press equals again -> 20 + 5 = 25
        calc.onEquals();
        assertEquals("25", calc.getCurrentInput());
    }
}
