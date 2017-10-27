package com.bg.library.Utils.Http;

import android.graphics.BitmapFactory;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * Created by BinGe on 2017/9/13.
 * http 功能类
 */

public class Http {

    /**
     * Post服务请求
     *
     * @param requestUrl 请求地址
     * @param params     请求参数
     * @return
     */
    public static JSONObject post(String requestUrl, String params) {
        JSONObject json = new JSONObject();
        try {
            //建立连接
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //设置连接属性
            connection.setDoOutput(true); //使用URL连接进行输出
            connection.setDoInput(true); //使用URL连接进行输入
            connection.setUseCaches(false); //忽略缓存
            connection.setRequestMethod("POST"); //设置URL请求方法

            //设置请求属性
            byte[] requestStringBytes = params.getBytes(); //获取数据字节数据
            connection.setRequestProperty("Content-length", "" + requestStringBytes.length);
//            connection.setRequestProperty("Content-Type", "application/octet-stream");
            connection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            connection.setRequestProperty("Charset", "UTF-8");

            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);

            //建立输出流,并写入数据
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestStringBytes);
            outputStream.close();

            //获取响应状态
            int responseCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) { //连接成功
                //当正确响应时处理数据
                StringBuffer buffer = new StringBuffer();
                String readLine;
                BufferedReader responseReader;
                //处理响应流
                responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((readLine = responseReader.readLine()) != null) {
                    buffer.append(readLine).append("\n");
                }
                responseReader.close();
                json = new JSONObject(buffer.toString());
            } else {
                json.put("status", "error");
                json.put("message", "Unknown!");
                json.put("url", requestUrl);
            }
        } catch (Exception e) {
            try {
                json.put("status", "error");
                json.put("message", e.getMessage());
                json.put("url", requestUrl);
            } catch (JSONException e1) {
            }
        }
        return json;
    }

    /**
     * Get服务请求
     *
     * @param requestUrl
     * @return
     */
    public static String sendGet(String requestUrl) {
        try {
            //建立连接
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.setDoInput(true);

            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);

            connection.connect();

            //获取响应状态
            int responseCode = connection.getResponseCode();

            if (HttpURLConnection.HTTP_OK == responseCode) { //连接成功
                //当正确响应时处理数据
                StringBuffer buffer = new StringBuffer();
                String readLine;
                BufferedReader responseReader;
                //处理响应流
                responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((readLine = responseReader.readLine()) != null) {
                    buffer.append(readLine).append("\n");
                }
                responseReader.close();
                //JSONObject result = new JSONObject(buffer.toString());
                return buffer.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String downloadImage(String urlString, String toDir) {
        String name = urlString.replaceAll("[^(A-Za-z.)]", "");
        return downloadImage(urlString, toDir, name);
    }

    /**
     * 下载图片
     *
     * @param urlString
     * @param toDir
     * @return
     */
    public static String downloadImage(String urlString, String toDir, String imageName) {
        File file = new File(toDir, imageName);
        if (checkImage(file.getAbsolutePath())) {
            Log.d("library", "Http.downloadImage : 从缓存中获取图片");
            return file.getAbsolutePath();
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);  // 注意要设置超时，设置时间不要超过10秒，避免被android系统回收
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                FileOutputStream outStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                outStream.close();
                is.close();

                if (checkImage(file.getAbsolutePath())) {
                    Log.d("library", "Http.downloadImage : 从网络下载图片");
                    return file.getAbsolutePath();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    private static boolean checkImage(String path) {
        if (new File(path).exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            if (options.outWidth > 0 && options.outHeight > 0) {
                return true;
            }
        }
        return false;
    }


    private static final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
    private static final String PREFIX = "--";
    private static final String LINE_END = "\r\n";
    private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型

    private String uploadFile(File file, String fileKey, String uploadUrl, Map<String, String> param) {
        String result;
        try {
            URL url = new URL(uploadUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", "utf-8"); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            StringBuffer sb;
            String params;

            /***
             * 以下是用于上传参数
             */
            if (param != null && param.size() > 0) {
                Iterator<String> it = param.keySet().iterator();
                while (it.hasNext()) {
                    sb = null;
                    sb = new StringBuffer();
                    String key = it.next();
                    String value = param.get(key);
                    sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
                    sb.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(LINE_END).append(LINE_END);
                    sb.append(value).append(LINE_END);
                    params = sb.toString();
                    Log.d("LogHttp:", key + "=" + params + "##");
                    dos.write(params.getBytes());
                    // dos.flush();
                }
            }

            sb = null;
            params = null;
            sb = new StringBuffer();
            /**
             * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
             * filename是文件的名字，包含后缀名的 比如:abc.png
             */
            sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
            sb.append("Content-Disposition:form-data; name=\"" + fileKey + "\"; filename=\"" + file.getName() + "\"" + LINE_END);
            sb.append("Content-Type:image/pjpeg" + LINE_END); // 这里配置的Content-type很重要的 ，用于服务器端辨别文件的类型的
            sb.append(LINE_END);
            params = sb.toString();
            sb = null;

            Log.d("LogHttp:", file.getName() + "=" + params + "##");
            dos.write(params.getBytes());
            /**上传文件*/
            InputStream is = new FileInputStream(file);
//            if (onUploadProcessListener != null) {
//                onUploadProcessListener.initUpload((int) file.length());
//            }
            byte[] bytes = new byte[1024];
            int len = 0;
            int curLen = 0;
            while ((len = is.read(bytes)) != -1) {
                curLen += len;
                dos.write(bytes, 0, len);
//                if (onUploadProcessListener != null) {
//                    onUploadProcessListener.onUploadProcess(curLen);
//                }
            }
            is.close();

            dos.write(LINE_END.getBytes());
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
            dos.write(end_data);
            dos.flush();
            //
            // dos.write(tempOutputStream.toByteArray());
            /**
             * 获取响应码 200=成功 当响应成功，获取响应的流
             */
            int res = conn.getResponseCode();
//            UploadUtil.requestTime = (int) ((responseTime - requestTime) / 1000);
            if (res == 200) {
                Log.d("LogHttp:", "request success");
                InputStream input = conn.getInputStream();
                StringBuffer sb1 = new StringBuffer();
                int ss;
                while ((ss = input.read()) != -1) {
                    sb1.append((char) ss);
                }
                result = sb1.toString();
                Log.d("LogHttp:",  result);
                return result;
            } else {
                Log.d("LogHttp:",  "上传失败：code=" + res);
                return "上传失败：code=" + res;
            }
        } catch (MalformedURLException e) {
            Log.d("LogHttp:", "上传失败：error=" + e.getMessage());
            e.printStackTrace();
            return "上传失败：error=" + e.getMessage();
        } catch (IOException e) {
            Log.d("LogHttp:", "上传失败：error=" + e.getMessage());
            e.printStackTrace();
            return "上传失败：error=" + e.getMessage();
        }
    }

}
