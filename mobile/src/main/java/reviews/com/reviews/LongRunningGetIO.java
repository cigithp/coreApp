package reviews.com.reviews;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;

public class LongRunningGetIO extends AsyncTask<Void, Void, String> {

    AppCompatActivity activity;

    public LongRunningGetIO(AppCompatActivity activity) {
        this.activity = activity;
    }
    @Override
    protected String doInBackground(Void... voids) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpGet httpGet = new HttpGet("http://10.0.2.2:8080/");
        String text = null;
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();
            text = getASCIIContentFromEntity(entity);
            if(text != null) {
                EditText editText = (EditText) activity.findViewById(R.id.searchResults);
                editText.setText(text);
            } else {

            }
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
        return text;
    }

    private String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
        InputStream in = entity.getContent();
        StringBuffer out = new StringBuffer();
        int n = 1;
        while (n>0) {
            byte[] b = new byte[4096];
            n =  in.read(b);
            if (n>0) out.append(new String(b, 0, n));
        }
        return out.toString();
    }
}

