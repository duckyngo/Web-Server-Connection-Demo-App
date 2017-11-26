package opennlp.duckyapp.com.webserverconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvAccounts;
    ArrayList<Accounts> al;
//    ServiceHandling sh;
    int position;
    WSHandling wsh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        sh = new ServiceHandling();
        lvAccounts = (ListView) findViewById(R.id.lvAccounts);
        lvAccounts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                return false;
            }
        });

        loadData();
        registerForContextMenu(lvAccounts);
    }

    private void loadData() {
        wsh = new WSHandling(MainActivity.this, lvAccounts);
        wsh.execute(WSHandling.DISPLAY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.m_add:
                Intent i = new Intent(this, AccountsActivity.class);
                startActivity(i);
                break;
            case R.id.m_exit:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.m_change:
                Intent i = new Intent(this, AccountsActivity.class);
                Bundle b = new Bundle();
                b.putBoolean("mode_update", true);
                al = wsh.getData();
                b.putSerializable("data", al.get(position));
                i.putExtra("mode", b);
                startActivity(i);
                break;
            case R.id.m_delete:
                al = wsh.getData();
                Accounts accounts = al.get(position);
                new WSHandling(MainActivity.this, accounts.acc_user, accounts.acc_pwd).execute(WSHandling.DELETE);
                loadData();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadData();
    }
}
