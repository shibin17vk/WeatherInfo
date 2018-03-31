package com.testapp.problem2.utils;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * @author shibin
 * @version 1.0
 * @date 31/03/18
 */

public class Utils {

    public static synchronized AppException getException(int errorCode) {

        switch (errorCode) {

            case AppException.ERROR_ACCESS_DENIED:
                return new AppException(errorCode, "Access denied !");

                case AppException.ERROR_SERVICE_NOT_AVAILABLE:
                return new AppException(errorCode, "Service is not available !");

            default:
                return null;
        }
    }

    public static synchronized double getRoundedValue(double value) {
        DecimalFormat df=new DecimalFormat("0.00");
        String formate = df.format(value);
        try {
            return (Double)df.parse(formate) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
