package tm.gis.datatransfer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDataTransfer extends AsyncTask<String,Void,String> {

    public HttpDataTransferResult<String> connectionTestResult = null;

    private ProgressDialog pDialog;
    private Activity activity;
    public int Code = -1;
    public HttpDataTransfer(Activity activity,int Code)
    {
        this.activity = activity;
        this.Code = Code;
    }


    //執行前，一些基本設定可以在這邊做。
    @Override
    public void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


    }

    //執行中，在背景做任務。
    @Override
    public String doInBackground(String... params) {

        Log.d("HttpConnection","arg0[0]:" + params[0]); //url
        Log.d("HttpConnection","arg0[1]:" + params[1]); //參數
        Log.d("HttpConnection","arg0[2]:" + params[2]); //POST or GET
        if(params.length > 3)
        {
            Log.d("HttpConnection","arg0[3]:" + params[3]); //Authenticate
        }else
        {
            Log.d("HttpConnection","沒 Authenticate");
        }

        URL url = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder;
        String Authenticate  = "";

        try
        {
            // create the HttpURLConnection
            url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            if(Authenticate != "")
            {
                connection.setRequestProperty("Authorization", Authenticate);
            }

            // 使用甚麼方法做連線
            connection.setRequestMethod(params[2]); //連線方式
            //connection.setDoInput(true); //允許輸入流，即允許下載
            // 是否添加參數(ex : json...等)
            connection.setDoOutput(true);//允許輸出流，即允許上傳
            OutputStream os = connection.getOutputStream();
            DataOutputStream writer = new DataOutputStream(os);
            String jsonString = params[1];
            writer.writeBytes(jsonString);
            writer.flush();
            writer.close();
            os.close();

            // 設定TimeOut時間
            connection.setReadTimeout(15*1000);
            connection.connect();

            // 伺服器回來的參數
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // close the reader; this can throw an exception too, so
            // wrap it in another try/catch block.
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    //執行中，當你呼叫publishProgress的時候會到這邊，可以告知使用者進度。
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);


    }

    //執行後，最後的結果會在這邊。
    @Override
    public void onPostExecute(String result) {
        super.onPostExecute(result);
        if(!"".equals(result) || null != result){
            //text.setText(result);

            Log.d("HttpConnection","result:" + result);
        }

        ///測試用等待 2 秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pDialog.dismiss();
        this.connectionTestResult.taskFinish( result,Code );
    }
}
