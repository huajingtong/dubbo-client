import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * Created by paul on 2017/11/27.
 */
public class HttpConnectionTest {
    /**
     * 连接请求头
     * @param urlConnection
     */
    public static void connectCommon(HttpURLConnection urlConnection ){
        urlConnection.setRequestProperty("accept", "*/*");
        urlConnection.setRequestProperty("contentType", "UTF-8");
        urlConnection.setRequestProperty("Cookie","SHIROSESSIONID=eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE1MTE3OTc1NTQsInN1YiI6IntcImRpc2FibGVkXCI6ZmFsc2UsXCJyb2xlTmFtZXNcIjpbXX0ifQ.pBaGE_X8sr6I5mq9pI6oXoEK2sxkPXbWGcULsJZXPeU760YDZXrzcPi95cWrSPqtnKvI7Zvc3Wl8AjU1LOuqZg;JSESSIONID=EC032A03DE011325E480565BB31BE41B;");
        urlConnection.setRequestProperty("accept-encoding", "gzip, deflate, sdch, br");
        urlConnection.setRequestProperty("accept-language", "zh-CN,zh;q=0.8");
        urlConnection.setRequestProperty("referer", "https://www.meme2c.com/loan/index");
        urlConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/"+System.currentTimeMillis()+" (KHTML, like Gecko) Chrome/"+System.currentTimeMillis()+" Safari/537.36");
    }
    /**
     * 使用非代理连接表列表页（GET请求）
     * @throws Exception
     */
    public static void connectToMeMe2c() throws Exception {
        URL url = new URL("https://www.meme2c.com/loan/page/2");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        connectCommon(urlConnection);
        urlConnection.connect();
        int responseCode = urlConnection.getResponseCode();
        String responseMessage = urlConnection.getResponseMessage();
        print(urlConnection);
        System.out.println(responseCode+":"+responseMessage);
    }

    /**
     * 访问首页
     * @throws Exception
     */
    public static void connectToMeMe2cIndex() throws Exception {
        URL url = new URL("https://www.meme2c.com/");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        connectCommon(urlConnection);
        urlConnection.connect();
        int responseCode = urlConnection.getResponseCode();
        String responseMessage = urlConnection.getResponseMessage();
        print(urlConnection);
        System.out.println(responseCode+":"+responseMessage);
    }
    /**
     * 使用代理连接表列表页（GET请求）
     * @throws Exception
     */
    public static void connectToMeMe2cProxy() throws Exception {
        URL url = new URL("https://www.meme2c.com/loan/page/2");
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("123.7.38.31", 9999));
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(proxy);
        connectCommon(urlConnection);
        urlConnection.connect();
        int responseCode = urlConnection.getResponseCode();
        String responseMessage = urlConnection.getResponseMessage();
        print(urlConnection);
        System.out.println(responseCode+":"+responseMessage);
    }
    /**
     * 请求登录（post请求）
     * @throws Exception
     */
    public static void connectToMeMe2cLogin() throws Exception {
        URL url = new URL("https://www.meme2c.com/login");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setUseCaches(false);
        connectCommon(urlConnection);
        urlConnection.connect();
        OutputStream outputStream = urlConnection.getOutputStream();
        String username = "18600787844";
        String password = "11111a";
        String usernameAndPassword = "username="+username+"&password="+password+"&type=0";
        outputStream.write(usernameAndPassword.getBytes("utf-8"));
        outputStream.flush();
        outputStream.close();
        int responseCode = urlConnection.getResponseCode();
        String responseMessage = urlConnection.getResponseMessage();
        print(urlConnection);
        System.out.println(responseCode+":"+responseMessage);
    }

    /**
     * 打印响应的内容
     * @param httpURLConnection
     */
    public static void print (HttpURLConnection httpURLConnection){
        System.out.println("============================================================="+httpURLConnection.getContentType());
        try {

            String charset = "UTF-8";
            Pattern pattern = Pattern.compile("charset=\\S*");
            Matcher matcher = pattern.matcher(httpURLConnection.getContentType());
            if (matcher.find()) {
                charset = matcher.group().replace("charset=", "");
            }
            String result = null;
            if (httpURLConnection.getContentEncoding() !=null && httpURLConnection.getContentEncoding().indexOf("gzip") != -1) {
                GZIPInputStream gzipin = new GZIPInputStream(
                        httpURLConnection.getInputStream());
                result = ioUtil(gzipin, charset);
            }else{
                result = ioUtil(httpURLConnection.getInputStream(), charset);
            }
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static String ioUtil(InputStream inputStream , String charset) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                inputStream, charset));
        String readLine = null;
        StringBuilder stringBuilder = new StringBuilder();
        while((readLine = bufferedReader.readLine()) !=null){
            stringBuilder.append(readLine+"\n");
        }
        return stringBuilder.toString();
    }
    public static void main(String[] args) throws Exception {
        //connectToMeMe2c();
        //connectToMeMe2cLogin();
        connectToMeMe2cLogin();



    }
}
