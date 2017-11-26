package opennlp.duckyapp.com.webserverconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AccountsActivity extends AppCompatActivity {

    EditText txtAccUser, txtAccPwd;
    boolean isUpdate;
    Accounts accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        txtAccUser = (EditText) findViewById(R.id.txtAccUser);
        txtAccPwd = (EditText) findViewById(R.id.txtAccPwd);
        isUpdate = false;
        Intent i = getIntent();
        Bundle b = i.getBundleExtra("mode");
        if (b != null) {
            isUpdate = b.getBoolean("mode_update");
            if (isUpdate) {
                accounts = (Accounts) b.getSerializable("data");
                txtAccUser.setText(accounts.acc_user);
                txtAccUser.setEnabled(false);
                txtAccPwd.setText(accounts.acc_pwd);
            }
        }
    }

    public void save(View view) {
        if (!isUpdate) {
            String accUser = txtAccUser.getText().toString();
            String accPwd = txtAccPwd.getText().toString();
            txtAccUser.setEnabled(true);
            new WSHandling(AccountsActivity.this, accUser, accPwd).execute(WSHandling.CREATE);
            Toast.makeText(this, "Successfull", Toast.LENGTH_SHORT).show();
        } else {
            String accPwd = txtAccPwd.getText().toString();
            new WSHandling(AccountsActivity.this, accounts.acc_user, accPwd).execute(WSHandling.UPDATE);
            Toast.makeText(this, "Successfull", Toast.LENGTH_SHORT).show();
        }
        switch (view.getId()) {
            case R.id.btnSave_Exit:
                finish();
        }
    }
}
