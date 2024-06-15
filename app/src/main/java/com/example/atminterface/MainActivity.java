package com.example.atminterface;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    public class BankAccount{
        private double balance;

        public BankAccount(double initialBalance){
            this.balance =initialBalance;
        }
        public double getBalance(){
            return balance;
        }
        public boolean deposit(double amount){
            if(amount > 0){
                balance+= amount;
                return true;
            }
            return false;
        }
        public boolean withdraw(double amount){
            if(amount > 0 && amount <=balance){
                balance-= amount;
                return true;
            }
            return false;
        }
    }
    public class ATM{
        private BankAccount account;
        public ATM(BankAccount account){
            this.account=account;
        }
        public boolean deposit(double amount){
            return account.deposit(amount);
        }
        public boolean withdraw(double amount){
            return account.withdraw(amount);
        }
        public double checkBalance(){
            return account.getBalance();
        }
    }

    private BankAccount account;
    private ATM atm;
    private TextView balanceTextView;
    private EditText amountEditText;
    private Button depositButton;
    private Button withdrawButton;
    private Button checkBalanceButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        balanceTextView = findViewById(R.id.balanceTextView);
        amountEditText = findViewById(R.id.amountEditText);
        depositButton = findViewById(R.id.depositButton);
        withdrawButton = findViewById(R.id.withdrawButton);
        checkBalanceButton = findViewById(R.id.checkBalanceButton);

        account = new BankAccount(1000.0);
        atm = new ATM(account);

        depositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = getAmountFromInput();
                if (atm.deposit(amount)) {
                    showMessage("Deposit successful");
                } else {
                    showMessage("Invalid amount");
                }
                updateBalanceDisplay();
            }
        });

        withdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = getAmountFromInput();
                if (atm.withdraw(amount)) {
                    showMessage("Withdrawal successful");
                } else {
                    showMessage("Insufficient balance or invalid amount");
                }
                updateBalanceDisplay();
            }
        });
        checkBalanceButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                updateBalanceDisplay();
            }
        });
        updateBalanceDisplay();




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private  double getAmountFromInput(){
        String amountText=amountEditText.getText().toString();
        try{
            return Double.parseDouble(amountText);
        } catch(NumberFormatException e){
            return -1;
        }
    }
    private void updateBalanceDisplay(){
        balanceTextView.setText("Blance: Rs"+String.format("%.2f",atm.checkBalance()));
    }
    private void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
