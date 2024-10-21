package vn.edu.stu.lab5;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Toolbar toolBar;
    TextView tvAction;
    Button btnContextMenu, btnAlertDialog, btnCustomDialog, btnDatePickerDialog, btnTimePickerDialog;
    Calendar calendar;
    SimpleDateFormat sdfDate, sdfTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }


    private void addControls() {
        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        tvAction = findViewById(R.id.tvAction);
        btnContextMenu = findViewById(R.id.btnContextMenu);
        registerForContextMenu(btnContextMenu);
        btnAlertDialog = findViewById(R.id.btnAlertDialog);
        btnCustomDialog = findViewById(R.id.btnCustomDialog);
        btnDatePickerDialog = findViewById(R.id.btnDatePickerDialog);
        btnTimePickerDialog = findViewById(R.id.btnTimePickerDialog);

        calendar = Calendar.getInstance();
        sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        sdfTime = new SimpleDateFormat("HH:mm");
    }

    private void addEvents() {
        btnAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processAlertDialog();
            }
        });
        btnCustomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processCustomDialog();
            }
        });
        btnDatePickerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processDatePickerDialog();
            }
        });
        btnTimePickerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processTimePickerDialog();
            }
        });
    }

    private void processTimePickerDialog() {
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                calendar.set(Calendar.HOUR_OF_DAY, i);
                calendar.set(Calendar.MINUTE, i1);
                tvAction.setText(sdfTime.format((calendar.getTime())));
            }
        };
        TimePickerDialog dialog = new TimePickerDialog(
                this,
                listener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        dialog.show();
    }

    private void processDatePickerDialog() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.DATE, i2);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.YEAR, i);
                tvAction.setText(sdfDate.format(calendar.getTime()));
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(
                this,

                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    private void processCustomDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        TextView tvTittle = dialog.findViewById(R.id.tvTitle);
        Button btnHihi = dialog.findViewById(R.id.btnHihi);
        Button btnHehe = dialog.findViewById(R.id.btnHehe);
        tvTittle.setText("This is an Custom Dialog");
        btnHehe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvAction.setText("Hehe");
            }
        });
        btnHihi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvAction.setText("Hihi");
                dialog.cancel();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void processAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert Dialog");
        builder.setMessage("This is an Alert Dialog");
//        builder.setIcon(R.drawable.ic_alert);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tvAction.setText("Yes");
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tvAction.setText("No");
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tvAction.setText("Cancel");
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false); // touch Outside will not cancel dialog
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(
                R.menu.menu_main,
                menu
        );
        MenuItem mnuSearch = menu.findItem(R.id.mnuSearch);
        SearchView searchView = (SearchView) mnuSearch.getActionView();
        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                tvAction.setText(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnuAlert) {
            tvAction.setText("Alert Menu");
        } else if (item.getItemId() == R.id.mnuAbout) {
            tvAction.setText("About Menu");
        } else if (item.getItemId() == R.id.mnuExit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.btnContextMenu) {
            getMenuInflater().inflate(R.menu.menu_context,
                    menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnuHaha) {
            tvAction.setText("Haha Menu");

        } else if (item.getItemId() == R.id.mnuHuhu) {
            tvAction.setText("Huhu Menu");

        }
        return super.onContextItemSelected(item);
    }
}