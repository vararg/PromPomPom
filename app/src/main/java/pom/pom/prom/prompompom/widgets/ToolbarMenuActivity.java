package pom.pom.prom.prompompom.widgets;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

/**
 * Created by vararg on 19.02.2017.
 */

public class ToolbarMenuActivity extends AppCompatActivity {

    protected OnCreateOptionsMenuListener optionsMenuListener;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (optionsMenuListener != null)
            return optionsMenuListener.OnCreateOptionsMenuListener(getMenuInflater(), menu);
        else return super.onCreateOptionsMenu(menu);
    }

    public void setOptionsMenuListener(OnCreateOptionsMenuListener optionsMenuListener) {
        this.optionsMenuListener = optionsMenuListener;
    }

    public interface OnCreateOptionsMenuListener {
        boolean OnCreateOptionsMenuListener(MenuInflater menuInflater, Menu menu);
    }
}
