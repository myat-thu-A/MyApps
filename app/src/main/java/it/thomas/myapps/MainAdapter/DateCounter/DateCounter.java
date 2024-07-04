package it.thomas.myapps.MainAdapter.DateCounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import it.thomas.myapps.R;
import it.thomas.myapps.databinding.ActivityDateCounterBinding;
import it.thomas.myapps.databinding.DialogUserNameInputBinding;

public class DateCounter extends AppCompatActivity {

    private ActivityDateCounterBinding binding;

    private static final String BOY = "boy";

    private static final String GIRL = "girl";
    private SharedPreferences sp;

    private LocalDate selectedDate;
    LocalDate todayDate = LocalDate.now();
    private static final int SELECTED_PICTURE = 100;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDateCounterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sp = getSharedPreferences("date_counter", MODE_PRIVATE);
        initUI();
        initListener();
    }

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private void initListener() {
        binding.btDate.setOnClickListener(v -> {
            DateCount dc = DateCount.calculate(selectedDate.getDayOfMonth(), selectedDate.getMonthValue(), selectedDate.getYear(), todayDate.getDayOfMonth(), todayDate.getMonthValue(), todayDate.getYear());
            String date = dc.getYear() + " years / " + dc.getMonth() + " months / " + dc.getDay() + " days";
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog dialog = builder.setTitle("Anniversary").setMessage(date).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).create();
            dialog.show();
        });
        binding.tvBoy.setOnClickListener(this::nameInputDialog);

        binding.tvGirl.setOnClickListener(this::nameInputDialog);

        binding.ivBoy.setOnClickListener(v -> {
            uploadPicture(v);
        });

        binding.ivGirl.setOnClickListener(v -> {
            uploadPicture(v);
        });

    }

    private void uploadPicture(View view) {
        iv = (ImageView) view;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECTED_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECTED_PICTURE && resultCode == RESULT_OK) {
            Uri image = data.getData();
            try {

                String imgStr = "";
                if (iv.getId() == R.id.ivBoy) {
                    imgStr = copyImageToPrivateStorage(image, "boy");
                    saveData(BOY, imgStr);
                } else {
                    imgStr = copyImageToPrivateStorage(image, "girl");
                    saveData(GIRL, imgStr);
                }
                if (!imgStr.isBlank()) iv.setImageBitmap(loadImageFromFilePath(imgStr));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String copyImageToPrivateStorage(Uri uri, String fileName) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        File outputFile = new File(getFilesDir(), fileName + ".jpg");
        OutputStream outputStream = new FileOutputStream(outputFile);
        byte[] buffer = new byte[4 * 1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        inputStream.close();
        return outputFile.getAbsolutePath();
    }

    private Bitmap loadImageFromFilePath(String imagePath) {
        Bitmap bitmap = null;
        if (imagePath != null) {
            File imgFile = new File(imagePath);
            if (imgFile.exists()) {
                bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            }
        }
        return bitmap;
    }

    private void initUI() {
        binding.tvBoy.setText(read(BOY));
        binding.tvGirl.setText(read(GIRL));
        selectedDate = fromStringToLocalDate(binding.btDate.getText().toString());
        long dateBetween = todayDate.toEpochDay() - selectedDate.toEpochDay();
        String days = dateBetween > 1 ? dateBetween + "Days" : dateBetween + "Day";
        binding.tvCount.setText(days);
        Bitmap boyImage = loadImageFromFilePath(read(BOY));
        if (boyImage != null) binding.ivBoy.setImageBitmap(loadImageFromFilePath(read(BOY)));
        Bitmap girlImage = loadImageFromFilePath(read(GIRL));
        if (girlImage != null) binding.ivGirl.setImageBitmap(loadImageFromFilePath(read(GIRL)));
    }

    private LocalDate fromStringToLocalDate(String str) {
        return LocalDate.parse(str, formatter);
    }

    private String fromLocalDateToString(LocalDate localDate) {
        return formatter.format(localDate);
    }

    private void nameInputDialog(View view) {
        DialogUserNameInputBinding inputBinding = DialogUserNameInputBinding.inflate(getLayoutInflater());
        TextView textView = (TextView) view;
        AlertDialog.Builder nameBuilder = new AlertDialog.Builder(this);
        AlertDialog nameDialog = nameBuilder.setView(inputBinding.getRoot()).create();
        nameDialog.show();

        class OnDialogClick implements View.OnClickListener {

            @Override
            public void onClick(View v) {
                String name = inputBinding.etName.getText().toString();
                if (textView.getId() == R.id.tvBoy) {
                    saveData(BOY, name);
                } else {
                    saveData(GIRL, name);
                }
                textView.setText(name);
                nameDialog.cancel();
            }
        }

        inputBinding.btConfirm.setOnClickListener(new OnDialogClick());
        inputBinding.btCancel.setOnClickListener(v -> nameDialog.cancel());
    }

    private void saveData(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private String read(String key) {
        return sp.getString(key, "");
    }
}