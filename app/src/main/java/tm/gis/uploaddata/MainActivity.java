package tm.gis.uploaddata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


import tm.gis.datatransfer.HttpDataTransfer;
import tm.gis.datatransfer.HttpDataTransferResult;

//implements宣告自己使用一個或者多個介面。
public class MainActivity extends AppCompatActivity implements HttpDataTransferResult {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONObject postData = new JSONObject();
        try {
            postData.put("LoginId","ellychen");
            postData.put("LoginPsd", "shadow");
            postData.put("ComputerName","MyComputer");

            String Authenticate = "";

            //HttpAsyncTask task = new HttpAsyncTask(this,1024);
           // task.connectionTestResult = this;
            HttpDataTransfer httpDataTransfer = new HttpDataTransfer(this,1024);
            httpDataTransfer.execute("https://tm.gis.tw/taichung_disability/api/Auth/Authentication",postData.toString(),"POST");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("HttpConnection", String.valueOf(this.getExternalFilesDir("File")));
        //HttpFileDownload fileDownload = new HttpFileDownload(1);
        //fileDownload.execute("https://i.insider.com/5df126b679d7570ad2044f3e?width=1100&format=jpeg&auto=webp", Environment.getDataDirectory() + "F/ileDown" ,"dog.jpg");
    }

    @Override
    public void taskFinish(Object result, int Code) {
        Toast.makeText(this, "Code:" + Code,Toast.LENGTH_LONG).show();
        Toast.makeText(this,(String)result,Toast.LENGTH_LONG).show();

        Log.d("HttpConnection","Code:" + Code);
        Log.d("HttpConnection",(String)result);
    }





}
