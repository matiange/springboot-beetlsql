/**
 * <br>Created by 马天歌 on 2020/5/9.
 * <br>星期二 at 16:20.
 */
$('#table').bootstrapTable({
    url: 'productPageList',
    columns: [
        {
            checkbox: true,
            width: '3%'
        }, {
            formatter: function (value, row, index) {
                return index + 1;
            },
            width: '3%'
        }, {
            field: 'produceName',
            title: '产品名',
            sortable: true,
            width: '10%'
        }, {
            field: 'produceCode',
            title: '产品编码',
            sortable: true,
            width: '10%'
        }, {
            field: 'postName',
            title: '岗位名称',
            sortable: true,
            width: '10%'
        }, {
            field: 'postCode',
            title: '岗位编码',
            sortable: true,
            width: '10%'
        },{
            field: 'createTime',
            title: '创建时间',
            sortable: true,
            width: '15%'
        }
    ]
});
/**
 * 新增方法
 */
$('#add').click(function () {
    layer.open({
        title: [$('#add').val(), 'background: mediumpurple;font-size:20px'],
        maxmin: true,
        offset: '50px',
        area: '600px',
        content: $('#editLayer'),
        btn: ['保存', '关闭'],
        success: function () {
            $('#editForm')[0].reset();
            //hideOrShow('1');
            //默认为菜单
            //$('[name=type]')[1].checked = true;
            //menuTree();
        },
        //确认按钮触发
        yes: function () {
            if ($('#name').val() === "") {
                layer.msg("菜单名称不能为空");
            } else {
                $.ajax({
                    type: 'post',
                    data: $('#editForm').serialize(),
                    url: 'addMenu',
                    success: function () {
                        layer.closeAll();
                        layer.msg('增加菜单成功');
                        $('#table').bootstrapTable('refresh');
                    }
                });
            }
        },
        btn2: function () {
            layer.close();
        }
    });
});
/**
 * 修改方法
 */
$('#edit').click(function () {
    var select = $('#table').bootstrapTable('getSelections');//获得选择行对象
    if (select.length === 0) {
        $.alert({
            type: 'red',
            title: '提示信息',
            content: '请选择一项',
            icon: 'fa fa-warning'
        });
    } else {
        $.map(select, function (row) {
            layer.open({
                title: [$('#edit').val(), 'background: mediumpurple;font-size:20px'],
                maxmin: true,
                offset: '50px',
                area: '600px',
                content: $('#editLayer'),
                btn: ['保存', '关闭'],
                success: function () {
                    hideOrShowEdit(row.type.toString());
                    menuTree();
                },
                yes: function () {
                    if ($('#name').val() === "") {
                        layer.msg("菜单名称不能为空");
                    } else {
                        $.ajax({
                            type: 'post',
                            data: $('#editForm').serialize(),
                            url: 'editMenu/' + row.menuId,
                            success: function () {
                                layer.closeAll();
                                $('#table').bootstrapTable('refresh');
                            }
                        });
                    }
                    layer.closeAll();
                    $('#table').bootstrapTable('refresh');
                },
                btn2: function () {
                    layer.close();
                }
            });
            function hideOrShowEdit(show) {
                if ("0" === show) {
                    initText();
                    catalogShow();
                } else if ("1" === show) {
                    initText();
                    menuShow();
                } else if ("2" === show) {
                    initText();
                    buttonShow();
                }
            }

            function initText() {
                /** @namespace row.parentName */
                selectRow('editForm', row,['type']);
                var type = $('[name=type]');
                type[row.type].checked = true;
            }
        });
    }
});
/**
 * 树状菜单选择弹层
 */
$('#parentName').click(function () {
    layer.open({
        title: ["选择菜单", 'background: mediumpurple;font-size:16px'],
        area: ['300px', '450px'],
        content: $("#menuLayer"),
        btn: ['确定', '取消'],
        btn1: function (index) {
            var node = ztree.getSelectedNodes();
            $('#parentName').val(node[0].name);
            $('#parentId').val(node[0].menuId);
            layer.close(index);
        }
    });
});
/**
 * 删除方法
 */
$('#del').click(function () {
    var select = $('#table').bootstrapTable('getSelections');//获得选择行对象
    if (select.length === 0) {
        $.alert({
            type: 'red',
            title: '提示信息',
            content: '请选择一项',
            icon: 'fa fa-warning'
        });
    } else {
        $.map(select, function (row) {
            /** @namespace row.menuId 菜单ID */
            /** @namespace row.name 菜单名称 */
            $.confirm({
                type: 'red',
                typeAnimated: true,
                title: '确认信息',
                content: '确定要删除菜单' + row.name + '吗?',
                buttons: {
                    确定: {
                        text: '确定',
                        btnClass: 'btn-red',
                        action: function () {
                            $.ajax({//ajax提交
                                type: "POST",
                                url: "delMenu/" + row.menuId,
                                success: function (result) {
                                    if (result) {
                                        layer.msg("删除菜单成功");
                                        $('#table').bootstrapTable('refresh');
                                    } else {
                                        $.alert({
                                            type: 'red',
                                            title: '错误',
                                            content: '删除菜单失败',
                                            icon: 'fa fa-warning'
                                        });
                                    }
                                }
                            });
                        }
                    },
                    返回: {
                        text: '返回'
                    }
                }
            });
        })
    }
});
function radioDiv() {//根据radio显示或隐藏div
    var show;
    var type = $('[name=type]');
    for (var i = 0; i < type.length; i++) {
        if (type[i].checked) {
            show = type[i].value
        }
    }
    hideOrShows(show)
}
function hideOrShow(show) {
    if ("0" === show) {
        clearText();
        catalogShow();
    } else if ("1" === show) {
        clearText();
        menuShow();
    } else if ("2" === show) {
        clearText();
        buttonShow();
    }
}
function hideOrShows(show) {
    if ("0" === show) {
        catalogShow();
    } else if ("1" === show) {
        menuShow();
    } else if ("2" === show) {
        buttonShow();
    }
}
function catalogShow() {
    $('#menuUrl').css({display: "none"});
    $('#menuIndex').css({display: "none"});
    $('#menuIcon').css({display: "block"});
}
function menuShow() {
    $('#produceName').css({display: "block"});
    $('#parentNames').css({display: "block"});
    $('#menuUrl').css({display: "block"});
    $('#menuPerms').css({display: "block"});
    $('#menuIndex').css({display: "block"});
    $('#menuIcon').css({display: "block"});
}
function buttonShow() {
    $('#menuUrl').css({display: "none"});
    $('#menuIndex').css({display: "none"});
    $('#menuIcon').css({display: "none"});
}
function clearText() {
    $('#editForm')[0].reset();
}
/**
 * 树状图配置
 */
var ztree;
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "menuId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    }
};
function menuTree() {
    $.get("menuNoButtonTree", function (r) {
        /** @namespace r.menuList */
        ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuList);
    });
}