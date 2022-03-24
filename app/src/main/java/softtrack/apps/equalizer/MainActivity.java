package softtrack.apps.equalizer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public TextView activityMainContainerEqualizerInfoVolumeValue;
    public SeekBar activityMainContainerVolumeController;
    public Menu myMenu;
    public DrawerLayout activityMainDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInfalter = getMenuInflater();
        menuInfalter.inflate(R.menu.activity_main_menu, menu);
        myMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        boolean isCaptureMenuItem = itemId == R.id.activity_main_menu_capture;
        boolean isMoreMenuItem = itemId == R.id.activity_main_menu_more;
        if (isCaptureMenuItem) {
            Log.d("debug", "Capture");
        } else if (isMoreMenuItem) {
            Log.d("debug", "More");
        }
        return super.onOptionsItemSelected(item);
    }

    public void initialize() {
        initializeActionBar();
        findViews();
        addHandlers();
    }

    public void initializeActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        ImageView burger = new ImageView(MainActivity.this);
        LinearLayout.LayoutParams burgerLayoutParams = new LinearLayout.LayoutParams(50, 50);
        burger.setLayoutParams(burgerLayoutParams);
        burger.setImageResource(R.drawable.burger);
        burger.setColorFilter(R.color.white);
        burger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityMainDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        actionBar.setCustomView(burger);
    }

    public void findViews() {
        activityMainContainerEqualizerInfoVolumeValue = findViewById(R.id.activity_main_container_equalizer_info_volume_value);
        activityMainContainerVolumeController = findViewById(R.id.activity_main_container_volume_controller);
        activityMainDrawerLayout = findViewById(R.id.activity_main_drawer_layout);
    }

    public void addHandlers() {
        activityMainContainerVolumeController.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String progress = String.valueOf(i);
                boolean isAddPrefix = i < 10;
                if (isAddPrefix) {
                    progress = "0" + progress;
                }
                activityMainContainerEqualizerInfoVolumeValue.setText(progress);
                AudioManager audioManager = getSystemService(AudioManager.class);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i / 10 - 1, AudioManager.FLAG_SHOW_UI);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}