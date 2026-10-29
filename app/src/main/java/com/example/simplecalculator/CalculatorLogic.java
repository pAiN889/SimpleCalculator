package com.example.simplecalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Encapsulates state and arithmetic logic for the Simple Calculator application.
 * Uses BigDecimal for accurate calculations and prevents floating-point rounding errors.
 */
public class CalculatorLogic {

    private String currentInput;
    private BigDecimal storedOperand;
    private String pendingOperator;
    private BigDecimal lastSecondOperand;
    private String lastOperator;
    private String expression;
    private boolean isNewInput;
    private boolean isError;

    public CalculatorLogic() {
        clear();
    }

    /**
     * Resets the calculator to its initial state.
     */
    public void clear() {
        currentInput = "0";
        storedOperand = null;
        pendingOperator = null;
        lastSecondOperand = null;
        lastOperator = null;
        expression = "";
        isNewInput = true;
        isError = false;
    }

    /**
     * Appends a numeric digit (0-9) to the current input.
     */
    public void onDigit(char digit) {
        if (isError) {
            clear();
        }

        if (isNewInput) {
            currentInput = String.valueOf(digit);
            isNewInput = false;
        } else {
            if ("0".equals(currentInput)) {
                if (digit != '0') {
                    currentInput = String.valueOf(digit);
                }
            } else if ("-0".equals(currentInput)) {
                if (digit != '0') {
                    currentInput = "-" + digit;
                }
            } else {
                currentInput += digit;
            }
        }
    }

    /**
     * Appends a decimal point to the current input.
     */
    public void onDecimal() {
        if (isError) {
            clear();
        }

        if (isNewInput) {
            currentInput = "0.";
            isNewInput = false;
        } else if (!currentInput.contains(".")) {
            currentInput += ".";
        }
    }

    /**
     * Handles arithmetic operators (+, −, ×, ÷).
     */
    public void onOperator(String op) {
        if (isError) {
            return;
        }

        // If user presses another operator right after an operator, update the operator
        if (pendingOperator != null && isNewInput) {
            pendingOperator = op;
            if (storedOperand != null) {
                expression = formatNumber(storedOperand) + " " + op;
            }
            return;
        }

        // Chained calculation support (e.g. 12 + 8 + ...)
        if (pendingOperator != null) {
            boolean success = evaluatePendingOperation();
            if (!success) {
                return;
            }
        }

        storedOperand = parseBigDecimal(currentInput);
        pendingOperator = op;
        expression = formatNumber(storedOperand) + " " + op;
        isNewInput = true;
    }

    /**
     * Evaluates the current expression when the equals (=) button is tapped.
     */
    public void onEquals() {
        if (isError) {
            return;
        }

        if (pendingOperator != null) {
            BigDecimal secondOperand = parseBigDecimal(currentInput);
            lastOperator = pendingOperator;
            lastSecondOperand = secondOperand;

            expression = formatNumber(storedOperand) + " " + pendingOperator + " " + formatNumber(secondOperand) + " =";
            
            BigDecimal result = performOperation(storedOperand, secondOperand, pendingOperator);
            if (result == null) {
                currentInput = "Cannot divide by zero";
                isError = true;
            } else {
                currentInput = formatNumber(result);
                storedOperand = result;
            }

            pendingOperator = null;
            isNewInput = true;
        } else if (lastOperator != null && lastSecondOperand != null) {
            // Repeated equals press behavior
            BigDecimal firstOperand = parseBigDecimal(currentInput);
            expression = formatNumber(firstOperand) + " " + lastOperator + " " + formatNumber(lastSecondOperand) + " =";

            BigDecimal result = performOperation(firstOperand, lastSecondOperand, lastOperator);
            if (result == null) {
                currentInput = "Cannot divide by zero";
                isError = true;
            } else {
                currentInput = formatNumber(result);
                storedOperand = result;
            }
            isNewInput = true;
        } else {
            // Pressing equals without pending operator
            isNewInput = true;
        }
    }

    /**
     * Removes the last character from current input.
     */
    public void onDelete() {
        if (isError) {
            clear();
            return;
        }

        if (isNewInput) {
            // Editing a previously completed result defaults back to 0
            currentInput = "0";
            return;
        }

        if (currentInput.length() <= 1 || (currentInput.length() == 2 && currentInput.startsWith("-"))) {
            currentInput = "0";
            isNewInput = true;
        } else {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
        }
    }

    /**
     * Toggles the positive/negative sign of current input.
     */
    public void onToggleSign() {
        if (isError) {
            return;
        }

        if ("0".equals(currentInput) || "0.".equals(currentInput)) {
            return;
        }

        if (currentInput.startsWith("-")) {
            currentInput = currentInput.substring(1);
        } else {
            currentInput = "-" + currentInput;
        }
    }

    /**
     * Computes percentage (val / 100).
     */
    public void onPercent() {
        if (isError) {
            return;
        }

        BigDecimal val = parseBigDecimal(currentInput);
        BigDecimal hundred = new BigDecimal("100");
        BigDecimal result = val.divide(hundred, 10, RoundingMode.HALF_UP);

        currentInput = formatNumber(result);
        isNewInput = true;
    }

    private boolean evaluatePendingOperation() {
        BigDecimal secondOperand = parseBigDecimal(currentInput);
        BigDecimal result = performOperation(storedOperand, secondOperand, pendingOperator);
        if (result == null) {
            currentInput = "Cannot divide by zero";
            isError = true;
            return false;
        }
        currentInput = formatNumber(result);
        storedOperand = result;
        return true;
    }

    private BigDecimal performOperation(BigDecimal op1, BigDecimal op2, String operator) {
        if (op1 == null || op2 == null) {
            return BigDecimal.ZERO;
        }
        switch (operator) {
            case "+":
                return op1.add(op2);
            case "−":
            case "-":
                return op1.subtract(op2);
            case "×":
            case "*":
                return op1.multiply(op2);
            case "÷":
            case "/":
                if (op2.compareTo(BigDecimal.ZERO) == 0) {
                    return null; // Signals division by zero error
                }
                return op1.divide(op2, 12, RoundingMode.HALF_UP);
            default:
                return op2;
        }
    }

    private BigDecimal parseBigDecimal(String text) {
        try {
            return new BigDecimal(text);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    private String formatNumber(BigDecimal number) {
        if (number == null) {
            return "Error";
        }
        BigDecimal stripped = number.stripTrailingZeros();
        String resultStr = stripped.toPlainString();
        if ("-0".equals(resultStr)) {
            return "0";
        }
        return resultStr;
    }

    // Getters for UI display
    public String getCurrentInput() {
        return currentInput;
    }

    public String getExpression() {
        return expression;
    }

    public boolean isError() {
        return isError;
    }
}
