package com.jchip.wear.country.city;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class SearchKeyboardActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_keyboard_activity);

        Intent intent = this.getIntent();
        String text = intent.getStringExtra("text");
        EditText textView = this.findViewById(R.id.input_text);
        textView.setText(text);

        this.findViewById(R.id.key_cancel).setOnClickListener((v) -> this.onCancel());
        this.findViewById(R.id.key_done).setOnClickListener((v) -> this.onSubmit());
        this.findViewById(R.id.key_back).setOnClickListener((v) -> this.finish());

        this.initKeyboard();
    }

    private void onKeyboard(String key) {
        EditText textView = this.findViewById(R.id.input_text);
        String text = textView.getText().toString();
        textView.setText(text + key);
    }

    private void onCancel() {
        TextView inputView = this.findViewById(R.id.input_text);
        String inputText = inputView.getText().toString();
        if (inputText != null && inputText.length() > 0) {
            inputView.setText(inputText.substring(0, inputText.length() - 1));
        }
    }

    private void onSubmit() {
        TextView inputView = this.findViewById(R.id.input_text);
        Intent data = new Intent();
        data.putExtra("search", inputView.getText().toString());
        this.setResult(RESULT_OK, data);
        this.finish();
    }

    private void initKeyboard() {
        String[] keys = {
                "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
                "A", "S", "D", "F", "G", "H", "J", "K", "L",
                "Z", "X", "C", "V", "B", "N", "M",
        };

        for (int index = 0; index < keys.length; index++) {
            int id = this.getResources().getIdentifier("key_" + index, "id",
                    this.getPackageName());
            TextView keyView = this.findViewById(id);
            keyView.setText(keys[index]);
            int finalIndex = index;
            keyView.setOnClickListener((v) -> this.onKeyboard(keys[finalIndex]));
        }
    }
}
