package com.gizwits.lease.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 * Created by Sunny on 2019/12/24 10:36
 */
public class PlanVerificationUtil {

    private static final List<String> WEEKS=new ArrayList<>(Arrays.asList("sat","fri","thu","wed","tue","mon","sun"));

    public static Boolean repeatVerification(String repeat){
         String[] array=repeat.split(",");

         return true;
    }

}
