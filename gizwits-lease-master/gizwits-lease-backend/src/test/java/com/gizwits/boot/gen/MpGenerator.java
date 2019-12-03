package com.gizwits.boot.gen;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.gizwits.boot.utils.MpGeneratorUtil;

/**
 * <p>
 * 代码生成器演示
 * </p>
 */
public class MpGenerator {

    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert(){
            //自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                return super.processTypeConvert(fieldType);
            }
        });
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("lease");
        dsc.setPassword("^gizwits-lease-admin$");
        dsc.setUrl("jdbc:mysql://119.29.216.25:3306/weibo?characterEncoding=utf8&useSSL=true");


       createBean(dsc);

        String modelOutputStr="/Users/yehongwei/gizwits/gizwits-lease/gizwits-lease-model/src/main/java";
        MpGeneratorUtil.createModel(dsc,modelOutputStr,"yinhui","com.gizwits.lease","benefit",null,
                new String[]{"share_profit_summary","share_profit_summary_detail"},null);


        String daoOutputStr = "/Users/yehongwei/gizwits/gizwits-lease/gizwits-lease-dao/src/main/java";
        MpGeneratorUtil.createDao(dsc,daoOutputStr,"yinhui","com.gizwits.lease","benefit",null,
                new String[]{"share_profit_summary","share_profit_summary_detail"},null);


        String serviceOutputStr = "/Users/yehongwei/gizwits/gizwits-lease/gizwits-lease-service/src/main/java";
        MpGeneratorUtil.createService(dsc,serviceOutputStr,"yinhui","com.gizwits.lease","benefit",null,
                new String[]{"share_profit_summary","share_profit_summary_detail"},null);

        String controllerOutputStr = "/Users/yehongwei/gizwits/gizwits-lease/gizwits-lease-backend/src/main/java";
        MpGeneratorUtil.createController(dsc,controllerOutputStr,"yinhui","com.gizwits.lease","benefit",null,
                new String[]{"share_profit_summary","share_profit_summary_detail"},null);


       String mapperDir = "/Users/yehongwei/gizwits/gizwits-lease/gizwits-lease-dao/src/main/resources/mapper/";
        MpGeneratorUtil.createMapperXml(dsc,mapperDir, "yinhui","com.gizwits.lease","benefit",null,
                new String[]{"share_profit_summary","share_profit_summary_detail"},null);
    }


    private static void createBean(DataSourceConfig dsc) {
        String modelOutputStr="G:/code/java/gizwits-lease-aep/gizwits-lease-model/src/main/java";
        MpGeneratorUtil.createModel(dsc,modelOutputStr,"Joke","com.gizwits.lease","order",null,
                new String[]{"order_data_flow"},null);


        String daoOutputStr = "G:/code/java/gizwits-lease-aep/gizwits-lease-dao/src/main/java";
        MpGeneratorUtil.createDao(dsc,daoOutputStr,"Joke","com.gizwits.lease","order",null,
                new String[]{"order_data_flow"},null);


        String serviceOutputStr = "G:/code/java/gizwits-lease-aep/gizwits-lease-service/src/main/java";
        MpGeneratorUtil.createService(dsc,serviceOutputStr,"Joke","com.gizwits.lease","order",null,
                new String[]{"order_data_flow"},null);

        String controllerOutputStr = "G:/code/java/gizwits-lease-aep/gizwits-lease-backend/src/main/java";
        MpGeneratorUtil.createController(dsc,controllerOutputStr,"Joke","com.gizwits.lease","order",null,
                new String[]{"order_data_flow"},null);

        String mapperOutputDir = "G:/code/java/gizwits-lease-aep/gizwits-lease-dao/src/main/resources/mapper/";
        MpGeneratorUtil.createMapperXml(dsc,mapperOutputDir, "Joke","com.gizwits.lease","order",null,
                new String[]{"order_data_flow"},null);
    }

}