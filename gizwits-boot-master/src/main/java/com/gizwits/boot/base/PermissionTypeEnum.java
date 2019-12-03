package com.gizwits.boot.base;

/**
 * Created by Chloe on 2017/7/5.
 */
public enum PermissionTypeEnum {

    /****************************/
    /***** 5XXX 权限类型提示  *****/
    /****************************/
    ALL(0,"全部权限"),
    // LEFT_MENU(1,"左侧菜单"),
    // HEAD_MENU(2,"头部菜单"),
    // TABLE_MENU(3,"表格菜单"),
    // DATA_PERMISSION_TYPE(4,"数据权限类型");
    NAVIGATION_MENU(1,"导航菜单"),
    FUNCTION_MODULE(2,"功能模块");

    private Integer key;
    private String value;

    private PermissionTypeEnum(Integer key,String  value){
        this.key = key;
        this.value = value;
    }

    public static PermissionTypeEnum  get(Integer key){
        if(key == null){
            return null;
        }else {
            PermissionTypeEnum[] arr$ = values();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                PermissionTypeEnum appResultKeyEnum = arr$[i$];
                if(appResultKeyEnum.getKey().equals(key)) {
                    return appResultKeyEnum;
                }
            }

            return null;
        }
    }

    public static boolean is(PermissionTypeEnum appResultKeyEnum, Integer key) {
        return appResultKeyEnum != null && key != null?appResultKeyEnum.getKey().equals(key):false;
    }

    public Integer getKey() {return key;}

    public String getValue() {return value;}

}
