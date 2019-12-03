package com.gizwits.lease.product.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.CommonEventPublisherUtils;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.QueryResolverUtils;
import com.gizwits.lease.common.perm.SysUserRoleTypeHelper;
import com.gizwits.lease.common.perm.SysUserRoleTypeHelperResolver;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.event.NameModifyEvent;
import com.gizwits.lease.model.DeleteDto;
import com.gizwits.lease.product.dao.ProductCategoryDao;
import com.gizwits.lease.product.dto.*;
import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.product.entity.ProductCategory;
import com.gizwits.lease.product.service.ProductCategoryService;
import com.gizwits.lease.product.service.ProductService;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 产品类型 服务实现类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryDao, ProductCategory> implements ProductCategoryService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ProductService productService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SysUserRoleTypeHelperResolver helperResolver;

    @Override
    public boolean add(ProductCategoryForAddDto dto) {
        SysUser current = sysUserService.getCurrentUserOwner();
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCtime(new Date());
        productCategory.setUtime(productCategory.getCtime());
        productCategory.setName(dto.getName());
        productCategory.setParentCategoryId((Integer) ObjectUtils.defaultIfNull(dto.getParentCategoryId(), 0));
        productCategory.setSysUserId(current.getId());
        productCategory.setSysUserName(current.getUsername());
        productCategory.setRemark(dto.getRemark());
        productCategory.setCategoryId(dto.getCategoryId());  //品类ID
        productCategory.setCategoryCount(dto.getCategoryCount());  //安全库存
        productCategory.setCategoryType(dto.getCategoryType());  //型号
        return insert(productCategory);
    }

    @Override
    public Page<ProductCategoryForListDto> page(Pageable<ProductCategoryForQueryDto> queryPage) {
        Page<ProductCategory> page = new Page<>();
        BeanUtils.copyProperties(queryPage, page);

        EntityWrapper<ProductCategory> wrapper = new EntityWrapper<>();
        //修改时间
        if (queryPage.getQuery().getUpTimeStart()!=null && queryPage.getQuery().getUpTimeEnd()!=null){
            wrapper.between("utime",queryPage.getQuery().getUpTimeStart(),queryPage.getQuery().getUpTimeEnd());
        }
        Page<ProductCategory> selectPage = selectPage(page, QueryResolverUtils.parse(queryPage.getQuery(),wrapper));
        Page<ProductCategoryForListDto> result = new Page<>();
        BeanUtils.copyProperties(selectPage, result);
        result.setRecords(new ArrayList<>(selectPage.getRecords().size()));
        selectPage.getRecords().forEach(item -> {
            ProductCategoryForListDto tmp = new ProductCategoryForListDto(item);
            tmp.setProductCount(deviceService.countLeaseDeviceByProductId(item.getId()));  //库存数量
            if (item.getCategoryId()!=null){
                tmp.setCategoryName(getProductByCategoryIdToName(item.getCategoryId()) == null? "":getProductByCategoryIdToName(item.getCategoryId()) );

            }
            result.getRecords().add(tmp);
        });
        return result;
    }

    @Override
    public ProductCategoryForDetailDto detail(Integer id) {
        ProductCategory productCategory = resolveProductCategory(id);
        if (Objects.nonNull(productCategory)) {
            ProductCategoryForDetailDto result = new ProductCategoryForDetailDto(productCategory);
            result.setProductCount(deviceService.countLeaseDeviceByProductId(productCategory.getId()));  //库存数量
            result.setCategoryName(getProductByCategoryIdToName(productCategory.getCategoryId()));
            return result;
        }
        return null;
    }

    private ProductCategory resolveProductCategory(Integer id) {
        Wrapper<ProductCategory> wrapper = new EntityWrapper<>();
        wrapper.in("sys_user_id", sysUserService.resolveSysUserAllSubIds(sysUserService.getCurrentUserOwner()));
        wrapper.eq("id", id);
        return selectOne(wrapper);
    }

    @Override
    public boolean update(ProductCategoryForUpdateDto dto) {
        ProductCategory productCategory = resolveProductCategory(dto.getId());
        if (Objects.nonNull(productCategory)) {
            String oldName = productCategory.getName();
            if (!ParamUtil.isNullOrEmptyOrZero(dto.getName())) {
                productCategory.setName(dto.getName());
                NameModifyEvent<Integer> nameModifyEvent = new NameModifyEvent<Integer>(productCategory, productCategory.getId(), oldName, dto.getName());
                CommonEventPublisherUtils.publishEvent(nameModifyEvent);
               productCategory.setCategoryType(dto.getCategoryType() == null ?productCategory.getCategoryType():dto.getCategoryType());
               productCategory.setCategoryId(dto.getCategoryId() > 0 ? dto.getCategoryId():productCategory.getCategoryId());
               productCategory.setCategoryCount(dto.getCategoryCount() < 0 ? dto.getCategoryCount():productCategory.getCategoryCount());
                return updateById(productCategory);
            }
        }
        return false;
    }

    @Override
    public List<ProductCategory> list(SysUser current) {
        return selectList(new EntityWrapper<ProductCategory>()
                .in("sys_user_id", sysUserService.resolveSysUserAllSubIds(sysUserService.getSysUserOwner(current)))
                .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }


    private Integer getProductByCategoryId(Integer id) {
        return productService.selectCount(new EntityWrapper<Product>().eq("category_id", id).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }

    private String getProductByCategoryIdToName(Integer id) {
        return productService.selectOne(new EntityWrapper<Product>().eq("id", id).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode())).getName();
    }


    @Override
    public List<ProductCategory> getProductsWithPermission() {
        //拥有的产品
        SysUser current = sysUserService.getCurrentUser();
        ProductQueryDto query = new ProductQueryDto();
        Integer manufacturerAccountId = resolveManufacturerAccount(current);
        if (Objects.nonNull(manufacturerAccountId)) {//若当前帐号是厂商体系下
            query.setManufacturerAccountId(manufacturerAccountId);
        } else { //当前帐号非厂商体系下，则走数据共享
            query.setAccessableUserIds(sysUserService.resolveOwnerAccessableUserIds(sysUserService.getSysUserOwner(current)));
        }
        query.setIsDeleted(DeleteStatus.NOT_DELETED.getCode());
        return selectList(QueryResolverUtils.parse(query, new EntityWrapper<ProductCategory>().orderBy("ctime", true)));
    }


    @Override
    public Integer resolveManufacturerAccount(SysUser current) {
        SysUserRoleTypeHelper helper = helperResolver.resolveManufacturer(current);
        if (Objects.nonNull(helper)) {
            return helper.getSysAccountId();
        }
        return null;
    }





    @Override
    public String deleted(List<Integer> ids) {
        SysUser current = sysUserService.getCurrentUserOwner();
        List<Integer> userIds = sysUserService.resolveSysUserAllSubAdminIds(current);
        List<String> fails = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        List<ProductCategory> productCategorys = selectList(new EntityWrapper<ProductCategory>().in("id", ids).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        if (!ParamUtil.isNullOrEmptyOrZero(productCategorys)) {
            for (ProductCategory productCategory : productCategorys) {
                if (userIds.contains(productCategory.getSysUserId())) {
                    if (deviceService.selectCount(new EntityWrapper<Device>().eq("product_id", productCategory.getId()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode())) <= 0) {
                        productCategory.setUtime(new Date());
                        productCategory.setIsDeleted(DeleteStatus.DELETED.getCode());
                        updateById(productCategory);
                    } else {
                        fails.add(productCategory.getName());
                    }
                } else {
                    fails.add(productCategory.getName());
                }
            }
        }


      switch (fails.size()) {
        case 0:
            sb.append("删除成功");
            break;
        case 1:
            sb.append("您欲删除的产品[" + fails.get(0) + "]已存在设备，请先删除产品下的所有设备。");
            break;
        case 2:
            sb.append("您欲删除的产品[" + fails.get(0) + "],[" + fails.get(1) + "]已存在设备，请先删除产品下的所有设备。");
            break;
        default:
            sb.append("您欲删除的产品[" + fails.get(0) + "],[" + fails.get(1) + "]等已存在设备，请先删除产品下的所有设备。");
            break;
    }
        return sb.toString();
}


    @Override
    public ProductCategory selectById(Integer id) {
        return selectOne(new EntityWrapper<ProductCategory>().eq("id", id).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }


    @Override
    public PreProductDto getAddProductPageData() {
        PreProductDto preProductDto = new PreProductDto();
        //类别列表
        preProductDto.setProductCategories(resolveProductCategories());
        //厂商账号
        preProductDto.setManufacturers(resolveManufacturerUsers());
        return preProductDto;
    }

    private List<ProductCategoryForPullDto> resolveProductCategories() {
        List<ProductCategory> productCategories = list(sysUserService.getCurrentUser());
        return productCategories.stream().map(ProductCategoryForPullDto::new).collect(Collectors.toList());
    }

    private List<ManufacturerUserDto> resolveManufacturerUsers() {
        List<SysUser> users = new LinkedList<>();

        users.addAll(sysUserService.getUsersByRoleId(2));

        return users.stream().map(ManufacturerUserDto::new).collect(Collectors.toList());
    }



}
