package com.gizwits.boot.gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
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
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                return super.processTypeConvert(fieldType);
            }
        });
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("charge@2017");
        dsc.setUrl("jdbc:mysql://116.62.4.120:3306/gizwits_lease?characterEncoding=utf8");

//        String modelOutputStr = "/Users/joey/java/gizwits-boot/src/main/java";
//        MpGeneratorUtil.createModel(dsc,modelOutputStr,"rongmc","com.gizwits.boot","sys",null,
//                new  String[]{"sys_user","sys_role","sys_permission","sys_role_to_permission","sys_user_to_role","sys_user_ext","sys_config"},null);
//
//        String daoOutputStr = "/Users/joey/java/gizwits-boot/src/main/java";
//        MpGeneratorUtil.createDao(dsc,daoOutputStr,"rongmc","com.gizwits.boot","sys",null,
//                new  String[]{"sys_user","sys_role","sys_permission","sys_role_to_permission","sys_user_to_role","sys_user_ext","sys_config"},null);
//
//        String serviceOutputStr = "/Users/joey/java/gizwits-boot/src/main/java";
//        MpGeneratorUtil.createService(dsc,serviceOutputStr,"rongmc","com.gizwits.boot","sys",null,
//                new  String[]{"sys_user","sys_role","sys_permission","sys_role_to_permission","sys_user_to_role","sys_user_ext","sys_config"},null);

        String controllerOutputStr = "/Users/joey/java/gizwits-boot/src/main/java";
        MpGeneratorUtil.createController(dsc,controllerOutputStr,"rongmc","com.gizwits.boot","sys",null,
                 new  String[]{"sys_user","sys_role","sys_permission","sys_role_to_permission","sys_user_to_role","sys_user_ext","sys_config"},null);

//        String mapperDir = "/Users/joey/java/gizwits-boot/src/main/resources/mapper/" ;
//        MpGeneratorUtil.createMapperXml(dsc,mapperDir, "rongmc","com.gizwits.boot","sys",null,
//                new  String[]{"sys_user","sys_role","sys_permission","sys_role_to_permission","sys_user_to_role","sys_user_ext","sys_config"},null);

    }




}