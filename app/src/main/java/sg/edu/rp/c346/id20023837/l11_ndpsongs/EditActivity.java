package sg.edu.rp.c346.id20023837.l11_ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    EditText etSongTitle, etSongSinger, etSongYear, etSongID;
    Button btnUpdate, btnDelete, btnCancel;
    RatingBar rbStars;
    Song song;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etSongID = findViewById(R.id.etSongID);
        etSongSinger = findViewById(R.id.etSingersName);
        etSongTitle = findViewById(R.id.etSongTitle);
        etSongYear = findViewById(R.id.etModifiedYear);
        btnCancel = findViewById(R.id.buttonCancel);
        btnDelete = findViewById(R.id.buttonDelete);
        btnUpdate = findViewById(R.id.buttonUpdate);
        rbStars = findViewById(R.id.ratingBarStars3);

        Intent i = getIntent();
        final Song currentSong = (Song) i.getSerializableExtra("song");

        etSongID.setText(currentSong.get_id()+"");
        etSongTitle.setText(currentSong.getTitle());
        etSongSinger.setText(currentSong.getSingers());
        etSongYear.setText(currentSong.getYear()+"");
        rbStars.setRating(currentSong.getStars());

        etSongID.setEnabled(false);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                int result = dbh.deleteSong(currentSong.get_id());
                if (result>0){
                    Toast.makeText(EditActivity.this, "Song deleted", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                }
                finish();

            }

        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                currentSong.setTitle(etSongTitle.getText().toString().trim());
                currentSong.setSingers(etSongSinger.getText().toString().trim());
                int year = 0;
                try {
                    year = Integer.valueOf(etSongYear.getText().toString().trim());
                } catch (Exception e){
                    Toast.makeText(EditActivity.this, "Invalid year", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentSong.setYear(year);

                currentSong.setStars((int) rbStars.getRating());
                int result = dbh.updateSong(currentSong);
                if (result>0){
                    Toast.makeText(EditActivity.this, "Song updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}