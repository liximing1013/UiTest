package tv.fun.common;

import android.support.annotation.Nullable;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("deprecation")
public class HttpUtils {
    public static String m_sJoUrl = "http://jo.funtv.bestv.com.cn";
    public static String m_sFilmMainPageUrl = m_sJoUrl + "/config/mretrievetabs/v2?" +
            "block_id=109&nav_id=8";
    public static String m_sPlayMainPageUrl = m_sJoUrl + "/config/mretrievetabs/v2?" +
            "block_id=110&nav_id=8";
    public static String m_sChildMainPageUrl = m_sJoUrl + "/config/mretrievetabs/v2?" +
            "block_id=119&nav_id=8";
    public static String m_sVarietyMainPageUrl = m_sJoUrl + "/config/mretrievetabs/v2?" +
            "block_id=112&nav_id=8";
    private final static String TAG = "HttpUtils";

	/**
     * 向指定URL发送GET方法的请求
     */
    public static String sendGet(String sRequestUrl) {
        String sResponseData = "";
        BufferedReader br = null;

        try {
            URL uUrl = new URL(sRequestUrl);
            // 打开和URL之间的连接
            URLConnection connection = uUrl.openConnection();
            // 设置通用的请求属性
//            connection.setRequestProperty("accept", "*/*");
//            connection.setRequestProperty("connection", "Keep-Alive");
//            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(5000);//设置连接超时5s
            connection.setReadTimeout(5000);//设置读取超时5s
            // 建立实际的连接
            connection.connect();

            //获取响应头中的ResponseCode, ResponseMsg
//            Map<String, List<String>> mapHttpHeaders = connection.getHeaderFields();
//            List<String> lsStatus = mapHttpHeaders.get(null);
//            String sStatus = lsStatus.get(0);
//            String sResponseCode = sStatus.split(" ")[1];
//            String sResponseMsg = sStatus.split(" ")[2];

            // 定义 BufferedReader输入流来读取URL的响应
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String sLine;
            while ((sLine = br.readLine()) != null) {
            	sResponseData += sLine;
            }
        } catch (Exception e) {
        	sResponseData = e.getMessage();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return sResponseData;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setConnectTimeout(5000);//设置连接超时5s
            conn.setReadTimeout(5000);//设置读取超时5s
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Http utils by using the OkHttp.
     * By ZhengJin, at 2016/11/15
     */
    private static final int TIME_OUT = 30;
    private static final String CHARSET_NAME = "UTF-8";

    private static final OkHttpClient mClient = new OkHttpClient();

    static {
        mClient.setConnectTimeout(TIME_OUT, TimeUnit.SECONDS);
    }

    public static Request buildRequest(String url) {
        return new Request.Builder().url(url).build();
    }

    private static Response execute(Request request) throws IOException {
        return mClient.newCall(request).execute();
    }

    public static String getStringFromServer(Request request) throws IOException {
        Response response = execute(request);
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code: " + response);
        }
    }

    private static String formatParams(List<BasicNameValuePair> params) {
        return URLEncodedUtils.format(params, CHARSET_NAME);
    }

    public static String attachHttpGetParams(String url, List<BasicNameValuePair> params) {
        return String.format("%s?%s", url, formatParams(params));
    }

    public static String attachHttpGetParam(String url, String name, String value) {
        return String.format("%s?%s=%s", url, name, value);
    }

    /**
     * Get tv information
     */
    @Nullable
    public TvInfo getTvInfoByName(String tvName) {
        final int limit = 50;
        return getTvInfoByName(tvName, limit);
    }

    @Nullable
    public TvInfo getTvInfoByName(String tvName, int limit) {
        JSONObject respObj = JSON.parseObject(
                this.doSendRequestAndRetResponse(this.buildTvSearchGetRequest(limit)));
        if (!this.isResponseOk(respObj)) {
            return null;
        }

        JSONArray dataTvs = respObj.getJSONArray("data");
        for (int idx = 0, size = dataTvs.size(); idx < size; idx++) {
            JSONObject tv = dataTvs.getJSONObject(idx);
            if (tvName.equals(tv.getString("name"))) {
                return new TvInfo(
                        tv.getIntValue("media_id"), tv.getString("name"),
                        tv.getIntValue("total_num"), tv.getBooleanValue("is_end"),
                        tv.getString("vip_type"));
            }
        }

        return null;
    }

    public int getLatestTvTotalNumByName(String tvName) {
        TvInfo tvInfo = this.getTvInfoByName(tvName);
        if (tvInfo == null) {
            return -1;
        }

        JSONObject respObj = JSON.parseObject(this.doSendRequestAndRetResponse(
                this.buildTvDetailsGetRequest(tvInfo.getMediaId())));
        if (!this.isResponseOk(respObj)) {
            return -1;
        }

        return respObj.getJSONObject("data").getJSONArray("episodes").size();
    }

    private boolean isResponseOk(JSONObject response) {
        String retCode = response.getString("retCode");
        String retMsg = response.getString("retMsg");
        if ("200".equals(retCode) && "ok".equals(retMsg)) {
            return true;
        } else {
            Log.e(TAG, String.format(
                    "Error, response ret code: %s, ret message: %s", retCode, retMsg));
            return false;
        }
    }

    private List<BasicNameValuePair> buildTvSearchGetRequestParams(int limit) {
        List<BasicNameValuePair> URL_PARAMS = new ArrayList<>(20);
        URL_PARAMS.add(new BasicNameValuePair("mtype", "2"));
        URL_PARAMS.add(new BasicNameValuePair("area", "0"));
        URL_PARAMS.add(new BasicNameValuePair("cate", "0"));
        URL_PARAMS.add(new BasicNameValuePair("year", "1900_2100"));
        URL_PARAMS.add(new BasicNameValuePair("order", "2"));
        URL_PARAMS.add(new BasicNameValuePair("pg", "1"));
        URL_PARAMS.add(new BasicNameValuePair("pz", String.valueOf(limit)));
        URL_PARAMS.add(new BasicNameValuePair("pv", "0"));
        URL_PARAMS.add(new BasicNameValuePair("version", "2.10.0.7_s"));
        URL_PARAMS.add(new BasicNameValuePair("sid", "FD5551A-SU"));
        URL_PARAMS.add(new BasicNameValuePair("mac", "28:76:CD:01:96:F6"));
        URL_PARAMS.add(new BasicNameValuePair("chiptype", "638"));

        return URL_PARAMS;
    }

    private List<BasicNameValuePair> buildTvDetailsGetRequestParams(int tvId) {
        List<BasicNameValuePair> URL_PARAMS = new ArrayList<>(20);
        URL_PARAMS.add(new BasicNameValuePair("id", String.valueOf(tvId)));
        URL_PARAMS.add(new BasicNameValuePair("account_id", "203186836"));
        URL_PARAMS.add(new BasicNameValuePair("token", "u9YuGT9-L5BCLqIPdaRQlV_Qop5FGdiS1ei" +
                "P7vnT0ijo43tKEEZ6CvI8SxiSTMnms4x45Wx4jhpYDJKBvgUGLAMrqPNHMwPfYEBgfj3kbiY"));
        URL_PARAMS.add(new BasicNameValuePair("version", "2.10.0.7_s"));
        URL_PARAMS.add(new BasicNameValuePair("sid", "FD5551A-SU"));
        URL_PARAMS.add(new BasicNameValuePair("mac", "28:76:CD:01:96:F6"));
        URL_PARAMS.add(new BasicNameValuePair("chiptype", "638"));

        return URL_PARAMS;
    }

    private Request buildTvSearchGetRequest(int limit) {
        final String tvAllUrl = "http://js.funtv.bestv.com.cn/search/mretrieve/v2";
        String url = HttpUtils.attachHttpGetParams(
                tvAllUrl, this.buildTvSearchGetRequestParams(limit));
        return HttpUtils.buildRequest(url);
    }

    private Request buildTvDetailsGetRequest(int tvId) {
        final String tvDetailsUrl = "http://jm.funtv.bestv.com.cn/media/episode/v2";
        String url = HttpUtils.attachHttpGetParams(
                tvDetailsUrl, this.buildTvDetailsGetRequestParams(tvId));
        return HttpUtils.buildRequest(url);
    }

    private String doSendRequestAndRetResponse(Request request) {
        String response;
        try {
            response = HttpUtils.getStringFromServer(request);
        } catch (IOException e) {
            response = String.format("{\"retCode\": \"-1\", \"retMsg\": \"%s\"}", e.getMessage());
            e.printStackTrace();
        }

        return response;
    }

    public static class TvInfo {
        private int mediaId;
        private String tvName;
        private int totalNum;
        private boolean isEnd;
        private String isVip;

        public TvInfo(int mediaId, String tvName, int totalNum, boolean isEnd, String isVip) {
            this.mediaId = mediaId;
            this.tvName = tvName;
            this.totalNum = totalNum;
            this.isEnd = isEnd;
            this.isVip = isVip;
        }

        public int getMediaId() {
            return this.mediaId;
        }

        public String getTvName() {
            return this.tvName;
        }

        public int getTotalNum() {
            return this.totalNum;
        }

        public boolean getIsEnd() {
            return this.isEnd;
        }

        public String getIsVip() {
            return this.isVip;
        }
    }

}
