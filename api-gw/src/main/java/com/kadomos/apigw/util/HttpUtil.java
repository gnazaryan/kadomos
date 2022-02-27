package com.kadomos.apigw.util;

import com.kadomos.apigw.constants.Constants;
import com.kadomos.apigw.controller.GatewayController;
import com.kadomos.apigw.exception.KadomosException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUtil {

    private static final Logger logger = LogManager.getLogger(HttpUtil.class);

    /**
     * Make a remote http get request for the provided address and return remote response
     * @param address the location to make the request
     * @param parameters the parameters to send the request data
     * @return
     * @throws Exception
     */
    public static String httpGetRequest(String address, Map<String, String> parameters) throws Exception {
        HttpURLConnection con = null;
        try {
            if (Convenience.isNotNullOrEmptyMap(parameters)) {
                address = address + "?" + getParamsString(parameters);
            }
            URL url = new URL(address);
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            int status = con.getResponseCode();
            Reader streamReader = null;
            if (status > 299) {
                throw new Exception("received wrong response from the requested remote service: " + getResponseFromInputStream(new InputStreamReader(con.getInputStream())));
            } else {
                streamReader = new InputStreamReader(con.getInputStream());
            }
            return getResponseFromInputStream(streamReader);
        } catch (Exception e) {
            logger.error("Unable to fetch data from remote API with error: " + e.getMessage());
            throw new Exception(e);
        } finally {
            con.disconnect();
        }
    }

    public static String getResponseFromInputStream(Reader streamReader) throws IOException {
        StringBuffer result = new StringBuffer();
        BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            result.append(inputLine);
        }
        return result.toString();
    }

    /**
     * The method does build a =n http get url parameters from the map input
     * @param params the parameters to build the url
     * @return string representing url parameters
     * @throws UnsupportedEncodingException
     */
    public static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}
