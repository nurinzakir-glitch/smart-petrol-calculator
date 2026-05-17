package com.example.smartpetrolcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CalculateActivity extends AppCompatActivity {

    Spinner spinnerPetrolType, spinnerEligible;
    EditText editPrice, editFuelUsage;
    Button btnCalculate, btnBackHome;
    TextView txtResult;

    final double SUBSIDY_RATE = 1.99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculate);

        Toolbar toolbarCalculate = findViewById(R.id.toolbarCalculate);
        setSupportActionBar(toolbarCalculate);

        spinnerPetrolType = findViewById(R.id.spinnerPetrolType);
        spinnerEligible = findViewById(R.id.spinnerEligible);
        editPrice = findViewById(R.id.editPrice);
        editFuelUsage = findViewById(R.id.editFuelUsage);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnBackHome = findViewById(R.id.btnBackHome);
        txtResult = findViewById(R.id.txtResult);

        setupSpinners();

        btnCalculate.setOnClickListener(v -> {
            Toast.makeText(CalculateActivity.this, "Calculate clicked!", Toast.LENGTH_SHORT).show();
            calculatePetrolCost();
        });

        btnBackHome.setOnClickListener(v -> {
            Toast.makeText(CalculateActivity.this, "Back clicked!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(CalculateActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void setupSpinners() {
        String[] petrolTypes = {"RON95", "RON97", "Diesel"};

        ArrayAdapter<String> petrolAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                petrolTypes
        );

        spinnerPetrolType.setAdapter(petrolAdapter);

        String[] eligibleOptions = {"Yes", "No"};

        ArrayAdapter<String> eligibleAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                eligibleOptions
        );

        spinnerEligible.setAdapter(eligibleAdapter);
    }

    private void calculatePetrolCost() {
        String priceText = editPrice.getText().toString().trim();
        String fuelUsageText = editFuelUsage.getText().toString().trim();

        if (priceText.isEmpty() || fuelUsageText.isEmpty()) {
            Toast.makeText(this, "Please enter petrol price and fuel usage", Toast.LENGTH_SHORT).show();
            return;
        }

        double petrolPrice;
        double fuelUsage;

        try {
            petrolPrice = Double.parseDouble(priceText);
            fuelUsage = Double.parseDouble(fuelUsageText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers only", Toast.LENGTH_SHORT).show();
            return;
        }

        if (petrolPrice <= 0 || fuelUsage <= 0) {
            Toast.makeText(this, "Values must be greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }

        String petrolType = spinnerPetrolType.getSelectedItem().toString();
        String eligibleStatus = spinnerEligible.getSelectedItem().toString();

        double totalPetrolCost = fuelUsage * petrolPrice;
        double budiRebate = 0.00;

        if (petrolType.equals("RON95") && eligibleStatus.equals("Yes")) {
            budiRebate = fuelUsage * SUBSIDY_RATE;
        }

        double finalPayableAmount = totalPetrolCost - budiRebate;
        double totalSavings = budiRebate;

        String result =
                "Petrol Type: " + petrolType +
                        "\nPetrol Price per Liter: RM" + String.format("%.2f", petrolPrice) +
                        "\nFuel Usage: " + String.format("%.2f", fuelUsage) + " liters" +
                        "\nBUDI MADANI Eligible: " + eligibleStatus +
                        "\n\nTotal Petrol Cost: RM" + String.format("%.2f", totalPetrolCost) +
                        "\nBUDI Rebate: RM" + String.format("%.2f", budiRebate) +
                        "\nFinal Payable Amount: RM" + String.format("%.2f", finalPayableAmount) +
                        "\nTotal Savings: RM" + String.format("%.2f", totalSavings);

        txtResult.setText(result);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_home) {
            Toast.makeText(this, "Home clicked!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(CalculateActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.menu_calculate) {
            Toast.makeText(this, "You are already on Calculate page", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.menu_about) {
            Toast.makeText(this, "About clicked!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(CalculateActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.menu_share) {
            Toast.makeText(this, "Share clicked!", Toast.LENGTH_SHORT).show();

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Smart Petrol Calculator");
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    "Try this Smart Petrol Cost Calculator with BUDI MADANI Rebate.");
            startActivity(Intent.createChooser(shareIntent, "Share using"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}