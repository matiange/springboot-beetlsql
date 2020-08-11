findByName
===
    * 根据用户名查询用户信息
    select * from sys_product_management where 1=1
    @if(!isEmpty(username)){
        and username = #username#
    @}
findById
===
    * 根据用户Id查询
    select * from sys_product_management where 1=1
    @if(!isEmpty(userId)){
        and user_id = #userId#
    @}
productPageList
===
    * 用户信息分页列表
    select * from sys_product_management where 1=1
    #use("search")#
    #use("sort")#
    ,produce_sid desc
    #use("limit")#
userPermsList
===
    * 如果是超级管理员 查找菜单表里全部权限com.matiange.utils.Constant.SUPER_ADMIN
   	SELECT m.perms 
   	@if(userId==@com.matiange.utils.Constant.SUPER_ADMIN){
   	    FROM sys_menu m where 1=1
    @}else{
        FROM sys_user_role ur
        #use("joinRoleMenu")#
        #use("joinMenu")#
        WHERE ur.user_id = #userId#
    @}
userMenuIdList
===
    * 用户拥有的菜单id集合
    select distinct rm.menu_id 
    from sys_user_role ur 			
    #use("joinRoleMenu")#
    where ur.user_id = #userId#

ajaxExistUser
===
    * ajax获取用户名
    select su.username  
    from sys_product_management su 
    WHERE su.username=#usernameAjax#
updatePassword
===
    * 修改密码
    update sys_product_management set 
    `password` = #newPassword#
    where user_id = #userId# 
    and password = #oldPassword#
joinMenu
===
    LEFT JOIN sys_menu m ON rm.menu_id = m.menu_id
joinRoleMenu
===
    LEFT JOIN sys_role_menu rm ON ur.role_id = rm.role_id
allCount
===
    select count(1) from
    sys_product_management where 1=1
    #use("search")#        
search
===
    *搜索内容
    @if(!isEmpty(params.search)){
        and PRODUCE_NAME like #'%'+params.search+'%'#
        or MAKER_NAME like #'%'+params.search+'%'#
        or MAKER_PHONE like #'%'+params.search+'%'#
        or PRODUCE_CODE like #'%'+params.search+'%'#
        or POST_CODE like #'%'+params.search+'%'#
        or POST_NAME like #'%'+params.search+'%'#
    @} 
sort
===
    @if(params.sort=='produceName'){
        order by produce_name
        #use("order")#
    @}else if(params.sort=='produceCode'){
        order by produce_code
        #use("order")#
    @}else if(params.sort=='postName'){
        order by post_name
        #use("order")#
    @}else if(params.sort=='postCode'){
        order by post_code
        #use("order")#
    @}else{
        order by create_time
        #use("order")#
    @}
order
===
    @if(params.order=='asc'){
        asc
    @}else{
        desc
    @}           
limit
===
    *分页
    @if(!isEmpty(params.offset)){
        limit #params.offset#   
    @}
    @if(!isEmpty(params.limit)){
        ,#params.limit#
    @}    
    
sysUserRole
===
    select sur.role_id from sys_user_role sur
    where sur.user_id=#userId#
    